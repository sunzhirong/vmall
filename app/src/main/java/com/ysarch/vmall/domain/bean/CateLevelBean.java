package com.ysarch.vmall.domain.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 09/09/2020
 **/
public class CateLevelBean implements Serializable {
    /**
     * id : 7
     * parentId : 1
     * name : 外套
     * level : 1
     * productCount : 100
     * productUnit : 件
     * navStatus : 1
     * showStatus : 1
     * sort : 0
     * icon :
     * keywords : 外套
     * children : []
     */

    private int id;
    private int parentId;
    private String name;
    private int level;
    private int productCount;
    private String productUnit;
    private int navStatus;
    private int showStatus;
    private int sort;
    private String icon;
    private String keywords;
    private List<CateLevelBean> children;


    private String enName;
    private String khName;
    private String pic;
    private int operateType;
    private String url;
    private String keyword;


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

    /**
     * local param
     */
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getNavStatus() {
        return navStatus;
    }

    public void setNavStatus(int navStatus) {
        this.navStatus = navStatus;
    }

    public int getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<CateLevelBean> getChildren() {
        return children;
    }

    public void setChildren(List<CateLevelBean> children) {
        this.children = children;
    }
}
