package com.architecture.util.net;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lz on 2016/11/7.
 * 响应通用类
 */
public class ResponseUtil {

    static final Pattern pattern = Pattern.compile("<([^>]*)>(\\s*);(\\s*)rel=\"next\"");
    static final String HEAD_LINK = "Link";
    static final String SERVER_DATE = "Date";
    static final String LAST_MODIFIED = "Last-Modified";

    /**
     * 获取NextUrl
     *
     * @param headers
     * @return
     */
    public static String getNextUrl(Map<String, String> headers) {
        String linkStr = null;
        if (headers != null && headers.size() > 1) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(HEAD_LINK)) {
                    linkStr = entry.getValue();
                    Matcher matcher = pattern.matcher(linkStr);
                    if (matcher.find()) {
                        String link = matcher.group(0);
                        linkStr = link.substring(link.indexOf("<") + 1, link.indexOf(">"));
                        break;
                    }
                }
            }
        }
        return linkStr;
    }

    /**
     * 获取当前服务器时间
     *
     * @param headers
     * @return
     */
    public static String getServerDate(Map<String, String> headers) {
        String time = null;
        if (headers != null && headers.size() > 1) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey().equals(SERVER_DATE)) {
                    time = entry.getValue();
                    break;
                }
            }
        }
        return time;
    }

    /**
     * 获取上次修改时间
     * @param headers
     * @return
     */
    public static String getLastModifiedTime(Map<String, String> headers) {
        String lastModifiedTime = null;
        if (headers != null && headers.size() > 1) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey().equals(LAST_MODIFIED)) {
                    lastModifiedTime = entry.getValue();
                    break;
                }
            }
        }
        return lastModifiedTime;
    }
}
