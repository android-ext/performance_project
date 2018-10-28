package com.lianjia.devext.contract;

import com.lianjia.devext.httpservice.HttpCallback;
import com.lianjia.devext.httpservice.ICallback;
import com.lianjia.devext.httpservice.bean.ProvinceBean;
import com.lianjia.devext.model.IBaseModel;
import com.lianjia.devext.presenter.BasePresenter;
import com.lianjia.devext.view.IBaseView;

import java.util.List;

public interface IMainContract {

    /**
     * 在activity页面进行UI操作的接口类
     */
    interface IMainView extends IBaseView {

        void onSuccess(List<ProvinceBean> data);

        void onFailure(int errorCode, String message);
    }

    /**
     * 在activity页面执行数据操作的接口类
     */
    interface IMainModel extends IBaseModel {

        void loadGirl(HttpCallback<List<ProvinceBean>> callBack);
    }

    abstract class IMainPresenter extends BasePresenter<IMainView, IMainModel> {

    }
}
