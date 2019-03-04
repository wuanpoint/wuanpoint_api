package org.wuancake.controller.movies;

import com.alibaba.fastjson.JSONArray;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wuancake.entity.MoviesDetails;
import org.wuancake.entity.MoviesGenresDetails;
import org.wuancake.entity.Resources;
import org.wuancake.service.IMoviesService;
import org.wuancake.service.oidc.JwtUtil;
import org.wuancake.service.oidc.LinkOIDC;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
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
     * 发现影视（Z3）
     */
    @PostMapping(value = "/api/movies", params = {"url", "type"})
    public String findMovies(String url, String type) throws IOException, URISyntaxException {
        return moviesService.findMovies(url, type);
    }
}
