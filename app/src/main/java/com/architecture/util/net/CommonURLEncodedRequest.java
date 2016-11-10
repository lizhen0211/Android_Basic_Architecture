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
import java.util.Map;

/**
 * Created by lz on 2016/11/9.
 */
public class CommonURLEncodedRequest<ResultType> extends Request<ResultType> {

    private final CommonResponse.Listener<ResultType> mListener;
    protected final Map<String, String> mRequestBody;

    private Gson mGson;

    private Class<ResultType> mJavaClass;

    /**
     * 是否解析响应头，默认为false
     */
    private boolean isParseResponseHeader;

    private ResponseHeader responseHeader;

    private CommonRequestParams mParams;

    private CommonResponse<ResultType> commonResponse;

    public CommonURLEncodedRequest(CommonRequestParams params, Class<ResultType> cls, CommonResponse.Listener<ResultType> listener,
                                   Response.ErrorListener errorListener) {
        super(params.getMethod(), params.getUrl(), errorListener);
        mListener = listener;
        mGson = new Gson();
        mRequestBody = params.getRequestBody();
        mJavaClass = cls;
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
            ResultType parsedGSON = mGson.fromJson(jsonString, mJavaClass);
            if (isParseResponseHeader) {
                responseHeader.setNextURL(ResponseUtil.getNextUrl(response.headers));
                responseHeader.setServerDate(ResponseUtil.getServerDate(response.headers));
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
}
