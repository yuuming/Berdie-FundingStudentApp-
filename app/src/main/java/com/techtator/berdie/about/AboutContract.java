package com.techtator.berdie.about;

public interface AboutContract {
    interface View{
        void onSuccess();
        void onError();
        void setLogoAndBody(String logo, String body);
    }

    interface Presenter{
    }
}
