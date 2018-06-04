package com.techtator.berdie.newsDetail;

/**
 * Created by user on 2018-04-01.
 */

public interface NewsDetailContract {
    interface View {
        void onSuccess();
        void onError();

        void notifyDataChange(String timeText, String titleText, String bodyText);
    }

    interface Presenter {
    }
}
