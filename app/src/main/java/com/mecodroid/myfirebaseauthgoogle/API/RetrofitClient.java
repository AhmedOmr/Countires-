package com.mecodroid.myfirebaseauthgoogle.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static final String API_URL = "https://restcountries.eu/";

    public static Retrofit getinstance() {
        return new Retrofit.Builder()
                .baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }
}

