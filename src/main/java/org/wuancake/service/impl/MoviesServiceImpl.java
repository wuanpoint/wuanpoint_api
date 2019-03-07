package org.wuancake.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wuancake.entity.*;
import org.wuancake.entity.mid.MoviesActors;
import org.wuancake.entity.mid.MoviesDirectors;
import org.wuancake.entity.mid.MoviesType;
import org.wuancake.mapper.MoviesMapper;
import org.wuancake.response.data.ResourceVO;
import org.wuancake.service.IMoviesService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ericheel on 2019/2/18.
 * 影视业务实现
 */
@Service
@Log4j
public class MoviesServiceImpl implements IMoviesService {

    @Autowired
    private MoviesMapper moviesMapper;

    public List<MoviesDetails> getDetailsByType(Integer offset, Integer limit, String type) {
        return moviesMapper.getDetailsByType(offset, limit, type);
    }

    @Override
    public List<MoviesDetails> getDetails(Integer offset, Integer limit) {
        return moviesMapper.getDetails(offset, limit);
    }

    @Override

    public List<MoviesGenresDetails> getMoviesType() {
        return moviesMapper.getMoviesTypeDetails();
    }

    @Override
    public String findMovies(String url, String type) throws IOException, URISyntaxException {
        Integer id = getIdByUrl(url);
        MoviesGenresDetails moviesGenresDetails = moviesMapper.getMoviesGenresDetailsByName(type);
        if (id == null || moviesGenresDetails == null)
            return "{\"error\": \"错误的连接\"}";
        else if (moviesMapper.getMoviesBaseById(id) != null)
            return "{\"error\": \"资源已存在\"}";

        //查询豆瓣接口
        JsonParser parse = new JsonParser();
        JsonObject json = (JsonObject) parse.parse(getMoviesMsg(id));

        moviesMapper.addMoviesType(new MoviesType(id, moviesGenresDetails.getGenresId()));//设置影片类型 type

        //遍历导演 directors
        JsonArray directorsArray = json.get("directors").getAsJsonArray();
        for (int i = 0; i < directorsArray.size(); i++) {
            JsonObject subObject = directorsArray.get(i).getAsJsonObject();
            Directors directors = moviesMapper.getDirectorsById(subObject.get("id").getAsInt());
            if (directors == null) {
                directors = new Directors(subObject.get("id").getAsInt(), subObject.get("name").getAsString());
                moviesMapper.addDirectors(directors);
            }

            moviesMapper.addMoviesDirectors(new MoviesDirectors(id, directors.getId()));
        }

        //遍历演员 casts
        JsonArray castsArray = json.get("directors").getAsJsonArray();
        for (int i = 0; i < castsArray.size(); i++) {
            JsonObject subObject = castsArray.get(i).getAsJsonObject();
            Actors actors = moviesMapper.getActorsById(subObject.get("id").getAsInt());
            if (actors == null) {
                actors = new Actors(subObject.get("id").getAsInt(), subObject.get("name").getAsString());
                moviesMapper.addActors(actors);
            }
            moviesMapper.addMoviesActors(new MoviesActors(id, actors.getId()));
        }

        //设置影片详情
        MoviesDetails moviesDetails = new MoviesDetails(
                json.get("original_title").getAsString(),
                null,
                json.get("year").getAsString(),
                null,
                url,
                null,
                0.0f,
                json.get("summary").getAsString());

        //获取地区 countries 数据库貌似只存一个
        JsonArray countries = json.get("countries").getAsJsonArray();
        for (int i = 0; i < countries.size(); i++) {
            moviesDetails.setCountries(countries.get(i).getAsString());
            break;
        }

        //获取别名 aka 数据库貌似只存一个
        JsonArray aka = json.get("aka").getAsJsonArray();
        for (int i = 0; i < countries.size(); i++) {
            moviesDetails.setAka(countries.get(i).getAsString());
            break;
        }

        //获取片名 title
        MoviesBase moviesBase = new MoviesBase(id, moviesGenresDetails.getGenresId(), json.get("title").getAsString(), moviesDetails.getSummary(), null);

        if (moviesMapper.addMoviesDetails(id, moviesDetails, moviesBase.getTitle()) > 0 && moviesMapper.addMoviesBase(moviesBase) > 0)
            return "{\"id\":" + id + "}";
        return "{\"error\": \"添加失败\"}";
    }

    /**
     * 根据豆瓣url截取id
     *
     * @param url
     * @return
     */
    private Integer getIdByUrl(String url) {
        String[] buffer = url.split("/");
        System.out.println(JSONArray.toJSONString(buffer));
        Integer id = null;
        try {
            id = new Integer(buffer[buffer.length - 1]);
        } catch (Exception e) {
            log.error("传入url并非影片的豆瓣url");
        } finally {
            return id;
        }
    }

    /**
     * 传入id，去豆瓣查询信息
     *
     * @param id
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    private String getMoviesMsg(Integer id) throws IOException, URISyntaxException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setConnectionRequestTimeout(5000)
                .build();
        HttpClient client = new DefaultHttpClient();

        String url = "https://api.douban.com/v2/movie/subject/" + id;

        HttpRequestBase httpRequestBase = null;
        URIBuilder uriBuilder = new URIBuilder(url);
        httpRequestBase = new HttpGet(uriBuilder.build());

        httpRequestBase.setConfig(requestConfig);
        HttpResponse response = client.execute(httpRequestBase);

        HttpEntity resEntity = response.getEntity();
        return EntityUtils.toString(resEntity, "utf-8");
    }

    public List<MoviesDetails> getDetailsByKey(String q, Integer offset, Integer limit) {
        return moviesMapper.getDetailsByKey(q, offset, limit);
    }

    @Override
    public List<ResourceVO> getResourcesById(Integer id, Integer offset, Integer limit) {
        return moviesMapper.getResourcesById(id, offset, limit);
    }

    @Override
    public void delResources(Integer movieId, Integer resourceId) {
        moviesMapper.delResources(movieId, resourceId);

    }

    @Override
    public MoviesDetails getMovieDetailsById(Integer id) {
        return moviesMapper.getMovieDetailsById(id);
    }

    @Override
    public List<Directors> getDirectorsByMovieId(Integer id) {
        return moviesMapper.getDirectorsByMovieId(id);
    }

    @Override
    public List<Actors> getActorsByMovieId(Integer id) {
        return moviesMapper.getActorsByMovieId(id);
    }

    @Override
    public List<MoviesGenresDetails> getMoviesGenresDetailsByMovieId(Integer id) {
        return moviesMapper.getMoviesGenresDetailsByMovieId(id);
    }

}
