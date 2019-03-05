package org.wuancake.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.wuancake.entity.MoviesDetails;
import org.wuancake.response.data.ResourceVO;

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

    @Select("SELECT resource_id as id,rtd.type_name as type,title,instruction,url,created_at,sharer,password FROM resources rs,resources_type_details rtd " +
            "WHERE rs.movies_id=#{id} AND rs.resource_type=rtd.type_id " +
            "LIMIT #{offset},#{limit}")
    List<ResourceVO> getResourcesById(@Param("id") Integer id, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Delete("delete from resources where movies_id=#{movieId} and resource_id=#{resourceId}")
    void delResources(@Param("movieId") Integer movieId, @Param("resourceId") Integer resourceId);
}
