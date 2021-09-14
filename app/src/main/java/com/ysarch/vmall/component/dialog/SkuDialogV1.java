package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.NestedRecyclerView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.SkuFlowAdapter;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.GoodsDetailBean;
import com.ysarch.vmall.domain.bean.SkuBean;
import com.ysarch.vmall.domain.local.LocalPropSkuEntity;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.ysarch.vmall.utils.LPUtils;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by fysong on 24/09/2020
 **/
public class SkuDialogV1 extends Dialog {
    @BindView(R.id.iv_cover_sku_dialog)
    ImageView mIVCover;
    @BindView(R.id.tv_price_sku_dialog)
    TextView mTVPrice;
    @BindView(R.id.tv_price_ori_sku_dialog)
    TextView mTVPriceOri;
    @BindView(R.id.tv_selected_sku_dialog)
    TextView mTVSelectedSku;
    @BindView(R.id.ly_sku_dialog)
    LinearLayout mLinearLayout;
    @BindView(R.id.iv_minus_sku_dialog)
    ImageView mIVMinus;
    @BindView(R.id.iv_plus_sku_dialog)
    ImageView mIVPlus;
    @BindView(R.id.tv_num_sku_dialog)
    TextView mTVCount;
    @BindView(R.id.tv_confirm_sku_dialog)
    TextView mTVConfirm;
    @BindView(R.id.sv_sku_dialog)
    NestedScrollView mNestedScrollView;
    private List<LocalPropSkuEntity> mLocalPropSkuEntities;
    private List<SkuFlowAdapter> mSkuFlowAdapters = new ArrayList<>();
    private List<SkuBean> mSkuBeans;
    private Map<String, String> mImages;
    private String mSkuSelectedPrefix;
    private String mSkuPricePrefix;
    private int mCount = 1;
    private LocalSkuEntity[] mLocalSkuEntities;
    private BeeGlide mBeeGlide;
    private String mCurImage;
    private Callback mDialogCallback;
    private SkuBean mSkuBeanSelected;
    private GoodsDetailBean mGoodsDetailBean;
    private SkuFlowAdapter.Callback mCallback = new SkuFlowAdapter.Callback() {
        @Override
        public void onSkuChanged(int position, LocalSkuEntity entity,int pos) {
            mLocalSkuEntities[position] = entity;

            SkuDialogV1.this.onSkuChanged();
        }
    };
    private String mDefaultImg;

    private SkuDialogV1(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mSkuSelectedPrefix = ResUtils.getString(R.string.label_already_selected_prefix);
        mSkuPricePrefix = ResUtils.getString(R.string.label_price_prefix);
        init();
    }

    public void setImages(Map<String, String> images) {
        mImages = images;

        if (mLocalSkuEntities != null) {
            onSkuChanged();
        }
    }

    public void setSkuBeans(List<SkuBean> skuBeans) {
        mSkuBeans = skuBeans;
    }

    public void setLocalPropSkuEntities(List<LocalPropSkuEntity> localPropSkuEntities, SkuBean selectedSkuBean) {
        mLocalPropSkuEntities = localPropSkuEntities;

//        List<LocalSkuEntity> localSkuEntities = mLocalPropSkuEntities.get(0).getLocalSkuEntities();
//        List<LocalSkuEntity> localSkuEntities2 = mLocalPropSkuEntities.get(1).getLocalSkuEntities();
//        localSkuEntities2.clear();
//        localSkuEntities2.addAll(localSkuEntities);


        if (CollectionUtils.isNotEmpty(mLocalPropSkuEntities)) {
            mNestedScrollView.setVisibility(View.VISIBLE);
            mTVSelectedSku.setVisibility(View.VISIBLE);
            mLocalSkuEntities = new LocalSkuEntity[mLocalPropSkuEntities.size()];
            for (int i = 0; i < mLocalPropSkuEntities.size(); i++) {
                LocalPropSkuEntity localPropSkuEntity = mLocalPropSkuEntities.get(i);

                if (selectedSkuBean == null) {
                    LocalSkuEntity localSkuEntity = localPropSkuEntity.getLocalSkuEntities().get(0);
                    localSkuEntity.setSelected(true);
                    mLocalSkuEntities[i] = localSkuEntity;
                } else {
                    mSkuBeanSelected = selectedSkuBean;
                    for (int j = 0; j < localPropSkuEntity.getLocalSkuEntities().size(); j++) {
                        LocalSkuEntity localSkuEntity = localPropSkuEntity.getLocalSkuEntities().get(j);
                        if (selectedSkuBean.getProperties().indexOf(localSkuEntity.getSkuCode()) != -1) {
                            localSkuEntity.setSelected(true);
                            mLocalSkuEntities[i] = localSkuEntity;
                        }
                    }
                }

                initAdapter(i, mLocalPropSkuEntities.get(i));
            }
        } else {
            mNestedScrollView.setVisibility(View.GONE);
            mTVSelectedSku.setVisibility(View.GONE);
        }

        onSkuChanged();
    }

    /**
     * 属性变更
     */
    private void onSkuChanged() {
        if (mLocalSkuEntities != null && mLocalSkuEntities.length > 0) {
            StringBuilder stringBuilder = new StringBuilder(mSkuSelectedPrefix + ": ");
            StringBuilder skuCodeStringBuilder = new StringBuilder();
            for (int i = 0; i < mLocalSkuEntities.length; i++) {
                stringBuilder.append(mLocalSkuEntities[i].getSkuLabel());
                skuCodeStringBuilder.append(mLocalSkuEntities[i].getSkuCode());
                if (i < mLocalSkuEntities.length - 1) {
                    stringBuilder.append("/");
                    skuCodeStringBuilder.append(";");
                }
            }

            mTVSelectedSku.setText(stringBuilder.toString());

            String skuCode = skuCodeStringBuilder.toString();
            SkuBean skuBean = null;
            for (int i = 0; i < mSkuBeans.size(); i++) {
                if (mSkuBeans.get(i).getProperties().equals(skuCode)) {
                    skuBean = mSkuBeans.get(i);
                    break;
                }
            }

            if (skuBean != null && skuBean != mSkuBeanSelected) {
                mSkuBeanSelected = skuBean;
                mTVPrice.setText(mSkuPricePrefix + ": $" + VMallUtils.convertTo2String(skuBean.getPrice()));
                if (skuBean.getOrginalPrice() != 0 && skuBean.getPrice() != skuBean.getOrginalPrice()) {
                    mTVPriceOri.setVisibility(View.VISIBLE);
                    mTVPriceOri.setText("$" + VMallUtils.convertTo2String(skuBean.getOrginalPrice()));
                } else {
                    mTVPriceOri.setVisibility(View.GONE);
                }
            }

            if (mImages != null && mLocalSkuEntities != null) {
                for (int i = 0; i < mLocalSkuEntities.length; i++) {
                    LocalSkuEntity entity = mLocalSkuEntities[i];
                    String img = mImages.get(entity.getSkuCode());
                    if (img != null && !img.equals(mCurImage)) {
                        mCurImage = img;
                        mBeeGlide.load(ImageLoadConfig.create(mCurImage).randomPlaceHolder(), mIVCover);
                        break;
                    }
                }
            } else if(!TextUtils.isEmpty(mDefaultImg)){
                if(mDefaultImg.equals(mCurImage))
                    return;
                mCurImage = mDefaultImg;
                mBeeGlide.load(ImageLoadConfig.create(mCurImage).randomPlaceHolder(), mIVCover);
            }

        } else {
            mTVPrice.setText(mSkuPricePrefix + ": $" + VMallUtils.convertTo2String(mGoodsDetailBean.getPrice()));
            if (mGoodsDetailBean.getOrginalPrice() != 0 && mGoodsDetailBean.getPrice() != mGoodsDetailBean.getOrginalPrice()) {
                mTVPriceOri.setVisibility(View.VISIBLE);
                mTVPriceOri.setText("$" + VMallUtils.convertTo2String(mGoodsDetailBean.getOrginalPrice()));
            } else {
                mTVPriceOri.setVisibility(View.GONE);
            }

            mCurImage = VMallUtils.correctUrl(mGoodsDetailBean.getPicUrl());
            mBeeGlide.load(ImageLoadConfig.create(mCurImage).randomPlaceHolder(), mIVCover);
        }


    }


    public void init() {
        mBeeGlide = BeeGlide.with(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_sku, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(true);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);

        mTVPriceOri.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        mTVCount.setText(mCount + "");

        RxTextView.textChanges(mTVCount)
                .subscribe(charSequence -> {
                    mIVMinus.setEnabled(mCount > 1);
                });


    }

    @OnClick({R.id.tv_confirm_sku_dialog, R.id.iv_minus_sku_dialog, R.id.iv_plus_sku_dialog})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm_sku_dialog:
                if (mDialogCallback != null) {
                    mDialogCallback.onGoodsSelected(mTVSelectedSku.getText().toString(), mSkuBeanSelected, mCurImage, mCount);
                }
                break;
            case R.id.iv_minus_sku_dialog:
                if (mCount > 1) {
                    mCount = mCount - 1;
                    mTVCount.setText(mCount + "");
                }
                break;
            case R.id.iv_plus_sku_dialog:
                mCount = mCount + 1;
                mTVCount.setText(mCount + "");
                break;

        }
    }

    private void initAdapter(int position, LocalPropSkuEntity localPropSkuEntity) {
        NestedRecyclerView fRecyclerView = new NestedRecyclerView(getContext());
        fRecyclerView.getItemAnimator().setAddDuration(0);
        fRecyclerView.getItemAnimator().setChangeDuration(0);
        fRecyclerView.getItemAnimator().setMoveDuration(0);
        fRecyclerView.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) fRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        SkuFlowAdapter skuFlowAdapter = new SkuFlowAdapter(position, getContext());
        skuFlowAdapter.setCallback(mCallback);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        fRecyclerView.setLayoutManager(flowLayoutManager);
        TextView textView = new TextView(getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setTextColor(0xff343434);
        textView.setText(localPropSkuEntity.getPropLabel());
        textView.setLayoutParams(new RecyclerView.LayoutParams(LPUtils.WRAP, LPUtils.WRAP));

        LinearLayout.LayoutParams layoutParams = LPUtils.getLinearLayoutParams(LPUtils.WRAP, LPUtils.WRAP);
        layoutParams.topMargin = SizeUtils.dp2px(20);
        layoutParams.bottomMargin = SizeUtils.dp2px(10);
        mLinearLayout.addView(textView, layoutParams);

        fRecyclerView.setAdapter(skuFlowAdapter);
        skuFlowAdapter.refreshData(localPropSkuEntity.getLocalSkuEntities());

        mLinearLayout.addView(fRecyclerView, LPUtils.FILL, LPUtils.WRAP);

        mSkuFlowAdapters.add(skuFlowAdapter);
    }

    public void setDialogCallback(Callback dialogCallback) {
        mDialogCallback = dialogCallback;
    }

    public void setGoodsDetailBean(GoodsDetailBean goodsDetailBean) {
        mGoodsDetailBean = goodsDetailBean;
    }

    public void setDefaultImg(String defaultImg) {
        mDefaultImg = defaultImg;
    }


    public interface Callback {
        void onGoodsSelected(String label, SkuBean skuBean, String img, int quantity);
    }

    public static class Builder {

        private List<LocalPropSkuEntity> mLocalPropSkuEntities;
        private List<SkuBean> mSkuBeans;
        private Map<String, String> mImages;
        private Callback mDialogCallback;
        private Context mContext;
        private SkuBean mSelectedSkuBean;
        private GoodsDetailBean mGoodsDetailBean;
        private String mDefaultImg;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setDefaultImg(String defaultImg) {
            mDefaultImg = defaultImg;
            return this;
        }

        public Builder setGoodsDetailBean(GoodsDetailBean goodsDetailBean) {
            mGoodsDetailBean = goodsDetailBean;
            return this;
        }

        public Builder setSelectedSkuBean(SkuBean selectedSkuBean) {
            mSelectedSkuBean = selectedSkuBean;
            return this;
        }

        public Builder setDialogCallback(Callback dialogCallback) {
            mDialogCallback = dialogCallback;
            return this;
        }

        public Builder setLocalPropSkuEntities(List<LocalPropSkuEntity> localPropSkuEntities) {
            mLocalPropSkuEntities = localPropSkuEntities;
            return this;
        }

        public Builder setSkuBeans(List<SkuBean> skuBeans) {
            mSkuBeans = skuBeans;
            return this;
        }

        public Builder setImages(List<Map<String, String>> mapList) {
            if (CollectionUtils.isNotEmpty(mapList)) {
                mImages = mapList.get(0);
            }
            return this;
        }

        public SkuDialogV1 build() {
            SkuDialogV1 skuDialog = new SkuDialogV1(mContext, R.style.ScrollDialog);
            skuDialog.setImages(mImages);
            skuDialog.setSkuBeans(mSkuBeans);
            skuDialog.setGoodsDetailBean(mGoodsDetailBean);
            skuDialog.setLocalPropSkuEntities(mLocalPropSkuEntities, mSelectedSkuBean);
            skuDialog.setDialogCallback(mDialogCallback);
            return skuDialog;
        }
    }


}
