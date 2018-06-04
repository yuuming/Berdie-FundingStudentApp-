package com.techtator.berdie.news;

import com.techtator.berdie.Models.FBModel.FBNews;

/**
 * Created by cemserin on 2018-04-03.
 */

public interface NewsContract {
    interface View {
        void onSuccess();
        void onError();
        void notifyDataChanged();
        void notifyNewsDataChanged();
    }

    interface Presenter {
        FBNews get(int position);
        int size();
    }

}
