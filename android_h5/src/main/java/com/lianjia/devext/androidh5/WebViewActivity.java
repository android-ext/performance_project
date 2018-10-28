package com.lianjia.devext.androidh5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import static com.lianjia.devext.androidh5.FormActivity.FROM_RESULT_CODE;
import static com.lianjia.devext.androidh5.MainActivity.FAVORITE_KEY;
import static com.lianjia.devext.androidh5.MainActivity.MAIN_REQUEST_CODE;
import static com.lianjia.devext.androidh5.MainActivity.NAME_KEY;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;
    public static final int WEBVIEW_REQUEST_CODE = 3;
    public static final int WEBVIEW_RESULT_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initView();
    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);
        //获取webSettings
        WebSettings settings = mWebView.getSettings();
        //让webView支持JS
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/form.html");

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                String pre = "protocol://android";
                if (!url.contains(pre)) {
                    return false;
                }
                try {
                    url = URLDecoder.decode(url,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Map<String, String> map = getParamsMap(url, pre);
                String code = map.get("code");
                String data = map.get("data");
                parseCode(code, data);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorCode == ERROR_UNSUPPORTED_SCHEME) {

                    //停止加载错误页面，否则会显示原来H5加载的错误页面
                    view.stopLoading();
                    view.loadUrl("file:///android_asset/error.html");
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 通过对URl的解析来决定调转到哪个页面
            }
        });
    }

    private Map<String, String> getParamsMap(String url, String pre) {

        ArrayMap<String, String> queryStrMap = new ArrayMap<>();
        if (url.contains(pre)) {
            int index = url.indexOf(pre);
            int end = index +pre.length();
            String queryStr = url.substring(end + 1);
            if (!TextUtils.isEmpty(queryStr)) {
                String[] splits = queryStr.split("&");

                String[] queryStringParam;
                if (splits != null && splits.length > 0) {
                    for (String str : splits) {
                        if (str.toLowerCase().startsWith("data=")) {
                            // 单独处理data项，避免data内部的&被拆分
                            int dataIndex = queryStr.indexOf("data=");
                            String dataValue = queryStr.substring(dataIndex + 5);
                            queryStrMap.put("data", dataValue);
                        } else {
                            queryStringParam = str.split("=");
                            String value = "";
                            if (queryStringParam != null && queryStringParam.length > 1) {
                                //避免后台有时候不传值,如"key="这种
                                value = queryStringParam[1];
                            }
                            queryStrMap.put(queryStringParam[0].toLowerCase(), value);
                        }
                    }
                    return queryStrMap;
                }
            }
        }
        return queryStrMap;
    }

    private void parseCode(String code, String data) {
        if ("transformNative".equals(code)) {
            Intent intent = new Intent(WebViewActivity.this, FormActivity.class);
            startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
            return;
        } else if ("submitForm".equals(code)) {
            try {
                JSONObject json = new JSONObject(data);
                String name = json.optString("user_name");
                String favorite = json.optString("user_favorite");
                Intent intent = new Intent();
                intent.putExtra(NAME_KEY, name);
                intent.putExtra(FAVORITE_KEY, favorite);
                setResult(WEBVIEW_RESULT_CODE, intent);
                finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (WEBVIEW_REQUEST_CODE == requestCode && FROM_RESULT_CODE == resultCode) {
            if (data != null) {
                String extra = data.getStringExtra(NAME_KEY);
                // 将 native 表单页面的数据回显到H5页面
                mWebView.loadUrl("javascript:inflateUserNameInput(\"" + extra + "\")");
            }
        }
    }
}
