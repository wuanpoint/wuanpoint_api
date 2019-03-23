package org.wuancake.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ericheel on 2019/2/18.
 * 影片详情
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement
public class MoviesDetails extends MoviesBase {

    /**
     * 影片原名
     */
    @JsonProperty("original_title")
    private String originalTitle;

    /**
     * 制片国家/地区
     */
    private String countries;

    /**
     * 年代
     */
    private String year;

    /**
     * 影片别名
     */
    private String aka;

    /**
     * 豆瓣链接
     */
    @JsonProperty("url_douban")
    private String urlDouban;

    /**
     * 海报链接（poster）
     */
    @JsonProperty("poster_url")
    private String url;

    /**
     * 评分
     */
    private float rating;

    /**
     * 简介
     */
    private String summary;


}
