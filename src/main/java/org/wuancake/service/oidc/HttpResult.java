package org.wuancake.service.oidc;
/**
 * 保存html请求结果的类
 */
public class HttpResult {
    private int resultCode;//html请求状态码
    private String resultMessage;//返回的json信息

    public HttpResult(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public HttpResult() {
    }

    @Override
    public String toString() {
        return "HtmlResult{" +
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
