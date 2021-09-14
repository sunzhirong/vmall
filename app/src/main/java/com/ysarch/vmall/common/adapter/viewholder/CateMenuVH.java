package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.constant.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fysong on 09/09/2020
 **/
public class CateMenuVH extends AbsViewHolder {
    @BindView(R.id.tv_label_cate_level1)
    TextView mTVLabel;
    @BindView(R.id.v_cate_level1)
    View mView;
    RelativeLayout mContainer;
    private CateLevelBean mCateLevelBean;

    public CateMenuVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mContainer = (RelativeLayout) itemView;
    }

    public static int getLayoutRes() {
        return R.layout.item_cate_level1;
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
        mCateLevelBean = (CateLevelBean) data;
        mTVLabel.setText(mCateLevelBean.getName());
        mContainer.setSelected(mCateLevelBean.isSelected());
        mTVLabel.getPaint().setFakeBoldText(mCateLevelBean.isSelected());


        switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
            case Constants.ID_LAN_KM:
                mTVLabel.setText(mCateLevelBean.getKhName());
                break;
            case Constants.ID_LAN_ZH:
                mTVLabel.setText(mCateLevelBean.getName());
                break;
            case Constants.ID_LAN_EN:
                mTVLabel.setText(mCateLevelBean.getEnName());
                break;
            default:
                mTVLabel.setText(mCateLevelBean.getName());
                break;
        }

    }

}
