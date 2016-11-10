package com.architecture.util.net;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lz on 2016/8/18.
 * 请求通用类
 */
public class RequestUtil {

    public static final int UNPROCESSABLE_ENTITY = 422;

    public static final int NOT_FOUND = 404;

    /**
     * 获取请求header
     *
     * @param headers
     * @return
     */
    public static Map<String, String> getHeader(Map<String, String> headers) {
        if (null == headers) {
            headers = new HashMap<String, String>();
        }

        headers.put("Accept", "");
        headers.put("Connection", "keep-alive");
        headers.put("Accept-Encoding", "UTF-8");
        headers.put("User-Agent", "");
        headers.put("Authorization", "token " + "");
        return headers;
    }

    /**
     * 获取请求json参数
     *
     * @param bodyMap
     * @return
     */
    public static String getJsonRequestBody(Map<String,String> bodyMap) {
        String requestBody = null;
        if (bodyMap != null) {
            Gson gson = new Gson();
            requestBody = gson.toJson(bodyMap);
        }
        return requestBody;
    }


    public static String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();

        try {
            Iterator uee = params.entrySet().iterator();

            while (uee.hasNext()) {
                Map.Entry entry = (Map.Entry) uee.next();
                encodedParams.append(URLEncoder.encode((String) entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode((String) entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            encodedParams.deleteCharAt(encodedParams.length() - 1);
            return encodedParams.toString();
        } catch (UnsupportedEncodingException var6) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, var6);
        }
    }
}
