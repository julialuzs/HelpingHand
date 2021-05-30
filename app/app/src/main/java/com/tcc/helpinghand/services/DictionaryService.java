package com.tcc.helpinghand.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DictionaryService {

    @GET("dictionary")
    Call<List<String>> getDictionary();
}
