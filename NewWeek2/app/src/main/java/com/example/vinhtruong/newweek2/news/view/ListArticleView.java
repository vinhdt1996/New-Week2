package com.example.vinhtruong.newweek2.news.view;



import com.example.vinhtruong.newweek2.model.Doc;

import java.util.List;

public interface ListArticleView {

    void showListArticle(List<Doc> docs);

    void showError();

    void showErrorNetwork();

    }
