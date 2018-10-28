package com.example.vinhtruong.newweek2.news.repository;



import com.example.vinhtruong.newweek2.model.Doc;

import java.util.List;

public interface DataListener {

    void onResponse(List<Doc> docs);

    void onError(String error);
}
