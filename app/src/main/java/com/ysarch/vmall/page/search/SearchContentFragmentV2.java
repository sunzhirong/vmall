package com.ysarch.vmall.page.search;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tendcloud.tenddata.TCAgent;
import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.uibase.recyclerview.itemDecoration.FRcyGridLayoutDecoration;
import com.ysarch.uibase.recyclerview.itemDecoration.FRcyGridLayoutNormalDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.ShouYeSubpageRcyAdapter;
import com.ysarch.vmall.common.adapter.ShouYeSubpageRcyAdapterV2;
import com.ysarch.vmall.common.event.NotificationDef;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.search.presenter.SearchContentPresenterV2;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.event.EventCenter;
import com.yslibrary.event.IEventListener;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchContentFragmentV2 extends CommonPureListFragment<SearchContentPresenterV2> implements IEventListener {

    private int mCateId = -1;
    private String mKeyword = null;
    private ShouYeSubpageRcyAdapterV2 mRcyAdapter;
    private String mCurKeyword;
//    private boolean mHasMore;
    private int mPlatformType = Constants.TYPE_PLATFORM_TB;

    public static Bundle getBundle(String keyword, int platformType) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_KEYWORD, keyword);
        bundle.putInt(BundleKey.ARG_PLATFORM_TYPE, platformType);
        return bundle;
    }

    /*public static Bundle getBundle(int cateId) {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.ARG_CATE_ID, cateId);
        return bundle;
    }*/

//    public void resetKeyword(String keyword) {
//        if (!TextUtils.isEmpty(keyword) && !keyword.equals(mKeyword)) {
//            mKeyword = keyword;
//            if (mCateId == -1) {
//                getPresenter().searchGoods(mKeyword, 1, true);
//            } else {
//                getPresenter().searchCateGoods(mCateId, mKeyword, 1, true);
//            }
//        }
//    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return mRcyAdapter;
    }


    @Override
    protected void requestDataOnEmpty() {
        getPresenter().searchGoods(mKeyword, 1, mPlatformType, true,
                SearchActivity.needCache(mKeyword));
    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        mRcyAdapter = new ShouYeSubpageRcyAdapterV2(getContext());
        mRcyAdapter.setOnItemClickListener((position, data) -> {
//            GoodsItemBean goodsItemBean = (GoodsItemBean) data;
            NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
                    GoodsDetailActivity.getBundle(((GoodsItemBeanV2) data).getId(), mPlatformType,Constants.TYPE_ENTRY_SEARCH));
        });

        int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
        int gap = SizeUtils.dp2px(10);
//        initRecyclerView(new FRcyGridLayoutDecoration(gap, gap, marginH, 2, mRcyAdapter)
//                .setGridPlaceViewType(ShouYeSubpageRcyAdapter.TYPE_GOODS));
        initRecyclerView(new FRcyGridLayoutNormalDecoration(gap,  2, mRcyAdapter)
                .setGridPlaceViewType(ShouYeSubpageRcyAdapter.TYPE_GOODS));

    }


    @Override
    public void bindEvent() {
        super.bindEvent();
        EventCenter.getInstance().registerNotification(NotificationDef.EVENT_KEYWORD_CHANGED, this);
    }

    @Override
    protected void requestData(int page) {
        getPresenter().searchGoods(mKeyword, page, mPlatformType, false,
                SearchActivity.needCache(mKeyword));
      /*  if (mCateId == -1) {
            getPresenter().searchGoods(mKeyword, page, false);
        } else {
            getPresenter().searchCateGoods(mCateId, mKeyword, page, false);
        }*/
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mKeyword = getArguments().getString(BundleKey.ARG_KEYWORD, null);
            mPlatformType = getArguments().getInt(BundleKey.ARG_PLATFORM_TYPE, Constants.TYPE_PLATFORM_TB);
        }

        if (!TextUtils.isEmpty(mKeyword)) {
            getPresenter().searchGoods(mKeyword, 1, mPlatformType, true,
                    SearchActivity.needCache(mKeyword));
        }

       /* if (mCateId == -1) {
            if (!TextUtils.isEmpty(mKeyword)) {
                getPresenter().searchGoods(mKeyword, 1, true);
            }
        } else {
            getPresenter().searchCateGoods(mCateId, mKeyword, 1, true);
        }*/
    }

    @Override
    public SearchContentPresenterV2 newPresenter() {
        return new SearchContentPresenterV2();
    }

    public void onLoadFail(String keyword, int page) {
        if (keyword.equals(mKeyword))  //不同关键词需要清理掉旧数据
            return;

        if (mRcyAdapter != null) {
            mRcyAdapter.refreshGoods(null);
        }
        resetUIStatus(page, false);
    }

    public void onLoadSucc(String keyword, int page, ListResult<GoodsItemBeanV2> goodsItemBeanListResult) {
        mPage = page;
        if (goodsItemBeanListResult != null && CollectionUtils.isNotEmpty(goodsItemBeanListResult.getList())) {
            mCurKeyword = keyword;
            if (page == 1) {
                mRcyAdapter.refreshGoods(goodsItemBeanListResult.getList());
                getRecycleView().smoothScrollToPosition(0);
            } else {
                mRcyAdapter.appendGoods(goodsItemBeanListResult.getList());
            }
//            mHasMore = goodsItemBeanListResult.getTotalPage() > page;
        }
        if (goodsItemBeanListResult != null && CollectionUtils.isEmpty(goodsItemBeanListResult.getList())) {
            mRcyAdapter.refreshGoods(new ArrayList<>());
        }
        resetUIStatus(page, true);
    }

    @Override
    protected boolean hasMore() {
        return getPresenter().hasMore;
    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        return gridLayoutManager;
    }

    @Override
    public void onNotify(Message message) {
        if (message.what == NotificationDef.EVENT_KEYWORD_CHANGED) {
            String keyword = (String) message.obj;
            if (!TextUtils.isEmpty(keyword) && !keyword.equals(mKeyword)) {
                mKeyword = keyword;
                getPresenter().searchGoods(keyword, 1, mPlatformType, true,
                        SearchActivity.needCache(mKeyword));
            }
        }
    }

    @Override
    protected String getPageName() {
        return "搜索结果页";
    }

}
