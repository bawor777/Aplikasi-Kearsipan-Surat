/*
 *  Aplikasi Surat
 *     ApiInterface.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Controller;

import com.example.aplikasisurat.Model_Login.Login;
import com.example.aplikasisurat.Model_Surat.Surat;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

            @FormUrlEncoded
            @POST("login.php")
            Call<Login> loginResponse(
                    @Field("nip") String nip,
                    @Field("pass") String pass);

            @GET("list_surat.php")
            Call<Surat> getSurat(

            );

            @Multipart
            @POST("tambah_surat.php")
            Call<Surat> postSurat(
                    @Part MultipartBody.Part Image,
                    @Part("kode_cabang") RequestBody kode_cabang,
                    @Part("judul") RequestBody judul,
                    @Part("no_surat") RequestBody no_surat,
                    @Part("tanggal") RequestBody tanggal);



            @Multipart
            @POST("ubah_surat.php")
            Call<Surat> postUpdateSurat(
                    @Part MultipartBody.Part Image,
                    @Part("id") RequestBody id,
                    @Part("kode_cabang") RequestBody kode_cabang,
                    @Part("judul") RequestBody judul,
                    @Part("no_surat") RequestBody no_surat,
                    @Part("tanggal") RequestBody tanggal
//                    @Part("foto") RequestBody foto
            );


            @FormUrlEncoded
            @POST("delete_surat.php")
            Call<Surat> deleteSurat(
                    @Field("id") String id);

//    @FormUrlEncoded
//    @HTTP(method = "DELETE", path = "surat", hasBody = true)
//    Call<Surat> deleteSurat(@Field("id") String id);


}
