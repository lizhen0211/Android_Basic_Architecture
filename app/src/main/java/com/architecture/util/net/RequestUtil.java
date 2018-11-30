package com.architecture.util.net;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
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
    public static String getJsonRequestBody(Map<String, Object> bodyMap) {
        String requestBody = null;
        if (bodyMap != null) {
            Gson gson = new Gson();
            requestBody = gson.toJson(bodyMap);
        }
        return requestBody;
    }

    /**
     * 请求参数编码
     *
     * @param params
     * @param paramsEncoding
     * @return
     */
    public static String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();

        try {
            Iterator uee = params.entrySet().iterator();

            while (uee.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) uee.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key != null && value != null) {
                    encodedParams.append(URLEncoder.encode((String) key, paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode((String) value, paramsEncoding));
                    encodedParams.append('&');
                }
            }
            if (encodedParams.length() > 0) {
                encodedParams.deleteCharAt(encodedParams.length() - 1);
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException var6) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, var6);
        }
    }

    private static final int TIMEOUT_MS = 3000 * 10;

    private static final int MAX_RETRIES = 0;

    private static final int BACKOFF_MULT = 1;


    public static RetryPolicy getDefaultRetryPolicy() {
        DefaultRetryPolicy payRetryPolicy = new DefaultRetryPolicy(
                TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT);
        return payRetryPolicy;
    }
}
