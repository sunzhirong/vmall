package com.ysarch.vmall.page.main.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.page.main.shoye.ShouyeSubpageFragment;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 09/09/2020
 **/
public class ShouYeSubpagePresenter extends BasePresenter<ShouyeSubpageFragment> {


    public boolean hasMore = true;

    /**
     *
     * @param cateName
     * @param page
     * @param showLoading
     */
    public void requestCateGoods(String cateName , int page, boolean showLoading) {
        GoodsLoader.getInstance().searchGoodsV2(cateName, page, Constants.TYPE_PLATFORM_TB, false)
                .compose(showLoading?showLoadingDialog():dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<ListResult<GoodsItemBeanV2>>(getV()) {
                    @Override
                    public void onSuccess(ListResult<GoodsItemBeanV2> goodsItemBeansResult) {
                        if (CollectionUtils.isNotEmpty(goodsItemBeansResult.getList())) {
                            hasMore = goodsItemBeansResult.getTotalPage() > page;
                        } else {
                            hasMore = false;
                        }
                        getV().onDataSucc(cateName,page,goodsItemBeansResult);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onDataFail(cateName,page);
                    }
                });
    }
}
