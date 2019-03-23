package org.wuancake.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.wuancake.entity.MoviesDetails;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class A1Response {

    private List<MoviesDetails> movies;

    private String total;
}
