package com.ysarch.vmall.page.address.presenter;

import android.graphics.Bitmap;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.services.AccountLoader;
import com.ysarch.vmall.domain.services.AddressLoader;
import com.ysarch.vmall.page.address.AddressListFragment;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by fysong on 27/09/2020
 **/
public class AddressListPresenter extends BasePresenter<AddressListFragment> {

    /**
     * 获取地址列表
     *
     * @param showLoading
     */
    public void requestAddressList(boolean showLoading) {
        AddressLoader.getInstance().requestAddressList()
                .compose(showLoading ? showLoadingDialog() : dontShowDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<AddressItemBean>>(getV()) {
                    @Override
                    public void onSuccess(List<AddressItemBean> addressItemBeans) {
                        getV().onAddressListSucc(addressItemBeans);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onAddressListFail();
                    }
                });
    }


    /**
     * 删除地址
     *
     * @param position
     * @param addressItemBean
     */
    public void deleteAddress(int position, AddressItemBean addressItemBean) {
        AddressLoader.getInstance().deleteAddress(addressItemBean.getId())
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().onDeleteSucc(position, addressItemBean);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }


    /* public void updateAddress(@Field("id") long addressId,
                              @Field("memberId") long memberId,
                              @Field("province") String province,
                              @Field("city") String city,
                              @Field("region") String region,
                              @Field("detailAddress") String detailAddress,
                              @Field("postCode") String postCode,
                              @Field("phoneNumber") String phoneNumber,
                              @Field("name") String name,
                              @Field("defaultStatus") int defaultStatus) {
        AccountLoader.getInstance().updateAddress(addressId, memberId, province, city, region, detailAddress,
                postCode, phoneNumber, name, defaultStatus)
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().onSubmitAddressSucc();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }*/

    /**
     * 设置默认地址
     * @param position
     * @param addressItemBean
     */
    public void setDefaultAddress(int position, AddressItemBean addressItemBean) {
        AddressLoader.getInstance().updateAddress(addressItemBean.getId(), UserInfoManager.getUser().getId(),
                addressItemBean.getProvince(), addressItemBean.getCity(), addressItemBean.getRegion(),
                addressItemBean.getDetailAddress(), addressItemBean.getPostCode(), addressItemBean.getPhoneNumber(),
                addressItemBean.getName(), 1 - addressItemBean.getDefaultStatus())
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<Object>(getV()) {
                    @Override
                    public void onSuccess(Object o) {
                        getV().onAddressDefaultSetSucc(position, addressItemBean);
                    }

                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                    }
                });
    }
}
