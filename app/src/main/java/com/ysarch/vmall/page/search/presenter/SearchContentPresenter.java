package com.ysarch.vmall.page.search.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.bean.GoodsItemBean;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.page.search.SearchContentFragment;
import com.yslibrary.utils.CollectionUtils;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchContentPresenter extends BasePresenter<SearchContentFragment> {


    public boolean hasMore = true;

    /**
     * 综合搜索商品
     *
     * @param keyword
     * @param page
     * @param showLoading
     */
    public void searchGoods(String keyword, int page, boolean showLoading) {
        GoodsLoader.getInstance().searchGoods(keyword, page, Constants.COUNT_PER_PAGE_GRID)
                .compose(showLoading ? showLoadingDialog() : dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<ListResult<GoodsItemBean>>(getV()) {
                    @Override
                    public void onSuccess(ListResult<GoodsItemBean> goodsItemBeanListResult) {
                        hasMore = (CollectionUtils.isNotEmpty(goodsItemBeanListResult.getList())
                                && goodsItemBeanListResult.getList().size() >= Constants.COUNT_PER_PAGE_GRID);
                        getV().onLoadSucc(keyword, page, goodsItemBeanListResult);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().resetUIStatus(page, false);
                        getV().onLoadFail(keyword, page);
                    }
                });
    }


    /**
     * 搜索类别商品
     *
     * @param cate
     * @param keyword
     * @param page
     * @param showLoading
     */
    public void searchCateGoods(int cate, String keyword, int page, boolean showLoading) {
        GoodsLoader.getInstance().searchCateGoods(cate, keyword, page, Constants.COUNT_PER_PAGE_GRID)
                .compose(showLoading ? showLoadingDialog() : dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<ListResult<GoodsItemBean>>(getV()) {
                    @Override
                    public void onSuccess(ListResult<GoodsItemBean> goodsItemBeanListResult) {
                        hasMore = (CollectionUtils.isNotEmpty(goodsItemBeanListResult.getList())
                                && goodsItemBeanListResult.getList().size() >= Constants.COUNT_PER_PAGE_GRID);
                        getV().onLoadSucc(keyword, page, goodsItemBeanListResult);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().resetUIStatus(page, false);
                        getV().onLoadFail(keyword, page);
                    }
                });
    }
}
