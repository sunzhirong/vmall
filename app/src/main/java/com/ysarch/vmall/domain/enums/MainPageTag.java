package com.ysarch.vmall.domain.enums;

/**
 * Created by fysong on 2019-10-11
 **/
public enum MainPageTag {
    MAIN_PAGE_FIRST_PAGE_TAG("main_page_first_page_tag"),
    MAIN_PAGE_CATE_TAG("main_page_cate_tag"),
    MAIN_PAGE_DISCOVER_TAG("main_page_discover_tag"),
    MAIN_PAGE_CART_TAG("main_page_cart_tag"),
    MAIN_PAGE_MINE_TAG("main_page_mine_tag");

    private String mValue;


    MainPageTag(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
