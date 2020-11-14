package com.kish2.hermitcrabapp.model;

import org.json.JSONException;

public interface IBaseModel {

    void onServerSuccess(String rawData) throws JSONException;

    void onServerFailure(int serverCode);

    void onLocalNetworkFailure(Throwable throwable);
}
