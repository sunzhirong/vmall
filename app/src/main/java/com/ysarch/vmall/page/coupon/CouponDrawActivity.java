package com.ysarch.vmall.page.coupon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.ysarch.uibase.base.BaseTitleActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.domain.bean.CouponBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 06/10/2020
 **/
public class CouponDrawActivity extends BaseTitleActivity {
    public static Bundle getBundle(long goodsId, List<CouponBean> couponBeans) {
        Bundle bundle = new Bundle();
        bundle.putLong(BundleKey.ARG_GOODS_ID, goodsId);
        if(CollectionUtils.isNotEmpty(couponBeans)){
            bundle.putString(BundleKey.ARG_COUPON_LIST_JSON, new Gson().toJson(couponBeans));
        }
        return bundle;
    }
    @Override
    protected String getCustomTitle() {
        return getString(R.string.label_draw_coupon);
    }

    @Override
    protected Fragment createFragment() {
        return new CouponDrawListFragment();
    }
}
