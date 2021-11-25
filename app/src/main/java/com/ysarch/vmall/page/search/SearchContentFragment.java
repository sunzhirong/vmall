package com.ysarch.vmall.page.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.uibase.recyclerview.itemDecoration.FRcyGridLayoutDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.ShouYeSubpageRcyAdapter;
import com.ysarch.vmall.domain.bean.GoodsItemBean;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.search.presenter.SearchContentPresenter;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.utils.CollectionUtils;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchContentFragment extends CommonPureListFragment<SearchContentPresenter> {

    private int mCateId = -1;
    private String mKeyword = null;
    private ShouYeSubpageRcyAdapter mRcyAdapter;
    private String mCurKeyword;
    private boolean mHasMore;
    private int mPlatformType = Constants.TYPE_PLATFORM_TB;

    public static Bundle getBundle(String keyword) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_KEYWORD, keyword);
        return bundle;
    }

    public static Bundle getBundle(int cateId) {
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.ARG_CATE_ID, cateId);
        return bundle;
    }

    public void resetKeyword(String keyword) {
        if (!TextUtils.isEmpty(keyword) && !keyword.equals(mKeyword)) {
            mKeyword = keyword;
            if (mCateId == -1) {
                getPresenter().searchGoods(mKeyword, 1, true);
            } else {
                getPresenter().searchCateGoods(mCateId, mKeyword, 1, true);
            }
        }
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return mRcyAdapter;
    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        mRcyAdapter = new ShouYeSubpageRcyAdapter(getContext());
        mRcyAdapter.setOnItemClickListener((position, data) -> {
//            GoodsItemBean goodsItemBean = (GoodsItemBean) data;
            if (data == null)
                NavHelper.startActivity(getActivity(), GoodsDetailActivity.class);
            else
                NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
                        GoodsDetailActivity.getBundle (((GoodsItemBean)data).getId(),Constants.TYPE_ENTRY_SEARCH));
        });

        int marginH = ResUtils.getDimeI(R.dimen.margin_h_common);
        int gap = SizeUtils.dp2px(11);
        initRecyclerView(new FRcyGridLayoutDecoration(gap, gap, marginH, 2, mRcyAdapter)
                .setGridPlaceViewType(ShouYeSubpageRcyAdapter.TYPE_GOODS));
    }

    @Override
    protected void requestData(int page) {
        if (mCateId == -1) {
            getPresenter().searchGoods(mKeyword, page, false);
        } else {
            getPresenter().searchCateGoods(mCateId, mKeyword, page, false);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mKeyword = getArguments().getString(BundleKey.ARG_KEYWORD, null);
            mCateId = getArguments().getInt(BundleKey.ARG_CATE_ID, -1);
        }

        if (mCateId == -1) {
            if (!TextUtils.isEmpty(mKeyword)) {
                getPresenter().searchGoods(mKeyword, 1, true);
            }
        } else {
            getPresenter().searchCateGoods(mCateId, mKeyword, 1, true);
        }
    }

    @Override
    public SearchContentPresenter newPresenter() {
        return new SearchContentPresenter();
    }

    public void onLoadFail(String keyword, int page) {
        if (keyword.equals(mKeyword))  //不同关键词需要清理掉旧数据
            return;

        if (mRcyAdapter != null) {
            mRcyAdapter.refreshGoods(null);
        }
        resetUIStatus(page, false);
    }

    public void onLoadSucc(String keyword, int page, ListResult<GoodsItemBean> goodsItemBeanListResult) {
        if (goodsItemBeanListResult != null && CollectionUtils.isNotEmpty(goodsItemBeanListResult.getList())) {
            mCurKeyword = keyword;
            if (page == 1) {
                mRcyAdapter.refreshGoods(goodsItemBeanListResult.getList());
            } else {
                mRcyAdapter.appendGoods(goodsItemBeanListResult.getList());
            }
            mHasMore = goodsItemBeanListResult.getTotalPage() > page;
        }
        resetUIStatus(page, true);
    }

    @Override
    protected boolean hasMore() {
        return mHasMore;
    }

    @Override
    protected RecyclerView.LayoutManager initLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        return gridLayoutManager;
    }

}
