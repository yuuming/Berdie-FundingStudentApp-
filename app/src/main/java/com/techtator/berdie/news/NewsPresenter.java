package com.techtator.berdie.news;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBNews;
import com.techtator.berdie.model.NewsDataModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cemserin on 2018-04-03.
 */

public class NewsPresenter implements  NewsContract.Presenter {

    private NewsContract.View view;
    private final NewsDataModel newsDataModel;
    private final List<FBNews> newsList;

    public NewsPresenter(final NewsContract.View v, LifecycleOwner owner) {
        this.view = v;
        newsList = new LinkedList<>();
        newsDataModel = new NewsDataModel();

    newsDataModel.getActiveNewsList().observe(owner, new Observer<List<FBNews>>() {
        @Override
        public void onChanged(@Nullable List<FBNews> list) {
            newsList.clear();
            newsList.addAll(list);
            view.notifyDataChanged();
            view.notifyNewsDataChanged();

        }
    });
}

@Override
public FBNews get(int position){
    return newsList.get(position);
}

@Override
public int size(){
    return newsList.size();
}



}
