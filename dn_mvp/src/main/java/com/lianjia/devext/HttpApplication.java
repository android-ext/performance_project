package com.lianjia.devext;


import android.app.Application;

import com.lianjia.devext.httpservice.HttpProcessorProxy;
import com.lianjia.devext.httpservice.VolleyProcessor;


public class HttpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        HttpProcessorProxy.init(new VolleyProcessor(this));
//        HttpProcessorProxy.init(new OkHttpProcessor());
    }
}
