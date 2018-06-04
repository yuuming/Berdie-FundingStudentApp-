package com.techtator.berdie.message;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBMessage;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.inboxStudent.MessageEntity;
import com.techtator.berdie.model.MessageDataModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2018-04-19.
 */

public class MessagePresenter implements MessageContract.Presenter{
    private MessageContract.View view;
    MessageDataModel messageDataModel;
    List<MessageEntity> messageEntities;
    FBUser me;
    FBUser someone;
    String conversationId;
    UserAuthManager userAuthManager;


    public MessagePresenter(final MessageContract.View view, final FBUser someone, LifecycleOwner lifecycleOwner) {
        this.view = view;
        this.someone = someone;
        this.messageEntities = new LinkedList();
        this.userAuthManager = new UserAuthManager();
        this.me = UserAuthManager.getInstance().getUser();
        // someone who wants to start chatting. In other words, the UId who already log in.
        //userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        messageDataModel = new MessageDataModel();
        messageDataModel.setMessageListByConversationId(me.getId() + "-" + someone.getId()).observe(lifecycleOwner, new Observer<List<FBMessage>>() {
            @Override
            public void onChanged(@Nullable List<FBMessage> list) {
                messageEntities.clear();
                FBUser sender, receiver;
                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).getSenderId().equals(me.getId())) {
                        sender = me;
                        receiver = someone;
                    } else {
                        if (list.get(i).getSenderId().equals(someone.getId())) {
                            sender = someone;
                        } else {
                            sender = new FBUser();
                        }
                        receiver = me;
                    }
                    MessageEntity message = new MessageEntity(list.get(i).getMessageId(), conversationId, sender, receiver, list.get(i).getBody(), list.get(i).getTimeStamp(), list.get(i).isRead());
                    messageEntities.add(message);
                }
                view.notifyDataChanged();
            }
        });

        this.conversationId = me.getId() + "-" + someone.getId();
    }


    public List<MessageEntity> getMessageEntities() {
        return messageEntities;
    }

    @Override
    public MessageEntity getMessageEntity(int position) {
        return messageEntities.get(position);
    }

    @Override
    public int size() {
        return messageEntities.size();
    }

    public void createMessage(String _senderId, String _receiverId, String _body) {
        messageDataModel.addMessage(_senderId, _receiverId, _body);
    }

    public void makeMessageRead(List<MessageEntity> list) {
        for(int i = 0; i<list.size(); i++) {
            if(list.get(i).getSender()==someone || list.get(i).getSender().getId()==null) {
                messageDataModel.setMessageReadByMessageId(list.get(i).getMessageId());
                messageDataModel.setSomeoneMessageReadByConversationId(someone.getId(), me.getId());
            }
        }
    }
}
