package com.ysarch.vmall.page.orders;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.main.MainActivity;
import com.ysarch.vmall.page.wallet.PayPwdModifyActivity;
import com.ysarch.vmall.utils.NavHelper;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import butterknife.BindView;
import butterknife.OnClick;

public class OrderPaidFragment extends BaseFragment {
    @BindView(R.id.tv_tips)
    LinearLayout mTvTips;

    @Override
    public void initData(Bundle savedInstanceState) {
//        mTvTips.setVisibility(UserInfoManager.getUser().isHasPayPassword()?View.GONE:View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_paid;
    }

    @Override
    public Object newPresenter() {
        return null;
    }

    @OnClick({R.id.tv_back_home, R.id.tv_check_order,R.id.tv_modify_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_home:
                NavHelper.startActivity(context, MainActivity.class);
                getActivity().finish();
                break;
            case R.id.tv_check_order:
                NavHelper.startActivity(getActivity(), OrderHistoryActivity.class,
                        OrderHistoryActivity.getBundle(Constants.STATUS_ORDER_ALL));
                getActivity().finish();
                break;
            case R.id.tv_modify_pwd:
                NavHelper.startActivity(getActivity(), PayPwdModifyActivity.class);
                break;
        }
    }
}
