package com.lianjia.devext.httpprocessor;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 代理模式 RealSubject
 */

public class OkHttpProcessor implements IHttpProcessor {

    private OkHttpClient mOkHttpClient;
    private Handler mHandler;


    public OkHttpProcessor() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(String url, Map<String, Object> params, final ICallback callback) {
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                disposeFailure(call, e, callback);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                disposeResponse(call, response, callback);
            }
        });
    }



    @Override
    public void get(String url, Map<String, Object> params, final ICallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "a")
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                disposeFailure(call, e, callback);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                disposeResponse(call, response, callback);
            }
        });
    }

    private void disposeFailure(final Call call, final IOException e, final ICallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailed(-1, e.getMessage());
            }
        });
    }

    private void disposeResponse(Call call, final Response response, final ICallback callback) throws IOException {
        if (response.isSuccessful()) {
            // 一手数据永远是String
            final String result = response.body().string();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(result);
                }
            });
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFailed(-1, response.message().toString());
                }
            });
        }
    }

    private RequestBody appendBody(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params == null || params.isEmpty()) {
            return builder.build();
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue().toString());
        }
        return builder.build();
    }
}
