package org.wuancake.mapper;

import org.apache.ibatis.annotations.*;
import org.wuancake.entity.MoviesDetails;

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

    @Select("SELECT mb.id,mb.title,digest,url as poster,rating " +
            "FROM movies_base mb,movies_details md,movies_poster mp,movies_rating mr " +
            "WHERE mb.title LIKE concat(concat('%',#{q}),'%') " +
            "AND mb.id=md.id AND mb.id=mp.id AND mb.id=mr.id " +
            "LIMIT #{offset},#{limit}")
    List<MoviesDetails> getDetailsByKey(@Param("q") String q, @Param("offset") Integer offset, @Param("limit") Integer limit);

}
