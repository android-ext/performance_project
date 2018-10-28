package com.lianjia.devext.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianjia.devext.dnmvp.R;
import com.lianjia.devext.presenter.BasePresenter;



public abstract class BaseFragment<P extends BasePresenter> extends Fragment {

   // 表示层的引用
   protected P mPresenter;

   public BaseFragment() {
      // Required empty public constructor
   }



   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_base, container, false);
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);

      mPresenter = createPresenter();
      if (mPresenter != null) {
         mPresenter.attachView(this);
      }
   }

   protected abstract P createPresenter();

   @Override
   public void onDestroy() {
      super.onDestroy();
      if (mPresenter != null) {
         mPresenter.detachView();
      }
   }
}
