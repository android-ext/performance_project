package com.lianjia.devext.model;

import com.lianjia.devext.contract.IMainContract;
import com.lianjia.devext.httpservice.HttpCallback;
import com.lianjia.devext.httpservice.HttpProcessorProxy;
import com.lianjia.devext.httpservice.bean.ProvinceBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainModelImpl implements IMainContract.IMainModel {

    private final String url = "http://v.juhe.cn/historyWeather/province";
    Map<String, Object> mParams = new HashMap<>();

    @Override
    public void setParams(Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            mParams = params;
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadGirl(final HttpCallback<List<ProvinceBean>> callBack) {
        HttpProcessorProxy.obtain().get(url, mParams, callBack);
    }
}
