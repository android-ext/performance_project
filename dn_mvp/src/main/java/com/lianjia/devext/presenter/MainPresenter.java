package com.lianjia.devext.presenter;

import com.lianjia.devext.contract.IMainContract;
import com.lianjia.devext.httpservice.HttpCallback;
import com.lianjia.devext.httpservice.bean.ProvinceBean;
import com.lianjia.devext.model.MainModelImpl;

import java.util.List;

public class MainPresenter extends IMainContract.IMainPresenter {


    @Override
    public IMainContract.IMainModel createModel() {
        return new MainModelImpl();
    }

    @Override
    public void fetch() {
        if (isViewAttached()) {
            mViewRef.get().showLoading();
            if (mModel != null) {

                mModel.setParams(mViewRef.get().initParams());
                mModel.loadGirl(new HttpCallback<List<ProvinceBean>>() {

                    @Override
                    public void onSuccess(List<ProvinceBean> result) {
                        if (isViewAttached()) {
                            mViewRef.get().onSuccess(result);
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        if (isViewAttached()) {
                            mViewRef.get().onFailure(errorCode, errorMessage);
                        }
                    }
                });
            }
        }
    }
}
