package com.lianjia.devext.androidh5;

import android.webkit.JavascriptInterface;

public class ImoocJsInterface {

  private JsBridge mJsBridge;

  public ImoocJsInterface(JsBridge jsBridge) {
    mJsBridge = jsBridge;
  }

  @JavascriptInterface
  public void setHtmlValue(String value) {
    mJsBridge.setTextViewValue(value);
  }
}
