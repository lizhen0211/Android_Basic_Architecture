package com.architecture.util.net;

import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.io.UnsupportedEncodingException;

/**
 * Created by lz on 2016/11/10.
 */
public class CommonJSONRequest<ResultType> extends CommonURLEncodedRequest<ResultType> {

    /**
     * Charset for request.
     */
    private static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    public CommonJSONRequest(CommonRequestParams params, Class<ResultType> cls, CommonResponse.Listener<ResultType> listener, Response.ErrorListener errorListener) {
        super(params, cls, listener, errorListener);
    }

    public CommonJSONRequest(Class listBeanClass, CommonRequestParams params, CommonResponse.Listener<ResultType> listener, Response.ErrorListener errorListener) {
        super(listBeanClass, params, listener, errorListener);
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() {
        try {
            String jsonRequestBody = RequestUtil.getJsonRequestBody(mRequestBody);
            return jsonRequestBody == null ? null : jsonRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }
}
