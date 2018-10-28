package com.lianjia.devext.presenter;

import com.lianjia.devext.model.IBaseModel;

import java.lang.ref.WeakReference;

/**
 * 协调View、Model
 * @param <V> View的泛型参数
 * @param <M> Model的泛型参数
 */
public abstract class BasePresenter<V, M extends IBaseModel> {

    protected WeakReference<V> mViewRef;
    
    protected M mModel;
    
    public abstract M createModel();

    // 进行绑定
    public void attachView(V view) {
        mViewRef = new WeakReference(view);
        mModel = createModel();
    }

    // 进行解绑
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
        }
        if (mModel != null) {
            mModel.onDestroy();
            mModel = null;
        }
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public abstract void fetch();
}
