package com.techtator.berdie.newsDetail;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBNews;
import com.techtator.berdie.model.NewsDataModel;

import java.text.SimpleDateFormat;

/**
 * Created by user on 2018-04-01.
 */

public class NewsDetailPresenter implements NewsDetailContract.Presenter {
    private NewsDetailContract.View view;
    NewsDataModel newsDataModel;
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy"); //Change the format of TimeStampData

    public NewsDetailPresenter(final NewsDetailContract.View view, String id, LifecycleOwner owner) {
        this.view = view;
        this.newsDataModel = new NewsDataModel();

        newsDataModel.getNewsById(id).observe(owner, new Observer<FBNews>() {
            @Override
            public void onChanged(@Nullable FBNews data) {
                String titleText = data.getTitle();
                String formattedStr = sdf.format(data.getTimeStamp()); //get TimeStamp
//                String news_detail_date = data.getTimeStamp().toString();
                String bodyText = data.getBody();
                view.notifyDataChange(titleText,formattedStr,bodyText);
            }
        });
    }
}

