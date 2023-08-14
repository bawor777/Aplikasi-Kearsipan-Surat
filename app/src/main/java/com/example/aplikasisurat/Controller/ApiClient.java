/*
 *  Aplikasi Surat
 *     ApiClient.java
 *     Created by Rifan Kurniawan on 14/8/2022
 *     email 	    : cah2701@gmail.com
 *     Copyright © 2022 Rifan Kurniawan. All rights reserved.
 */

package com.example.aplikasisurat.Controller;


import com.example.aplikasisurat.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = Config.BASE_URL;
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
//    public static ApiInterface getApiInterface(){
//        return getClient().create(ApiInterface.class);
//    }

