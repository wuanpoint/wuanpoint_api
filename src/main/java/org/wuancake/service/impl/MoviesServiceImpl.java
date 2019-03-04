package org.wuancake.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wuancake.entity.MoviesDetails;
import org.wuancake.mapper.MoviesMapper;
import org.wuancake.service.IMoviesService;

import java.util.List;

/**
 * Created by ericheel on 2019/2/18.
 * 影视业务实现
 */
@Service
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
    public List<MoviesDetails> getDetailsByKey(String q, Integer offset, Integer limit) {
        return moviesMapper.getDetailsByKey(q,offset,limit);
    }
}
