package com.ysarch.vmall.domain.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fysong on 23/09/2020
 **/
public class HomeContentResult implements Serializable {
    @SerializedName("advertiseList")
    private List<HomeBannerBean> mHomeBannerBeans;

    private List<HomeRecommendBean> homeRecommend;

    public List<HomeRecommendBean> getHomeRecommend() {
        return homeRecommend;
    }

    public void setHomeRecommend(List<HomeRecommendBean> homeRecommend) {
        this.homeRecommend = homeRecommend;
    }

    public List<HomeBannerBean> getHomeBannerBeans() {
        return mHomeBannerBeans;
    }

    public void setHomeBannerBeans(List<HomeBannerBean> homeBannerBeans) {
        mHomeBannerBeans = homeBannerBeans;
    }
}
