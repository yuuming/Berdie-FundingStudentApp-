package com.techtator.berdie.scholarshipDetail;

import java.util.Date;

/**
 * Created by bominkim on 2018-04-16.
 */

public interface ScholarshipDetailContract {

    interface View {

        void setScholarshipText(String text);
        void setScholarshipAmount(Double amount);
        void setDetailDate(Date date);
        void setTextBody(String textBody);
        void setImageView(String image);
        void setWebLink(String link);

    }

    interface Presenter {
        void connectLink(String link);
    }

}
