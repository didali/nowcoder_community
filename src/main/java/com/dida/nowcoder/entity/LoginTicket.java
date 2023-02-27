package com.dida.nowcoder.entity;

import java.io.Serializable;
import java.util.Date;

public class LoginTicket implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private int id;

    //用户id
    private int userId;

    //登录凭证
    private String ticket;

    //状态(0-正常有效， 1-过期无效)
    private int status;

    //过期时间
    private Date expired;

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
