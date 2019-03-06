package org.wuancake.entity.mid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ericheel on 2019/2/18.
 * 影片——演员
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement
public class MoviesActors {
    private int movieId;//影片id
    private int actorId;//演员id
}
