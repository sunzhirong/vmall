package com.ysarch.vmall.domain.enums;

/**
 * Created by fysong on 2019-10-11
 **/
public enum SearchPageTag {
    SEARCH_HISTORY_PAGE("search_history_page"),
    SEARCH_CONTENT_PAGE("search_content_page");

    private String mValue;

    SearchPageTag(String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
