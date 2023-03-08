package com.dida.nowcoder.service;

import com.dida.nowcoder.entity.Message;

import java.util.List;

public interface MessageService {

    //获取当前用户的会话列表 (每个会话只显示一条最新的私信)
    public List<Message> getConversations(int userId, int offset, int limit);

    //获取当前用户的会话数量
    public int getConversationCount(int userId);

    //获取某个会话所包含的私信列表
    public List<Message> getLetters(String conversationId, int offset, int limit);

    //获取某个会话的私信数量
    public int getLetterCount(String conversationId);

    //获取未读的消息数量
    int getLetterUnreadCount(int userId, String conversationId);
}
