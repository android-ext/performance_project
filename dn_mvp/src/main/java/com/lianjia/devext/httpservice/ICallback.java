package com.lianjia.devext.httpservice;

public interface ICallback {

    void onResponse(String json);
    void onFailure(int errorCode, String errorMessage);
}