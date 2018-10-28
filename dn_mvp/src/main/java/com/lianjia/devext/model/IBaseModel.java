package com.lianjia.devext.model;

import java.util.Map;

public interface IBaseModel {

   void setParams(Map<String, Object> params);

   void onDestroy();
}
