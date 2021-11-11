package com.ysarch.vmall.page.main.shoye;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.uibase.recyclerview.itemDecoration.FRcyGridLayoutDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.ShouYeSubpageRcyAdapter;
import com.ysarch.vmall.common.adapter.ShouYeSubpageRcyAdapterV2;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.main.presenter.ShouYeSubpagePresenter;
import com.ysarch.vmall.page.recharge.RechargeAmountActivity;
import com.ysarch.vmall.page.search.SearchActivity;
import com.ysarch.vmall.page.webview.CommonWebActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by fysong on 09/09/2020
 **/
public class ShouyeSubpageFragment extends CommonPureListFragment<ShouYeSubpagePresenter> {

    private int mCateId;
    private String mCateName;

    private List<CateLevelBean> mCateLevelBeans;
    private ShouYeSubpageRcyAdapterV2 mRcyAdapter;
    private CateLevelBean mLastSelectedCate;
    private TextView mTVCateSelected;
    private String mCurCateName;
    private int mCurCateDataId;

    private boolean mHasMore;

//    public static Bundle getBundle(int cateId, List<CateLevelBean> cateLevelBeans) {
//        Bundle bundle = new Bundle();
//        bundle.putInt(BundleKey.ARG_CATE_ID, cateId);
//        bundle.putString(BundleKey.ARG_CATE_LIST, new Gson().toJson(cateLevelBeans));
//        return bundle;
//    }

    public static Bundle getBundle(String cateName, List<CateLevelBean> cateLevelBeans,String keywords) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_CATE_NAME, cateName);
        bundle.putString(BundleKey.ARG_CATE_LIST, new Gson().toJson(cateLevelBeans));
        bundle.putString(BundleKey.ARG_CATE_KEYWORDS, keywords);
        return bundle;
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return mRcyAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (mRcyAdapter.getItemViewType(position) != ShouYeSubpageRcyAdapter.TYPE_GOODS) {
//                    return 2;
//                }
//                return 1;
//            }
//        });

        return gridLayoutManager;
    }

    @Override
    protected void requestData(int page) {
        if (mLastSelectedCate != null) {
            getPresenter().requestCateGoods(mLastSelectedCate.getName(), page, false);
        } else {
            getPresenter().requestCateGoods(mCateName, page, false);
        }
    }


    @Override
    protected void refreshData() {
        isRefresh = true;
        isLoadingMore = false;

        if (mLastSelectedCate != null) {
            mLastSelectedCate.setSelected(false);
            mTVCateSelected.setSelected(false);
            mLastSelectedCate = null;
            mTVCateSelected = null;
        }

        getPresenter().requestCateGoods(mCateName, 1, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mRcyAdapter = new ShouYeSubpageRcyAdapterV2(getContext());

        mRcyAdapter.setOnItemClickListener((position, data) -> {
            if (data == null)
                NavHelper.startActivity(getActivity(), GoodsDetailActivity.class);
            else
                NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
                        GoodsDetailActivity.getBundle(((GoodsItemBeanV2) data).getId()));
        });


        mRcyAdapter.setCallback((position, data, mTextView) -> {
            switch (data.getOperateType()){
                case Constants.OPERATETYPE_RECHARGE:
                    NavHelper.startActivity(getActivity(), RechargeAmountActivity.class);
                    break;
                case Constants.OPERATETYPE_CUSTOMER_SERVICE:
//                    if (UserInfoManager.judeIsLogin(context))
//                        EaseHelper.getInstance().navKefu(context, this);
//                    else
//                        NavHelper.startActivity(getActivity(), AccountActivity.class, AccountActivity.getBundle(AccountActivity.TYPE_LOGIN));
//                    NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("https://m.me/105800634679932?ref=sabayshop"));
                    NavHelper.startWeb(context,"https://m.me/105800634679932?ref=sabayshop");
                    break;
                case Constants.OPERATETYPE_HELP:
                    break;
                case Constants.OPERATETYPE_SEARCH_BY_KEYWORD:
//                case Constants.OPERATETYPE_SEARCH_BY_KEYWORD_SUBPAGE:
                    NavHelper.startActivity(getActivity(), SearchActivity.class, SearchActivity.getBundle(data.getKeyword(), true));
                    break;
                case Constants.OPERATETYPE_GOODS_H5:
                case Constants.OPERATETYPE_H5:
                    if("https://m.me/105800634679932?ref=sabayshop".equals(data.getUrl())){
                        NavHelper.startWeb(context,data.getUrl());
                        return;
                    }
                    if(data.getUrl().contains("http://")||data.getUrl().contains("https://")){
                        NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle(data.getUrl()));
                    }else {
                        NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://"+data.getUrl()));
                    }
                    break;

            }
        });

        mCateName = getArguments().getString(BundleKey.ARG_CATE_KEYWORDS);
        String catesStr = getArguments().getString(BundleKey.ARG_CATE_LIST);
        if (!TextUtils.isEmpty(catesStr)) {
            mCateLevelBeans = new Gson().fromJson(catesStr, new TypeToken<List<CateLevelBean>>() {
            }.getType());
        }
        int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
        int gap = SizeUtils.dp2px(10);
        initRecyclerView(new FRcyGridLayoutDecoration(gap, gap, marginH, 2, mRcyAdapter)
                .setGridPlaceViewType(ShouYeSubpageRcyAdapter.TYPE_GOODS).setHeadCount((mCateLevelBeans==null||mCateLevelBeans.size()==0)?0:1));
//        initRecyclerView(null);
        getRecycleView().setFRcySpanLookUp(position -> {
            if (mRcyAdapter.getItemViewType(position) != ShouYeSubpageRcyAdapter.TYPE_GOODS) {
                return 2;
            }
            return 1;
        });

        getPresenter().requestCateGoods(mCateName, 1, getUserVisibleHint());

        int gaps = SizeUtils.dp2px(5);
        getRecycleView().setPadding(gaps,0,gaps,gaps);
    }

    @Override
    public ShouYeSubpagePresenter newPresenter() {
        return new ShouYeSubpagePresenter();
    }

    @Override
    protected boolean hasMore() {
        return mHasMore;
    }


    public void onDataSucc(String cateName, int page, ListResult<GoodsItemBeanV2> goodsItemBeanListResult) {
        mPage = page;
        mCurCateName = cateName;
        if (mPage == 1) {
            mRcyAdapter.refreshSubCateDatas(mCateLevelBeans, goodsItemBeanListResult.getList());
        } else {
            mRcyAdapter.appendGoods(goodsItemBeanListResult.getList());
        }

        mHasMore = goodsItemBeanListResult.getTotalPage() > mPage;
        resetUIStatus(page, true);
    }

    public void onDataFail(String cateName, int page) {
        resetUIStatus(page, false);

        if (!cateName.equals(mCurCateName) && page == 1) {
            mRcyAdapter.refreshSubCateDatas(mCateLevelBeans, null);
            mCurCateName = cateName;
            mHasMore = false;
        }
    }


    @Deprecated
    public void onDataSucc(int cateId, int page, ListResult<GoodsItemBeanV2> goodsItemBeanListResult) {
        mCurCateDataId = cateId;
        mPage = page;
        mHasMore = goodsItemBeanListResult.getTotalPage() > mPage;
        resetUIStatus(page, true);

        if (mPage == 1) {
            mRcyAdapter.refreshSubCateDatas(mCateLevelBeans, goodsItemBeanListResult.getList());
        } else {
            mRcyAdapter.appendGoods(goodsItemBeanListResult.getList());
        }
    }

    @Deprecated
    public void onDataFail(int cateId, int page) {
        resetUIStatus(page, false);

        if (cateId != mCurCateDataId && page == 1) {
            mRcyAdapter.refreshSubCateDatas(mCateLevelBeans, null);
            mCurCateDataId = cateId;
            mHasMore = false;
        }
    }
}
