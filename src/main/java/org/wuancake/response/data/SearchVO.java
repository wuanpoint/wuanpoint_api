package org.wuancake.response.data;

import lombok.Data;
import org.wuancake.entity.MoviesDetails;

import java.util.List;

@Data

public class SearchVO {
    private List<MoviesDetails> movies;
    private Integer total;

    public SearchVO() {
    }

    public SearchVO(List<MoviesDetails> movies) {
        this.movies = movies;
        this.total = movies.size();
    }
}
