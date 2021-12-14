package com.ysarch.vmall.page.goods;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.ysarch.uibase.base.BaseActivity;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.GoodsPictureRvAdapter;
import com.ysarch.vmall.common.adapter.GoodsPictureVpAdapter;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.local.LocalPropSkuEntity;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.yslibrary.utils.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class GoodsPictureDetailsActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_label)
    TextView mTvLabel;
    private int pos;


//    public static Bundle getBundle(ArrayList<String> list, int position) {
//        Bundle bundle = new Bundle();
//        bundle.putStringArrayList(BundleKey.ARG_GOODS_LIST, list);
//        bundle.putInt(BundleKey.ARG_GOODS_POSITION, position);
//        return bundle;
//    }

    public static Bundle getBundle(List<LocalPropSkuEntity> list,int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.ARG_GOODS, (Serializable) list);
        bundle.putInt(BundleKey.ARG_GOODS_POSITION, position);
        return bundle;
    }



    private int position;
//    private ViewPager mViewPager;
//    private RecyclerView recyclerview;
    private GoodsPictureRvAdapter horizontalAdapter;
//    private PhotoView photoview;
    private GoodsPictureVpAdapter goodsPictureVpAdapter;
    private ArrayList<PhotoView> imageViews;
//    private ArrayList<String> smallList;
    private int selectPos;

    private List<LocalSkuEntity> mLocalSkuEntities = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        if(getIntent().getExtras()!=null){
//            GoodsDetailItemBean  itemBean = (GoodsDetailItemBean)getIntent().getExtras().getSerializable(BundleKey.ARG_GOODS);
            List<LocalPropSkuEntity>  list = (List<LocalPropSkuEntity> )getIntent().getExtras().getSerializable(BundleKey.ARG_GOODS);
            if(list==null){
                finish();
            }

            selectPos = getIntent().getExtras().getInt(BundleKey.ARG_GOODS_POSITION);
            Log.e("list", "list +"+list.size());
            for(LocalPropSkuEntity localPropSkuEntity : list){
                List<LocalSkuEntity> localSkuEntities = localPropSkuEntity.getLocalSkuEntities();
                if(CollectionUtils.isNotEmpty(localSkuEntities))
                for(LocalSkuEntity localSkuEntity:localSkuEntities){
                    if(!TextUtils.isEmpty(localSkuEntity.getImage())){
                        mLocalSkuEntities.add(localSkuEntity);
                    }
                }
            }

            if(mLocalSkuEntities.size()==0){
                finish();
                return;
            }

            horizontalAdapter = new GoodsPictureRvAdapter(context,new BeeGlide(context));
            mRecyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            mRecyclerview.setAdapter(horizontalAdapter);
            horizontalAdapter.requestData(mLocalSkuEntities,0);

            imageViews = new ArrayList<>();
            for (int i = 0; i < mLocalSkuEntities.size(); i++) {
                PhotoView photoview = new PhotoView(this);
//                GoodsDetailsPhotoView photoview = new GoodsDetailsPhotoView(this);
                photoview.enable();
                imageViews.add(photoview);
//                photoview.enable();
//                photoViewDismiss();
            }
            goodsPictureVpAdapter = new GoodsPictureVpAdapter(context,imageViews,mLocalSkuEntities);
            mViewPager.setAdapter(goodsPictureVpAdapter);
            mTvLabel.setText(mLocalSkuEntities.get(0).getSkuLabel());


            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    mRecyclerview.smoothScrollToPosition(position);
//                    photoViewDismiss();
//                    horizontalAdapter.setBg(position);
                }

                @Override
                public void onPageSelected(int position) {
                    horizontalAdapter.requestData(mLocalSkuEntities,position);
                    mTvLabel.setText(mLocalSkuEntities.get(position).getSkuLabel());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            horizontalAdapter.setOnItemClickListener((position, data) -> mViewPager.setCurrentItem(position));


            mViewPager.setCurrentItem(selectPos);
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_picture_details;
    }

    @Override
    public Object newPresenter() {
        return null;
    }


    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        finish();
    }
}
