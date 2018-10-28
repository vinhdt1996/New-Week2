package com.example.vinhtruong.newweek2.news.presenter;


import com.example.vinhtruong.newweek2.model.SearchFilters;

public interface ListArticlePresenter {

    void searchArticles(Integer page, SearchFilters searchFilters);

}