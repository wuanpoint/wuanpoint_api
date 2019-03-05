package org.wuancake.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.annotations.ConstructorArgs;

import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@XmlRootElement
public class ResultBody {
    /**
     * 状态码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应内容
     */
    private Object data;

    public ResultBody(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
