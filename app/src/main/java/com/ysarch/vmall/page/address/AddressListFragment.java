package com.ysarch.vmall.page.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.ysarch.uibase.dialog.SimpleDialogWithTwoBtn;
import com.ysarch.uibase.fragment.CommonPureListFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.AddressListAdapter;
import com.ysarch.vmall.common.adapter.viewholder.AddressItemVH;
import com.ysarch.vmall.component.EmptyView;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.constant.RequestCode;
import com.ysarch.vmall.page.address.presenter.AddressListPresenter;
import com.ysarch.vmall.utils.NavHelper;

import java.util.List;

/**
 * Created by fysong on 27/09/2020
 **/
public class AddressListFragment extends CommonPureListFragment<AddressListPresenter> {

    private AddressListAdapter mAdapter;
    private AddressItemBean mSelAddressBean;
    private int mMode;


    public boolean isAddressEmpty(){
        return mAdapter.getItemCount() == 0;
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        mAdapter = new AddressListAdapter(getContext());
        mAdapter.setCallback(new AddressItemVH.Callback() {
            @Override
            public void onDeleteAddress(int position, AddressItemBean addressItemBean) {
                new SimpleDialogWithTwoBtn.Builder(getContext())
                        .setCancelable(true)
                        .setAutoDismissWhileClick(true)
                        .setPageName("删除地址弹窗")
                        .setOnSubmitListener(new SimpleDialogWithTwoBtn.OnSubmitListener() {
                            @Override
                            public void onLeftBtnClick() {

                            }

                            @Override
                            public void onRightBtnClick() {
                                getPresenter().deleteAddress(position, addressItemBean);
                            }
                        })
                        .setWarning(getString(R.string.tips_delete_address))
                        .setRightLabel(getString(R.string.label_confirm))
                        .setLeftLabel(getString(R.string.label_cancel))
                        .build().show();

            }

            @Override
            public void onEditClick(int position, AddressItemBean addressItemBean) {
                NavHelper.startActivity(getActivity(), AddressEditActivity.class,
                        AddressEditActivity.getBundle(addressItemBean), RequestCode.CODE_EDIT_ADDRESS);
            }

            @Override
            public void onSetDefaultClick(int position, AddressItemBean addressItemBean) {
                getPresenter().setDefaultAddress(position, addressItemBean);
            }
        });
        mAdapter.setMode(mMode);

//        if (mMode == Constants.MODE_ADDRESS_SELECT) {
            mAdapter.setSelAddressItemBean(mSelAddressBean);
            mAdapter.setOnItemClickListener((position, data) -> {
                Intent intent = new Intent();
                intent.putExtras(AddressListActivity.getBundle((AddressItemBean) data));
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            });
//        }

        return mAdapter;
    }

    @Override
    protected void requestData(int page) {
        getPresenter().requestAddressList(false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mSelAddressBean = (AddressItemBean) getArguments().getSerializable(BundleKey.ARG_ADDRESS_BEAN);
            mMode = getArguments().getInt(BundleKey.ARG_ADDRESS_MODE, Constants.MODE_ADDRESS_EDIT);
        }
        initRecyclerView(null);
        getPresenter().requestAddressList(true);




        setEmptyOpt(getString(R.string.label_add_new_address), () -> {
            NavHelper.startActivity(getActivity(), AddressEditActivity.class,
                    AddressEditActivity.getNoShowDefaultOptBundle(),
                    RequestCode.CODE_EDIT_ADDRESS);
        });
    }

    @Override
    public AddressListPresenter newPresenter() {
        return new AddressListPresenter();
    }

    public void onAddressListSucc(List<AddressItemBean> addressItemBeans) {
        mAdapter.refreshData(addressItemBeans);
        resetUIStatus(1, true);
    }

    public void onAddressListFail() {
        resetUIStatus(1, false);
    }

    @Override
    protected boolean isPagingData() {
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.CODE_EDIT_ADDRESS) {
            if (resultCode == Activity.RESULT_OK)
                getPresenter().requestAddressList(true);
        }
    }

    public void onDeleteSucc(int position, AddressItemBean addressItemBean) {
        mAdapter.deleteAddress(addressItemBean);
    }

    public void onAddressDefaultSetSucc(int position, AddressItemBean addressItemBean) {
        addressItemBean.setDefaultStatus(1 - addressItemBean.getDefaultStatus());
        mAdapter.changeDefaultAddress(position, addressItemBean);
    }

    @Override
    protected String getPageName() {
        return "地址管理页";
    }
}
