package com.example.vinhtruong.newweek2.news.repository;

import android.support.annotation.NonNull;
import android.util.Log;


import com.example.vinhtruong.newweek2.api.ApiService;
import com.example.vinhtruong.newweek2.model.Doc;
import com.example.vinhtruong.newweek2.model.ResponseWrapper;
import com.example.vinhtruong.newweek2.model.SearchFilters;
import com.example.vinhtruong.newweek2.utils.RetrofitUtil;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ArticleRepositoryImpl implements ArticleRepository{

    private ApiService service;

    public ArticleRepositoryImpl() { service = RetrofitUtil.createService(); }

    @Override
    public void getData(DataListener listener, Integer page, SearchFilters filters) {

        service.getArticles(ApiService.API_KEY,page, filters.getSortOrder(),
                filters.getQuery(), filters.isIgnoreBeginDate() ? null : filters.getBeginDateString(), filters.getNewsDesk())
                .enqueue(new Callback<ResponseWrapper>() {

                    @Override
                    public void onResponse(@NonNull Call<ResponseWrapper> call, @NonNull Response<ResponseWrapper> response) {
                        if (response.body() != null && Objects.requireNonNull(response.body()).getResponse().getDocs() != null) {
                            Log.d("AAA",call.request().toString());
                            List<Doc> docs = Objects.requireNonNull(response.body()).getResponse().getDocs();
                            listener.onResponse(docs);

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                        listener.onError(t.getMessage());
                    }
                });

    }

}
