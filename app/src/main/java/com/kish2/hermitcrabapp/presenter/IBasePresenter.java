package com.kish2.hermitcrabapp.presenter;

import com.kish2.hermitcrabapp.model.BaseModel;

import java.util.Map;

/* 同理，BasePresenter必须给予实现约束*/
public interface IBasePresenter {

    void detachView();

    /* model端数据的成功与失败 */
    void onModelSuccess(Map<BaseModel.MODEL_RET, Object> data);

    void onModelFailure(Map<BaseModel.MODEL_RET, Object> data);
}
