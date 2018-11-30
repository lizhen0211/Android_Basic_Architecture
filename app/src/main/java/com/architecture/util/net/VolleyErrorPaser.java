package com.architecture.util.net;


import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * Created by lz on 2016/11/7.
 */
public class VolleyErrorPaser {

    /**
     * 解析Volley错误
     *
     * @param error
     * @param paser 实体错误解析器，如果不需要解析422实体错误，可传null
     */
    public static String parseVolleyError(VolleyError error, EntityErrorPaser paser) {
        if (error instanceof NetworkError) {
            return "网络错误";
        } else if (error instanceof ClientError) {
            return parseEntityError(error, paser);
        } else if (error instanceof ServerError) {
            return "服务器错误";
        } else if (error instanceof AuthFailureError) {
            return "身份认证失败";
        } else if (error instanceof ParseError) {
        } else if (error instanceof NoConnectionError) {
            return "不能建立连接";
        } else if (error instanceof TimeoutError) {
            return "请求超时";
        }
        return "";
    }

    private static String parseEntityError(VolleyError error, EntityErrorPaser paser) {
        if (error.networkResponse.statusCode == RequestUtil.UNPROCESSABLE_ENTITY) {
            try {
                String data = new String(error.networkResponse.data, "UTF-8");
                Gson gson = new Gson();
                UnprocessableEntity unprocessableEntity = null;
                try {
                    unprocessableEntity = gson.fromJson(data, UnprocessableEntity.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (paser != null) {
                    if (unprocessableEntity != null) {
                        UnprocessableEntity.Error[] errors = unprocessableEntity.getErrors();
                        if (errors != null && errors.length > 0) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < errors.length; i++) {
                                String errorMessage = paser.parseUnprocessableEntity(errors[i]);
                                if (errorMessage != null) {
                                    if (i > 0) {
                                        sb.append("\n");
                                    }
                                    sb.append(errorMessage);
                                }
                            }
                            return sb.toString();
                        }
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (error.networkResponse.statusCode == RequestUtil.NOT_FOUND) {
            return "请求资源不存在";
        }
        return "";
    }

    public interface EntityErrorPaser {
        String parseUnprocessableEntity(UnprocessableEntity.Error error);
    }
}
