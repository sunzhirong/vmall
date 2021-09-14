package com.ysarch.vmall.page.search.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchPresenter extends BasePresenter<SearchFragment> {
    private Gson mGson;

    /**
     * 更新历史关键词
     *
     * @param keyword
     */
    public void refreshHistoryCache(String keyword) {
        if (keyword == null || TextUtils.isEmpty(keyword.trim())) {
            return;
        }

        keyword = keyword.trim();
        String keywordJson = CacheHelper.getString(CacheKeys.KEY_SEARCH_HISTORY);
        List<String> keywordSet = null;

        if (!TextUtils.isEmpty(keywordJson)) {
            keywordSet = getGson().fromJson(keywordJson, new TypeToken<List<String>>() {
            }.getType());

            if (!keywordSet.contains(keyword)) {
                keywordSet.add(keyword);
                CacheHelper.putString(CacheKeys.KEY_SEARCH_HISTORY, getGson().toJson(keywordSet));
            }
        } else {
            keywordSet = new ArrayList<>();
            keywordSet.add(keyword);
            CacheHelper.putString(CacheKeys.KEY_SEARCH_HISTORY, getGson().toJson(keywordSet));
        }
    }

    public Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson;
    }

}
