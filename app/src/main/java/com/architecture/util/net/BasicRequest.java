package com.architecture.util.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lz on 2016/11/9.
 */
public class BasicRequest<ResultType> extends Request<ResultType> {

    /**
     * Charset for request.
     */
    public static final String PROTOCOL_CHARSET = "utf-8";

    private int processResultType = BEAN_RESULT_TYPE;

    public static final int BEAN_RESULT_TYPE = 1;

    public static final int LIST_RESULT_TYPE = 2;

    private CommonResponse.Listener<ResultType> mListener;

    private Gson mGson;

    private Class<ResultType> mResultClass;

    private Class mListBeanClass;

    /**
     * 是否解析响应头，默认为false
     */
    private boolean isParseResponseHeader;

    /**
     * 是否解析响应体
     * 是 将响应解析为实体；
     * 否 不解析 将原始字符串放到 响应的 orignalResult中
     */
    private boolean isParseResponse = true;

    private ResponseHeader responseHeader;

    protected CommonRequestParams mParams;

    private CommonResponse<ResultType> commonResponse;

    public BasicRequest(Class listBeanClass, CommonRequestParams params, CommonResponse.Listener<ResultType> listener,
                        Response.ErrorListener errorListener) {
        this(params, null, listener, errorListener);
        mListBeanClass = listBeanClass;
    }

    public BasicRequest(CommonRequestParams params, Class<ResultType> cls, CommonResponse.Listener<ResultType> listener,
                        Response.ErrorListener errorListener) {
        super(params.getMethod(), params.getUrl(), errorListener);
        mListener = listener;
        mGson = new Gson();
        mResultClass = cls;
        mParams = params;
        responseHeader = new ResponseHeader();
        commonResponse = new CommonResponse<ResultType>();
        setRetryPolicy(RequestUtil.getDefaultRetryPolicy());
    }

    @Override
    protected void deliverResponse(ResultType response) {
        commonResponse.setResult(response);
        if (mListener != null) {
            mListener.onResponse(commonResponse);
        }
    }

    public void setmListener(CommonResponse.Listener<ResultType> mListener) {
        this.mListener = mListener;
    }

    @Override
    protected Response<ResultType> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            ResultType parsedGSON = null;

            if (isParseResponse) {
                if (processResultType == BEAN_RESULT_TYPE) {
                    if (mResultClass != null) {
                        parsedGSON = mGson.fromJson(jsonString, mResultClass);
                    }
                } else {
                    if (mListBeanClass != null) {
                        ListParameterizedType listParameterizedType = new ListParameterizedType(mListBeanClass);
                        //This will work too, at least with Gson 2.2.4.
                        //Type type = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class, clazz);
                        parsedGSON = mGson.fromJson(jsonString, listParameterizedType);
                    }
                }
            } else {
                commonResponse.setOrignalResult(jsonString);
            }

            if (isParseResponseHeader) {
                responseHeader.setNextURL(ResponseUtil.getNextUrl(response.headers));
                responseHeader.setServerDate(ResponseUtil.getServerDate(response.headers));
                responseHeader.setOrignalHeaderMap(response.headers);
                commonResponse.setHeader(responseHeader);
            }

            commonResponse.setStatusCode(response.statusCode);

            return Response.success(parsedGSON,
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException je) {
            return Response.error(new ParseError(je));
        }
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return RequestUtil.getHeader(null);
    }

    public void setParseResponseHeader(boolean parseResponseHeader) {
        isParseResponseHeader = parseResponseHeader;
    }

    public void setParseResponse(boolean parseResponse) {
        isParseResponse = parseResponse;
    }

    public void setProcessResultType(int processResultType) {
        this.processResultType = processResultType;
    }

    private static class ListParameterizedType implements ParameterizedType {

        private Type type;

        private ListParameterizedType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

        // implement equals method too! (as per javadoc)
    }
}
