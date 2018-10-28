package com.lianjia.devext.httpprocessor;

public interface ICallback {

    void onResponse(String json);
    void onFailed(int errorCode, String errorMessage);
}