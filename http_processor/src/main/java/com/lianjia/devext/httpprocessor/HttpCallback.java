package com.lianjia.devext.httpprocessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class HttpCallback<T> implements ICallback {

    protected Type mGenericType;

    public HttpCallback() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            this.mGenericType = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }
    }

    @Override
    public void onResponse(String json) {
        try {
            JSONObject object = new JSONObject(json);
            String reason = object.optString("reason");
            int errorCode = object.optInt("error_code");
            JSONArray jsonResult = object.getJSONArray("result");
            T t = (new Gson()).fromJson(jsonResult.toString(), mGenericType);
            if (t != null) {
                onSuccess(t);
            } else {
                onFailed(errorCode, reason);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onFailed(-10, "解析失败");
        }

    }

    @Override
    public void onFailed(int errorCode, String errorMessage) {
        onFailure(errorCode, errorMessage);
    }

    public abstract void onSuccess(T data);

    public abstract void onFailure(int errorCode, String errorMessage);
}