package org.wuancake.service;

import org.springframework.transaction.annotation.Transactional;
import org.wuancake.entity.MoviesDetails;
import org.wuancake.entity.MoviesGenresDetails;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ericheel on 2019/2/18.
 * 影视业务接口
 */
@Transactional
public interface IMoviesService {

    /**
     * 首页/影片分类页（A1）
     * 根据类型获取影片的一些详情
     *
     * @param offset 起始页数 可以为空
     * @param limit  每页显示条数 可以为空
     * @param type   影片类型（午安点评内部的类型） 可以为空
     * @return 影片详细信息
     */
    List<MoviesDetails> getDetailsByType(Integer offset, Integer limit, String type);

    /**
     * 首页/影片分类页（A1）
     * 根据类型获取影片的一些详情
     * 没有给出type
     *
     * @param offset
     * @param limit
     * @return
     */
    List<MoviesDetails> getDetails(Integer offset, Integer limit);

    /**
     * 获取分类条目录(A4)
     *
     * @return
     */
    List<MoviesGenresDetails> getMoviesType();

    /**
     * 发现影片（Z3）
     * @param url
     * @param type
     * @return
     */
    String findMovies(String url,String type) throws IOException, URISyntaxException;
}
