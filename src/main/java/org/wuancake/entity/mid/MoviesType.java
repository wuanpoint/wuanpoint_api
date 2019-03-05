package org.wuancake.entity.mid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Administrator on 2019/2/18.
 * 影片——类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@ToString
public class MoviesType {
    private int moviesId;//影片id
    private int genresId;//影片类型id
}
