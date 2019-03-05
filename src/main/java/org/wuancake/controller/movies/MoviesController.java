package org.wuancake.controller.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wuancake.response.CoolResponseUtils;
import org.wuancake.response.data.ResourceVO;
import org.wuancake.response.data.ResultBody;
import org.wuancake.response.data.SearchVO;
import org.wuancake.service.IMoviesService;
import org.wuancake.service.oidc.LinkOIDC;

import java.util.List;

/**
 * Created by ericheel on 2019/2/18.
 * 影视Controller
 */
@RestController
public class MoviesController {

    @Autowired
    private IMoviesService moviesService;
    @Autowired
    private LinkOIDC linkOIDC;

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
    public ResultBody getDetailsByType(@RequestParam(value = "offset",required = false,defaultValue = "0") Integer offset,
                                                @RequestParam(value = "limit",required = false,defaultValue = "10") Integer limit,
                                                String type) {

        if ("".equals(type) || null == type) {//TODO
            return CoolResponseUtils.ok(moviesService.getDetails(offset, limit), "响应成功", "200");
        }

        return CoolResponseUtils.ok(moviesService.getDetailsByType(offset, limit, type), "响应成功", "200");
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
        public ResultBody search(@RequestParam("q") String q,
                               @RequestParam(value = "offset",required = false,defaultValue = "0") Integer offset,
                               @RequestParam(value = "limit",required = false,defaultValue = "10") Integer limit) {
        return CoolResponseUtils.ok(new SearchVO(moviesService.getDetailsByKey(q,offset, limit)),"200","响应成功");
    }


    /**
     *  显示资源（评论)（Z2）
     *  电影详情类
     *
     * @param offset 起始页数 可以为空
     * @param limit 每页显示条数 可以为空
     * @param id 影视movieId
     * @return 资源列表
     */
    @RequestMapping(value = "/api/movies/{id}/resources",method = RequestMethod.GET)
    public List<ResourceVO> getResources(@RequestParam(value = "offset",required = false,defaultValue = "0") Integer offset,
                                         @RequestParam(value = "limit",required = false,defaultValue = "10") Integer limit,
                                         @PathVariable Integer id){
        return moviesService.getResourcesById(id,offset, limit);
    }


    /**
     *  删除电影资源（R2）
     *
     * @param accessToken
     * @param idToken
     * @param movieId
     * @param resourceId
     * @return
     */
    @RequestMapping(value = "/api/movies/{movieId}/resources/{resourceId}",method = RequestMethod.DELETE)
    public ResultBody delResources(@RequestParam("Access-Token") String accessToken,
                                       @RequestParam("ID-Token") String idToken,
                                       @PathVariable Integer movieId,
                                       @PathVariable Integer resourceId){
        try {
            if (linkOIDC.getUserMsg_U3(idToken, accessToken)!=null){
                moviesService.delResources(movieId,resourceId);
                return new ResultBody("204","删除成功");
            }
            return new ResultBody("400", "删除失败,用户权限不足");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultBody("400", "删除失败");
        }
    }
}
