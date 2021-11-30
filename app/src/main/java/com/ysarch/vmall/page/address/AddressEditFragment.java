package com.ysarch.vmall.page.address;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.common.WinSoftKeyboardManager;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.domain.bean.AddressItemBean;
import com.ysarch.vmall.domain.bean.JsonBean;
import com.ysarch.vmall.domain.bean.RegionsResult;
import com.ysarch.vmall.domain.bean.ZoneItemBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.address.presenter.AddressEditPresenter;
import com.ysarch.vmall.utils.Log;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SystemUtil;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
//    private ArrayList<ZoneItemBean> mProvinces = new ArrayList<>();
//    private ArrayList<ArrayList<ZoneItemBean>> mCities = new ArrayList<>();
//    private ArrayList<ArrayList<ArrayList<ZoneItemBean>>> mAreaes = new ArrayList<>();
    private int mProvinceSelIndex = -1;
    private int mCitySelIndex = -1;


    private boolean mShowOpt = true;
    private boolean mAutoSelectDefault = false;
    private boolean mIsConfirmOrder = false;

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    public void onRegionDataSucc(RegionsResult regionsResult) {
//        mProvinces.clear();
//        mProvinces.addAll(regionsResult.getProvinceList());

        List<ZoneItemBean> provinceList = regionsResult.getProvinceList();
        List<ZoneItemBean> cityList = regionsResult.getCityList();
        List<ZoneItemBean> districtList = regionsResult.getDistrictList();


        for (ZoneItemBean provinceItemBean : provinceList){
            JsonBean jsonBean = new JsonBean();
            jsonBean.setText(provinceItemBean.getText());
            jsonBean.setValue(provinceItemBean.getValue());

            List<JsonBean.CityBean> cityBeans = new ArrayList<>();
            for (ZoneItemBean cityItemBean : cityList){
                if(cityItemBean.getParentVal().equals(jsonBean.getValue())){
                    JsonBean.CityBean cityBean = new JsonBean.CityBean();
                    cityBean.setParentVal(cityItemBean.getParentVal());
                    cityBean.setText(cityItemBean.getText());
                    cityBean.setValue(cityItemBean.getValue());
                    List<ZoneItemBean> zoneItemBeans = new ArrayList<>();
                    for(ZoneItemBean districtItemBean : districtList){
                        if(districtItemBean.getParentVal().equals(cityItemBean.getValue())){
                            zoneItemBeans.add(districtItemBean);
                        }
                    }
                    cityBean.setArea(zoneItemBeans);
                    cityBeans.add(cityBean);
                }
            }
            jsonBean.setCityList(cityBeans);
            options1Items.add(jsonBean);
        }


        initJsonData();


        showRegionPicker();
    }

    private void initJsonData() {

        for (int i = 0; i < options1Items.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < options1Items.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = options1Items.get(i).getCityList().get(c).getText();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                for(int j=0;j< options1Items.get(i).getCityList().get(c).getArea().size();j++){
                    city_AreaList.add(options1Items.get(i).getCityList().get(c).getArea().get(j).getText());
                }
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }


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
//            mTVRegion.setText(VMallUtils.decodeString(mProvinceLabel = mAddressItemBean.getProvince()) +
//                    VMallUtils.decodeString(mCityLabel = mAddressItemBean.getCity())+
//                    VMallUtils.decodeString(mRegionLabel = mAddressItemBean.getRegion()));
            mProvinceLabel = mAddressItemBean.getProvince();
            mCityLabel = mAddressItemBean.getCity();
            mRegionLabel = mAddressItemBean.getRegion();

            mTVRegion.setText(VMallUtils.decodeString(VMallUtils.getAddress(mProvinceLabel,mCityLabel,mRegionLabel)));


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
                if (CollectionUtils.isEmpty(options1Items)) {
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


    private void showRegionPicker() {
        int optIndex1 = mProvinceSelIndex == -1 ? 0 : mProvinceSelIndex;
        int optIndex2 = mCitySelIndex == -1 ? 0 : mCitySelIndex;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(),
                (options1, options2, options3, v) -> {
//                    mProvinceSelIndex = options1;
//                    mCitySelIndex = options2;
//                    //返回的分别是三个级别的选中位置
//                    mProvinceLabel = mProvinces.size() > 0 ?
//                            mProvinces.get(options1).getPickerViewText() : "";
//                    mCityLabel = mCities.size() > 0
//                            && mCities.get(options1).size() > 0 ?
//                            mCities.get(options1).get(options2).getPickerViewText() : "";
//                    mTVRegion.setText(mProvinceLabel + mCityLabel);
                    Log.e("niko","options1 = " +options1 + "options2 = " +options2+" options3 = " +options3 );

                    String result = "";
                    String province = ","+options1Items.get(options1).getText();
                    mProvinceLabel = options1Items.get(options1).getText();

                    JsonBean.CityBean cityBean = options1Items.get(options1).getCityList().get(options2);

                    String city = options1Items.get(options1).getCityList().get(options2).getText();
                    mCityLabel =options1Items.get(options1).getCityList().get(options2).getText();

                    String area = "";
                    if(cityBean.getArea()!=null&&cityBean.getArea().size()>0){
                        area = cityBean.getArea().get(options3).getText()+",";
                        mRegionLabel = cityBean.getArea().get(options3).getText();
                    }else {
                        mRegionLabel = "";
                    }
                    mTVRegion.setText(area+city+province);

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
//        pvOptions.setPicker(mProvinces, mCities);
//        pvOptions.setSelectOptions(optIndex1, optIndex2);
        pvOptions.setPicker(options1Items, options2Items, options3Items);
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

    @Override
    protected String getPageName() {
        if (mAddressItemBean != null) {
            return "编辑地址页";
        } else {
            return "添加地址页";
        }

    }
}
