package com.architecture.util.net;

import java.util.Map;

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

    /**
     * header原始数据
     */
    private Map<String, String> orignalHeaderMap;

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

    public Map<String, String> getOrignalHeaderMap() {
        return orignalHeaderMap;
    }

    public void setOrignalHeaderMap(Map<String, String> orignalHeaderMap) {
        this.orignalHeaderMap = orignalHeaderMap;
    }
}
