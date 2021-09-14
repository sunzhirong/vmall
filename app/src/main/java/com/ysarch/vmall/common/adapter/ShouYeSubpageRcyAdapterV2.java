package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.ysarch.uibase.recyclerview.AbsFRecyclerAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CateHomeItemVH;
import com.ysarch.vmall.common.adapter.viewholder.GoodsItemVHV2;
import com.ysarch.vmall.common.adapter.viewholder.HomeBannerVH;
import com.ysarch.vmall.common.adapter.viewholder.HomeCatesGridVH;
import com.ysarch.vmall.common.adapter.viewholder.RecGoodsHeader;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.HomeBannerBean;
import com.ysarch.vmall.domain.bean.HomeRecommendBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by fysong on 09/09/2020
 **/
public class ShouYeSubpageRcyAdapterV2 extends AbsFRecyclerAdapter {

    public static final int TYPE_BANNER = 1;
    public static final int TYPE_GOODS_HEADER = 2;
    public static final int TYPE_GOODS = 3;
    public static final int TYPE_CATES_GRID = 4;

    private LayoutInflater mInflater;
    private BeeGlide mBeeGlide;
    private Context mContext;


    private CateHomeItemVH.Callback mCallback;
    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;
    private HomeBannerVH.Callback mBannerCallback;

    public ShouYeSubpageRcyAdapterV2(Context context) {
        mInflater = LayoutInflater.from(context);
        mBeeGlide = BeeGlide.with(context);
        mContext = context;
    }

    public void setBannerCallback(HomeBannerVH.Callback bannerCallback) {
        mBannerCallback = bannerCallback;
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setCallback(CateHomeItemVH.Callback callback) {
        Log.e("niko","2");
        mCallback = callback;
    }

    /**
     * 最受 欢迎页面数据
     *
     * @param bannerBeans
     * @param goodsItemBeans
     */
    public void refreshWelcomeDatas(List<HomeBannerBean> bannerBeans, List<HomeRecommendBean> homeRecommendBeans, List<GoodsItemBeanV2> goodsItemBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(bannerBeans))
            mMixData.add(new ItemDataWrapper(TYPE_BANNER, bannerBeans, mBannerCallback));
        if(CollectionUtils.isNotEmpty(homeRecommendBeans)) {
            mMixData.add(new ItemDataWrapper(TYPE_CATES_GRID, homeRecommendBeans, mCallback));
        }
        mMixData.add(new ItemDataWrapper(TYPE_GOODS_HEADER, null));

        if (CollectionUtils.isNotEmpty(goodsItemBeans)) {
            for (int i = 0; i < goodsItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(TYPE_GOODS, goodsItemBeans.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }


    public void appendWelcomeDatas(List<GoodsItemBeanV2> goodsItemBeans) {
        if (CollectionUtils.isNotEmpty(goodsItemBeans)) {
            for (int i = 0; i < goodsItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(TYPE_GOODS, goodsItemBeans.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
            }

            notifyItemRangeChanged(getItemCount() - goodsItemBeans.size(), goodsItemBeans.size());
        }
    }

    /**
     * 分类数据
     *
     * @param cateLevelBeans
     * @param goodsItemBeans
     */
    public void refreshSubCateDatas(List<CateLevelBean> cateLevelBeans, List<GoodsItemBeanV2> goodsItemBeans) {
        mMixData.clear();
        Log.e("niko", JSON.toJSONString(cateLevelBeans));
        if(CollectionUtils.isNotEmpty(cateLevelBeans)){
            List<HomeRecommendBean> homeRecommendBeans = new ArrayList<>();
            for (CateLevelBean bean :cateLevelBeans){
                HomeRecommendBean homeRecommendBean = new HomeRecommendBean();
                homeRecommendBean.setId(bean.getId());
                homeRecommendBean.setName(bean.getName());
                homeRecommendBean.setEnName(bean.getEnName());
                homeRecommendBean.setKhName(bean.getKhName());
                homeRecommendBean.setPic(bean.getIcon());
                homeRecommendBean.setOperateType(bean.getOperateType());
                homeRecommendBean.setUrl(bean.getUrl());
                homeRecommendBean.setKeyword(bean.getKeywords());
                homeRecommendBean.setSort(bean.getSort());
                homeRecommendBean.setShowStatus(bean.getShowStatus()==1);
//                homeRecommendBean.setOperateType(bean.getOperateType());
                homeRecommendBean.setOperateType(Constants.OPERATETYPE_SEARCH_BY_KEYWORD);
                homeRecommendBeans.add(homeRecommendBean);
            }
            mMixData.add(new ItemDataWrapper(TYPE_CATES_GRID, homeRecommendBeans, mCallback));

        }
        if (CollectionUtils.isNotEmpty(goodsItemBeans)) {
            for (int i = 0; i < goodsItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(TYPE_GOODS, goodsItemBeans.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 分类数据
     *
     * @param goodsItemBeans
     */
    public void refreshGoods(List<GoodsItemBeanV2> goodsItemBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(goodsItemBeans)) {
            for (int i = 0; i < goodsItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(TYPE_GOODS, goodsItemBeans.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
            }
        }
        notifyDataSetChanged();
    }


    /**
     * 分类分页数据
     *
     * @param goodsItemBeans
     */
    public void appendGoods(List<GoodsItemBeanV2> goodsItemBeans) {
        if (CollectionUtils.isNotEmpty(goodsItemBeans)) {
            for (int i = 0; i < goodsItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(TYPE_GOODS, goodsItemBeans.get(i)).setOnItemClickListener(mOnItemClickListener));
            }
            notifyItemRangeInserted(getItemCount() - goodsItemBeans.size(), goodsItemBeans.size());
        }
    }

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AbsViewHolder holder = null;
        switch (viewType) {
            case TYPE_BANNER:
                holder = new HomeBannerVH(mInflater.inflate(HomeBannerVH.getLayoutRes(), parent, false), mBeeGlide);
                break;
            case TYPE_GOODS_HEADER:
                holder = new RecGoodsHeader(mInflater.inflate(RecGoodsHeader.getLayoutRes(), parent, false));
                break;
            case TYPE_GOODS:
                holder = new GoodsItemVHV2(mInflater.inflate(GoodsItemVHV2.getLayoutRes(), parent, false), mBeeGlide);
                break;
            case TYPE_CATES_GRID:
                holder = new HomeCatesGridVH(mInflater.inflate(HomeCatesGridVH.getLayoutRes(), parent, false)
                        , mContext, mBeeGlide);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
