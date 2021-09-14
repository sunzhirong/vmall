package com.ysarch.vmall.component.dialog.multisku;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysarch.uibase.recyclerview.itemDecoration.FlexibleDividerDecoration;
import com.ysarch.uibase.recyclerview.itemDecoration.HorizontalDividerItemDecoration;
import com.ysarch.uibase.recyclerview.itemDecoration.VerticalDividerItemDecoration;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.MultiSkuAdapter;
import com.ysarch.vmall.common.adapter.viewholder.MultiSkuVH;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.local.LocalSkuEntity;
import com.ysarch.vmall.domain.local.MultiSkuEntity;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.List;

/**
 * Created by fysong on 12/10/2020
 **/
public class MultiSkuFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private MultiSkuAdapter mAdapter;
    private LocalSkuEntity mLocalSkuEntity;
    private List<MultiSkuEntity> mMultiSkuEntities;
    private MultiSkuVH.Callback mCallback;

    public void setCallback(MultiSkuVH.Callback callback) {
        mCallback = callback;
    }

    public static Bundle getBundle(LocalSkuEntity localSkuEntity, List<MultiSkuEntity> multiSkuEntities) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_MULTI_SKU_ENTITY, new Gson().toJson(multiSkuEntities));
        bundle.putSerializable(BundleKey.ARG_LOCAL_SKU_ENTITY, localSkuEntity);
        return bundle;
    }

    //    public static Bundle getBundle(LocalSkuEntity skuEntity, List<LocalSkuEntity>) {
//        Bundle bundle = new Bundle();
//    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_sku_add, container, false);
        return mRecyclerView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mLocalSkuEntity = (LocalSkuEntity) getArguments().getSerializable(BundleKey.ARG_LOCAL_SKU_ENTITY);
            String referSkus = getArguments().getString(BundleKey.ARG_MULTI_SKU_ENTITY);
            if (!TextUtils.isEmpty(referSkus)) {
                mMultiSkuEntities = new Gson().fromJson(referSkus, new TypeToken<List<MultiSkuEntity>>() {
                }.getType());
            }
        }


        if (mLocalSkuEntity != null && CollectionUtils.isNotEmpty(mMultiSkuEntities)) {
            initAdapter();
            mAdapter.refreshData(mMultiSkuEntities);
        }
    }


    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new MultiSkuAdapter(getContext());
            mAdapter.setCallback(mCallback);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            int gapV = SizeUtils.dp2px(1);
            mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                    .colorProvider((position, parent) -> 0xfff2f2f2).size(gapV)
                    .build());
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
