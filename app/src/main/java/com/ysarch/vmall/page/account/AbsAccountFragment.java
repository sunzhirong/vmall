package com.ysarch.vmall.page.account;


import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.base.BasePresenter;

/**
 * Created by fysong on 2019-10-18
 **/
public abstract class AbsAccountFragment<P extends BasePresenter> extends BaseFragment<P> {

    protected IAccountFragmentCallback mAccountFragmentCallback;

    public void setAccountFragmentCallback(IAccountFragmentCallback accountFragmentCallback) {
        mAccountFragmentCallback = accountFragmentCallback;
    }

    protected abstract void clearData();

    public interface IAccountFragmentCallback{
        void onSwitchPage(@AccountActivity.PageType int pageType);
    }
}
