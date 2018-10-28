package com.example.vinhtruong.newweek2.news.presenter;


import com.example.vinhtruong.newweek2.model.Doc;
import com.example.vinhtruong.newweek2.model.SearchFilters;
import com.example.vinhtruong.newweek2.news.repository.ArticleRepository;
import com.example.vinhtruong.newweek2.news.repository.DataListener;
import com.example.vinhtruong.newweek2.news.view.ListArticleView;
import com.example.vinhtruong.newweek2.utils.NetWorkUtil;

import java.util.List;

public class ListArticlePresenterImpl implements ListArticlePresenter, DataListener {

    private ListArticleView mView;
    private ArticleRepository articleRepository;

    public ListArticlePresenterImpl(ListArticleView mView, ArticleRepository articleRepository){
        this.mView = mView;
        this.articleRepository = articleRepository;
    }
    @Override
    public void onResponse(List<Doc> docs) {

        mView.showListArticle(docs);
    }

    @Override
    public void onError(String error) {
        mView.showError();
    }


    @Override
    public void searchArticles(Integer page, SearchFilters searchFilters) {
        if (NetWorkUtil.isNetworkAvailable() || NetWorkUtil.isOnline()) {
            articleRepository.getData(this, page, searchFilters);
        }
        else mView.showErrorNetwork();
    }
}
