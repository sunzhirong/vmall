package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 18/09/2020
 **/
public class HomeBannerBean implements Serializable {

    /**
     * id : 9
     * name : 电影推荐广告
     * type : 1
     * pic : http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20181113/movie_ad.jpg
     * startTime : 2018-10-31T16:00:00.000+0000
     * endTime : 2018-11-23T16:00:00.000+0000
     * status : 1
     * clickCount : 0
     * orderCount : 0
     * url : https://www.baidu.com/
     * note : 电影推荐广告
     * sort : 100
     */

    private int id;
    private String name;
    private int type;
    private String pic ;
    private String startTime;
    private String endTime;
    private int status;
    private int clickCount;
    private int orderCount;
    private String url;
    private String note;
    private String keyword;
    private int sort;
    private int operateType;

    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }



    public int getOperateType(){
        return this.operateType;
    }

    public void setOperateType(int operateType){
        this.operateType = operateType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
