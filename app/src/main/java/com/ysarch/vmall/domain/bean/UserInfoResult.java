package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 14/09/2020
 **/
public class UserInfoResult implements Serializable {

    /**
     * id : 12.0
     * memberLevelId : 4.0
     * username : 沙世
     * password : $2a$10$I/J0ex8PlRqp2R5NscwQN.mKoOtBz.pMGzoWqqOXWWEaWX2YWcdT2
     * phone : 13535075370
     * status : 1.0
     * createTime : 2020-09-07T02:28:33.000+0000
     * icon: 头像
     */

    private long id;
    private long memberLevelId;
    private String username;
    private String password;
    private String phone;
    private int status;
    private String createTime;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(long memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
