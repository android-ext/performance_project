package com.lianjia.devext;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lianjia.devext.base.BaseActivity;
import com.lianjia.devext.contract.IMainContract;
import com.lianjia.devext.dnmvp.R;
import com.lianjia.devext.httpservice.bean.ProvinceBean;
import com.lianjia.devext.presenter.MainPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainContract.IMainView,
   View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.network_tv);
        // 观察者设计模式
        findViewById(R.id.volley_btn_click).setOnClickListener(this);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }


    @Override
    public Map<String, Object> initParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("key", "356f48d72ed9d8b7d11533984a9b1436");
        return params ;
    }

    @Override
    public void showLoading() {
        showToast(R.string.loading_data);
    }

    @Override
    public void hideLoading() {
        showToast(R.string.load_data_finished);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.volley_btn_click:
                mPresenter.fetch();
                break;
        }
    }

    @Override
    public void onSuccess(List<ProvinceBean> data) {
        StringBuilder builder = new StringBuilder();

        for (ProvinceBean provinceBean : data) {
            builder.append("id = ")
               .append(provinceBean.id)
               .append(" 省份 = ")
               .append(provinceBean.province)
               .append("\r\n");
        }
        mTextView.setText(builder.toString());
    }

    @Override
    public void onFailure(int errorCode, String message) {
        showToast(message);
    }
}
