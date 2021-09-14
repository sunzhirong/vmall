package com.ysarch.vmall.page.main;

import com.ysarch.uibase.base.BaseFragment;

import cn.droidlover.xdroidmvp.mvp.IPresent;

/**
 * Created by fysong on 27/09/2020
 **/
public abstract class  MainBaseFragment<P extends IPresent> extends BaseFragment<P> {
    public boolean onBackPress(){
        return true;
    }
}
