package com.ysarch.vmall.page.main.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.GoodsItemBeanV2;
import com.ysarch.vmall.domain.bean.HomeContentResult;
import com.ysarch.vmall.domain.bean.ListResult;
import com.ysarch.vmall.domain.bean.LoginResult;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.page.main.shoye.ShouyeWelcomeFragment;
import com.yslibrary.utils.CollectionUtils;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 09/09/2020
 **/
public class ShouYeWelcomePresenter extends BasePresenter<ShouyeWelcomeFragment> {

    public boolean hasMore = true;

    public void refreshToken() {
        AccountLoader.getInstance().refreshToken()
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        UserInfoManager.updateToken(loginResult.getToken(),loginResult.getTokenHead());
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        UserInfoManager.logout();
                    }
                });
    }

    public void requestRecGoods(int page) {
        GoodsLoader.getInstance().requestRecommendGoods(page)
                .compose(dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<ListResult<GoodsItemBeanV2>>(getV()) {
                    @Override
                    public void onSuccess(ListResult<GoodsItemBeanV2> goodsItemBeanListResult) {
                        if (CollectionUtils.isNotEmpty(goodsItemBeanListResult.getList())) {
                            hasMore = goodsItemBeanListResult.getTotalPage() > page;
                        } else {
                            hasMore = false;
                        }
                        getV().dismissLoadingDialog();
                        getV().onGoodsDataSucc(page, goodsItemBeanListResult);
                    }


                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onGoodsDataFail();

                    }
                });
    }


    public void requestContents() {
        GoodsLoader.getInstance().requestWelcomeContent()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<HomeContentResult>() {
                    @Override
                    public void onSuccess(HomeContentResult homeContentResult) {
                        getV().onContentsSucc(homeContentResult);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onContentsSucc(null);
                    }
                });
    }

}
