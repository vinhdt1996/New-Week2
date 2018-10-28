package com.example.vinhtruong.newweek2.api;
import com.example.vinhtruong.newweek2.model.ResponseWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    String API_KEY = "c5ebedbb85d44120a198556ce60e2006";
    String BASE_URL = "https://api.nytimes.com";

    @GET("/svc/search/v2/articlesearch.json")
    Call<ResponseWrapper> getArticles(@Query("api_key") String api_key,
                                      @Query("page") Integer page,
                                      @Query("sort") String order,
                                      @Query("q") String search,
                                      @Query("begin_date") String beginDate,
                                      @Query("fq") String newsDesk
    );
}
