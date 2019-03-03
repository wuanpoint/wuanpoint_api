package org.wuancake.response;

import org.wuancake.response.data.ResultBody;

/**
 * 自定义响应工具类
 */
public class CoolResponseUtils {

    private CoolResponseUtils() {
    }

    /**
     *
     * @param resultData 响应内容
     * @return 响应体
     */
    public static ResultBody ok(Object resultData, String message, String code) {

        ResultBody resultBody = new ResultBody();

        resultBody.setCode(code);
        resultBody.setMessage(message);
        resultBody.setData(resultData);

        return resultBody;
    }
}
