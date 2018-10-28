package com.example.vinhtruong.newweek2.news.repository;


import com.example.vinhtruong.newweek2.model.SearchFilters;

public interface ArticleRepository {

    //void getData(DataListener listener,Integer page, String order, String query, String beginDate, String newsDesk);

    void getData(DataListener dataListener, Integer page, SearchFilters filters);

}
