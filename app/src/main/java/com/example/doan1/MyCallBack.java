package com.example.doan1;

public interface MyCallBack<T> {
    void onSuccess(T result);

    void onFailure(Throwable t);
}