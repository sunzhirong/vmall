package com.ysarch.vmall.common.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.constant.Constants;

/**
 * Created by fysong on 11/09/2020
 **/
public class CateLevel3Header extends AbsViewHolder {
    private TextView mTextView;
    public CateLevel3Header(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.tv_cate_name);
    }

    @Override
    public void onBindData(int position, Object data, Object callback) {
//        mTextView.setText((String)data);


        CateLevelBean cateLevelBean = (CateLevelBean) data;


        switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
            case Constants.ID_LAN_KM:
                mTextView.setText(cateLevelBean.getKhName());
                break;
            case Constants.ID_LAN_ZH:
                mTextView.setText(cateLevelBean.getName());
                break;
            case Constants.ID_LAN_EN:
                mTextView.setText(cateLevelBean.getEnName());
                break;
            default:
                mTextView.setText(cateLevelBean.getName());
                break;

        }
    }


    public static int getLayoutRes() {
        return R.layout.item_header_cate_level3;
    }
}
