package com.aran.ocrapp.api

import com.aran.ocrapp.helper.PostResponse
import com.aran.ocrapp.helper.Responses
import com.aran.ocrapp.helper.SignInResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    @Headers("token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHBpcmVzSW4iOiIxIHllYXJzIiwiYXVkaWVuY2UiOiJtb2JpbGUiLCJpc3N1ZXIiOiJiYWNrZW5kIiwiaWF0IjoxNjU1Mjk4ODQ1fQ._r5DlZobWk8pht9veDpWW_eyHcz7xQk1aZ4y1AjXfaU")
    fun createAccount(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<Responses>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<SignInResponse>

    @FormUrlEncoded
    @POST("load-model")
    @Headers("token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHBpcmVzSW4iOiIxIHllYXJzIiwiYXVkaWVuY2UiOiJtb2JpbGUiLCJpc3N1ZXIiOiJiYWNrZW5kIiwiaWF0IjoxNjU1Mjk4ODQ1fQ._r5DlZobWk8pht9veDpWW_eyHcz7xQk1aZ4y1AjXfaU")
    fun postID(
        @Field("img") img: String
    ) : Call<PostResponse>

    @FormUrlEncoded
    @Headers("token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHBpcmVzSW4iOiIxIHllYXJzIiwiYXVkaWVuY2UiOiJtb2JpbGUiLCJpc3N1ZXIiOiJiYWNrZW5kIiwiaWF0IjoxNjU1Mjk4ODQ1fQ._r5DlZobWk8pht9veDpWW_eyHcz7xQk1aZ4y1AjXfaU")
    @GET("user")
    fun getData(
        @Field("email") email: String
    ) : Call<PostResponse>

    @FormUrlEncoded
    @Headers("token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHBpcmVzSW4iOiIxIHllYXJzIiwiYXVkaWVuY2UiOiJtb2JpbGUiLCJpc3N1ZXIiOiJiYWNrZW5kIiwiaWF0IjoxNjU1Mjk4ODQ1fQ._r5DlZobWk8pht9veDpWW_eyHcz7xQk1aZ4y1AjXfaU")
    @POST("register-profile")
    fun postAllData(
        @Field("nik") nik: String,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("provinsi") provinsi: String,
        @Field("kabupaten") kabupaten: String
    ) : Call<Responses>
}