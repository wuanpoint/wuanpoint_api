package org.wuancake.service.oidc;


import com.alibaba.fastjson.JSONArray;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 连接OIDC项目的工具类
 * oidc项目的负责人gt说client_id属性由前端发给后端，oidc对这个属性不做检查，值是什么无所谓。
 * IC系列的接口貌似是午安系PHP项目专属的内部接口，java访问会有偏差。
 */
@Service
@Log4j
public class LinkOIDC {
    @Value("${WUAN_POINT.OIDC_DOMAIN_NAME}")
    private String domainName;
    @Value("${WUAN_POINT.APP_NAME}")
    private String app;
    @Value("${WUAN_POINT.SCOPE}")
    private String scope;
    @Value("${WUAN_POINT.SECRET}")
    private String secret;
    @Value("${WUAN_POINT.SUB_APP}")
    private Integer sub_app;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * OIDC授权接口(A2)，成功返回Access-Token，失败返回错误信息
     *
     * @param idToken
     * @return
     * @throws NullPointerException
     */
    public HttpResult auth(String idToken) throws NullPointerException, IOException, URISyntaxException {
        if (idToken == null)
            throw new NullPointerException("参数不能为空");
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("scope", scope));
        List<BasicHeader> basicHeaders = new ArrayList<>();
        basicHeaders.add(new BasicHeader("ID-Token", idToken));
        return this.sendHttpRequest(formParams, basicHeaders, domainName + "/api/auth/", HttpRequestType.POST);
    }

    /**
     * OIDC登录接口(U1)，成功返回idToken，失败返回错误信息。
     *
     * @param email
     * @param password
     * @param client_id
     * @return
     * @throws NullPointerException
     */
    public HttpResult login(String email, String password, String client_id) throws NullPointerException, IOException, URISyntaxException {
        if (email == null || password == null || client_id == null)
            throw new NullPointerException("参数不能为空");
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("email", email));
        formParams.add(new BasicNameValuePair("password", password));
        formParams.add(new BasicNameValuePair("client_id", client_id));
        return this.sendHttpRequest(formParams, null, domainName + "/api/users/login", HttpRequestType.POST);
    }

    /**
     * OIDC注册接口(U2)，成功返回idToken，失败返回错误信息。
     *
     * @param name
     * @param email
     * @param password
     * @param client_id
     * @return
     * @throws NullPointerException
     */
    public HttpResult register(String name, String email, String password, String client_id) throws NullPointerException, IOException, URISyntaxException {
        if (name == null || email == null || password == null || client_id == null)
            throw new NullPointerException("参数不能为空");
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("name", name));
        formParams.add(new BasicNameValuePair("email", email));
        formParams.add(new BasicNameValuePair("password", password));
        formParams.add(new BasicNameValuePair("client_id", client_id));
        return this.sendHttpRequest(formParams, null, domainName + "/api/users/register", HttpRequestType.POST);
    }

    /**
     * 获取用户信息接口(U3)
     *
     * @param idToken
     * @return
     */
    public HttpResult getUserMsg_U3(String idToken, String accessToken) throws Exception {
        if (idToken == null || accessToken == null)
            throw new NullPointerException("参数不能为空");
        Claims claims = jwtUtil.parseJWT(idToken);
        Integer id = (Integer) claims.get("uid");
        List<BasicHeader> basicHeaders = new ArrayList<>();
        basicHeaders.add(new BasicHeader("ID-Token", idToken));
        basicHeaders.add(new BasicHeader("Access-Token", accessToken));
        return this.sendHttpRequest(null, basicHeaders, domainName + "/api/users/" + id, HttpRequestType.GET);
    }

    /**
     * OIDC登出接口(U4),不需要任何参数
     *
     * @return
     * @throws IOException
     */
    public HttpResult logout() throws IOException, URISyntaxException {
        return this.sendHttpRequest(null, null, domainName + "/api/users/logout", HttpRequestType.POST);
    }

    /**
     * 修改用户信息(U5),支持修改头像、昵称、性别、生日
     *
     * @param user
     * @param idToken
     * @param accessToken
     * @return
     */
    public HttpResult updateUserMsg(User user, String idToken, String accessToken) throws Exception {
        if (user == null || idToken == null || accessToken == null)
            throw new NullPointerException("参数不能为空");
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("name", user.getName()));
        formParams.add(new BasicNameValuePair("avatar_url", user.getAvatar_url()));
        formParams.add(new BasicNameValuePair("sex", user.getSex()));
        formParams.add(new BasicNameValuePair("birthday", new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday())));
        List<BasicHeader> basicHeaders = new ArrayList<>();
        basicHeaders.add(new BasicHeader("ID-Token", idToken));
        basicHeaders.add(new BasicHeader("Access-Token", accessToken));
        Claims claims = jwtUtil.parseJWT(idToken);
        Integer id = (Integer) claims.get("uid");
        return this.sendHttpRequest(formParams, basicHeaders, domainName + "/api/users/" + id, HttpRequestType.PUT);
    }

    /**
     * 获取午安积分(Q2)
     *
     * @param idToken
     * @param accessToken
     * @return
     * @throws Exception
     */
    public HttpResult getWuanPoint(String idToken, String accessToken) throws Exception {
        if (idToken == null || accessToken == null)
            throw new NullPointerException("参数不能为空");
        List<BasicHeader> basicHeaders = new ArrayList<>();
        basicHeaders.add(new BasicHeader("ID-Token", idToken));
        basicHeaders.add(new BasicHeader("Access-Token", accessToken));
        Claims claims = jwtUtil.parseJWT(idToken);
        Integer id = (Integer) claims.get("uid");
        return this.sendHttpRequest(null, basicHeaders, domainName + "/api/users/" + id + "/wuan_points", HttpRequestType.GET);
    }

    /**
     * 兑换午安积分(Q3)
     *
     * @param idToken
     * @param accessToken
     * @return
     * @throws Exception
     */
    public HttpResult exchangeWuanPoint(String idToken, String accessToken, Integer sub_points) throws Exception {
        if (idToken == null || accessToken == null || sub_points == null)
            throw new NullPointerException("参数不能为空");
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("sub_points", sub_points.toString()));
        formParams.add(new BasicNameValuePair("sub_points", sub_app.toString()));
        List<BasicHeader> basicHeaders = new ArrayList<>();
        basicHeaders.add(new BasicHeader("ID-Token", idToken));
        basicHeaders.add(new BasicHeader("Access-Token", accessToken));
        Claims claims = jwtUtil.parseJWT(idToken);
        Integer id = (Integer) claims.get("uid");
        return this.sendHttpRequest(formParams, basicHeaders, domainName + "/api/users/" + id + "/points", HttpRequestType.PUT);
    }

    /**
     * 请求用户信息接口(IC1)，不推荐用
     *
     * @param id
     * @return
     */
    public HttpResult getUserMsg_IC1(Integer id) throws IOException, URISyntaxException {
        if (id == null || id <= 0)
            throw new NullPointerException("参数不能为空,id不能小于等于0");
        List<NameValuePair> formParams = this.getParams();
        return this.sendHttpRequest(formParams, null, domainName + "/api/app/users/" + id, HttpRequestType.GET);
    }

    /**
     * 获取午安积分接口(IC2),不推荐使用
     *
     * @param id
     * @param idToken
     * @param accessToken
     * @return
     * @throws NullPointerException
     * @throws IOException
     */
    public HttpResult getPoint(Integer id, String idToken, String accessToken) throws NullPointerException, IOException, URISyntaxException {
        if (id == null || id <= 0 || idToken == null || accessToken == null)
            throw new NullPointerException("参数不能为空,id不能小于等于0");
        List<NameValuePair> formParams = this.getParams();
        List<BasicHeader> basicHeaders = new ArrayList<>();
        basicHeaders.add(new BasicHeader("ID-Token", idToken));
        basicHeaders.add(new BasicHeader("Access-Token", accessToken));
        return this.sendHttpRequest(formParams, basicHeaders, domainName + "/api/app/users/" + id + "/point", HttpRequestType.GET);
    }

    /**
     * 兑换午安积分接口(IC3)，不推荐使用
     *
     * @param id
     * @param idToken
     * @param accessToken
     * @return
     * @throws NullPointerException
     * @throws IOException
     * @throws URISyntaxException
     */
    public HttpResult putPoint(Integer id, String idToken, String accessToken) throws NullPointerException, IOException, URISyntaxException {
        if (id == null || id <= 0 || idToken == null || accessToken == null)
            throw new NullPointerException("参数不能为空,id不能小于等于0");
        List<NameValuePair> formParams = this.getParams();
        List<BasicHeader> basicHeaders = new ArrayList<>();
        basicHeaders.add(new BasicHeader("ID-Token", idToken));
        basicHeaders.add(new BasicHeader("Access-Token", accessToken));
        return this.sendHttpRequest(formParams, basicHeaders, domainName + "/api/app/users/" + id + "/point", HttpRequestType.PUT);
    }

    /**
     * 通过email获得用户id(IC4)，不推荐使用
     *
     * @param email
     * @param idToken
     * @param accessToken
     * @return
     * @throws NullPointerException
     * @throws IOException
     * @throws URISyntaxException
     */
    public HttpResult getIdByEmail(String email, String idToken, String accessToken) throws NullPointerException, IOException, URISyntaxException {
        if (email == null || idToken == null || accessToken == null)
            throw new NullPointerException("参数不能为空");
        List<NameValuePair> formParams = this.getParams();
        return this.sendHttpRequest(formParams, null, domainName + "/app/users/email/" + email, HttpRequestType.GET);
    }

    /**
     * 获取OIDC内部请求用的参数
     *
     * @return
     */
    private List<NameValuePair> getParams() {
        Map<String, Object> buffer = new HashMap<>();
        buffer.put("app", app);
        buffer.put("iat", System.currentTimeMillis());
        buffer.put("exp", System.currentTimeMillis() + 60);

        String info = JSONArray.toJSONString(buffer);
        String key = BCrypt.hashpw(info + secret, BCrypt.gensalt());

        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("app", app));
        formParams.add(new BasicNameValuePair("info", info));
        formParams.add(new BasicNameValuePair("key", key));

        return formParams;
    }

    /**
     * 发起Http请求，由于OIDC项目只有post、get、put三种请求方式，所以这里不考虑其他的请求方式。
     * 注意get请求添加表单参数直接在url里写就好。
     *
     * @param formParams      http请求的表单参数
     * @param headers         http请求的header参数
     * @param url             要请求的url
     * @param httpRequestType http请求方法
     * @return
     * @throws IOException
     */
    private HttpResult sendHttpRequest(List<NameValuePair> formParams, List<BasicHeader> headers, String url, HttpRequestType httpRequestType) throws IOException, URISyntaxException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setConnectionRequestTimeout(5000)
                .build();
        HttpClient client = new DefaultHttpClient();

        HttpRequestBase httpRequestBase = null;
        if (httpRequestType.equals(HttpRequestType.POST)) {
            //先用post设置表单的值，
            HttpPost httpPost = new HttpPost(url);
            if (formParams != null) {
                HttpEntity reqEntity = new UrlEncodedFormEntity(formParams, "utf-8");
                httpPost.setEntity(reqEntity);
            }
            httpRequestBase = httpPost;
        } else if (httpRequestType.equals(HttpRequestType.PUT)) {
            //先用put设置表单的值，
            HttpPut httpPut = new HttpPut(url);
            if (formParams != null) {
                HttpEntity reqEntity = new UrlEncodedFormEntity(formParams, "utf-8");
                httpPut.setEntity(reqEntity);
            }
            httpRequestBase = httpPut;
        } else if (httpRequestType.equals(HttpRequestType.GET)) {
            //get设置表单的值
            URIBuilder uriBuilder = new URIBuilder(url);
            if (formParams != null)
                uriBuilder.setParameters(formParams);
            httpRequestBase = new HttpGet(uriBuilder.build());
        }

        httpRequestBase.setConfig(requestConfig);
        //添加header
        if (headers != null) {
            for (BasicHeader httpHead : headers)
                httpRequestBase.setHeader(httpHead);
        }
        HttpResponse response = client.execute(httpRequestBase);

        HttpEntity resEntity = response.getEntity();
        HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(resEntity, "utf-8"));
        log.info(httpResult.toString());
        return httpResult;
    }
}
