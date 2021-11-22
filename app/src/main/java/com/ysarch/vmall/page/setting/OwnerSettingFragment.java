package com.ysarch.vmall.page.setting;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.component.SettingItem;
import com.ysarch.vmall.component.dialog.ModifyUserPicDialog;
import com.ysarch.vmall.domain.bean.UserInfoBean;
import com.ysarch.vmall.domain.constant.RequestCode;
import com.ysarch.vmall.utils.GlideUtils;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.VMallUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.kit.PictureUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class OwnerSettingFragment extends BaseFragment<OwnerSettingPresenter> {
    private static final int TYPE_PIC_CAMERA = 1, TYPE_PIC_GALLERY = 2;


    @BindView(R.id.iv_ava)
    ImageView mIvAva;
    @BindView(R.id.rl_ava)
    RelativeLayout mRlAva;
    @BindView(R.id.siv_nickname)
    SettingItem mSivNickname;
    @BindView(R.id.siv_phone_num)
    SettingItem mSivPhoneNum;
    @BindView(R.id.siv_gender)
    SettingItem mSivGender;
    @BindView(R.id.siv_birthday)
    SettingItem mSivBirthday;
    private String mPublicPhotoPath;
    private String mPicPath;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (UserInfoManager.isLogin()) {
            setUI();
        }
    }

    private void setUI() {
        mSivNickname.setRightText(UserInfoManager.getUser().getNickname());
        mSivPhoneNum.setRightText(UserInfoManager.getUser().getAccount());
        mSivGender.setRightText(UserInfoManager.getUser().getGender()==1?getString(R.string.male):getString(R.string.female));
        if(!TextUtils.isEmpty(UserInfoManager.getUser().getBirthday())) {
            mSivBirthday.setRightText(VMallUtils.GTMToLocal(UserInfoManager.getUser().getBirthday(), "yyyy-MM-dd"));
        }
//        Glide.with(this).load(UserInfoManager.getUser().getIcon()).into(mIvAva);

//        BeeGlide.with(this)
//                .load(ImageLoadConfig.create(UserInfoManager.getUser().getIcon())
//                        .placeHolder(R.drawable.ic_avatar_placeholder), mIvAva);
        GlideUtils.loadImageView(context,UserInfoManager.getUser().getIcon(),mIvAva,context.getDrawable(R.drawable.ic_avatar_placeholder));
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_owner_setting;
    }

    @Override
    public OwnerSettingPresenter newPresenter() {
        return new OwnerSettingPresenter();
    }

    @OnClick({R.id.rl_ava, R.id.siv_nickname, R.id.siv_gender, R.id.siv_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_ava:
                showDialog2SelectPic();
                break;
            case R.id.siv_nickname:
                NavHelper.startActivity(getActivity(),EditNicknameActivity.class, RequestCode.CODE_EDIT_NICKNAME);
                break;
            case R.id.siv_gender:
                showGenderDialog();
                break;
            case R.id.siv_birthday:
                int year,month,day;

                if(!TextUtils.isEmpty(UserInfoManager.getUser().getBirthday())) {
                    String s = VMallUtils.GTMToLocal(UserInfoManager.getUser().getBirthday(), "yyyy-MM-dd");
                    String[] split = s.split("-");
                    year = Integer.parseInt(split[0]);
                    month = Integer.parseInt(split[1] ) -1;
                    day = Integer.parseInt(split[2]);
                }else {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }

                new DatePickerDialog(context, 0, new DatePickerDialog.OnDateSetListener() {
                    // 绑定监听器(How the parent is notified that the date is set.)
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作
//                        showTs("您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        UserInfoBean user = UserInfoManager.getUser();
                        String birthday = String.valueOf(year);
                        if(monthOfYear + 1<10){
                            birthday = birthday + "0"+(monthOfYear + 1);
                        }else {
                            birthday = birthday + (monthOfYear + 1);
                        }
                        if(dayOfMonth<10){
                            birthday = birthday + "0"+dayOfMonth;
                        }else {
                            birthday = birthday +dayOfMonth;
                        }

                        getPresenter().updateMemberInfo(birthday,user.getGender(),user.getIcon(),user.getNickname());
                    }
                }
                        , year
                        , month
                        , day).show();
                        // 设置初始日期
//                        , calendar.get(Calendar.YEAR)
//                        , calendar.get(Calendar.MONTH)
//                        , calendar.get(Calendar.DAY_OF_MONTH)).show();

                break;
        }
    }

    private void showGenderDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.dialog_gender);
        bottomSheetDialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.findViewById(R.id.tv_female).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            UserInfoBean user = UserInfoManager.getUser();
            getPresenter().updateMemberInfo(VMallUtils.updateBirthday(user.getBirthday()),0,user.getIcon(),user.getNickname());
        });
        bottomSheetDialog.findViewById(R.id.tv_male).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            UserInfoBean user = UserInfoManager.getUser();
            getPresenter().updateMemberInfo(VMallUtils.updateBirthday(user.getBirthday()),1,user.getIcon(),user.getNickname());
        });
        bottomSheetDialog.show();
    }


    /**
     * 显示选择图片的dialog
     */
    private void showDialog2SelectPic() {
        getRxPermissions().request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        ModifyUserPicDialog.showDialog(getActivity())
                                .setOnPickerClickListener(new ModifyUserPicDialog.onPickerClickListener() {
                                    @Override
                                    public void onClickToTakePhone() {
                                        mPublicPhotoPath = Kits.PHOTO.takePhoto(getActivity(), BuildConfig.APPLICATION_ID, TYPE_PIC_CAMERA);
                                    }

                                    @Override
                                    public void onClickToFromAlbum() {
                                        Kits.PHOTO.getImageFromAlbum(getActivity(), TYPE_PIC_GALLERY);
                                    }
                                });
                    } else {
                        showAlert2AppInfo(getString(R.string.text_please_agree_to_authorize));
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TYPE_PIC_CAMERA:
                if (resultCode != RESULT_OK) return;
                Uri uri = Uri.parse(mPublicPhotoPath);
                mPicPath = uri.getPath();
                getPresenter().uploadPic(mPicPath);
                break;
            case TYPE_PIC_GALLERY:
                if (data == null) return;
                Uri urlFrom = data.getData();
                int sdkVersion = Build.VERSION.SDK_INT;
                if (sdkVersion >= 19) {
                    mPicPath = PictureUtils.getPath_above19(getActivity(), urlFrom);
                } else {
                    mPicPath = PictureUtils.getFilePath_below19(getActivity(), urlFrom);
                }
                getPresenter().uploadPic(mPicPath);
                break;
            case RequestCode.CODE_EDIT_NICKNAME:
                if (resultCode != RESULT_OK) return;
                String nickname = data.getStringExtra("nickname");
                UserInfoBean user = UserInfoManager.getUser();
                getPresenter().updateMemberInfo(VMallUtils.updateBirthday(user.getBirthday()),user.getGender(),user.getIcon(),nickname);
                break;
            default:
                break;
        }
    }


    public void onUserInfoSucc() {
        setUI();
    }


}
