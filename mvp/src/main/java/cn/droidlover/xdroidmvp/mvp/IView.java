package cn.droidlover.xdroidmvp.mvp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IView<P> {
    void bindUI(View rootView);

    void bindEvent();

    void initData(Bundle savedInstanceState);

    int getOptionsMenuId();

    int getLayoutId();

    boolean useEventBus();

    P newPresenter();

    Dialog showLoadingDialog();

    Dialog showLoadingDialog(String loadingText, boolean isCancelable);

    Dialog showLoadingDialog(boolean isCancelable);

    void dismissLoadingDialog();

    void  showTs(String msg);

    void closeSelf();
}
