package com.kish2.hermitcrabapp.model.fragments;

import com.kish2.hermitcrabapp.bean.SecondHandPreview;
import com.kish2.hermitcrabapp.model.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondHandModel extends BaseModel {

    public SecondHandModel(OnRequestModelCallBack callBack) {
        super(callBack);
    }

    public List<SecondHandPreview> getSecondHandPreview(int n) {
        ArrayList<SecondHandPreview> previews = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            SecondHandPreview bean = new SecondHandPreview();
            bean.setDesirePeople(i);
            bean.setProductPrice(i);
            bean.setProductTag("电子产品" + i);
            bean.setProductTitle("这是标题" + i);
            bean.setVenderName("这是Vender" + i);
            previews.add(bean);
        }
        return previews;
    }

    @Override
    protected int getModelRequestCode() {
        return 0;
    }

    @Override
    public Object onServerSuccess(JSONObject jsonObject) throws JSONException {
        return null;
    }
}
