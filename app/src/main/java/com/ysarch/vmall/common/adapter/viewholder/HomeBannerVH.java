package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.VMallApplication;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.HomeBannerBean;
import com.ysarch.vmall.utils.VMallUtils;

import java.util.List;

/**
 * Created by fysong on 18/09/2020
 **/
public class HomeBannerVH extends AbsViewHolder {
    private ConvenientBanner<HomeBannerBean> mBanner;
    private List<HomeBannerBean> mHomeBannerBeans;
    private boolean bInited = false;

    private BeeGlide mBeeGlide;

    public HomeBannerVH(View itemView, BeeGlide beeGlide) {
        super(itemView);
        mBeeGlide = beeGlide;
        mBanner = itemView.findViewById(R.id.cb_home_banner);

    }

    public static int getLayoutRes() {
        return R.layout.item_home_banner;
    }

    private Callback mCallback;
    @Override
    public void onBindData(int position, Object data, Object callback) {
        if( callback != null){
            mCallback = (Callback) callback;
        }

        if (mHomeBannerBeans != null) {
            Gson gson = new Gson();
            String json1 = gson.toJson(mHomeBannerBeans);
            String json2 = gson.toJson(data);
            if (json1.equals(json2)) {
                return;
            }
        }

        if (mBanner.isTurning()) {
            mBanner.stopTurning();
        }

        mHomeBannerBeans = (List<HomeBannerBean>) data;

        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new BannerItemVH(itemView, mBeeGlide);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_img;
            }
        }, mHomeBannerBeans);

        mBanner.setPointViewVisible(true);
        mBanner.startTurning(3000);
        mBanner.setOnItemClickListener(position1 -> {
            if(mCallback != null){
                mCallback.onBannerClick(position1, mHomeBannerBeans.get(position1));
            }
        });

        mBanner.setPageIndicator(new int[]{R.drawable.ic_page_indicator,R.drawable.ic_page_indicator_focused});
    }


    public interface Callback{
        void onBannerClick(int position, HomeBannerBean bannerBean);
    }


    public static class BannerItemVH extends Holder<HomeBannerBean> {

        private BeeGlide mBeeGlide;
        private ImageView mImageView;

        public BannerItemVH(View itemView, BeeGlide beeGlide) {
            super(itemView);
            mBeeGlide = beeGlide;
            mImageView = (ImageView) itemView;
        }

        @Override
        protected void initView(View itemView) {

        }

        @Override
        public void updateUI(HomeBannerBean data) {
//            mBeeGlide.load(ImageLoadConfig.create(data.getPic()).randomPlaceHolder(), mImageView);
            Glide.with(VMallApplication.sApplication).load(VMallUtils.decodeString(data.getPic())).into(mImageView);

        }
    }
}
