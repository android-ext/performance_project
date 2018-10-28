package com.lianjia.devext.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lianjia.devext.httpprocessor.HttpCallback;
import com.lianjia.devext.httpprocessor.HttpProcessorProxy;
import com.lianjia.devext.httpprocessor.R;
import com.lianjia.devext.httpprocessor.bean.ProvinceBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String url = "http://v.juhe.cn/historyWeather/province";

    private Map<String, Object> mParams = new HashMap<>();
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.network_tv);
        // 观察者设计模式
        findViewById(R.id.volley_btn_click).setOnClickListener(this);
        findViewById(R.id.okhttp_btn_click).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mParams.put("key", "356f48d72ed9d8b7d11533984a9b1436");
        switch (v.getId()) {
            case R.id.volley_btn_click:
                HttpProcessorProxy.obtain().get(url, mParams, new HttpCallback<List<ProvinceBean>>() {

                    @Override
                    public void onFailure(int errorCode, String e) {
                        mTextView.setText(e);
                    }

                    @Override
                    public void onSuccess(List<ProvinceBean> list) {
                        StringBuilder builder = new StringBuilder();
                        if (list != null) {
                            for (ProvinceBean provinceBean : list) {
                                builder.append("id = ")
                                        .append(provinceBean.id)
                                        .append(" 省份 = ")
                                        .append(provinceBean.province)
                                        .append("\r\n");
                            }
                        }
                        mTextView.setText(builder.toString());
                    }
                });
                break;
            case R.id.okhttp_btn_click:
                HttpProcessorProxy.obtain().get(url, mParams, new HttpCallback<List<ProvinceBean>>() {

                    @Override
                    public void onSuccess(List<ProvinceBean> list) {
                        StringBuilder builder = new StringBuilder();
                        if (list != null) {
                            for (ProvinceBean provinceBean : list) {
                                builder.append("id = ")
                                        .append(provinceBean.id)
                                        .append(" 省份 = ")
                                        .append(provinceBean.province)
                                        .append("\r\n");
                            }
                        }
                        mTextView.setText(builder.toString());
                    }

                    @Override
                    public void onFailure(int errorCode, String e) {
                        mTextView.setText(e);
                    }
                });
                break;
        }
    }
}
