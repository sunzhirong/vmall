package com.ysarch.vmall.domain.bean;

public class HomeRecommendBean {

    /**
     * id : 1
     * name : 中文
     * enName : 英文
     * khName : 柬文
     * pic : http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20210119/202101191239/WING.jpeg
     * operateType : 6
     * url : www.baidu.com
     * keyword :
     * sort : 0
     * showStatus : true
     */

    private int id;
    private String name;
    private String enName;
    private String khName;
    private String pic;
    private int operateType;
    private String url;
    private String keyword;
    private int sort;
    private boolean showStatus;

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

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getKhName() {
        return khName;
    }

    public void setKhName(String khName) {
        this.khName = khName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isShowStatus() {
        return showStatus;
    }

    public void setShowStatus(boolean showStatus) {
        this.showStatus = showStatus;
    }
}
