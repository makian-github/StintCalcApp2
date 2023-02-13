package com.example.stintcalcapp2.controller;

/**
 * コールバックインターフェース
 */
public interface AsyncFunctionCallback {
    // 成功したらisSucceedにtrueを渡すようにする
    void onAsyncFunctionFinished(boolean isSucceed);
}