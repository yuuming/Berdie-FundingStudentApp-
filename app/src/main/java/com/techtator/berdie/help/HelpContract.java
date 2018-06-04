package com.techtator.berdie.help;

import com.techtator.berdie.Models.FBModel.FBHelp;

/**
 * Created by bominkim on 2018-04-03.
 */

public interface HelpContract {
    interface View {
        void onSuccess();
        void onError();
        void notifyHelpDataChanged();
    }

    interface Presenter {
        FBHelp get(int position);
        int size();
    }
}
