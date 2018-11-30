package com.architecture.util.net;

/**
 * Created by lz on 2016/11/9.
 */
public class CommonResponse<ResultType> {

    private ResponseHeader header;

    private int statusCode;

    private ResultType result;

    private String orignalResult;

    public ResultType getResult() {
        return result;
    }

    public void setResult(ResultType result) {
        this.result = result;
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public String getOrignalResult() {
        return orignalResult;
    }

    public void setOrignalResult(String orignalResult) {
        this.orignalResult = orignalResult;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public interface Listener<ResultType> {
        void onResponse(CommonResponse<ResultType> response);
    }
}
