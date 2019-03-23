package org.wuancake.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wuancake.entity.Resources;
import org.wuancake.entity.ResourcesType;
import org.wuancake.mapper.ResourcesMapper;
import org.wuancake.service.ResourcesService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
@Log4j
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    private ResourcesMapper resourcesMapper;

    @Override
    public boolean updateResources(Resources resources) {
        //如果分享者id不符则不做任何修改
        Integer sharerId = resourcesMapper.getSharerId(resources.getResourceId());
        if (sharerId == null || sharerId != resources.getSharerId()) {
            log.error("id为: " + resources.getSharerId() + " 的账号试图修改其他分享者的资源。");
            return false;
        }
        return resourcesMapper.update(resources) > 0;
    }

    @Override
    public List<ResourcesType> getResourcesType() {
        return resourcesMapper.getResourcesType();
    }

    @Override
    public void addResources(Resources resources) throws SQLException {

        try {
            resourcesMapper.addResource(resources);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        }

    }

    @Override
    public Integer getResourceTypeIdByResourceTypeName(String type) {
        return resourcesMapper.getResourceTypeIdByResourceTypeName(type);
    }
}
