package com.techtator.berdie.newsDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBNews;
import com.techtator.berdie.R;

/**
 * Created by user on 2018-04-01.
 */

public class NewsDetailFragment extends Fragment implements NewsDetailContract.View {

    private TextView news_detail_title;
    private TextView news_detail_date;
    private TextView news_body;


    private NewsDetailContract.Presenter presenter;// = new NewsDetailPresenter(this, new NewsDataModel());

    public NewsDetailFragment(){

    }

    public static NewsDetailFragment newInstance(FBNews item) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        //put as news_id
        args.putString("news_id", item.getNewsId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.news);

        String id = getArguments().getString("news_id");
// create new presenter by id
        presenter = new NewsDetailPresenter(this, id, this);
        return view;
    }
    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        news_detail_title = (TextView) view.findViewById(R.id.news_detail_title);
        news_detail_date = (TextView) view.findViewById(R.id.news_detail_date);
        news_body = (TextView) view.findViewById(R.id.news_body);
    }

    @Override
    public void notifyDataChange(String titleText, String timeText,String bodyText) {
        news_detail_date.setText(timeText);
        news_detail_title.setText(titleText);
        news_body.setText(bodyText);
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onError() {
    }
}
