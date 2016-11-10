package com.architecture.util.net;

/**
 * Created by lz on 2016/11/7.
 * 响应头信息
 */
public class ResponseHeader {

    /**
     * 下一页URL
     */
    private String nextURL;

    /**
     * 服务器时间
     */
    private String serverDate;

    public String getNextURL() {
        return nextURL;
    }

    public void setNextURL(String nextURL) {
        this.nextURL = nextURL;
    }

    public String getServerDate() {
        return serverDate;
    }

    public void setServerDate(String serverDate) {
        this.serverDate = serverDate;
    }
}
