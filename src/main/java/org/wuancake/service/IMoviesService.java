package org.wuancake.service;

import org.springframework.transaction.annotation.Transactional;
import org.wuancake.entity.Actors;
import org.wuancake.entity.Directors;
import org.wuancake.entity.MoviesDetails;

import org.wuancake.entity.MoviesGenresDetails;

import org.wuancake.response.data.ResourceVO;


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
     *
     * @param url
     * @param type
     * @return
     */
    String findMovies(String url, String type) throws IOException, URISyntaxException;

    List<MoviesDetails> getDetailsByKey(String q, Integer offset, Integer limit);

    List<ResourceVO> getResourcesById(Integer id, Integer offset, Integer limit);

    void delResources(Integer movieId, Integer resourceId);

    /**
     * 根据影片id获取影片详细信息，
     * 这个比byType查询获取的数据更多点
     *
     * @return
     */
    MoviesDetails getMovieDetailsById(Integer id);

    /**
     * 根据影片id获取这个影片的导演
     *
     * @param id
     * @return
     */
    List<Directors> getDirectorsByMovieId(Integer id);

    /**
     * 根据影片id获取这个影片的演员集合
     *
     * @param id
     * @return
     */
    List<Actors> getActorsByMovieId(Integer id);

    /**
     * 获取genres根据影片id
     * <p>
     * 是genres还是type，文档里是genres就按照这个来
     *
     * @param id
     * @return
     */
    List<MoviesGenresDetails> getMoviesGenresDetailsByMovieId(Integer id);
}
