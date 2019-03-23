package org.wuancake.mapper;

import org.apache.ibatis.annotations.*;
import org.wuancake.entity.Resources;
import org.wuancake.entity.ResourcesType;

import java.util.List;

/**
 * 资源相关的mapper接口
 */
@Mapper
public interface ResourcesMapper {
    /**
     * 获取全部资源分类列表
     *
     * @return
     */
    @Select("SELECT type_id typeId ,type_name typeName FROM resources_type_details")
    List<ResourcesType> getResourcesType();

    /**
     * 获取分享者id
     *
     * @param id
     * @return
     */
    @Select("SELECT sharer FROM resources WHERE `resource_id` = #{id}")
    Integer getSharerId(@Param("id") Integer id);

    /**
     * 编辑资源
     *
     * @param resources
     * @return
     */
    @Update("UPDATE resources SET `resource_type` = #{resources.resourceType}," +
            "`title` = #{resources.title}," +
            "`url` = #{resources.url}," +
            "`password` = #{resources.password}," +
            "`instruction` = #{resources.instruction}")
    int update(@Param("resources") Resources resources);

    @Insert("INSERT INTO resources(movies_id,resource_type,title,url,instruction,password,created_at,sharer) " +
            "VALUES(#{resources.moviesId},#{resources.resourceType},#{resources.title},#{resources.url},#{resources.instruction},#{resources.password},#{resources.createdAt},#{resources.sharerId})")
    @Options(useGeneratedKeys=true, keyProperty="resourceId", keyColumn="resource_id")
    void addResource(@Param("resources") Resources resources);

    @Select("select type_id from resources_type_details where type_name=#{type}")
    Integer getResourceTypeIdByResourceTypeName(String type);
}
