package org.wuancake.controller.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wuancake.entity.MoviesDetails;
import org.wuancake.response.data.SearchVO;
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
    public List<MoviesDetails> getDetailsByType(@RequestParam(value = "offset",required = false,defaultValue = "0") Integer offset,
                                                @RequestParam(value = "limit",required = false,defaultValue = "10") Integer limit,
                                                String type) {

        if ("".equals(type) || null == type) {//TODO
            return moviesService.getDetails(offset, limit);
        }

        return moviesService.getDetailsByType(offset, limit, type);
    }

    /**
     * 首页/搜索影视页（A3）
     * 根据标题模糊查询影片的一些详情
     *
     * @param q 影片类型（午安点评内部的类型） 不能为空
     * @param offset 起始页数 可以为空
     * @param limit 每页显示条数 可以为空
     * @return 影片详细信息
     */
    @RequestMapping(value = "/api/movies/search", method = RequestMethod.POST)
        public SearchVO search(@RequestParam("q") String q,
                               @RequestParam(value = "offset",required = false,defaultValue = "0") Integer offset,
                               @RequestParam(value = "limit",required = false,defaultValue = "10") Integer limit) {
        return new SearchVO(moviesService.getDetailsByKey(q,offset, limit));
    }

    /**
     *  显示资源（评论)（Z2）
     *  电影详情类
     *  resources.id	Int	资源id
     *  resources.title	String	资源标题
     *  resources.instruction	String	资源说明
     *  resources.url	String	资源URL
     *  resources.create_at	String	资源分享时间
     *  resources.password	String	资源密码(网盘)
     *  resources.type	String	资源类型
     *  resources.sharer.id	Int	分享者id
     *  resources.sharer.name	String	分享者姓名
     *
     * @param offset 起始页数 可以为空
     * @param limit 每页显示条数 可以为空
     * @return
     */
    @RequestMapping(value = "/api/movies/{id}/resources",method = RequestMethod.GET)
    public void getResources( @RequestParam(value = "offset",required = false,defaultValue = "0") Integer offset,
                              @RequestParam(value = "limit",required = false,defaultValue = "10") Integer limit){

    }

    /**
     *  删除电影资源（R2）
     * @param accessToken
     * @param idToken
     * @return
     */
    @RequestMapping(value = "/api/movies/{movieId}/resources/{resourceId}",method = RequestMethod.DELETE)
    public ResponseEntity delResources(@RequestParam("Access-Token") String accessToken,
                                       @RequestParam("ID-Token") String idToken){
        try {

            return ResponseEntity.ok().body(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }
    }
}
