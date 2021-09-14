package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 27/10/2020
 **/
public class MsgBean implements Serializable {

    /**
     * content : string
     * createTime : 2020-10-26T16:30:04.181Z
     * hasread : 0
     * id : 0
     * link : string
     * memberId : 0
     * title : string
     * type : 0
     * updateTime : 2020-10-26T16:30:04.181Z
     */

    private String content;
    private String createTime;
    private int hasread;
    private long id;
    private String link;
    private long memberId;
    private String title;
    private int type;
    private String updateTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getHasread() {
        return hasread;
    }

    public void setHasread(int hasread) {
        this.hasread = hasread;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
