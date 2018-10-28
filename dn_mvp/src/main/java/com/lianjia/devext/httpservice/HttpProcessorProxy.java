package com.lianjia.devext.httpservice;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 代理模式 delegate
 */
public class HttpProcessorProxy implements IHttpProcessor {

    private static final String TAG = "HttpProcessorProxy";

    private static IHttpProcessor mHttpProcessor = null;

    private HttpProcessorProxy() {
    }

    public static HttpProcessorProxy obtain() {
        return HttpHelperInternal.mSingle;
    }

    // 利用了classloader的机制来保证初始化instance时只有一个线程，线程安全的，同时没有性能损耗
    private static class HttpHelperInternal {
        private static HttpProcessorProxy mSingle = new HttpProcessorProxy();
    }

    public static void init(IHttpProcessor httpProcessor) {
        mHttpProcessor = httpProcessor;
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallback callback) {
        final String finalUrl = appendParams(url, params);
        mHttpProcessor.post(finalUrl, params, callback);
        Log.i(TAG, "mHttpProcessor = " + mHttpProcessor.getClass().getSimpleName());
    }

    @Override
    public void get(String url, Map<String, Object> params, ICallback callback) {
        final String finalUrl = appendParams(url, params);
        mHttpProcessor.get(finalUrl, params, callback);
        Log.i(TAG, "mHttpProcessor = " + mHttpProcessor.getClass().getSimpleName());
    }

    private String appendParams(String url, Map<String, Object> params) {

        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder builder = new StringBuilder(url);
        if (builder.indexOf("?") <= 0) {
            builder.append("?");
        } else {
            if (!builder.toString().endsWith("?")) {
                builder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.append(entry.getKey()).append("=").append(encode(entry.getValue().toString()));
        }
        return builder.toString();
    }

    /**
     * URI不允许有空格等字符，如果参数值有空格，需要此方法转换
     */
    private static String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(HttpProcessorProxy.class.getSimpleName(), e.toString());
            throw new RuntimeException(e);
        }
    }
}