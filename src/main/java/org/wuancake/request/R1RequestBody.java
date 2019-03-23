package org.wuancake.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class R1RequestBody {
    private String type;
    private String title;
    private String url;
    private String password;
    private String instruction;
}
