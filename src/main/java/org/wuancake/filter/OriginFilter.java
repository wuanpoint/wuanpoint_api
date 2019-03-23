package org.wuancake.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Ericheel
 * @Description: 跨域访问过滤
 * @date 2019-3-8 21:44:50
 */
@Component
public class OriginFilter implements Filter {

    @Value("${whiteList}")//跨域访问白名单
    private static List<String> whiteList;
    private boolean isValidOrigin = false;

    /**
     * false允许所有源,开发环境下false
     * TODO 这里记得修改
     */
    private boolean doFilte = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
//    	req.setCharacterEncoding("utf-8");
//    	res.setCharacterEncoding("utf-8");
        HttpServletResponse response = (HttpServletResponse) res;
//        if (null == whiteList || whiteList.size() == 0) {
//            response.setHeader("Access-Control-Allow-Origin", "null");
//        }
//        String origin = ((HttpServletRequest) req).getHeader("origin");
//        for (String ip : whiteList) {
//            if (null != ip && origin.equals(ip)) {
//                isValidOrigin = true;
//            }
//        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "content-type");
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }

}
