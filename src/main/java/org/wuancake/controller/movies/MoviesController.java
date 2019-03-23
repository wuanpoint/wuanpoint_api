package org.wuancake.controller.movies;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.wuancake.entity.*;
import org.wuancake.request.R1RequestBody;
import org.wuancake.response.data.*;
import org.wuancake.service.IMoviesService;
import org.wuancake.service.ResourcesService;
import org.wuancake.service.oidc.HttpResult;
import org.wuancake.service.oidc.JwtUtil;

import org.wuancake.response.CoolResponseUtils;

import org.wuancake.service.oidc.LinkOIDC;
import org.wuancake.service.oidc.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * Created by ericheel on 2019/2/18.
 * 影视Controller
 */
@RestController
@Log4j
public class MoviesController {
    @Autowired
    private IMoviesService moviesService;
    @Autowired
    private LinkOIDC linkOIDC;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ResourcesService resourcesService;

    /**
     * 首页/影片分类页（A1）
     * 根据类型获取影片的一些详情
     *
     * @param offset 起始页数 可以为空
     * @param limit  每页显示条数 可以为空
     * @param type   影片类型（午安点评内部的类型） 可以为空
     * @return 影片详细信息
     */
    @RequestMapping(value = "/api/movies", method = RequestMethod.GET)
    public A1Response getDetailsByType(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                       @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                       String type) {
        String count = moviesService.countMovies();

        if ("".equals(type) || null == type) {//TODO
            return new A1Response(moviesService.getDetails(offset, limit), count);
        }
        return new A1Response(moviesService.getDetailsByType(offset, limit, type), count);
    }


    /**
     * (A2)废除
     * 首页/搜索影视页（A3）
     * 根据标题模糊查询影片的一些详情
     *
     * @param q      影片类型（午安点评内部的类型） 不能为空
     * @param offset 起始页数 可以为空
     * @param limit  每页显示条数 可以为空
     * @return 影片详细信息
     */
    @RequestMapping(value = "/api/movies/search", method = RequestMethod.POST)
    public ResultBody search(@RequestParam("q") String q,
                             @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                             @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<MoviesDetails> detailsByKey = moviesService.getDetailsByKey(q, offset, limit);
        if (detailsByKey != null && "".equals(detailsByKey)) {
            return CoolResponseUtils.ok(detailsByKey, "200", "响应成功");
        }
        return CoolResponseUtils.ok(null, "400", "获取搜索信息失败");
    }

    /**
     * 获取分类条目录(A4)
     *
     * @return
     */
    @GetMapping(value = "/api/movies/type")
    public List<MoviesGenresDetails> getMoviesType() {
        return moviesService.getMoviesType();
    }

    /**
     * 获取影视详情(Z1)
     *
     * @param id 影片id
     * @return blabla
     */
    @RequestMapping(value = "/api/movies/{id}", method = RequestMethod.GET)
    public Object movieDetails(@PathVariable("id") final Integer id) {
        MovieDetailsData movieDetailsData = null;
        if (null == id) {
            return CoolResponseUtils.ok(null, "获取影片信息失败", "400");
        } else {
            MoviesDetails moviesDetails = moviesService.getMovieDetailsById(id);

            if (null == moviesDetails) {
                //不存在这个影片
                return CoolResponseUtils.ok(null, "影片不存在", "404");
            }

            List<Directors> directorsList = moviesService.getDirectorsByMovieId(id);
            List<Actors> actorsList = moviesService.getActorsByMovieId(id);
            List<MoviesGenresDetails> moviesTypeDetailsList = moviesService.getMoviesGenresDetailsByMovieId(id);
            movieDetailsData = new MovieDetailsData(moviesDetails, moviesTypeDetailsList, directorsList, actorsList);
        }
        return movieDetailsData;
    }

    /**
     * 显示资源（评论)（Z2）
     * 电影详情类
     *
     * @param offset 起始页数 可以为空
     * @param limit  每页显示条数 可以为空
     * @param id     影视movieId
     * @return 资源列表
     */
    @RequestMapping(value = "/api/movies/{id}/resources", method = RequestMethod.GET)
    public List<ResourceVO> getResources(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                         @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                         @PathVariable Integer id) {
        return moviesService.getResourcesById(id, offset, limit);
    }

    /**
     * 发现影视（Z3）
     */
    @PostMapping(value = "/api/movies", params = {"url", "type"})
    public String findMovies(String url, String type) throws IOException, URISyntaxException {
        return moviesService.findMovies(url, type);
    }


    @RequestMapping(value = "/api/movies/{id}/resources", method = RequestMethod.POST)
    public Object addReousrces(HttpServletRequest request,
                               @PathVariable Integer id,
                               @RequestBody R1RequestBody r1Body) {
        String accessToken = request.getHeader("Access-Token");
        String idToken = request.getHeader("ID-Token");
        if (null == accessToken || null == idToken) {
            return null;
        }
        try {
            HttpResult userMsg_u3 = linkOIDC.getUserMsg_U3(idToken, accessToken);
            String resultMessage = userMsg_u3.getResultMessage();
            User user = (User) JSON.parse(resultMessage);
            String name = user.getName();
            Claims claims = jwtUtil.parseJWT(idToken);
            Integer uid = (Integer) claims.get("uid");//貌似R3是这样获取分享者id的
            Integer resourceTypeId = getResourceTypeIdByResourceTypeName(r1Body.getType());//沒理解接口文檔
            Resources resources = new Resources(id, resourceTypeId, r1Body.getTitle(), r1Body.getUrl(), r1Body.getInstruction(), r1Body.getPassword(), uid, name, null);
            Date date = new Date();
            resources.setCreatedAt(date);
            resourcesService.addResources(resources);

            return new Resources(resources.getResourceId(), r1Body.getTitle(), r1Body.getInstruction(), uid, name, r1Body.getUrl(), date, r1Body.getPassword(), r1Body.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"添加资源失败\"}";
        }
    }

    private Integer getResourceTypeIdByResourceTypeName(String type) {
        return resourcesService.getResourceTypeIdByResourceTypeName(type);
    }


    /**
     * 删除电影资源（R2）
     * (R3)在ResourcesController
     *
     * @param accessToken
     * @param idToken
     * @param movieId
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/api/movies/{movieId}/resources/{resourceId}", method = RequestMethod.DELETE)
    public ResultBody delResources(@RequestParam("Access-Token") String accessToken,
                                   @RequestParam("ID-Token") String idToken,
                                   @PathVariable Integer movieId,
                                   @PathVariable Integer resourceId) {
        try {
            if (linkOIDC.getUserMsg_U3(idToken, accessToken) != null) {
                moviesService.delResources(movieId, resourceId);
                return new ResultBody("204", "删除成功");
            }
            return new ResultBody("400", "删除失败,用户权限不足");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBody("400", "删除失败");
        }
    }

}
