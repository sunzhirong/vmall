package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.ysarch.uibase.recyclerview.AbsRVAdapter;
import com.ysarch.uibase.recyclerview.AbsViewHolder;
import com.ysarch.uibase.recyclerview.ItemDataWrapper;
import com.ysarch.vmall.VMallApplication;
import com.ysarch.vmall.common.adapter.viewholder.CateLevel2SumVH;
import com.ysarch.vmall.common.adapter.viewholder.CateLevel3Header;
import com.ysarch.vmall.common.adapter.viewholder.CateLevel3VH;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.constant.Constants;
import com.yslibrary.utils.CollectionUtils;
import com.yslibrary.utils.ToastUtils;

import java.util.List;

/**
 * Created by fysong on 11/09/2020
 **/
public class CatesLevel3Adapter extends AbsRVAdapter {
    public static final int TYPE_CATE_LEVEl2_HEADER = 1;
    public static final int TYPE_CATE_LEVEl3_HEADER = 2;
    public static final int TYPE_CATE_LEVEl3 = 3;

    private LayoutInflater mInflater;
    private BeeGlide mBeeGlide;

    private ItemDataWrapper.OnItemClickListener mOnItemClickListener;

    public CatesLevel3Adapter(Context context, BeeGlide beeGlide) {
        mInflater = LayoutInflater.from(context);
        mBeeGlide = beeGlide;
    }

    public void setOnItemClickListener(ItemDataWrapper.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void refreshData(CateLevelBean cateLevelBean) {
        mMixData.clear();
        String title ;
        switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
            case Constants.ID_LAN_KM:
                title = cateLevelBean.getKhName();
                break;
            case Constants.ID_LAN_ZH:
                title = cateLevelBean.getName();
                break;
            case Constants.ID_LAN_EN:
                title = cateLevelBean.getEnName();
                break;
            default:
                title = cateLevelBean.getName();
                break;
        }
        mMixData.add(new ItemDataWrapper(TYPE_CATE_LEVEl2_HEADER, title).setOnItemClickListener(mOnItemClickListener));
        List<CateLevelBean> cateLevelBeans2 = cateLevelBean.getChildren();
        if (CollectionUtils.isNotEmpty(cateLevelBeans2)) {
            for (int j = 0; j < cateLevelBeans2.size(); j++) {
                CateLevelBean cateLevelBean2 = cateLevelBeans2.get(j);
                String name ;
                switch (AppContext.getsInstance().getLanguageEntity().getLanId()){
                    case Constants.ID_LAN_KM:
                        name = cateLevelBean2.getKhName();
                        break;
                    case Constants.ID_LAN_ZH:
                        name = cateLevelBean2.getName();
                        break;
                    case Constants.ID_LAN_EN:
                        name = cateLevelBean2.getEnName();
                        break;
                    default:
                        name = cateLevelBean2.getName();
                        break;
                }
                mMixData.add(new ItemDataWrapper(TYPE_CATE_LEVEl3_HEADER, name).setOnItemClickListener(mOnItemClickListener));

                List<CateLevelBean> children = cateLevelBean2.getChildren();
                if (CollectionUtils.isNotEmpty(children)) {
                    for (int k = 0; k < children.size(); k++) {
                        CateLevelBean cateLevelBean3 = children.get(k);
                        mMixData.add(new ItemDataWrapper(TYPE_CATE_LEVEl3, cateLevelBean3)
                                .setOnItemClickListener(mOnItemClickListener));
                    }
                }

        }
    }

    notifyDataSetChanged();

}

    @NonNull
    @Override
    public AbsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AbsViewHolder holder = null;
        switch (viewType) {
            case TYPE_CATE_LEVEl3_HEADER:
                holder = new CateLevel3Header(mInflater.inflate(CateLevel3Header.getLayoutRes(), parent, false));
                break;
            case TYPE_CATE_LEVEl2_HEADER:
                holder = new CateLevel2SumVH(mInflater.inflate(CateLevel2SumVH.getLayoutRes(), parent, false));
                break;
            case TYPE_CATE_LEVEl3:
                holder = new CateLevel3VH(mInflater.inflate(CateLevel3VH.getLayoutRes(), parent, false), mBeeGlide);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AbsViewHolder holder, int position) {
        holder.onBindDataWrapper(position, mMixData.get(position));
    }
}
