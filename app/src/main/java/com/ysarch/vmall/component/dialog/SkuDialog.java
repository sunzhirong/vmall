package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
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
import com.ysarch.vmall.domain.bean.GoodsDetailItemBean;
import com.ysarch.vmall.domain.bean.SkuBeanV2;
import com.ysarch.vmall.domain.local.LocalPropSkuEntity;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.ysarch.vmall.page.goods.GoodsPictureDetailsActivity;
import com.ysarch.vmall.utils.LPUtils;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

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
public class SkuDialog extends Dialog {
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
    //    private List<SkuFlowAdapter> mSkuFlowAdapters = new ArrayList<>();
    private List<SkuBeanV2> mSkuBeans;
    //    private Map<String, String> mImages;
    private String mSkuSelectedPrefix;
    private String mSkuPricePrefix;
    private int mCount = 1;
    private LocalSkuEntity[] mLocalSkuEntities;
    private BeeGlide mBeeGlide;
    private String mCurImage;
    private Callback mDialogCallback;
    private SkuBeanV2 mSkuBeanSelected;
    private GoodsDetailItemBean mGoodsDetailBean;
    private int selectPos = -1;
    private SkuFlowAdapter.Callback mCallback = new SkuFlowAdapter.Callback() {
        @Override
        public void onSkuChanged(int position, LocalSkuEntity entity,int selectPosition) {
            mLocalSkuEntities[position] = entity;

            if(!TextUtils.isEmpty(entity.getImage())){
                selectPos = selectPosition;
            }
            SkuDialog.this.onSkuChanged(entity.getImage());
        }
    };
    private String mDefaultImg;

    private SkuDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mSkuSelectedPrefix = ResUtils.getString(R.string.label_already_selected_prefix);
        mSkuPricePrefix = ResUtils.getString(R.string.label_price_prefix);
        init();
    }

    public void setSkuBeans(List<SkuBeanV2> skuBeans) {
        mSkuBeans = skuBeans;
    }

    public void setLocalPropSkuEntities(List<LocalPropSkuEntity> localPropSkuEntities, SkuBeanV2 selectedSkuBean) {
        mLocalPropSkuEntities = localPropSkuEntities;
        if (CollectionUtils.isNotEmpty(mLocalPropSkuEntities)) {
            mNestedScrollView.setVisibility(View.VISIBLE);
            mTVSelectedSku.setVisibility(View.VISIBLE);
            mLocalSkuEntities = new LocalSkuEntity[mLocalPropSkuEntities.size()];
            for (int i = 0; i < mLocalPropSkuEntities.size(); i++) {
                LocalPropSkuEntity localPropSkuEntity = mLocalPropSkuEntities.get(i);

                if (selectedSkuBean == null) {
                    for (int j = 0; j < localPropSkuEntity.getLocalSkuEntities().size(); j++) {
                        localPropSkuEntity.getLocalSkuEntities().get(j).setSelected(j == 0);
                    }
                    mLocalSkuEntities[i] = localPropSkuEntity.getLocalSkuEntities().get(0);
                } else {
                    mSkuBeanSelected = selectedSkuBean;
                    for (int j = 0; j < localPropSkuEntity.getLocalSkuEntities().size(); j++) {
                        LocalSkuEntity localSkuEntity = localPropSkuEntity.getLocalSkuEntities().get(j);
                        if (selectedSkuBean.getPropPath().indexOf(localSkuEntity.getSkuCode()) != -1) {
                            localSkuEntity.setSelected(true);
                            mLocalSkuEntities[i] = localSkuEntity;
                        } else {
                            localSkuEntity.setSelected(false);
                        }
                    }
                }

//                if(i!=0){continue;}
                initAdapter(i, mLocalPropSkuEntities.get(i));
            }
        } else {
            mNestedScrollView.setVisibility(View.GONE);
            mTVSelectedSku.setVisibility(View.GONE);
        }
        onSkuChanged(null);
    }

    /**
     * 属性变更
     */
    private void onSkuChanged(String image) {
        SkuBeanV2 skuBean = null;
        if (mLocalSkuEntities != null && mLocalSkuEntities.length > 0) {
            loop1:
            for (int i = 0; i < mSkuBeans.size(); i++) {
                String skuCode = mSkuBeans.get(i).getPropPath();
                loop2:
                for (int j = 0; j < mLocalSkuEntities.length; j++) {
                    if (!skuCode.contains(mLocalSkuEntities[j].getSkuCode()))
                        continue loop1;
                    if (j == mLocalSkuEntities.length - 1) {
                        skuBean = mSkuBeans.get(i);
                        break loop1;
                    }
                }
            }


            if (skuBean != null) {
                mSkuBeanSelected = skuBean;
//                mTVPrice.setText(mSkuPricePrefix + ": $" + VMallUtils.convertTo2String(skuBean.getFloatPrice()));
//                mTVPrice.setText(VMallUtils.convertPriceString(skuBean.getFloatPrice()));
                mTVPrice.setText(VMallUtils.getCurrencySign()+skuBean.getPrice());

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

                if(!TextUtils.isEmpty(image)){
                    mCurImage = image;
                    mBeeGlide.load(ImageLoadConfig.create(mCurImage).randomPlaceHolder(), mIVCover);
                }else {
                    if (mLocalSkuEntities != null && mLocalSkuEntities.length > 0) {
                        for (int i = 0; i < mLocalSkuEntities.length; i++) {
                            if (!TextUtils.isEmpty(mLocalSkuEntities[i].getImage())) {
                                mCurImage = mLocalSkuEntities[i].getImage();
                                mBeeGlide.load(ImageLoadConfig.create(mCurImage).randomPlaceHolder(), mIVCover);
                            } else {
                                mCurImage = mDefaultImg;
                                mBeeGlide.load(ImageLoadConfig.create(mDefaultImg).randomPlaceHolder(), mIVCover);
                            }
                        }
                    } else {
                        mCurImage = mDefaultImg;
                        mBeeGlide.load(ImageLoadConfig.create(mDefaultImg).randomPlaceHolder(), mIVCover);
                    }
                }

                if(mSkuBeanSelected.getQuantityInt()==0){
                    mTVConfirm.setText(R.string.label_no_quality);
                    mTVConfirm.setEnabled(false);
//                    ToastUtils.showShortToast(getContext(),"缺货中");
                }else {
                    mTVConfirm.setText(R.string.label_add_to_cart);
                    mTVConfirm.setEnabled(true);
//                    ToastUtils.showShortToast(getContext(),"可以下单");
                }


            }

        } else {
            mCurImage = mDefaultImg;
            mBeeGlide.load(ImageLoadConfig.create(mDefaultImg).randomPlaceHolder(), mIVCover);
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

    @OnClick({R.id.tv_confirm_sku_dialog, R.id.iv_minus_sku_dialog,
            R.id.iv_plus_sku_dialog, R.id.iv_close_sku_dialog, R.id.iv_cover_sku_dialog, R.id.v_mark_sku_dialog})
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
            case R.id.v_mark_sku_dialog:
            case R.id.iv_close_sku_dialog:
                dismiss();
                break;
            case R.id.iv_cover_sku_dialog:
                if (mLocalPropSkuEntities == null || mLocalPropSkuEntities.size() == 0) {
                    return;
                }
                NavHelper.startActivity(getOwnerActivity(), GoodsPictureDetailsActivity.class, GoodsPictureDetailsActivity.getBundle(mLocalPropSkuEntities,selectPos));
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
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new RecyclerView.LayoutParams(LPUtils.WRAP, LPUtils.WRAP));

        LinearLayout.LayoutParams layoutParams = LPUtils.getLinearLayoutParams(LPUtils.WRAP, LPUtils.WRAP);
        layoutParams.topMargin = SizeUtils.dp2px(10);
        layoutParams.bottomMargin = SizeUtils.dp2px(10);
        mLinearLayout.addView(textView, layoutParams);

        fRecyclerView.setAdapter(skuFlowAdapter);
        skuFlowAdapter.refreshData(localPropSkuEntity.getLocalSkuEntities());

        mLinearLayout.addView(fRecyclerView, LPUtils.FILL, LPUtils.WRAP);

//        mSkuFlowAdapters.add(skuFlowAdapter);

        View view = new View(getContext());
        view.setBackgroundResource(R.color.gray_divide_line);
        LinearLayout.LayoutParams layoutParamsLine = LPUtils.getLinearLayoutParams(LPUtils.FILL, SizeUtils.dp2px(0.5f));
        layoutParams.topMargin = SizeUtils.dp2px(7);
        mLinearLayout.addView(view, layoutParamsLine);
    }

    public void setDialogCallback(Callback dialogCallback) {
        mDialogCallback = dialogCallback;
    }

    public void setGoodsDetailBean(GoodsDetailItemBean goodsDetailBean) {
        mGoodsDetailBean = goodsDetailBean;
    }

    public void setDefaultImg(String defaultImg) {
        this.mDefaultImg = defaultImg;
    }


    public interface Callback {
        void onGoodsSelected(String label, SkuBeanV2 skuBean, String img, int quantity);
    }

    public static class Builder {

        private List<LocalPropSkuEntity> mLocalPropSkuEntities;
        private List<SkuBeanV2> mSkuBeans;
        private Callback mDialogCallback;
        private Context mContext;
        private SkuBeanV2 mSelectedSkuBean;
        private GoodsDetailItemBean mGoodsDetailBean;
        private String mDefaultImg;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setDefaultImg(String defaultImg) {
            mDefaultImg = defaultImg;
            return this;
        }

        public Builder setGoodsDetailBean(GoodsDetailItemBean goodsDetailBean) {
            mGoodsDetailBean = goodsDetailBean;
            return this;
        }

        public Builder setSelectedSkuBean(SkuBeanV2 selectedSkuBean) {
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

        public Builder setSkuBeans(List<SkuBeanV2> skuBeans) {
            mSkuBeans = skuBeans;
            return this;
        }


        public SkuDialog build() {
            SkuDialog skuDialog = new SkuDialog(mContext, R.style.ScrollDialog);
            skuDialog.setDefaultImg(mDefaultImg);
            skuDialog.setSkuBeans(mSkuBeans);
            skuDialog.setGoodsDetailBean(mGoodsDetailBean);
            skuDialog.setLocalPropSkuEntities(mLocalPropSkuEntities, mSelectedSkuBean);
            skuDialog.setDialogCallback(mDialogCallback);

            return skuDialog;
        }
    }


}
