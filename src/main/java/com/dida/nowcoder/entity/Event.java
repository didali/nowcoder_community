package com.dida.nowcoder.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//事件实体
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    //主题
    private String topic;

    //事件触发人
    private int userId;

    //实体的类型 (用来表示这个事件是发生在哪个实体之上，是点个赞，还是回复....)
    private int EntityType;

    //实体id
    private int EntityId;

    //实体作者 (帖子作者、评论作者等)
    private int EntityUserId;

    //用来存储其他的数据的业务
    private Map<String, Object> data = new HashMap<>();

    public String getTopic() {
        return topic;
    }

    public Event setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public Event setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getEntityType() {
        return EntityType;
    }

    public Event setEntityType(int entityType) {
        EntityType = entityType;
        return this;
    }

    public int getEntityId() {
        return EntityId;
    }

    public Event setEntityId(int entityId) {
        EntityId = entityId;
        return this;
    }

    public int getEntityUserId() {
        return EntityUserId;
    }

    public Event setEntityUserId(int entityUserId) {
        EntityUserId = entityUserId;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Event setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return "Event{" +
                "topic='" + topic + '\'' +
                ", userId=" + userId +
                ", EntityType=" + EntityType +
                ", EntityId=" + EntityId +
                ", EntityUserId=" + EntityUserId +
                ", data=" + data +
                '}';
    }
}
