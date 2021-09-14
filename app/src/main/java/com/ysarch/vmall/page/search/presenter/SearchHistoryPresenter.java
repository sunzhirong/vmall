package com.ysarch.vmall.page.search.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.search.SearchHistoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchHistoryPresenter extends BasePresenter<SearchHistoryFragment> {
    private List<String> mKeywords;
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

        if (mKeywords == null) {
            queryHistoryKeyword();
        }
        if (!mKeywords.contains(keyword)) {
            mKeywords.add(keyword);
            CacheHelper.putString(CacheKeys.KEY_SEARCH_HISTORY, getGson().toJson(mKeywords));
        }
    }

    public void deleteKeyword(String keyword){
        if(!TextUtils.isEmpty(keyword) && mKeywords.contains(keyword)){
            mKeywords.remove(keyword);
            CacheHelper.putString(CacheKeys.KEY_SEARCH_HISTORY, getGson().toJson(mKeywords));
        }
    }

    /**
     * 检索历史关键词
     *
     * @return
     */
    public List<String> queryHistoryKeyword() {
        if (mKeywords == null) {
            String keywordJson = CacheHelper.getString(CacheKeys.KEY_SEARCH_HISTORY);
            if (!TextUtils.isEmpty(keywordJson)) {
                try {
                    mKeywords = getGson().fromJson(keywordJson, new TypeToken<List<String>>() {
                    }.getType());
                } catch (JsonSyntaxException exp) {
                    exp.printStackTrace();
                    mKeywords = new ArrayList<>();
                }
            } else {
                mKeywords = new ArrayList<>();
            }
        }

        return mKeywords;
    }

    /**
     * 清理关键词
     */
    public void clearKeywords() {
        CacheHelper.removeKey(CacheKeys.KEY_SEARCH_HISTORY);
        if (mKeywords != null) {
            mKeywords.clear();
        }

        if(getV().mAdapter != null){
            getV().mAdapter.refreshData(null);
        }
    }

    public Gson getGson() {
        if (mGson == null)
            mGson = new Gson();
        return mGson;
    }
}
