package com.architecture.util.net;

import java.util.Map;

/**
 * Created by lz on 2016/11/4.
 * 通用请求参数
 */
public class CommonRequestParams {

    /**
     * 请求方法类型
     */
    private int method;

    /**
     * 请求URL
     */
    private String url;

    /**
     * 请求体
     */
    private Map<String,String> requestBody;

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Map<String,String> requestBody) {
        this.requestBody = requestBody;
    }
}
