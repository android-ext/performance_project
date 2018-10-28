package com.lianjia.devext.httpservice;


import java.util.Map;

/**
 * 代理模式 Subject
 */
public interface IHttpProcessor {
    void post(String url, Map<String, Object> params, ICallback callback);

    void get(String url, Map<String, Object> params, ICallback callback);
}