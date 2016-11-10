package com.architecture.util.net;

/**
 * Created by lz on 2016/11/9.
 */
public class CommonResponse<ResultType> {

    private ResponseHeader header;

    private ResultType result;

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

    public interface Listener<ResultType> {
        void onResponse(CommonResponse<ResultType> response);
    }
}
