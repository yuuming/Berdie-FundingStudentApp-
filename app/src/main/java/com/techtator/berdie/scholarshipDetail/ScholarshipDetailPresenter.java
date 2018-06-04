package com.techtator.berdie.scholarshipDetail;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.model.ScholarshipDataModel;

/**
 * Created by bominkim on 2018-04-16.
 */

public class ScholarshipDetailPresenter implements ScholarshipDetailContract.Presenter {

    private ScholarshipDetailContract.View view;
    ScholarshipDataModel scholarshipDataModel;

    public ScholarshipDetailPresenter(final ScholarshipDetailContract.View view, String id, LifecycleOwner owner) {
        this.view = view;
        this.scholarshipDataModel = new ScholarshipDataModel();

        scholarshipDataModel.getScholarshipById(id).observe(owner, new Observer<FBScholarship>() {
            @Override
            public void onChanged(@Nullable FBScholarship data) {
                view.setImageView(data.getPicture());
                view.setTextBody(data.getBody());
                view.setScholarshipAmount(data.getScholarshipFee());
                view.setDetailDate(data.getDueDate());
                view.setScholarshipText(data.getHeader());
                view.setWebLink(data.getWebsiteLink());
            }
        });
    }

    @Override
    public void connectLink(String link) {

    }
}
