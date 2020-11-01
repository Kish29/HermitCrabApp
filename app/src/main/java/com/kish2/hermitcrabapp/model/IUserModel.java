package com.kish2.hermitcrabapp.model;


import com.kish2.hermitcrabapp.utils.network.Api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUserModel {

    @POST(Api.API_USER_REG)
    Call<ResponseBody> userRegister(@Query("mobile") String mobile, @Query("code") String code);

    @POST(Api.API_USERNAME_UPDATE)
    Call<ResponseBody> usernameUpdate(@Query("uid") long uid, @Query("username") String username);

    @POST(Api.API_PASSWORD_UPDATE)
    Call<ResponseBody> passwordUpdate(@Query("uid") long uid, @Query("password") String password);

    @POST(Api.API_AUTH_BY_USERNAME)
    Call<ResponseBody> authByUsername(@Query("username") String username, @Query("password") String password);

    @POST(Api.API_AUTH_BY_MOBILE_CODE)
    Call<ResponseBody> authByMobile(@Query("mobile") String mobile, @Query("code") String code);

    Call<ResponseBody> getDozenOfUsers(int num);
}
