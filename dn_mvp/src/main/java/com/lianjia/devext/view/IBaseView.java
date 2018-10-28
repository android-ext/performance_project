package com.lianjia.devext.view;

import java.util.Map;

public interface IBaseView {

    Map<String, Object> initParams();

    void showLoading();

    void hideLoading();
}
