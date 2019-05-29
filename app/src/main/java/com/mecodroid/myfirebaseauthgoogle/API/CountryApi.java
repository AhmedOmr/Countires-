package com.mecodroid.myfirebaseauthgoogle.API;

import com.mecodroid.myfirebaseauthgoogle.Model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CountryApi {
    @GET("rest/v2/all")
    Call<List<Country>> getListcountry();


}
