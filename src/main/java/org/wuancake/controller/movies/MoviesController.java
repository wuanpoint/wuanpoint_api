package org.wuancake.controller.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wuancake.entity.MoviesDetails;
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
    public List<MoviesDetails> getDetailsByType(Integer offset, Integer limit, String type) {

        if ("".equals(type) || null == type) {//TODO
            return moviesService.getDetails(offset, limit);
        }

        return moviesService.getDetailsByType(offset, limit, type);
    }
}
