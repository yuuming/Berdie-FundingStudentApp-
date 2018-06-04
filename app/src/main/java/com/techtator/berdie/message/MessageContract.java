package com.techtator.berdie.message;

import com.techtator.berdie.inboxStudent.MessageEntity;

/**
 * Created by user on 2018-04-19.
 */

public interface MessageContract {
    interface View {
        void notifyDataChanged();
    }

    interface Presenter {
        MessageEntity getMessageEntity(int position);
        int size();
    }
}
