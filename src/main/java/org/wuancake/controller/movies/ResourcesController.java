package org.wuancake.controller.movies;

import com.alibaba.fastjson.JSONArray;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wuancake.entity.Resources;
import org.wuancake.entity.ResourcesType;
import org.wuancake.service.ResourcesService;
import org.wuancake.service.oidc.JwtUtil;
import org.wuancake.service.oidc.LinkOIDC;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Log4j
/**
 * 资源相关的控制器
 */
public class ResourcesController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private LinkOIDC linkOIDC;
    @Autowired
    private ResourcesService resourcesService;

    /**
     * 编辑资源
     *
     * @param id
     * @param type
     * @param title
     * @param url
     * @param password
     * @param instruction
     * @param sharer
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/api/movies/{id}/resources/{id}", params = {"type", "title", "url", "password", "instruction", "sharer"})
    public String updateResources(@PathVariable int id, String type, String title, String url, String password, String instruction, String sharer, HttpServletRequest request) throws Exception {
        //装填参数，不要问我为啥要在方法里装填。。。这个接口文档参数命名真是一绝
        String accessToken = request.getHeader("Access-Token");
        String idToken = request.getHeader("ID-Token");
        if (accessToken == null || idToken == null)
            return null;
        Claims claims = jwtUtil.parseJWT(idToken);
        Resources resources = new Resources(id, null, title, instruction, url, password, (Integer) claims.get("uid"), sharer);

        //检查类型是否存在
        ServletContext app = request.getSession().getServletContext();
        List<ResourcesType> resourcesTypeList = null;
        if (app.getAttribute("resourcesTypeList") == null) {
            resourcesTypeList = resourcesService.getResourcesType();
            app.setAttribute("resourcesTypeList", resourcesTypeList);
        } else
            resourcesTypeList = (List<ResourcesType>) app.getAttribute("resourcesTypeList");
        for (ResourcesType resourcesType : resourcesTypeList) {
            if (type.equals(resourcesType.getTypeName())) {
                resources.setResourceType(resourcesType.getTypeId());
                resources.setType(resourcesType.getTypeName());
                break;
            }
        }
        if (resources.getResourceType() == null)
            return null;

        //修改资源
        if (resourcesService.updateResources(resources)){
            resources.setResourceType(null);
            return JSONArray.toJSONString(resources);
        }
        else
            return "{\"error\":\"添加资源失败\"}";
    }

    @GetMapping("/login")
    public String login() throws IOException, URISyntaxException {
        String emial = "asjdkasjdl@163.com";
        String password = "123456";
        return JSONArray.toJSONString(linkOIDC.login(emial, password, "222"));
    }

    @GetMapping("/auth")
    public String auth(String idToken) throws IOException, URISyntaxException {
        return JSONArray.toJSONString(linkOIDC.auth(idToken));
    }
}
