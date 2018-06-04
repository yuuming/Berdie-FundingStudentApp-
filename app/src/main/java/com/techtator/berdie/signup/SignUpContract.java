package com.techtator.berdie.signup;

/**
 * Created by yuminakamura on 2018-04-01.
 */

public interface SignUpContract {
    interface View {
        void onSuccess();
        void onError();

    }

    interface Presenter {

        boolean createUser(String userId, long selectedItemId, String firstname, String lastname, String date, String email, String phone);
    }
}
