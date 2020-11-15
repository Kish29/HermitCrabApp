package com.kish2.hermitcrabapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public interface IBaseModel {

    Object onServerSuccess(JSONObject jsonObject) throws JSONException;

    void onServerFailure(int serverCode);

    void onLocalNetworkFailure(Throwable throwable);

    /* presenter层必须实现的方式 */
    /* requestModelMethod最好在model层中进行定义 */
    interface OnRequestModelCallBack {

        void onModelSuccess(Map<BaseModel.MODEL_RET, Object> data, int requestModelMethod);

        void onModelFailure(Map<BaseModel.MODEL_RET, Object> data, int requestModelMethod);
    }
}
