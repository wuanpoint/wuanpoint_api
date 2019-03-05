package org.wuancake.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wuancake.entity.*;
import org.wuancake.entity.mid.MoviesActors;
import org.wuancake.entity.mid.MoviesDirectors;
import org.wuancake.entity.mid.MoviesType;

import java.util.List;

/**
 * Created by ericheel on 2019/2/18.
 */
@Mapper
public interface MoviesMapper {

    @Select("SELECT mb.id,mb.title,digest,url " +
            "FROM movies_base mb,movies_details md,movies_poster mp " +
            "WHERE mb.type=#{type} " +
            "AND mb.id=md.id " +
            "AND mb.id=mp.id " +
            "LIMIT #{offset},#{limit}")
    List<MoviesDetails> getDetailsByType(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("type") String type);

    @Select("SELECT mb.id,mb.title,digest,url " +
            "FROM movies_base mb,movies_details md,movies_poster mp " +
            "WHERE mb.id=md.id " +
            "AND mb.id=mp.id " +
            "LIMIT #{offset},#{limit}")
    List<MoviesDetails> getDetails(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取分类条目
     *
     * @return
     */
    @Select("SELECT genres_id genresId,genres_name genresName FROM movies_genres_details")
    List<MoviesGenresDetails> getMoviesTypeDetails();

    /**
     * 根据名称查询分类
     *
     * @param name
     * @return
     */
    @Select("SELECT genres_id genresId,genres_name genresName FROM movies_genres_details WHERE genres_name = #{genres_name}")
    MoviesGenresDetails getMoviesGenresDetailsByName(@Param("genres_name") String name);

    /**
     * 新增分类
     *
     * @param name
     * @return
     */
    @Insert("INSERT INTO movies_genres_details(genres_name) VALUES(#{genres_name})")
    int addMoviesGenresDetails(@Param("genres_name") String name);

    /**
     * 为影片设置类型
     * @param moviesType
     * @return
     */
    @Insert("INSERT INTO movies_genres(`movies_id`,`genres_id`) VALUES(#{moviesType.moviesId},#{moviesType.genresId})")
    int addMoviesType(@Param("moviesType")MoviesType moviesType);

    /**
     * 根据id查询影片
     *
     * @param id
     * @return
     */
    @Select("SELECT id, `type` ,title ,digest ,created_at createdAt FROM movies_base WHERE id = #{id}")
    MoviesBase getMoviesBaseById(@Param("id") int id);

    /**
     * 根据id查询导演
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM directors WHERE id = #{id}")
    Directors getDirectorsById(@Param("id") int id);

    /**
     * 添加导演
     *
     * @param directors
     * @return
     */
    @Insert("INSERT INTO directors(`id`,`name`) VALUES(#{directors.id},#{directors.name})")
    int addDirectors(@Param("directors") Directors directors);

    /**
     * 向影片添加导演
     * @param moviesDirectors
     * @return
     */
    @Insert("INSERT INTO movies_directors(`movie_id`,`director_id`) VALUES(#{moviesDirectors.movieId},#{moviesDirectors.directorId})")
    int addMoviesDirectors(@Param("moviesDirectors") MoviesDirectors moviesDirectors);

    /**
     * 根据id查询演员
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM actors WHERE id = #{id}")
    Actors getActorsById(@Param("id") int id);

    /**
     * 添加演员
     *
     * @param actors
     * @return
     */
    @Insert("INSERT INTO actors(`id`,`name`) VALUES(#{actors.id},#{actors.name})")
    int addActors(@Param("actors") Actors actors);

    /**
     * 向影片添加演员
     * @param moviesActors
     * @return
     */
    @Insert("INSERT INTO movies_actors(`movie_id`,`actor_id`) VALUES(#{moviesActors.movieId},#{moviesActors.actorId})")
    int addMoviesActors(@Param("moviesActors")MoviesActors moviesActors);

    /**
     * 添加影片详情
     * @param id
     * @param moviesDetails
     * @return
     */
    @Insert("INSERT INTO movies_details (`id`,`title`,`original_title`,`countries`,`year`,`aka`,`url_douban`)" +
            "VALUES(#{id},#{title},#{moviesDetails.originalTitle},#{moviesDetails.countries},#{moviesDetails.year},#{moviesDetails.aka},#{moviesDetails.urlDouban})")
    int addMoviesDetails(@Param("id") int id,@Param("moviesDetails") MoviesDetails moviesDetails,@Param("title") String title);

    /**
     * 添加影片基础信息
     * @param moviesBase
     * @return
     */
    @Insert("INSERT INTO movies_base(`id`,`type`,`title`,`digest`) VALUES(#{moviesBase.id},#{moviesBase.type},#{moviesBase.title},#{moviesBase.digest})")
    int addMoviesBase(@Param("moviesBase")MoviesBase moviesBase);
}
