package com.ysarch.vmall.page.main.presenter;

import com.alibaba.fastjson.JSON;
import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.bean.UpdateBean;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.page.main.shoye.MainShouYeFragment;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 24/08/2020
 **/
public class MainShouYePresenter extends BasePresenter<MainShouYeFragment> {

    public void requestCateDatas() {
        GoodsLoader.getInstance().requestHeaderCateTree()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<CateLevelBean>>(getV()) {
                    @Override
                    public void onSuccess(List<CateLevelBean> cateLevelBeans) {
//                        List<CateLevelBean> header = new ArrayList<>();
//                        if(CollectionUtils.isNotEmpty(cateLevelBeans)){
//                            for(CateLevelBean bean : cateLevelBeans){
//                                header.addAll(bean.getChildren());
//                            }
//                        }
                        AppContext.getsInstance().setCateHeaderBeans(cateLevelBeans);
//                        CacheHelper.putString("nikotest", JSON.toJSONString(cateLevelBeans));
                        getV().onCateDatasSucc(cateLevelBeans);
                    }


                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onCateDatasFail();
                    }
                });
    }

    public void checkUpdate(){
        AccountLoader.getInstance().checkUpdate(BuildConfig.VERSION_CODE)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<UpdateBean>(getV()) {
                    @Override
                    public void onSuccess(UpdateBean updateBean) {
                        getV().onCheckUpdateSucc(updateBean);
                    }


                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onCateDatasFail();
                    }
                });
    }
}
