package com.ysarch.vmall.page.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysarch.uibase.StatusBarView;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.recyclerview.itemDecoration.LinearVerDecoration;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.CartGoodsAdapter;
import com.ysarch.vmall.common.adapter.viewholder.CartGoodsVH;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.component.dialog.DeleteCartOrderDialog;
import com.ysarch.vmall.component.dialog.SkuDialogV1;
import com.ysarch.vmall.domain.bean.CartGoodsBean;
import com.ysarch.vmall.domain.bean.GenerateOrderConfirmResult;
import com.ysarch.vmall.domain.bean.GoodsDetailBean;
import com.ysarch.vmall.domain.bean.SkuBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.local.LocalPropSkuEntity;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.main.presenter.MainCartPresenter;
import com.ysarch.vmall.page.orders.OrderConfirmActivity;
import com.ysarch.vmall.utils.CalculateUtil;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.ysarch.vmall.utils.SkuParser;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.event.EventCenter;
import com.yslibrary.event.IEventListener;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ??????
 * Created by fysong on 24/08/2020
 **/
public class MainCartFragment extends BaseFragment<MainCartPresenter> implements IEventListener {
    @BindView(R.id.rcy_cart_main)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_edit_cart_main)
    TextView mTVEdit;
    @BindView(R.id.ctv_sel_all_cart_main)
    CompatTextView mCTVSelAll;
    @BindView(R.id.tv_price_cart_main)
    TextView mTVPrice;
    @BindView(R.id.tv_confirm_cart_main)
    TextView mTVConfirm;
    @BindView(R.id.srl_cart_main)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv_empty_cart_main)
    ImageView mIVEmpty;
    @BindView(R.id.iv_back_cart_main)
    ImageView mIVBack;
    @BindView(R.id.sbv_cart_main)
    StatusBarView mStatusBarView;
//    @BindView(R.id.iv_radio_goods_cart)
//    ImageView mIvCart;

    private CartGoodsAdapter mAdapter;
    /**
     * 0?????????
     * 1:??????
     */
    private int mStatus;
    private List<CartGoodsBean> mCartGoodsBeans = new ArrayList<>();
    private List<CartGoodsBean> mSelectCartGoodsBeans = new ArrayList<>();
    private double mPrice;
    private SkuDialogV1 mSkuDialog;
    private boolean isRefreshing = false;
//    private int selectCount = 0;

    //??????????????????
    private boolean isSeparatePage;


    public MainCartFragment() {
        this(false);
    }

    public MainCartFragment(boolean isSeparatePage) {
        this.isSeparatePage = isSeparatePage;
    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (!isRefreshing) {
                isRefreshing = true;
                getPresenter().requestCartList(false);
            }

        });

        if (isSeparatePage) {
            mIVBack.setVisibility(View.VISIBLE);
            mStatusBarView.setVisibility(View.GONE);
        }
    }


    @Override
    public void bindEvent() {
        super.bindEvent();
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_USER_ACCOUNT_CHANGE, this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        initAdapter();
//        Log.e("MainCartFragment", "initData" );
        isRefreshing = true;
        getPresenter().requestCartList(true);
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new CartGoodsAdapter(getContext());
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    if (mAdapter.getItemCount() > 0) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mIVEmpty.setVisibility(View.GONE);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        mIVEmpty.setVisibility(View.VISIBLE);

                        switchEditMode(0);
                        mCTVSelAll.setSelected(false);
                    }
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                }


                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    if (mAdapter.getItemCount() > 0) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mIVEmpty.setVisibility(View.GONE);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        mIVEmpty.setVisibility(View.VISIBLE);
                        switchEditMode(0);
                        mCTVSelAll.setSelected(false);
                    }
                }
            });
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
//            int gap = SizeUtils.dp2px(16);
//            mRecyclerView.addItemDecoration(new LinearVerDecoration(0, gap, gap, gap));
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setCallback(new CartGoodsVH.Callback() {
                @Override
                public void onItemSelectStatusChange(int position, CartGoodsBean cartGoodsBean) {
                    if (!cartGoodsBean.isSelected()) {
                        mCTVSelAll.setSelected(false);
                        mSelectCartGoodsBeans.remove(cartGoodsBean);
//                        selectCount--;
                    } else {
//                        selectCount++;
                        mSelectCartGoodsBeans.add(cartGoodsBean);
                    }
//                    if (selectCount == mCartGoodsBeans.size()) {
//                        mCTVSelAll.setSelected(true);
//                    }
                    boolean isAll = true;
                    for(CartGoodsBean bean:mCartGoodsBeans){
                        boolean selected = bean.isSelected();
                        if (!bean.isSelected()){
                            isAll = false;
                            break;
                        }
                    }
                    mCTVSelAll.setSelected(isAll);
                    if (mStatus == 0) {
                        calculatePrice();
                    }

                }

                @Override
                public void onSkuClick(int position, CartGoodsBean cartGoodsBean) {
                    getPresenter().requestGoodsDetail(position, cartGoodsBean);
                }

                @Override
                public void onItemNumChange(int position, CartGoodsBean cartGoodsBean) {
                    if (mStatus == 0) {
                        calculatePrice();
                    }
                }

                @Override
                public void onQuantityChange(int id, int quantity) {
                    getPresenter().updateCartQuantity(id, quantity);
                }

                @Override
                public void onItemClick(int position, CartGoodsBean cartGoodsBean) {

                    NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
                            GoodsDetailActivity.getBundle(cartGoodsBean.getProductId(),cartGoodsBean.getSource(), Constants.TYPE_ENTRY_CART));
                }
            });
        }
    }

    private void switchEditMode(int mode) {
        mStatus = mode;
        if (mStatus == 1) {
            mTVEdit.setText(R.string.label_complete);
            mTVConfirm.setText(R.string.label_delete);
            mTVPrice.setVisibility(View.INVISIBLE);
            mSwipeRefreshLayout.setEnabled(false);
        } else {
            mTVEdit.setText(R.string.label_edit);
            mTVConfirm.setText(R.string.label_create_order);
            mTVPrice.setVisibility(View.VISIBLE);
            calculatePrice();
            mSwipeRefreshLayout.setEnabled(true);
        }
    }


    @OnClick(R.id.iv_back_cart_main)
    void onBackClick() {
        if (isSeparatePage)
            getActivity().finish();
    }

    @OnClick({R.id.tv_edit_cart_main, R.id.ctv_sel_all_cart_main, R.id.tv_confirm_cart_main})
    void onViewClick(View view) {
        if (!UserInfoManager.judeIsLogin(getActivity())) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_edit_cart_main:

//                    if (mStatus == 0) {
//                        mTVEdit.setText("??????");
//                        mTVConfirm.setText("??????");
//                        mTVPrice.setVisibility(View.GONE);
//                        mStatus = 1;
//                    } else {
//                        mTVEdit.setText("??????");
//                        mTVConfirm.setText("????????????");
//                        mTVPrice.setVisibility(View.VISIBLE);
//                        mStatus = 0;
//                        calculatePrice();
//                    }

                switchEditMode(~mStatus & 1);

                break;
            case R.id.ctv_sel_all_cart_main:
                view.setSelected(!view.isSelected());
//                mIvCart.setSelected(!view.isSelected());
                if (CollectionUtils.isNotEmpty(mCartGoodsBeans)) {
                    for (int i = 0; i < mCartGoodsBeans.size(); i++) {
                        mCartGoodsBeans.get(i).setSelected(view.isSelected());
                    }
                    mAdapter.notifyDataSetChanged();
                }

                if (mStatus == 0) {
                    calculatePrice();
                }
                break;
            case R.id.tv_confirm_cart_main:

                if (CollectionUtils.isEmpty(mCartGoodsBeans)) {
                    return;
                }

//                if (selectCount == 0) {
//                    return;
//                }

                if (mStatus == 0) {
                    List<String> ids = new ArrayList<>();

                    for (int i = 0; i < mCartGoodsBeans.size(); i++) {
                        if (mCartGoodsBeans.get(i).isSelected()) {
                            ids.add(mCartGoodsBeans.get(i).getId() + "");
                        }
                    }
                    if (CollectionUtils.isNotEmpty(ids)) {
                        getPresenter().generateConfirmOrder(ids);
                    } else {
                        showTs(ResUtils.getString(R.string.tip_no_goods_selected));
                    }

                } else {
                    new DeleteCartOrderDialog.Builder(context)
                            .setTitle(getString(R.string.dialog_confirm_delete_cart))
                            .setCancelText(getString(R.string.dialog_delete))
                            .setSubmitText(getString(R.string.dialog_think_again))
                            .setDeleteCallback(new DeleteCartOrderDialog.DeleteCallback() {
                                @Override
                                public void onConfirm() {
                                    if (mCTVSelAll.isSelected()) {
                                        getPresenter().clearCart();
                                    } else {
                                        List<CartGoodsBean> cartGoodsBeans = new ArrayList<>();

                                        for (int i = 0; i < mCartGoodsBeans.size(); i++) {
                                            if (mCartGoodsBeans.get(i).isSelected()) {
                                                cartGoodsBeans.add(mCartGoodsBeans.get(i));
                                            }
                                        }
                                        if (CollectionUtils.isNotEmpty(cartGoodsBeans)) {
                                            getPresenter().deleteCertainCartGoods(cartGoodsBeans);
                                        }
                                    }
                                }
                            }).build().show();

                }



                break;
        }
    }

    private void calculatePrice() {
        if (CollectionUtils.isNotEmpty(mCartGoodsBeans)) {
            mPrice = 0;
            for (int i = 0; i < mCartGoodsBeans.size(); i++) {
                CartGoodsBean cartGoodsBean = mCartGoodsBeans.get(i);
                if (cartGoodsBean.isSelected()) {
                    mPrice = CalculateUtil.add(mPrice,
                            CalculateUtil.mul(cartGoodsBean.getPrice(), cartGoodsBean.getQuantity()));
                }
            }

            mTVPrice.setText(VMallUtils.convertPriceString(mPrice));
        } else {
            mTVPrice.setText(VMallUtils.convertPriceString(0));
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.e("MainCartFragment", "onResume");
        if (getUserVisibleHint() && UserInfoManager.isLogin()) {
            mTVPrice.setText(VMallUtils.convertPriceString(0));
            mCTVSelAll.setSelected(false);
            getPresenter().requestCartList(CollectionUtils.isEmpty(mCartGoodsBeans));
        }

        mCTVSelAll.setSelected(false);

//        if(!getUserVisibleHint()){
//            switchEditMode(0);
//            mCTVSelAll.setSelected(false);
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("MainCartFragment", "onAttach ");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (mSkuDialog != null && mSkuDialog.isShowing()) {
                mSkuDialog.dismiss();
                mSkuDialog = null;
            }

            switchEditMode(0);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("MainCartFragment", "onDetach");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_cart_main;
    }

    @Override
    public MainCartPresenter newPresenter() {
        return new MainCartPresenter();
    }

    public void onCartDataSucc(List<CartGoodsBean> cartGoodsBeans) {
        if (UserInfoManager.isLogin()) {
            isRefreshing = false;
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            mCartGoodsBeans = cartGoodsBeans;
            initAdapter();
            refreshSelectCount();
            mAdapter.refreshData(mCartGoodsBeans);
        }
    }
    private void refreshSelectCount(){
        for(Iterator<CartGoodsBean> it = mSelectCartGoodsBeans.iterator(); it.hasNext();){
            CartGoodsBean next = it.next();
            if(!mCartGoodsBeans.contains(next)){
                mSelectCartGoodsBeans.remove(next);
            }
        }
//        for (CartGoodsBean cartGoodsBean :mCartGoodsBeans){
//            cartGoodsBean.setSelected(mSelectCartGoodsBeans.contains(cartGoodsBean));
//        }
//        selectCount = mSelectCartGoodsBeans.size();
    }

    public void onCartDataFail() {
        isRefreshing = false;
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void onClearCartSucc() {
//        selectCount = 0;
        mSelectCartGoodsBeans.clear();
        mAdapter.refreshData(null);
        mTVPrice.setText(VMallUtils.convertPriceString(0));
        mCartGoodsBeans.clear();
        mCTVSelAll.setSelected(false);
    }


    public void onDataFail() {
    }

    public void onDeleteCartSucc(List<CartGoodsBean> cartGoodsBeans) {
        if (CollectionUtils.isEmpty(cartGoodsBeans))
            return;
        refreshSelectCount();
        if (cartGoodsBeans.size() == 1) {
            int index = mCartGoodsBeans.indexOf(cartGoodsBeans.get(0));
            mAdapter.deleteCart(index);
        } else {
            mCartGoodsBeans.removeAll(cartGoodsBeans);
            mAdapter.refreshData(mCartGoodsBeans);
        }

        mCTVSelAll.setSelected(false);
    }

    public void onDataSuccess(int position, CartGoodsBean cartGoodsBean, GoodsDetailBean goodsDetailBean) {
        List<LocalPropSkuEntity> propSkuDatas = SkuParser.parseProps(goodsDetailBean.getPropsName());

        SkuBean skuBean = null;
        if (!TextUtils.isEmpty(cartGoodsBean.getProductSkuId()) && goodsDetailBean.getSkus() != null
                && CollectionUtils.isNotEmpty(goodsDetailBean.getSkus().getSku())) {
            String[] skus = cartGoodsBean.getProductSkuId().split(";");
            List<SkuBean> skuBeans = goodsDetailBean.getSkus().getSku();

            loop2:
            for (int i = 0; i < skuBeans.size(); i++) {
                String skuId = skuBeans.get(i).getSkuId();
                loop1:
                for (int j = 0; j < skus.length; j++) {
                    if (TextUtils.isEmpty(skus[j])) {
                        if (j == skus.length - 1) {
                            skuBean = skuBeans.get(i);
                            break loop2;
                        }
                    } else if (!skuId.contains(skus[j])) {
                        break loop1;
                    } else if (j == skus.length - 1) {
                        skuBean = skuBeans.get(i);
                        break loop2;
                    }
                }
            }
        }
        SkuDialogV1.Builder builder = new SkuDialogV1.Builder(getContext())
                .setSkuBeans(goodsDetailBean.getSkus().getSku())
                .setImages(goodsDetailBean.getPropsImg())
                .setDefaultImg(goodsDetailBean.getPicUrl())
                .setLocalPropSkuEntities(propSkuDatas)
                .setSelectedSkuBean(skuBean)
                .setGoodsDetailBean(goodsDetailBean)
                .setDialogCallback((skuString, skuBean1, img, quantity) -> {
                    getPresenter().updateCartGoodsSkuData(position, goodsDetailBean, cartGoodsBean, skuBean1, img, skuString, quantity);
                });
        mSkuDialog = builder.build();
        mSkuDialog.show();
    }


    public boolean onBackPress() {
        if (mSkuDialog != null && mSkuDialog.isShowing()) {
            mSkuDialog.dismiss();
            mSkuDialog = null;
            return true;
        }
        return false;
    }

    public void onSkuChangeSucc(int position, CartGoodsBean cartGoodsBean) {
        mAdapter.refreshCertainCartGoods(position, cartGoodsBean);
        if (mSkuDialog != null && mSkuDialog.isShowing()) {
            mSkuDialog.dismiss();
            mSkuDialog = null;
        }
    }

    public void onGenerateConfirmOrderSucc(GenerateOrderConfirmResult generateOrderConfirmResult) {
        NavHelper.startActivity(getActivity(), OrderConfirmActivity.class, OrderConfirmActivity.getBundle(generateOrderConfirmResult));
    }

    @Override
    public void onNotify(Message message) {
        if (message.what == NotificationDef.EVENT_USER_ACCOUNT_CHANGE) {
            if (!UserInfoManager.isLogin()) {
                clearDatas();
            }
        }
    }


    private void clearDatas() {
        if (CollectionUtils.isNotEmpty(mCartGoodsBeans)) {
            refreshSelectCount();
            mCartGoodsBeans.clear();
            if (mAdapter != null) {
                mAdapter.refreshData(mCartGoodsBeans);
            }
        }
        switchEditMode(0);
        mCTVSelAll.setSelected(false);
    }


//    private void checkEmpty(){
//        if(mAdapter ==)
//    }


    @Override
    protected String getPageName() {
        return "????????????";
    }
}
