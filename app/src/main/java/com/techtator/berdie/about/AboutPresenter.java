package com.techtator.berdie.about;

import com.techtator.berdie.Models.FBModel.FBAbout;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.model.AboutDataModel;

import java.util.List;

public class AboutPresenter implements AboutContract.Presenter {
    final private AboutContract.View view;
    AboutDataModel aboutDataModel;

    public AboutPresenter(AboutContract.View v) {
        this.view = v;
        this.aboutDataModel = new AboutDataModel();

        aboutDataModel = new AboutDataModel();
        aboutDataModel.setAboutList(new AboutDataModel.OnChangeListListener() {
            @Override
            public void notifyChangedList(List<FBAbout> list) {
                if (list.isEmpty()) return;
                FBAbout data = list.get(0);
                view.setLogoAndBody(data.getLogo(), data.getBody());
            }
        });
    }


}
