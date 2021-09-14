package com.ysarch.vmall.page.wallet.presenter;

import android.text.TextUtils;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.component.oss.OSSHelper;
import com.ysarch.vmall.domain.bean.BankItemBean;
import com.ysarch.vmall.domain.bean.OssBean;
import com.ysarch.vmall.domain.services.ConfigLoader;
import com.ysarch.vmall.domain.services.WalletLoader;
import com.ysarch.vmall.page.wallet.RechargeFragment;
import com.ysarch.vmall.utils.ResUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 2020/10/15
 **/
public class RechargePresenter extends BasePresenter<RechargeFragment> {

    private Map<String, String> mStringMap = new HashMap<>();

    public void submitInfo(String amount, long bankId, String proofPicPath, String rechargeDate, String remark) {
//        mStringMap.put(proofPicPath, "http://p3.music.126.net/hLA-epSzNOVvTmWpNdd5fw==/109951165377200613.jpg");
        if (mStringMap.containsKey(proofPicPath) && !TextUtils.isEmpty(mStringMap.get(proofPicPath))) {
            //图片已经上传过
            postRechargeInfo(amount, bankId, mStringMap.get(proofPicPath), rechargeDate, remark);
        } else {
            if (OSSHelper.getInstance().isInited) {
                OSSHelper.getInstance().uploadImage(proofPicPath, new OSSHelper.Callback() {
                    @Override
                    public void onUploadSucc(String url) {
                        mStringMap.put(proofPicPath, url);
                        postRechargeInfo(amount, bankId, url, rechargeDate, remark);
                    }

                    @Override
                    public void onUploadFail() {
                        getV().dismissLoadingDialog();
                        getV().showTs(ResUtils.getString(R.string.tip_img_upload_fail));
                    }
                });
            } else {
                requestOssData(amount, bankId, proofPicPath, rechargeDate, remark);
            }
        }
    }


    /**
     * 提交充值信息
     *
     * @param amount
     * @param bankId
     * @param rechargePic
     * @param rechargeTime
     * @param remark
     */
    private void postRechargeInfo(String amount, long bankId,
                                  String rechargePic, String rechargeTime, String remark) {
        WalletLoader.getInstance().postRechargeInfo(amount, bankId, rechargePic, rechargeTime, remark)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<String>(getV()) {
                    @Override
                    public void onSuccess(String msg) {
                        getV().onSubmitSucc(msg);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }


//    /**
//     * 获取银行列表数据
//     */
//    public void requestBanks() {
//        if (CollectionUtils.isNotEmpty(AppContext.getsInstance().getBankItemBeans())) {
//            getV().onBankData(AppContext.getsInstance().getBankItemBeans());
//            return;
//        }
//        WalletLoader.getInstance().listBanks()
//                .compose(showLoadingDialog())
//                .compose(getV().bindToLifecycle())
//                .subscribe(new ApiSubscriber<List<BankItemBean>>(getV()) {
//                    @Override
//                    public void onSuccess(List<BankItemBean> bankItemBeans) {
//                        AppContext.getsInstance().setBankItemBeans(bankItemBeans);
//                        getV().onBankData(bankItemBeans);
//                    }
//
//                    @Override
//                    protected void onFail(NetError error) {
//                        super.onFail(error);
//                    }
//                });
//    }


    public void requestOssData(String amount, long bankId, String proofPicPath, String rechargeDate, String remark) {
        ConfigLoader.getInstance().requestOss()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<OssBean>() {
                    @Override
                    public void onSuccess(OssBean ossBean) {
                        OSSHelper.getInstance().initOss(ossBean);
                        submitInfo(amount, bankId, proofPicPath, rechargeDate, remark);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().dismissLoadingDialog();
                        getV().showTs(error.getMessage());
                    }
                });
    }


}
