package com.ysarch.vmall.page.main.shoye;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.uibase.recyclerview.FRecyclerView;
import com.ysarch.uibase.recyclerview.itemDecoration.FRcyGridLayoutDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.ShouYeSubpageRcyAdapter;
import com.ysarch.vmall.common.adapter.ShouYeSubpageRcyAdapterV2;
import com.ysarch.vmall.common.adapter.viewholder.HomeBannerVH;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.component.dialog.TBShareCmdDialog;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.HomeBannerBean;
import com.ysarch.vmall.domain.bean.HomeContentResult;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.main.presenter.ShouYeWelcomePresenter;
import com.ysarch.vmall.page.recharge.RechargeAmountActivity;
import com.ysarch.vmall.page.search.SearchActivity;
import com.ysarch.vmall.page.webview.CommonWebActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.utils.CollectionUtils;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by fysong on 09/09/2020
 **/
public class ShouyeWelcomeFragment extends CommonPureListFragment<ShouYeWelcomePresenter> {

    private ShouYeSubpageRcyAdapterV2 mRcyAdapter;
    private HomeContentResult mHomeContentResult;
    private FRcyGridLayoutDecoration mLayoutDecoration;
    private boolean isFirstLoad = true;
//    private boolean mHasMore;

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return mRcyAdapter;
    }

    @Override
    protected void requestData(int page) {
//        refreshData();
//        getPresenter().requestContents();
        getPresenter().requestRecGoods(page);
    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        return gridLayoutManager;
    }

    @Override
    public void bindUI(View mRootView) {
        mRcyAdapter = new ShouYeSubpageRcyAdapterV2(getContext());
        mRcyAdapter.setOnItemClickListener((position, data) -> {
//            GoodsItemBeanV2 goodsItemBean = (GoodsItemBeanV2) data;
            if (data == null) {
                TBShareCmdDialog.Builder builder = new TBShareCmdDialog.Builder(getContext())
                        .setCallback(new TBShareCmdDialog.Callback() {
                            @Override
                            public void onNavGoodsDetail(long goodsId) {

                            }
                        }).setTBShareCmdBean(null);

                builder.build().show();
            } else {
                NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
                        GoodsDetailActivity.getBundle(((GoodsItemBeanV2) data).getId(),Constants.TYPE_ENTRY_SHOUYE));
            }
        });
        mRcyAdapter.setBannerCallback(new HomeBannerVH.Callback() {
            @Override
            public void onBannerClick(int position, HomeBannerBean data) {
//                if (!TextUtils.isEmpty(bannerBean.getUrl())) {
//                    RouterManager.handleRouter(getActivity(), bannerBean.getUrl());
//                }
                switch (data.getOperateType()){
                    case Constants.OPERATETYPE_RECHARGE:
                        NavHelper.startActivity(getActivity(), RechargeAmountActivity.class);
                        break;
                    case Constants.OPERATETYPE_CUSTOMER_SERVICE:
//                        NavHelper.startWeb(context,"https://m.me/105800634679932?ref=sabayshop");
                        NavHelper.startWeb(context,data.getUrl());
//                        NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("https://m.me/105800634679932?ref=sabayshop"));
//                        if (UserInfoManager.judeIsLogin(context))
//                            EaseHelper.getInstance().navKefu(context, ShouyeWelcomeFragment.this);
//                        else
//                            NavHelper.startActivity(getActivity(), AccountActivity.class, AccountActivity.getBundle(AccountActivity.TYPE_LOGIN));
                        break;
                    case Constants.OPERATETYPE_HELP:
                        int lanId = AppContext.getsInstance().getLanguageEntity().getLanId();
                        switch (lanId){
                            case Constants.ID_LAN_KM:
                                NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://portal.sabayshop.club/help/km/index.html"));
                                break;
                            case Constants.ID_LAN_ZH:
                                NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://portal.sabayshop.club/help/zh/index.html"));
                                break;
                            default:
                                NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle(" http://portal.sabayshop.club/help/en/index.html"));
                                break;
                        }
                        break;
                    case Constants.OPERATETYPE_SEARCH_BY_KEYWORD:
                        NavHelper.startActivity(getActivity(), SearchActivity.class, SearchActivity.getBundle(data.getKeyword(), true));
                        break;
                    case Constants.OPERATETYPE_GOODS_H5:
                    case Constants.OPERATETYPE_H5:
//                        if("https://m.me/105800634679932?ref=sabayshop".equals(data.getUrl())){
//                            NavHelper.startWeb(context,data.getUrl());
//                            return;
//                        }
//                        if(data.getUrl().contains("http://")||data.getUrl().contains("https://")){
//                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle(data.getUrl()));
//                        }else {
//                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://"+data.getUrl()));
//                        }
                        NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle(data.getUrl()));
                        break;
                    case Constants.OPERATETYPE_OUT_H5:
                        NavHelper.startWeb(context,data.getUrl());
                        break;

                }
            }
        });

        mRcyAdapter.setCallback((position, data, mTextView) -> {
            switch (data.getOperateType()){
                case Constants.OPERATETYPE_RECHARGE:
                    NavHelper.startActivity(getActivity(), RechargeAmountActivity.class);
                    break;
                case Constants.OPERATETYPE_CUSTOMER_SERVICE:
//                        NavHelper.startWeb(context,"https://m.me/105800634679932?ref=sabayshop");
                    NavHelper.startWeb(context,data.getUrl());
//                        NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("https://m.me/105800634679932?ref=sabayshop"));
//                        if (UserInfoManager.judeIsLogin(context))
//                            EaseHelper.getInstance().navKefu(context, ShouyeWelcomeFragment.this);
//                        else
//                            NavHelper.startActivity(getActivity(), AccountActivity.class, AccountActivity.getBundle(AccountActivity.TYPE_LOGIN));
                    break;
                case Constants.OPERATETYPE_HELP:
                    int lanId = AppContext.getsInstance().getLanguageEntity().getLanId();
                    switch (lanId){
                        case Constants.ID_LAN_KM:
                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://portal.sabayshop.club/help/km/index.html"));
                            break;
                        case Constants.ID_LAN_ZH:
                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://portal.sabayshop.club/help/zh/index.html"));
                            break;
                        default:
                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle(" http://portal.sabayshop.club/help/en/index.html"));
                            break;
                    }
                    break;
                case Constants.OPERATETYPE_SEARCH_BY_KEYWORD:
                    NavHelper.startActivity(getActivity(), SearchActivity.class, SearchActivity.getBundle(data.getKeyword(), true));
                    break;
                case Constants.OPERATETYPE_GOODS_H5:
                case Constants.OPERATETYPE_H5:
//                        if("https://m.me/105800634679932?ref=sabayshop".equals(data.getUrl())){
//                            NavHelper.startWeb(context,data.getUrl());
//                            return;
//                        }
//                        if(data.getUrl().contains("http://")||data.getUrl().contains("https://")){
//                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle(data.getUrl()));
//                        }else {
//                            NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle("http://"+data.getUrl()));
//                        }
                    NavHelper.startActivity(context, CommonWebActivity.class, CommonWebActivity.getBundle(data.getUrl()));
                    break;
                case Constants.OPERATETYPE_OUT_H5:
                    NavHelper.startWeb(context,data.getUrl());
                    break;

            }
        });
        super.bindUI(mRootView);
        int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
        int gap = SizeUtils.dp2px(10);
        initRecyclerView(mLayoutDecoration = new FRcyGridLayoutDecoration(gap, 0, 0, 2, mRcyAdapter)
                .setGridPlaceViewType(ShouYeSubpageRcyAdapter.TYPE_GOODS).setHeadCount(3));
//        initRecyclerView(mLayoutDecoration = new FRcyGridLayoutDecoration(gap, gap, marginH, 2, mRcyAdapter)
//                .setGridPlaceViewType(ShouYeSubpageRcyAdapter.TYPE_GOODS).setHeadCount(3));
//        initRecyclerView(null);
        getRecycleView().setFRcySpanLookUp(new FRecyclerView.FRcySpanLookUp() {
            @Override
            public int getSpanSize(int position) {
                if (mRcyAdapter.getItemViewType(position) != ShouYeSubpageRcyAdapter.TYPE_GOODS) {
                    return 2;
                }
                return 1;
            }
        });

//        int gaps = SizeUtils.dp2px(5);
//        getRecycleView().setPadding(gaps,gaps,gaps,gaps);
    }

    @Override
    protected boolean isPagingData() {
        return true;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getPresenter().requestContents();
    }

    @Override
    public ShouYeWelcomePresenter newPresenter() {
        return new ShouYeWelcomePresenter();
    }

    public void onGoodsDataSucc(int page, ListResult<GoodsItemBeanV2> homeRecResult) {

        
        if(homeRecResult == null || CollectionUtils.isEmpty(homeRecResult.getList()) && isFirstLoad){
            isFirstLoad = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getPresenter().requestRecGoods(1);
                }
            }, 500);

            return;
        } else {
            mPage = page;
            resetUIStatus(page, true);
        }

        if(mPage == 1){
            if(mHomeContentResult != null && CollectionUtils.isNotEmpty(mHomeContentResult.getHomeBannerBeans())) {
//                mLayoutDecoration.setHeadCount(2);
                mRcyAdapter.refreshWelcomeDatas(mHomeContentResult.getHomeBannerBeans(),mHomeContentResult.getHomeRecommend(), homeRecResult.getList());
            } else {
//                mLayoutDecoration.setHeadCount(1);
                mRcyAdapter.refreshWelcomeDatas(null,null, homeRecResult.getList());
            }
        } else {
            mRcyAdapter.appendWelcomeDatas(homeRecResult.getList());
        }

//        mHasMore = homeRecResult.getTotalPage() > mPage;

    }

    public void onContentsSucc(HomeContentResult homeContentResult) {
        if (homeContentResult != null) {
            mHomeContentResult = homeContentResult;
        }

        getPresenter().requestRecGoods(1);
    }


    public void onGoodsDataFail() {
        dismissLoadingDialog();

        if (mHomeContentResult != null) {
            mRcyAdapter.refreshWelcomeDatas(mHomeContentResult.getHomeBannerBeans(),mHomeContentResult.getHomeRecommend(), null);
        }
        resetUIStatus(1, false);

    }

    @Override
    protected boolean hasMore() {
        return getPresenter().hasMore;
    }


    @Override
    protected String getPageName() {
        return "首页最受欢迎";
    }

}
