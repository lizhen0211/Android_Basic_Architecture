package com.architecture.util.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.Map;

/**
 * Created by lz on 2016/11/9.
 */
public class CommonURLEncodedRequest<ResultType> extends BasicRequest<ResultType> {

    public CommonURLEncodedRequest(Class listBeanClass, CommonRequestParams params, CommonResponse.Listener<ResultType> listener, Response.ErrorListener errorListener) {
        super(listBeanClass, params, listener, errorListener);
    }

    public CommonURLEncodedRequest(CommonRequestParams params, Class<ResultType> cls, CommonResponse.Listener<ResultType> listener, Response.ErrorListener errorListener) {
        super(params, cls, listener, errorListener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams.getRequestParams();
    }
}
