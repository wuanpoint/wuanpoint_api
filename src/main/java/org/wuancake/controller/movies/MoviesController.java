package org.wuancake.controller.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wuancake.entity.MoviesDetails;
import org.wuancake.response.CoolResponseUtils;
import org.wuancake.response.data.ResultBody;
import org.wuancake.service.IMoviesService;

import java.util.List;

/**
 * Created by ericheel on 2019/2/18.
 * 影视Controller
 */
@RestController
public class MoviesController {

    @Autowired
    private IMoviesService moviesService;

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
    public ResultBody getDetailsByType(Integer offset, Integer limit, String type) {

        if ("".equals(type) || null == type) {//TODO
            return CoolResponseUtils.ok(moviesService.getDetails(offset, limit), "响应成功", "200");
        }

        return CoolResponseUtils.ok(moviesService.getDetailsByType(offset, limit, type), "响应成功", "200");
    }

    /**
     * 电影详情类/影视详情(Z1)
     * 查询影视的详情内容
     * 请求url: GET /api/movies/:id
     * 响应内容:
     * ---------------------------------------------
     * id	Int	影片id
     * title	String	影片标题
     * poster_url	String	影片海报
     * original_title	String	影片原名
     * countries	String	制片国家/地区
     * year	String	年代
     * genres	String	影片类型
     * aka	String	影片别名
     * directors	String	导演
     * casts	String	主演
     * url_douban	String	豆瓣链接
     * summary	String	剧情简介
     * rating
     * -----------------------------------------------
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResultBody getMovieDetails(Integer id) {
        return null;
    }
}
