package com.example.vinhtruong.newweek2.news.view;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.vinhtruong.newweek2.DetailActivity;
import com.example.vinhtruong.newweek2.R;
import com.example.vinhtruong.newweek2.SearchFilterFragment;
import com.example.vinhtruong.newweek2.adapter.RecyclerViewAdapter;
import com.example.vinhtruong.newweek2.model.Doc;
import com.example.vinhtruong.newweek2.model.SearchFilters;
import com.example.vinhtruong.newweek2.news.presenter.ListArticlePresenter;
import com.example.vinhtruong.newweek2.news.presenter.ListArticlePresenterImpl;
import com.example.vinhtruong.newweek2.news.repository.ArticleRepository;
import com.example.vinhtruong.newweek2.news.repository.ArticleRepositoryImpl;
import com.example.vinhtruong.newweek2.utils.ItemClickSupport;
import com.example.vinhtruong.newweek2.utils.NetWorkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ListArticleView, SearchFilterFragment.OnFragmentInteractionListener {
    @BindView(R.id.rvArticles)
    RecyclerView rvArticles;
    @BindView(R.id.swipe_refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbarMain)
    Toolbar toolbar;
    ListArticlePresenter presenter;
    RecyclerViewAdapter adapter;
    SearchFilters searchFilters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NetWorkUtil.setContext(this);
        //Create searchFilters
        searchFilters = new SearchFilters();
        // Create repository
        ArticleRepository repository = new ArticleRepositoryImpl();
        presenter = new ListArticlePresenterImpl(this, repository);
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("   New York Times");
        //setup view
        setupView();
    }

    private void setupView() {
        adapter = new RecyclerViewAdapter(this );
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        rvArticles.setLayoutManager(layoutManager);
        presenter.searchArticles(0,searchFilters);

        ItemClickSupport.addTo(rvArticles).setOnItemClickListener((recyclerView, position, v) -> {
            Doc article = adapter.getItem(position);
            if (article != null) {
                Intent displayArticleIntent = new Intent(getApplicationContext(), DetailActivity.class);
                displayArticleIntent.putExtra("url", article.getWebUrl());
                startActivity(displayArticleIntent);
            } else {
                Snackbar.make(v, "Internal error. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.white,
                R.color.colorPrimary,
                android.R.color.white);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.searchArticles(0,searchFilters);
            swipeRefreshLayout.setRefreshing(false);
        });
        rvArticles.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        int searchEditId = android.support.v7.appcompat.R.id.search_src_text;
        EditText et = searchView.findViewById(searchEditId);
        et.setHint(getResources().getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchFilters.setQuery(s);
                presenter.searchArticles(0,searchFilters);
                searchView.clearFocus();
                return true;

            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                item.expandActionView();
                searchView.requestFocus();
                return true;

            case R.id.action_filter:
                showFilterFragment();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFilterFragment() {
        FragmentManager fm = getSupportFragmentManager();
        SearchFilterFragment filterDialogFragment = SearchFilterFragment.newInstance(searchFilters);
        filterDialogFragment.show(fm, "fragment_filter");
    }

    @Override
    public void onFinishDialog(SearchFilters filters) {
        // update search filters;
        searchFilters = filters;
        // search articles using updated filters;
        presenter.searchArticles(0,searchFilters);
    }

    @Override
    public void showListArticle(List<Doc> docs) {
        adapter.setData(docs);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "May be have error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorNetwork() {
        Snackbar snackbar = Snackbar.make(rvArticles, "Network Error. Please connect to Internet and try again", Snackbar.LENGTH_INDEFINITE)
                .setAction("Wi-Fi Settings", v -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)));
        snackbar.show();
    }


}