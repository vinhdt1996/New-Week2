package com.example.vinhtruong.newweek2.utils;



import com.example.vinhtruong.newweek2.api.ApiService;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtil {

    private ApiService service;


    private static Retrofit builder() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client())
                .build();
    }

    private static OkHttpClient client() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url()
                            .newBuilder()
                            .build();
                    request = request.newBuilder()
                            .url(url)
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }

    public static  ApiService createService() {
        return builder().create(ApiService.class);
    }


}
