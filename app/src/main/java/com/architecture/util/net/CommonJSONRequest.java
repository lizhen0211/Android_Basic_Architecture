package com.architecture.util.net;

import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.io.UnsupportedEncodingException;

/**
 * Created by lz on 2016/11/10.
 */
public class CommonJSONRequest<ResultType> extends BasicRequest<ResultType> {

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

    /**
     * 如果json参数不全是string类型，可以重写此方法。
     * 设置body json 内容
     *
     * @return
     */
    @Override
    public byte[] getBody() {
        String requestBody = null;
        try {
            requestBody = mParams.getRequestBody();
            return requestBody == null ? null : requestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    requestBody, PROTOCOL_CHARSET);
            return null;
        }
    }
}
