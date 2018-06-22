package com.architecture.util.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
public class CommonURLEncodedRequest<ResultType> extends Request<ResultType> {

    private int processResultType = BEAN_RESULT_TYPE;

    public static final int BEAN_RESULT_TYPE = 1;

    public static final int LIST_RESULT_TYPE = 2;

    private final CommonResponse.Listener<ResultType> mListener;

    protected final Map<String, String> mRequestBody;

    private Gson mGson;

    private Class<ResultType> mResultClass;

    private Class mListBeanClass;

    /**
     * 是否解析响应头，默认为false
     */
    private boolean isParseResponseHeader;

    private ResponseHeader responseHeader;

    private CommonRequestParams mParams;

    private CommonResponse<ResultType> commonResponse;

    public CommonURLEncodedRequest(Class listBeanClass, CommonRequestParams params, CommonResponse.Listener<ResultType> listener,
                                   Response.ErrorListener errorListener) {
        this(params, null, listener, errorListener);
        mListBeanClass = listBeanClass;
    }

    public CommonURLEncodedRequest(CommonRequestParams params, Class<ResultType> cls, CommonResponse.Listener<ResultType> listener,
                                   Response.ErrorListener errorListener) {
        super(params.getMethod(), params.getUrl(), errorListener);
        mListener = listener;
        mGson = new Gson();
        mRequestBody = params.getRequestBody();
        mResultClass = cls;
        mParams = params;
        responseHeader = new ResponseHeader();
        commonResponse = new CommonResponse<ResultType>();
    }

    @Override
    protected void deliverResponse(ResultType response) {
        commonResponse.setResult(response);
        mListener.onResponse(commonResponse);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    @Override
    protected Response<ResultType> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            ResultType parsedGSON;
            if (processResultType == BEAN_RESULT_TYPE) {
                parsedGSON = mGson.fromJson(jsonString, mResultClass);
            } else {
                ListParameterizedType listParameterizedType = new ListParameterizedType(mListBeanClass);
                //This will work too, at least with Gson 2.2.4.
                //Type type = com.google.gson.internal.$Gson$Types.newParameterizedTypeWithOwner(null, ArrayList.class, clazz);
                parsedGSON = mGson.fromJson(jsonString, listParameterizedType);
            }

            if (isParseResponseHeader) {
                responseHeader.setNextURL(ResponseUtil.getNextUrl(response.headers));
                responseHeader.setServerDate(ResponseUtil.getServerDate(response.headers));
                responseHeader.setOrignalHeaderMap(response.headers);
                commonResponse.setHeader(responseHeader);
            }
            return Response.success(parsedGSON,
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException je) {
            return Response.error(new ParseError(je));
        }
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return mRequestBody;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return RequestUtil.getHeader(null);
    }

    public void setParseResponseHeader(boolean parseResponseHeader) {
        isParseResponseHeader = parseResponseHeader;
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
