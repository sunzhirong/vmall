package com.ysarch.vmall.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.ysarch.vmall.component.GoodsDetailsPhotoView;
import com.ysarch.vmall.domain.local.LocalSkuEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class GoodsPictureVpAdapter extends PagerAdapter {
    private ArrayList<PhotoView> mList;
    private Context context;
    private List<LocalSkuEntity> picList;

    public GoodsPictureVpAdapter(Context context, ArrayList<PhotoView> mList, List<LocalSkuEntity> bigList) {
        this.mList = mList;
        this.context = context;
        this.picList = bigList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView imageView = mList.get(position);
//        GoodsDetailsPhotoView imageView = mList.get(position);
        container.removeView(imageView);
        container.addView(imageView);
        Glide.with(context).load(picList.get(position).getImage()).into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
