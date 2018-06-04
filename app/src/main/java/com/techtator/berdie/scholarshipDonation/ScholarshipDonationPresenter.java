package com.techtator.berdie.scholarshipDonation;

import com.techtator.berdie.model.ScholarshipDataModel;

public class ScholarshipDonationPresenter implements ScholarshipDOnationContract.Presenter {
    private ScholarshipDOnationContract.View view;
    ScholarshipDataModel scholarshipDataModel;

    public ScholarshipDonationPresenter(ScholarshipDOnationContract.View view, ScholarshipDataModel scholarshipDataModel){
        this.view = view;
        this.scholarshipDataModel = scholarshipDataModel;
    }
}
