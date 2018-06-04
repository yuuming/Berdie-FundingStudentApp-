package com.techtator.berdie.inboxStudent;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBMessage;
import com.techtator.berdie.model.MessageDataModel;
import com.techtator.berdie.model.UserDataModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2018-04-19.
 */

public class InboxStudentPresenter implements InboxStudentContract.Presenter {
    private InboxStudentContract.View view;
    MessageDataModel messageDataModel;
    List<FBMessage> messageListInBox;
    UserDataModel userDataModel;
    List<MessageEntity> messageEntities;

    public InboxStudentPresenter(final InboxStudentContract.View view, LifecycleOwner owner) {
        this.view = view;
        this.messageListInBox = new LinkedList<>();
        this.messageEntities = new LinkedList();
        messageDataModel = new MessageDataModel();
        userDataModel = new UserDataModel();
        userDataModel.refleshUserMap(owner);
        messageDataModel.getConversationRoomsByUserId(UserAuthManager.getInstance().getUserId()).observe(owner, new Observer<List<FBMessage>>() {
            @Override
            public void onChanged(@Nullable List<FBMessage> list) {
                messageEntities.clear();
                for(int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSenderSystem()) {
                        String senderId = list.get(i).getConversationId().substring(list.get(i).getReceiverId().length()+1);
                        MessageEntity message = new MessageEntity(list.get(i).getMessageId(), list.get(i).getConversationId(), userDataModel.getUserById(senderId), userDataModel.getUserById(list.get(i).getReceiverId()), list.get(i).getBody(), list.get(i).getTimeStamp(), list.get(i).isRead());
                        messageEntities.add(message);
                    } else {
                        MessageEntity message = new MessageEntity(list.get(i).getMessageId(), list.get(i).getConversationId(), userDataModel.getUserById(list.get(i).getSenderId()), userDataModel.getUserById(list.get(i).getReceiverId()), list.get(i).getBody(), list.get(i).getTimeStamp(), list.get(i).isRead());
                        messageEntities.add(message);
                    }
                }
                view.notifyMessageChanged();
            }
        });
    }

    public List<MessageEntity> getMessageEntities() {
        return messageEntities;
    }
}
