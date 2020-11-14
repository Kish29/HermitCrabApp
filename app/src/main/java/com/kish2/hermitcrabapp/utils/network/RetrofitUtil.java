package com.kish2.hermitcrabapp.utils.network;

import retrofit2.Retrofit;

public class RetrofitUtil {

    public static Retrofit getBaseRequest() {
        return new Retrofit.Builder().baseUrl(Api.API_BASE).build();
    }

}
