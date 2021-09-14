package com.ysarch.vmall.page.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.common.WinSoftKeyboardManager;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.bean.RegionsResult;
import com.ysarch.vmall.domain.bean.ZoneItemBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.address.presenter.AddressEditPresenter;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SystemUtil;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 原有地址编辑、添加新地址
 * Created by fysong on 27/09/2020
 **/
public class AddressEditFragment extends BaseFragment<AddressEditPresenter> {
    @BindView(R.id.et_consignee_name_edit_address)
    EditText mETName;
    @BindView(R.id.et_consignee_phone_edit_address)
    EditText mETPhone;
    @BindView(R.id.tv_region_edit_address)
    TextView mTVRegion;
    @BindView(R.id.et_detail_edit_address)
    EditText mETDetail;
    @BindView(R.id.iv_set_default_address_edit)
    ImageView mIVDefaultSwitcher;
    @BindView(R.id.ly_default_address_edit)
    LinearLayout mLyDefault;

    private AddressItemBean mAddressItemBean;
    private String mProvinceLabel = "";
    private String mCityLabel = "";
    private String mRegionLabel = "";
    private WinSoftKeyboardManager mWinSoftKeyboardManager;


    //    private ArrayList<ZoneItemBean> mProvinces = new ArrayList<>();
    private ArrayList<ZoneItemBean> mProvinces = new ArrayList<>();
    private ArrayList<ArrayList<ZoneItemBean>> mCities = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<ZoneItemBean>>> mAreaes = new ArrayList<>();
    private int mProvinceSelIndex = -1;
    private int mCitySelIndex = -1;


    private boolean mShowOpt = true;
    private boolean mAutoSelectDefault = false;
    private boolean mIsConfirmOrder = false;


    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mAddressItemBean = (AddressItemBean) getArguments().getSerializable(BundleKey.ARG_ADDRESS_BEAN);
            mShowOpt = getArguments().getBoolean(BundleKey.ARG_ADDRESS_DEFAULT_OPT_SHOW, true);
            mAutoSelectDefault = getArguments().getBoolean(BundleKey.ARG_AUTO_SELECT, false);
            mIsConfirmOrder = getArguments().getBoolean(BundleKey.ARG_IS_CONFIRM_ORDER, false);
        }


        mLyDefault.setVisibility(mShowOpt ? View.VISIBLE : View.GONE);
        mIVDefaultSwitcher.setSelected(mAutoSelectDefault);


        if (mAddressItemBean != null) {
            mTVRegion.setText(VMallUtils.decodeString(mProvinceLabel = mAddressItemBean.getProvince()) +
                    VMallUtils.decodeString(mCityLabel = mAddressItemBean.getCity())); /*+
                    VMallUtils.decodeString(mRegion = mAddressItemBean.getRegion()));*/

            mETPhone.setText(VMallUtils.decodeString(mAddressItemBean.getPhoneNumber()));

            mETName.setText(VMallUtils.decodeString(mAddressItemBean.getName()));
            mETDetail.setText(VMallUtils.decodeString(mAddressItemBean.getDetailAddress()));
            mIVDefaultSwitcher.setSelected(mAddressItemBean.getDefaultStatus() == 1);
        } else {
            mLyDefault.setVisibility(mShowOpt ? View.VISIBLE : View.GONE);
            mIVDefaultSwitcher.setSelected(mAutoSelectDefault);
        }

    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        mWinSoftKeyboardManager = new WinSoftKeyboardManager((ViewGroup) mRootView);
        mWinSoftKeyboardManager.registEditTexts();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_address_edit;
    }

    @Override
    public AddressEditPresenter newPresenter() {
        return new AddressEditPresenter();
    }

    public void onSaveClick() {
        String name = mETName.getText().toString().trim();
        String phone = mETPhone.getText().toString().trim();
        String detail = mETDetail.getText().toString().trim();


        if (TextUtils.isEmpty(name)) {
            showTs(ResUtils.getString(R.string.hint_fill_consignee_name));
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            showTs(ResUtils.getString(R.string.hint_fill_consignee_phone));
            return;
        } else if (phone.length() < 6) {
            showTs(ResUtils.getString(R.string.tip_phone_illegal));
            return;
        }

        if (TextUtils.isEmpty(mTVRegion.getText().toString())) {
            showTs(ResUtils.getString(R.string.tip_fill_region_belong));
            return;
        }
//        if (TextUtils.isEmpty(detail)) {
//            showTs(ResUtils.getString(R.string.tip_fill_detail_address));
//            return;
//        }

//        String province = "";
//        String city = "";
//        String region = "";
//        if (CollectionUtils.isNotEmpty(mProvinces) && mProvinceSelIndex >= 0 && mProvinceSelIndex < mProvinces.size()) {
//            province = mProvinces.get(mProvinceSelIndex).getText();
//            if (CollectionUtils.isNotEmpty(mCities) && mProvinceSelIndex >= 0 && mProvinceSelIndex < mCities.size()) {
//                city = mCities.get(mProvinceSelIndex).get(mCitySelIndex).getText();
//            }
//        }

        if (mAddressItemBean != null) {
            //原有地址编辑
            getPresenter().updateAddress(mAddressItemBean.getId(), UserInfoManager.getUser().getId(),
                    mProvinceLabel, mCityLabel, mRegionLabel,
                    detail, "", phone, name, mIVDefaultSwitcher.isSelected() ? 1 : 0);
        } else {
            //添加新地址
            getPresenter().addAddress(mProvinceLabel, mCityLabel, mRegionLabel, detail, "", phone,
                    name, mIVDefaultSwitcher.isSelected() ? 1 : 0);
        }
    }

    @OnClick({R.id.iv_set_default_address_edit, R.id.ctv_locate_edit_address, R.id.tv_region_edit_address})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.iv_set_default_address_edit:
                mIVDefaultSwitcher.setSelected(!mIVDefaultSwitcher.isSelected());
                break;
            case R.id.ctv_locate_edit_address:
                showTs(getString(R.string.label_wait_handler));
                break;
            case R.id.tv_region_edit_address:
                SystemUtil.hideKeyboard(view);
                if (CollectionUtils.isEmpty(mProvinces)) {
                    getPresenter().checkRegionDatas();
                } else {
                    showRegionPicker();
                }
                break;
        }

    }

    public void onSubmitAddressSucc() {
        if (mAddressItemBean != null) {
            showTs(ResUtils.getString(R.string.tip_modify_address_succ));
        } else {
            showTs(ResUtils.getString(R.string.tip_add_address_succ));
        }
        if(mIsConfirmOrder){
            getPresenter().requestAddressList();
        }else {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    public void onRegionDataSucc(RegionsResult regionsResult) {
        mProvinces.clear();
        mProvinces.addAll(regionsResult.getCityList());

        // 地区数据解析成选择器格式
        ArrayList<ZoneItemBean> cities = new ArrayList<>();
        cities.addAll(regionsResult.getDistrictList());

        for (int i = 0; i < mProvinces.size(); i++) {
            Iterator<ZoneItemBean> citiesIterator = cities.iterator();
            ArrayList<ZoneItemBean> provinceCities = new ArrayList<>();

            ArrayList<ArrayList<ZoneItemBean>> cityDistricts = new ArrayList<>();
            ZoneItemBean province = mProvinces.get(i);

            if (!TextUtils.isEmpty(mProvinceLabel) && mProvinceSelIndex == -1) {
                if (mProvinceLabel.equals(province.getText())) {
                    mProvinceSelIndex = i;
                }
            }
            while (citiesIterator.hasNext()) {
                ZoneItemBean zoneItemBean = citiesIterator.next();
                if (zoneItemBean.getParentVal().equals(province.getValue())) {
                    provinceCities.add(zoneItemBean);
                    citiesIterator.remove();

//                    ArrayList<ZoneItemBean> cityDis = new ArrayList<>();
//                    Iterator<ZoneItemBean> districtsIterator = districts.iterator();
//                    while (districtsIterator.hasNext()){
//                        ZoneItemBean districtBean = districtsIterator.next();
//                        if(districtBean.getParentVal().equals(zoneItemBean.getValue())){
//                            cityDis.add(districtBean);
//                            districtsIterator.remove();
//                        }
//                    }
//                    cityDistricts.add(cityDis);
                }
            }
            mCities.add(provinceCities);
//            mDistricts.add(cityDistricts);
        }

        if (mCitySelIndex == -1
                && !TextUtils.isEmpty(mCityLabel) && mProvinceSelIndex != -1) {
            ArrayList<ZoneItemBean> cityBeans = mCities.get(mProvinceSelIndex);
            for (int i = 0; i < cityBeans.size(); i++) {
                ZoneItemBean zoneItemBean = cityBeans.get(i);
                if (zoneItemBean.getText().equals(mCityLabel)) {
                    mCitySelIndex = i;
                    break;
                }
            }
        }

        showRegionPicker();
    }

    private void showRegionPicker() {
        int optIndex1 = mProvinceSelIndex == -1 ? 0 : mProvinceSelIndex;
        int optIndex2 = mCitySelIndex == -1 ? 0 : mCitySelIndex;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(),
                (options1, options2, options3, v) -> {
                    mProvinceSelIndex = options1;
                    mCitySelIndex = options2;
                    //返回的分别是三个级别的选中位置
                    mProvinceLabel = mProvinces.size() > 0 ?
                            mProvinces.get(options1).getPickerViewText() : "";
                    mCityLabel = mCities.size() > 0
                            && mCities.get(options1).size() > 0 ?
                            mCities.get(options1).get(options2).getPickerViewText() : "";
                    mTVRegion.setText(mProvinceLabel + mCityLabel);
                })

                .setTitleText(ResUtils.getString(R.string.label_city_choose))
                .setDividerColor(ResUtils.getColor(R.color.gray_divide_line))
                .setTextColorCenter(ResUtils.getColor(R.color.colorPrimary)) //设置选中项文字颜色
                .setCancelColor(0xff666666)
                .setSubCalSize(14)
                .setCancelText(ResUtils.getString(R.string.label_cancel))
                .setSubmitText(ResUtils.getString(R.string.label_confirm))
                .setSubmitColor(0xff666666)
                .setContentTextSize(14)
                .build();
        pvOptions.setPicker(mProvinces, mCities);
        pvOptions.setSelectOptions(optIndex1, optIndex2);
        pvOptions.show();
    }

    public void onRegionDataFail() {
    }

    public void onAddressListSucc(AddressItemBean addressItemBean) {
        Intent intent = new Intent();
        intent.putExtras(AddressListActivity.getBundle((AddressItemBean) addressItemBean));
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
