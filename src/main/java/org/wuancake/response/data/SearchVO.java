package org.wuancake.response.data;

import lombok.Data;
import org.wuancake.entity.MoviesDetails;

import java.util.List;

@Data

public class SearchVO {
    /**
     * 响应内容
     */
    private List<MoviesDetails> movies;
    /**
     * 搜索结果总数
     */
    private Integer total;

    public SearchVO(List<MoviesDetails> movies) {
        this.movies = movies;
        this.total = movies.size();
    }
}
