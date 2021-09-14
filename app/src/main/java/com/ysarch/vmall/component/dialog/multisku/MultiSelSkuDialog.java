package com.ysarch.vmall.component.dialog.multisku;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ysarch.uibase.viewpager.FragmentPagerItem;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.viewholder.MultiSkuVH;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.GoodsDetailItemBean;
import com.ysarch.vmall.domain.bean.PriceRangeItemBean;
import com.ysarch.vmall.domain.bean.SkuBeanV2;
import com.ysarch.vmall.domain.local.LocalPropSkuEntity;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.ysarch.vmall.domain.local.MultiSkuEntity;
import com.ysarch.vmall.page.goods.MultiSkuPageFragmentAdapter;
import com.ysarch.vmall.utils.CalculateUtil;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SystemUtil;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 12/10/2020
 **/
public class MultiSelSkuDialog extends DialogFragment {

    @BindView(R.id.tb_multi_sku_dialog)
    TabLayout mTabLayout;
    @BindView(R.id.vp_multi_sku_dialog)
    ViewPager mViewPager;
    @BindView(R.id.ly_vp_multi_sku_dialog)
    LinearLayout mLyVp;

    @BindView(R.id.ctl_single_multi_sku_dialog)
    ConstraintLayout mCtlSingle;
    @BindView(R.id.tv_sku_single_multi_sku_dialog)
    TextView mTVSkuSingle;
    @BindView(R.id.et_num_sku_single_multi_sku_dialog)
    EditText mETNumSingle;
    @BindView(R.id.iv_minus_sku_single_multi_sku_dialog)
    ImageView mIVMinus;
    @BindView(R.id.iv_plus_sku_single_multi_sku_dialog)
    ImageView mIVPlus;
    @BindView(R.id.tv_title_multi_sku_dialog)
    TextView mTVTitle;
    @BindView(R.id.tv_price_multi_sku_dialog)
    TextView mTVPrice;
    @BindView(R.id.iv_cover_multi_sku_dialog)
    ImageView mIVCover;
    @BindView(R.id.tv_total_amount_multi_sku_dialog)
    TextView mTVTotalAmount;

    private MultiSkuPageFragmentAdapter mFragmentAdapter;
    private List<FragmentPagerItem> mFragmentPagerItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private GoodsDetailItemBean mGoodsDetailBean;
    private Callback mCallback;
    private double mCommonPrice = 0;
    /**
     * tab页数据
     */
    private List<LocalSkuEntity> mTabLists = new ArrayList<>();
    /**
     * 分页sku数据
     */
    private List<List<MultiSkuEntity>> mSubSkuLists = new ArrayList<>();
    private int mStatus = 0;
    private MultiSkuEntity mMultiSkuEntity;
    private boolean isPriceFixed = false;
    private String mDefaultImg;
    private String mCurrentImg;
    private BeeGlide mBeeGlide;
    private List<MultiSkuEntity> mSelMultiSkuEntity = new ArrayList<>();
    private double mTotalAmount = 0;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
        mBeeGlide = BeeGlide.with(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        return inflater.inflate(R.layout.dialog_multi_sku, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        ButterKnife.bind(this, view);
        if (mStatus == 0) {
            initSingleSku();
        } else {
            initViewPager();
        }
        mTVTitle.setText(mGoodsDetailBean.getTitle());
        mTVPrice.setText("$" + mGoodsDetailBean.getDollarPrice());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViewPager() {
        mLyVp.setVisibility(View.VISIBLE);
        mCtlSingle.setVisibility(View.GONE);

        for (int i = 0; i < mTabLists.size(); i++) {
            mFragmentPagerItems.add(new FragmentPagerItem(MultiSkuFragment.class,
                    mTabLists.get(i).getSkuLabel(),
                    MultiSkuFragment.getBundle(mTabLists.get(i), mSubSkuLists.get(i))));
        }

        mFragmentAdapter = new MultiSkuPageFragmentAdapter(getContext(), getChildFragmentManager(), mFragmentPagerItems);
        mFragmentAdapter.setCallback(new MultiSkuVH.Callback() {
            @Override
            public void onSkuNumChanged(int position, MultiSkuEntity multiSkuEntity) {
                onSkuChanged(multiSkuEntity);
            }
        });
        mViewPager.setAdapter(mFragmentAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        if (mTabLists.size() > 1) {
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    Log.d("MultiSku", "onPageScrolled");
                    LocalSkuEntity localSkuEntity = mTabLists.get(position);
                    if (!TextUtils.isEmpty(localSkuEntity.getImage())) {
                        if (!localSkuEntity.getImage().equals(mCurrentImg)) {
                            mCurrentImg = localSkuEntity.getImage();
                            mBeeGlide.load(ImageLoadConfig.create(mCurrentImg), mIVCover);
                        }
                    } else if (!TextUtils.isEmpty(mDefaultImg)) {
                        if (!mDefaultImg.equals(mCurrentImg)) {
                            mCurrentImg = localSkuEntity.getImage();
                            mBeeGlide.load(ImageLoadConfig.create(mCurrentImg), mIVCover);
                        }
                    }
                }

                @Override
                public void onPageSelected(int position) {
//                    Log.d("MultiSku", "onPageSelected");

                }

                @Override
                public void onPageScrollStateChanged(int state) {
//                    Log.d("MultiSku", "onPageScrollStateChanged");
                }
            });
        } else if (!TextUtils.isEmpty(mTabLists.get(0).getImage())) {
            mCurrentImg = mTabLists.get(0).getImage();
            mBeeGlide.load(ImageLoadConfig.create(mCurrentImg), mIVCover);
        } else {
            mCurrentImg = mDefaultImg;
            mBeeGlide.load(ImageLoadConfig.create(mCurrentImg), mIVCover);
        }

        mTVTotalAmount.setText(VMallUtils.convertPriceString(mTotalAmount));
    }

    /**
     * 1、单一商品
     * 2、固定价格
     * 3、价格区间
     *
     * @param multiSkuEntity
     */
    private void onSkuChanged(MultiSkuEntity multiSkuEntity) {
        if (multiSkuEntity.getAmount() == 0) {
            // 删除
            if (CollectionUtils.isNotEmpty(mSelMultiSkuEntity)) {
                Iterator<MultiSkuEntity> multiSkuEntityIterator = mSelMultiSkuEntity.iterator();
                while (multiSkuEntityIterator.hasNext()) {
                    MultiSkuEntity entity = multiSkuEntityIterator.next();
                    if (entity.getSkuBeanV2().getSkuId().equals(multiSkuEntity.getSkuBeanV2().getSkuId())) {
                        multiSkuEntityIterator.remove();
                        break;
                    }
                }
            }
        } else {
            boolean isSet = false;
            if (CollectionUtils.isNotEmpty(mSelMultiSkuEntity)) {
                for (int i = 0; i < mSelMultiSkuEntity.size(); i++) {
                    MultiSkuEntity entity = mSelMultiSkuEntity.get(i);
                    if (entity.getSkuBeanV2().getSkuId().equals(multiSkuEntity.getSkuBeanV2().getSkuId())) {
                        mSelMultiSkuEntity.set(i, multiSkuEntity);
                        isSet = true;
                        break;
                    }
                }

            }

            if (!isSet) {
                mSelMultiSkuEntity.add(multiSkuEntity);
            }
        }

        calculateTotalAmount();

        if (mTVTotalAmount != null) {
            mTVTotalAmount.setText(VMallUtils.convertPriceString(mTotalAmount));
        }
    }


    /**
     * 计算总价
     */
    private void calculateTotalAmount() {
        if (mStatus == 0) {
            //没有sku
            if (CollectionUtils.isNotEmpty(mGoodsDetailBean.getShowPriceRanges())) {
                int amount = mMultiSkuEntity.getAmount();
                mTotalAmount = calculateTotalAmountInRange(amount);
            }
        } else if (CollectionUtils.isNotEmpty(mSelMultiSkuEntity)) {
            int num = 0;
            for (int i = 0; i < mSelMultiSkuEntity.size(); i++) {
                MultiSkuEntity multiSkuEntity = mSelMultiSkuEntity.get(i);
                num += multiSkuEntity.getAmount();
                if (isPriceFixed) {
                    SkuBeanV2 skuBeanV2 = multiSkuEntity.getSkuBeanV2();
                    if (skuBeanV2.getFloatPrice() > 0) {
                        mTotalAmount += CalculateUtil.mul(multiSkuEntity.getAmount(), skuBeanV2.getFloatPrice());
                    }
                }
            }

            if (!isPriceFixed) {
                mTotalAmount = calculateTotalAmountInRange(num);
            }
        } else {
            mTotalAmount = 0;
        }
    }


    /**
     * 根据价格区间计算总价
     *
     * @param amount
     * @return
     */
    private double calculateTotalAmountInRange(int amount) {
        double lastPrice = 0;
        for (int i = 0; i < mGoodsDetailBean.getShowPriceRanges().size(); i++) {
            PriceRangeItemBean bean = mGoodsDetailBean.getShowPriceRanges().get(i);
            if (amount >= bean.getStartAmount() || lastPrice == 0) {
                lastPrice = bean.getPrice();
            }
            if (amount <= bean.getStartAmount()) {
                break;
            }
        }

        mCommonPrice = lastPrice;
        return CalculateUtil.mul(lastPrice , amount);
    }

    public void setGoodsDetailBean(GoodsDetailItemBean goodsDetailBean) {
        mGoodsDetailBean = goodsDetailBean;
        if (CollectionUtils.isNotEmpty(mGoodsDetailBean.getImages()))
            mDefaultImg = mGoodsDetailBean.getImages().get(0);
    }

    public void setDatas(List<SkuBeanV2> skuBeanV2s, List<LocalPropSkuEntity> localPropSkuEntities,
                         List<MultiSkuEntity> selectedMultiSkus) {

        if (CollectionUtils.isNotEmpty(selectedMultiSkus)) {
            mSelMultiSkuEntity.addAll(selectedMultiSkus);
        }

        if (CollectionUtils.isNotEmpty(localPropSkuEntities) && CollectionUtils.isNotEmpty(skuBeanV2s)) {
            isPriceFixed = !TextUtils.isEmpty(skuBeanV2s.get(0).getPrice()) &&
                    CollectionUtils.isNotEmpty(mGoodsDetailBean.getShowPriceRanges());
            // 一对多
            if (localPropSkuEntities.size() == 1) {
                mStatus = 1;
                LocalSkuEntity localSkuEntity = new LocalSkuEntity();
                localSkuEntity.setSkuLabel(localPropSkuEntities.get(0).getPropLabel());
                mTabLists.add(localSkuEntity);

                List<LocalSkuEntity> localSkuEntities = localPropSkuEntities.get(0).getLocalSkuEntities();
                List<MultiSkuEntity> multiSkuEntities = new ArrayList<>();
                for (int i = 0; i < localSkuEntities.size(); i++) {
                    MultiSkuEntity multiSkuEntity = new MultiSkuEntity();
                    LocalSkuEntity localSku = localSkuEntities.get(i);

                    if (!TextUtils.isEmpty(localSku.getImage())) {
                        multiSkuEntity.setPic(localSku.getImage());
                    } else {
                        multiSkuEntity.setPic(mDefaultImg);
                    }
                    SkuBeanV2 skuBeanV2 = getSingleReferSkuBean(skuBeanV2s, localSku.getSkuCode());
                    if (skuBeanV2 != null) {
                        multiSkuEntity.setLocalSkuEntity(localSku.clone());
                        multiSkuEntity.setSkuBeanV2(skuBeanV2);
                        multiSkuEntities.add(multiSkuEntity);
                        checkSelectSkuPro(multiSkuEntity, selectedMultiSkus);
                    }
                }
                mSubSkuLists.add(multiSkuEntities);
            } else {
                // 多对多
                mStatus = 2;
                List<LocalSkuEntity> localSkuEntities1 = localPropSkuEntities.get(0).getLocalSkuEntities();
                List<LocalSkuEntity> localSkuEntities2 = localPropSkuEntities.get(1).getLocalSkuEntities();
                for (int i = 0; i < localSkuEntities1.size(); i++) {
                    List<MultiSkuEntity> multiSkuEntities = new ArrayList<>();
                    LocalSkuEntity localSkuEntity1 = localSkuEntities1.get(i);
                    for (int j = 0; j < localSkuEntities2.size(); j++) {
                        LocalSkuEntity localSkuEntity2 = localSkuEntities2.get(j);
                        SkuBeanV2 skuBeanV2 = getMultiReferSkuBean(skuBeanV2s,
                                localSkuEntity1.getSkuCode(), localSkuEntity2.getSkuCode());
                        if (skuBeanV2 != null) {

                            MultiSkuEntity multiSkuEntity = new MultiSkuEntity();
                            if (!TextUtils.isEmpty(localSkuEntity2.getImage())) {
                                multiSkuEntity.setPic(localSkuEntity2.getImage());
                            } else if (!TextUtils.isEmpty(localSkuEntity1.getImage())) {
                                multiSkuEntity.setPic(localSkuEntity1.getImage());
                            } else {
                                multiSkuEntity.setPic(mDefaultImg);
                            }

                            multiSkuEntity.setLocalSkuEntity(localSkuEntity2.clone());
                            multiSkuEntity.setSkuBeanV2(skuBeanV2);
                            checkSelectSkuPro(multiSkuEntity, selectedMultiSkus);
                            multiSkuEntities.add(multiSkuEntity);
                        }
                    }

                    //有库存才展示出来
                    if (CollectionUtils.isNotEmpty(multiSkuEntities)) {
                        mSubSkuLists.add(multiSkuEntities);
                        mTabLists.add(localSkuEntities1.get(i));
                    }
                }
            }
//            initViewPager();
        } else {
            mStatus = 0;
            MultiSkuEntity multiSkuEntity = new MultiSkuEntity();
//            multiSkuEntity.setLocalSkuEntity();
            LocalSkuEntity localSkuEntity = new LocalSkuEntity();
            localSkuEntity.setSkuLabel(ResUtils.getString(R.string.label_default));
            if (CollectionUtils.isNotEmpty(selectedMultiSkus)) {
                MultiSkuEntity multiSkuEntity1 = selectedMultiSkus.get(0);
                multiSkuEntity.setAmount(multiSkuEntity1.getAmount());
            }
            multiSkuEntity.setLocalSkuEntity(localSkuEntity);
            multiSkuEntity.setPic(mDefaultImg);
//            multiSkuEntity.setLocalSkuEntity();
            mMultiSkuEntity = multiSkuEntity;
//            initSingleSku();
        }

        if (CollectionUtils.isNotEmpty(mSelMultiSkuEntity)) {
            calculateTotalAmount();
        }
    }

    private void initSingleSku() {
        mLyVp.setVisibility(View.GONE);
        mCtlSingle.setVisibility(View.VISIBLE);
        mETNumSingle.setText(mMultiSkuEntity.getAmount() + "");
        mIVMinus.setEnabled(mMultiSkuEntity.getAmount() > 0);
        mTVSkuSingle.setText(mMultiSkuEntity.getLocalSkuEntity().getSkuLabel());

        mETNumSingle.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                SystemUtil.hideKeyboard(mETNumSingle);
                String priceStr = mETNumSingle.getText().toString().trim();
                if (!TextUtils.isEmpty(priceStr) && priceStr.matches("^0*\\d*")) {
                    priceStr = priceStr.replaceAll("^0*", "");
                    mETNumSingle.setText(priceStr);
                }

                String amountStr = mETNumSingle.getText().toString().trim();
                int amount = 0;
                if (!TextUtils.isEmpty(amountStr))
                    amount = Integer.parseInt(amountStr);

                mMultiSkuEntity.setAmount(amount);
                mIVMinus.setEnabled(mMultiSkuEntity.getAmount() > 0);
                calculateTotalAmount();
            }
            return false;
        });
    }

    @OnClick({R.id.iv_plus_sku_single_multi_sku_dialog, R.id.iv_minus_sku_single_multi_sku_dialog,
            R.id.v_mark_multi_sku_dialog, R.id.iv_close_multi_sku_dialog,
            R.id.et_num_sku_single_multi_sku_dialog,
            R.id.tv_confirm_multi_sku_dialog})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_plus_sku_single_multi_sku_dialog:
                mMultiSkuEntity.setAmount(mMultiSkuEntity.getAmount() + 1);
                mIVMinus.setEnabled(mMultiSkuEntity.getAmount() > 0);
                mETNumSingle.setText(mMultiSkuEntity.getAmount() + "");
                calculateTotalAmount();
                break;
            case R.id.iv_minus_sku_single_multi_sku_dialog:
                mMultiSkuEntity.setAmount(mMultiSkuEntity.getAmount() - 1);
                mIVMinus.setEnabled(mMultiSkuEntity.getAmount() > 0);
                mETNumSingle.setText(mMultiSkuEntity.getAmount() + "");
                calculateTotalAmount();
                break;
            case R.id.v_mark_multi_sku_dialog:
            case R.id.iv_close_multi_sku_dialog:
                if (mCallback != null) {
                    if (mStatus == 0) {
                        mCallback.onNoneSkuConfirm(mMultiSkuEntity, mTotalAmount, mCommonPrice, false);
                    } else if (isPriceFixed) {
                        mCallback.onSkuPriceFixedConfirm(mSelMultiSkuEntity, mTotalAmount, false);
                    } else {
                        mCallback.onSkuPriceRangeConfirm(mSelMultiSkuEntity, mTotalAmount, mCommonPrice, false);
                    }
                }
                dismiss();
                break;
            case R.id.tv_confirm_multi_sku_dialog:
                if (mCallback != null) {
                    if (mStatus == 0) {
                        mCallback.onNoneSkuConfirm(mMultiSkuEntity, mTotalAmount, mCommonPrice, true);
                    } else if (isPriceFixed) {
                        mCallback.onSkuPriceFixedConfirm(mSelMultiSkuEntity, mTotalAmount, true);
                    } else {
                        mCallback.onSkuPriceRangeConfirm(mSelMultiSkuEntity, mTotalAmount, mCommonPrice, true);
                    }
                }
                break;
//            case R.id.et_num_sku_single_multi_sku_dialog:
//                mTVSkuSingle.requestFocus();
//                SystemUtil.showKeyboard(view);
//                break;

        }
    }

    /**
     * 获取已选择的数据
     *
     * @param multiSkuEntity
     * @param selectedMultiSkus
     */
    private void checkSelectSkuPro(MultiSkuEntity multiSkuEntity, List<MultiSkuEntity> selectedMultiSkus) {
        if (CollectionUtils.isNotEmpty(selectedMultiSkus)) {
            String propPath = multiSkuEntity.getSkuBeanV2().getPropPath();
            for (int i = 0; i < selectedMultiSkus.size(); i++) {
                MultiSkuEntity entity = selectedMultiSkus.get(i);
                if (propPath.equals(entity.getSkuBeanV2().getPropPath())) {
                    multiSkuEntity.setAmount(entity.getAmount());
                }
            }
        }
    }

    /**
     * 获取对应的skuBean
     *
     * @param skuBeanV2s
     * @param skuCode
     * @return
     */
    private SkuBeanV2 getSingleReferSkuBean(List<SkuBeanV2> skuBeanV2s, String skuCode) {
        if (!TextUtils.isEmpty(skuCode)) {
            for (int i = 0; i < skuBeanV2s.size(); i++) {
                String propPath = skuBeanV2s.get(i).getPropPath();
                int quantity = skuBeanV2s.get(i).getQuantityInt();
                if (quantity > 0 && !TextUtils.isEmpty(propPath) && propPath.contains(skuCode)) {
                    return skuBeanV2s.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 获取对应的skuBean
     *
     * @param skuBeanV2s
     * @param skuCode
     * @return
     */
    private SkuBeanV2 getMultiReferSkuBean(List<SkuBeanV2> skuBeanV2s, String skuCode, String skuCode2) {
        if (!TextUtils.isEmpty(skuCode)) {
            for (int i = 0; i < skuBeanV2s.size(); i++) {
                String propPath = skuBeanV2s.get(i).getPropPath();
                int quantity = skuBeanV2s.get(i).getQuantityInt();
                if (quantity > 0 && !TextUtils.isEmpty(propPath) && propPath.contains(skuCode)
                        && propPath.contains(skuCode2)) {
                    return skuBeanV2s.get(i);
                }
            }
        }
        return null;
    }

    public interface Callback {
        void onSkuPriceRangeConfirm(List<MultiSkuEntity> multiSkuEntities, double totalAmount, double price, boolean submit);

        void onSkuPriceFixedConfirm(List<MultiSkuEntity> multiSkuEntities, double totalAmount, boolean submit);

        void onNoneSkuConfirm(MultiSkuEntity multiSkuEntity, double totalAmount, double price, boolean submit);
    }

    public static class Builder {
        private Context mContext;
        private List<LocalPropSkuEntity> mLocalPropSkuEntities;
        private List<SkuBeanV2> mSkuBeans;
        private GoodsDetailItemBean mGoodsDetailBean;

        private List<MultiSkuEntity> mMultiSkuEntities;
        private Callback mCallback;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setCallback(Callback callback) {
            mCallback = callback;
            return this;
        }

        public Builder setSelectedMultiSkuEntities(List<MultiSkuEntity> multiSkuEntities) {
            mMultiSkuEntities = multiSkuEntities;
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

        public Builder setGoodsDetailBean(GoodsDetailItemBean goodsDetailBean) {
            mGoodsDetailBean = goodsDetailBean;
            return this;
        }


        public MultiSelSkuDialog build() {
            MultiSelSkuDialog dialog = new MultiSelSkuDialog();
            dialog.setGoodsDetailBean(mGoodsDetailBean);
            dialog.setDatas(mSkuBeans, mLocalPropSkuEntities, mMultiSkuEntities);
            dialog.setCallback(mCallback);
            return dialog;
        }
    }
}
