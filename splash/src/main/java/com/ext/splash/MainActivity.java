package com.ext.splash;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewStub;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private Handler mHandler = new Handler();
	private SplashFragment splashFragment;
	private ViewStub viewStub;

	private void updateText(String tips) {
		((TextView)findViewById(R.id.tv_tips)).setText(tips);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		splashFragment = new SplashFragment();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.frame, splashFragment);
		transaction.commit();
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				updateText("加载完成");
			}
		}, 2500);
		
		viewStub = (ViewStub)findViewById(R.id.content_viewstub);
		//1.判断当窗体加载完毕的时候,立马再加载真正的布局进来
		getWindow().getDecorView().post(new Runnable() {
			
			@Override
			public void run() {
				// 开启延迟加载
				mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						//将viewstub加载进来
						viewStub.inflate();
					}
				} );
			}
		});
		
		
		//2.判断当窗体加载完毕的时候执行,延迟一段时间做动画。
		getWindow().getDecorView().post(new Runnable() {
			
			@Override
			public void run() {
				// 开启延迟加载,也可以不用延迟可以立马执行（我这里延迟是为了实现fragment里面的动画效果的耗时）
				mHandler.postDelayed(new DelayRunnable(MainActivity.this, splashFragment) ,2000);
//				mHandler.post(new DelayRunnable());
				
			}
		});
		//3.同时进行异步加载数据
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	static class DelayRunnable implements Runnable{
		private WeakReference<Context> contextRef;
		private WeakReference<SplashFragment> fragmentRef;
		
		public DelayRunnable(Context context, SplashFragment f) {
			contextRef = new WeakReference<Context>(context);
			fragmentRef = new WeakReference<SplashFragment>(f);
		}

		@Override
		public void run() {
			// 移除fragment
			if(contextRef!=null){
				SplashFragment splashFragment = fragmentRef.get();
				if(splashFragment==null){
					return;
				}
				FragmentActivity activity = (FragmentActivity) contextRef.get();
				FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
				transaction.remove(splashFragment);
				transaction.commit();
				
			}
		}
		
	}

}
