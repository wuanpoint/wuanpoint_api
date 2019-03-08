package org.wuancake.controller.movies;

import com.alibaba.fastjson.JSONArray;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.wuancake.entity.*;
import org.wuancake.response.data.MovieDetailsData;
import org.wuancake.service.IMoviesService;
import org.wuancake.service.ResourcesService;
import org.wuancake.service.oidc.JwtUtil;

import org.wuancake.response.CoolResponseUtils;
import org.wuancake.response.data.ResourceVO;
import org.wuancake.response.data.ResultBody;
import org.wuancake.response.data.SearchVO;
import org.wuancake.service.IMoviesService;

import org.wuancake.service.oidc.LinkOIDC;

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
    public String getDetailsByType(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                   String type) {
        String count = moviesService.countMovies();

        if ("".equals(type) || null == type) {//TODO
            return "{\"movies\":" + moviesService.getDetails(offset, limit) + ",\"total\":\"" + count + "\"}";
        }
        return "{\"movies\":" + moviesService.getDetailsByType(offset, limit, type) + ",\"total\":\"" + count + "\"}";
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
        return CoolResponseUtils.ok(new SearchVO(moviesService.getDetailsByKey(q, offset, limit)), "200", "响应成功");
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
    public ResultBody movieDetails(@PathVariable("id") final Integer id) {
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
        return CoolResponseUtils.ok(movieDetailsData, "响应成功", "200");
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

    /**
     * 增加资源(R1)
     *
     * @param request
     * @param id
     * @param type
     * @param title
     * @param url
     * @param password
     * @param instruction
     * @return
     */
    @RequestMapping(value = "/api/movies/{id}/resources", method = RequestMethod.POST)
    public String addReousrces(HttpServletRequest request, @PathVariable Integer id, String type, String title, String url, String password, String instruction) {
        String accessToken = request.getHeader("Access-Token");
        String idToken = request.getHeader("ID-Token");
        if (null == accessToken || null == idToken) {
            return null;
        }
        try {
            Claims claims = jwtUtil.parseJWT(idToken);
            Integer uid = (Integer) claims.get("uid");
            Resources resources = new Resources(id, type, title, instruction, url, password, uid, "", null);
            resources.setCreatedAt(new Date());
            resourcesService.addResources(resources);

            return resources.toString();
        } catch (Exception e) {
            return "{\"error\":\"添加资源失败\"}";
        }
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
