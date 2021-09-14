package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsFRecyclerAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.common.adapter.viewholder.CateHomeItemVH;
import com.ysarch.vmall.common.adapter.viewholder.GoodsItemVH;
import com.ysarch.vmall.common.adapter.viewholder.HomeBannerVH;
import com.ysarch.vmall.common.adapter.viewholder.HomeCatesGridVH;
import com.ysarch.vmall.common.adapter.viewholder.RecGoodsHeader;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.GoodsItemBean;
import com.ysarch.vmall.domain.bean.HomeBannerBean;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 09/09/2020
 **/
public class ShouYeSubpageRcyAdapter extends AbsFRecyclerAdapter {

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

    public void setBannerCallback(HomeBannerVH.Callback bannerCallback) {
        mBannerCallback = bannerCallback;
    }

    public ShouYeSubpageRcyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mBeeGlide = BeeGlide.with(context);
        mContext = context;
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setCallback(CateHomeItemVH.Callback callback) {
        mCallback = callback;
    }

    /**
     * 最受 欢迎页面数据
     *
     * @param bannerBeans
     * @param goodsItemBeans
     */
    public void refreshWelcomeDatas(List<HomeBannerBean> bannerBeans, List<GoodsItemBean> goodsItemBeans) {
        mMixData.clear();
        if (CollectionUtils.isNotEmpty(bannerBeans))
            mMixData.add(new ItemDataWrapper(TYPE_BANNER, bannerBeans, mBannerCallback));
        mMixData.add(new ItemDataWrapper(TYPE_GOODS_HEADER, null).setOnItemClickListener(mOnItemClickListener));
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
     * @param cateLevelBeans
     * @param goodsItemBeans
     */
    public void refreshSubCateDatas(List<CateLevelBean> cateLevelBeans, List<GoodsItemBean> goodsItemBeans) {
        mMixData.clear();
        mMixData.add(new ItemDataWrapper(TYPE_CATES_GRID, cateLevelBeans, mCallback));
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
    public void refreshGoods(List<GoodsItemBean> goodsItemBeans) {
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
    public void appendGoods(List<GoodsItemBean> goodsItemBeans) {
        if (CollectionUtils.isNotEmpty(goodsItemBeans)) {
            for (int i = 0; i < goodsItemBeans.size(); i++) {
                mMixData.add(new ItemDataWrapper(TYPE_GOODS, goodsItemBeans.get(i))
                        .setOnItemClickListener(mOnItemClickListener));
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
                holder = new GoodsItemVH(mInflater.inflate(GoodsItemVH.getLayoutRes(), parent, false), mBeeGlide);
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
