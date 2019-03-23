package org.wuancake.response.data;

/**
 * Created by Administrator on 2019/3/7.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.wuancake.entity.*;

import java.util.List;

/**
 * 方便Z1接口
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MovieDetailsData {

    private int id;

    private String title;

    @JsonProperty("poster_url")
    private String posterUrl;

    @JsonProperty("original_title")
    private String originalTitle;

    private String countries;

    private String year;

    private String aka;

    @JsonProperty("url_douban")
    private String urlDouban;

    private String summary;

    private float rating;

    private List<MoviesGenresDetails> genres;

    private List<Directors> directors;

    private List<Actors> casts;

    public MovieDetailsData(MoviesDetails moviesDetails, List<MoviesGenresDetails> list0, List<Directors> list1, List<Actors> list2) {
        this.id = moviesDetails.getId();
        this.title = moviesDetails.getTitle();
        this.posterUrl = moviesDetails.getUrl();
        this.originalTitle = moviesDetails.getOriginalTitle();
        this.countries = moviesDetails.getCountries();
        this.year = moviesDetails.getYear();
        this.aka = moviesDetails.getAka();
        this.urlDouban = moviesDetails.getUrlDouban();
        this.summary = moviesDetails.getSummary();
        this.rating = moviesDetails.getRating();
        this.genres = list0;
        this.directors = list1;
        this.casts = list2;
    }

}
