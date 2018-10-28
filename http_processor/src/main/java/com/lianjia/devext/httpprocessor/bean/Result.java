package com.lianjia.devext.httpprocessor.bean;

import java.io.Serializable;

public class Result<T> implements Serializable {

    public int error_code;
    public String reason;
    public T result;
}
