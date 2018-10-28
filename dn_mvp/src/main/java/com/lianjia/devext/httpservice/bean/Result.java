package com.lianjia.devext.httpservice.bean;

import java.io.Serializable;

public class Result<T> implements Serializable {

    public int error_code;
    public String reason;
    public T result;
}
