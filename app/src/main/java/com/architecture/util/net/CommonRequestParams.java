package com.architecture.util.net;

import com.android.volley.Request;

import java.util.Map;

/**
 * Created by lz on 2016/11/4.
 * 通用请求参数
 */
public class CommonRequestParams {

    /**
     * 请求方法类型
     */
    private int method = Request.Method.GET;

    /**
     * 请求URL
     */
    private String url;

    /**
     * json请求 请求体
     */
    private String requestBody;

    /**
     * 表单请求 请求参数
     */
    private Map<String, String> requestParams;

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

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }
}
