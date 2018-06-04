package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBUserConversation  extends FBObject{

    private String userId;
    private String conversationId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConversationId() {
        return conversationId;
    }


    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public FBUserConversation(){}

    public FBUserConversation(String userId, String conversationId) {
        this.userId = userId;
        this.conversationId = conversationId;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user_id", userId);
        result.put("conversation_id", conversationId);

        return result;
    }

}
