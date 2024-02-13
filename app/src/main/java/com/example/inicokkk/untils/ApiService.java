package com.example.inicokkk.untils;

import okhttp3.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/index_post")
    @FormUrlEncoded
    Call postLogin(@Field("name") String username, @Field("pass") String password);
}