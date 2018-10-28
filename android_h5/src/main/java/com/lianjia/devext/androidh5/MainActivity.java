package com.lianjia.devext.androidh5;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import static com.lianjia.devext.androidh5.WebViewActivity.WEBVIEW_RESULT_CODE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, JsBridge {

    public static final int MAIN_REQUEST_CODE = 1;
    public static final String NAME_KEY = "name";
    public static final String FAVORITE_KEY = "favorite";

    private EditText mResultEt;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        findViewById(R.id.transform_h5).setOnClickListener(this);
        findViewById(R.id.btn_click).setOnClickListener(this);

        mResultEt = findViewById(R.id.result_tv);
        mWebView = findViewById(R.id.web_view);

        // 1. 允许WebView加载js
        mWebView.getSettings().setJavaScriptEnabled(true);

        // 2. 编写js接口类 ImoocJsInterface 类

        // 给WebView添加js接口
        mWebView.addJavascriptInterface(new ImoocJsInterface(this), "imoocBridge");

        mWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.transform_h5:
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                startActivityForResult(intent, MAIN_REQUEST_CODE);
                break;
            case R.id.btn_click:
                String result = mResultEt.getText().toString().trim();
                if (!TextUtils.isEmpty(result)) {
                    // 向H5页面传递参数
                    // Android版本变量
                    final int version = Build.VERSION.SDK_INT;
                    // 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
                    if (version < 18) {
                        mWebView.loadUrl("javascript:if (window.remote) {window.remote('" + result + "')}");
                    } else {
                        mWebView.evaluateJavascript("javascript:remote('" + result + "')", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                                Log.i("MainActivity", value);
                            }
                        });
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (MAIN_REQUEST_CODE == requestCode && WEBVIEW_RESULT_CODE == resultCode) {
            if (data != null) {
                ((TextView) findViewById(R.id.name_tv)).setText(data.getStringExtra(NAME_KEY));
                ((TextView) findViewById(R.id.favorite_tv)).setText(data.getStringExtra(FAVORITE_KEY));
            }
        }
    }

    @Override
    public void setTextViewValue(final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(value)) {
                    mResultEt.setText(value);
                }
            }
        });
    }
}
