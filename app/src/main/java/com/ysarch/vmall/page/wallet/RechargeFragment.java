package com.ysarch.vmall.page.wallet;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.common.WinSoftKeyboardManager;
import com.ysarch.uibase.textview.CompatTextView;
import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.component.dialog.ModifyUserPicDialog;
import com.ysarch.vmall.component.dialog.RechargeBankSelectDialog;
import com.ysarch.vmall.domain.bean.BankItemBean;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.page.recharge.RechargeSuccessActivity;
import com.ysarch.vmall.page.wallet.presenter.RechargePresenter;
import com.ysarch.vmall.utils.GlideUtils;
import com.ysarch.vmall.utils.Log;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.SystemUtil;
import com.yslibrary.utils.CollectionUtils;

//import org.devio.takephoto.app.TakePhoto;
//import org.devio.takephoto.app.TakePhotoImpl;
//import org.devio.takephoto.compress.CompressConfig;
//import org.devio.takephoto.model.InvokeParam;
//import org.devio.takephoto.model.TContextWrap;
//import org.devio.takephoto.model.TResult;
//import org.devio.takephoto.permission.InvokeListener;
//import org.devio.takephoto.permission.PermissionManager;
//import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.kit.PictureUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * Created by fysong on 2020/10/15
 **/
public class RechargeFragment extends BaseFragment<RechargePresenter>
        implements InvokeListener, TakePhoto.TakeResultListener
{
    private static final int TYPE_PIC_CAMERA = 1, TYPE_PIC_GALLERY = 2;

    @BindView(R.id.ly_upload_pic_recharge)
    LinearLayout mLyPicUpload;
    @BindView(R.id.iv_pic_recharge)
    ImageView mIVPic;
    @BindView(R.id.iv_add)
    ImageView mIVAdd;


    @BindView(R.id.tv_bank_select)
    CompatTextView mTvBankSelect;
    @BindView(R.id.tv_bank)
    TextView mTvBank;
    @BindView(R.id.tv_clip_bank)
    TextView mTvClipBank;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.tv_clip_account)
    TextView mTvClipAccount;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_clip_name)
    TextView mTvClipName;
    @BindView(R.id.tv_submit_recharge)
    TextView mTvSubmitRecharge;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;

    private String mPicPath;
    private BankItemBean mBankItemBeanSelected;
    private Date mSelDate;
    private String mDateRecharge;
    private String mPublicPhotoPath;
    private double amount;
    private int mBankId;
    private List<BankItemBean> mBankItemBeans;

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            amount = getArguments().getDouble(BundleKey.ARG_RECHARGE_AMOUNT, 0);
            mTvAmount.setText(String.valueOf(amount));
            mBankId = getArguments().getInt(BundleKey.ARG_RECHARGE_BANK_ID);
            mBankItemBeans = new ArrayList<>();
            Log.e("niko bank", JSON.toJSONString(AppContext.getsInstance().getBankItemBeans()));
            for (BankItemBean bean : AppContext.getsInstance().getBankItemBeans()) {
                if (bean.getId() == mBankId) {
                    mBankItemBeanSelected = bean;
                    resetBankInfo();
                    break;
                }
            }
            if(mBankItemBeanSelected==null){
                return;
            }
            for (BankItemBean bean : AppContext.getsInstance().getBankItemBeans()) {
                if (mBankItemBeanSelected.getBankName().equals(bean.getBankName())) {
                    mBankItemBeans.add(bean);
                }
            }
        }

    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
        new WinSoftKeyboardManager((ViewGroup) mRootView).registEditTexts();
    }

    @OnClick({R.id.tv_bank_select, R.id.ly_upload_pic_recharge, R.id.iv_pic_recharge,
            R.id.tv_submit_recharge, R.id.tv_clip_bank, R.id.tv_clip_name, R.id.tv_clip_account})
    void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bank_select:
//            case R.id.tv_bank_prefix_recharge:
                SystemUtil.hideKeyboard(view);
//                getPresenter().requestBanks();
                onBankData(mBankItemBeans);
                break;


            case R.id.ly_upload_pic_recharge:
            case R.id.iv_pic_recharge:
                SystemUtil.hideKeyboard(view);
                showDialog2SelectPic();
                break;

            case R.id.tv_submit_recharge:
                SystemUtil.hideKeyboard(view);
                onSubmitClick();
                break;
            case R.id.tv_clip_bank:
                if (mBankItemBeanSelected != null) {
                    SystemUtil.copy(context, mBankItemBeanSelected.getBankName());
                }
                break;
            case R.id.tv_clip_name:
                if (mBankItemBeanSelected != null) {
                    SystemUtil.copy(context, mBankItemBeanSelected.getBankNo());
                }
                break;
            case R.id.tv_clip_account:
                if (mBankItemBeanSelected != null) {
                    SystemUtil.copy(context, mBankItemBeanSelected.getBankNo());
                }
                break;
        }
    }

//    /**
//     * 选择充值时间
//     *
//     * @param selDate
//     */
//    private void selectTime(Date selDate) {
//        Calendar curDate;// = Calendar.getInstance();
//        Calendar startDate = Calendar.getInstance();
//        Date dateNow = new Date();
//        startDate.setTime(dateNow);
//        if (selDate == null) {
//            curDate = startDate;
//        } else {
//            curDate = Calendar.getInstance();
//            curDate.setTime(selDate);
//        }
//
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(dateNow.getYear() - 5 + 1900, 1, 0);
//
//        TimePickerBuilder builder = new TimePickerBuilder(getContext(), (date, v) -> {
//            mSelDate = date;
//            mDateRecharge = TimeUtils.formatCustomDate(date, "yyyy-MM-dd HH:mm");
////            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
////            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
////            String t = sdf1.format(mSelDate);
////            t= sdf2.format(mSelDate);
////            mTVTime.setText(mDateRecharge);
//        })
//                .setRangDate(endDate, startDate)
//                .setTitleText("")
//                .setType(new boolean[]{true, true, true, true, true, false})
//                .setTitleColor(ResUtils.getColor(R.color.picker_title))
//                .setCancelColor(ResUtils.getColor(R.color.picker_cancel))
//                .setSubmitColor(ResUtils.getColor(R.color.picker_confirm))
//                .setTitleBgColor(ResUtils.getColor(R.color.picker_title_bg))
//                .setTextColorOut(ResUtils.getColor(R.color.picker_text_out))
//                .setTextColorCenter(ResUtils.getColor(R.color.picker_text_center))
//                .setContentTextSize(16)
//                .setBgColor(0xffffffff)
//                .setDate(curDate)
//                .setSubCalSize(18);
//        builder.build().show();
//    }

    /**
     * 提交信息前数据处理
     */
    private void onSubmitClick() {
        if (mBankItemBeanSelected == null) {
            showTs(getString(R.string.tip_pls_select_bank));
            return;
        }
//        String priceStr = mETAmount.getText().toString().trim();
//        if (!TextUtils.isEmpty(priceStr) && priceStr.matches("^0*\\d*")) {
//            priceStr = priceStr.replaceAll("^0*", "");
//            mETAmount.setText(priceStr);
//        }

//        priceStr = mETAmount.getText().toString().trim();
//        if (TextUtils.isEmpty(priceStr)) {
//            showTs(getString(R.string.tip_pls_input_recharge_amount));
//            return;
//        }

//        if (TextUtils.isEmpty(mDateRecharge)) {
//            showTs(getString(R.string.tip_pls_select_recharge_date));
//            return;
//        }

        if (TextUtils.isEmpty(mPicPath)) {
            showTs(getString(R.string.tip_pls_upload_recharge_proof));
            return;
        }
//        TimeUtils.getUTCDate(mSelDate)
        getPresenter().submitInfo(String.valueOf(amount), mBankItemBeanSelected.getId(), mPicPath,
                "", "");
    }

    /**
     * 显示选择图片的dialog
     */
    private void showDialog2SelectPic() {
//        getRxPermissions().request(Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(aBoolean -> {
//                    if (aBoolean) {
//                        ModifyUserPicDialog.showDialog(getActivity())
//                                .setOnPickerClickListener(new ModifyUserPicDialog.onPickerClickListener() {
//                                    @Override
//                                    public void onClickToTakePhone() {
////                                        mPublicPhotoPath = Kits.PHOTO.takePhoto(getActivity(), BuildConfig.APPLICATION_ID, TYPE_PIC_CAMERA);
//
//
//                                        getTakePhoto().onPickFromCapture(getImageUri());
//                                    }
//
//                                    @Override
//                                    public void onClickToFromAlbum() {
////                                        Kits.PHOTO.getImageFromAlbum(getActivity(), TYPE_PIC_GALLERY);
//                                        getTakePhoto().onPickFromGallery();
//                                    }
//                                });
//                    } else {
//                        showAlert2AppInfo(getString(R.string.text_please_agree_to_authorize));
//                    }
//                });

//        getTakePhoto().onPickFromCapture(getImageUri());
//        getTakePhoto().onPickFromGallery();

            ModifyUserPicDialog.showDialog(getActivity())
            .setOnPickerClickListener(new ModifyUserPicDialog.onPickerClickListener() {
                @Override
                public void onClickToTakePhone() {
    //                                        mPublicPhotoPath = Kits.PHOTO.takePhoto(getActivity(), BuildConfig.APPLICATION_ID, TYPE_PIC_CAMERA);


                    getTakePhoto().onPickFromCapture(getImageUri());
                }

                @Override
                public void onClickToFromAlbum() {
    //                                        Kits.PHOTO.getImageFromAlbum(getActivity(), TYPE_PIC_GALLERY);
                    getTakePhoto().onPickFromGallery();
                }
            });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    @Override
    public RechargePresenter newPresenter() {
        return new RechargePresenter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (getTakePhoto() != null) {
            getTakePhoto().onActivityResult(requestCode, resultCode, data);
        }
//        switch (requestCode) {
//            case TYPE_PIC_CAMERA:
//                if (resultCode != RESULT_OK) return;
//                Uri uri = Uri.parse(mPublicPhotoPath);
//                mPicPath = uri.getPath();
//                mIVPic.setAdjustViewBounds(true);
//                mIVPic.setImageURI(uri);
////                mLyPicUpload.setVisibility(View.GONE);
//                mIVAdd.setVisibility(View.GONE);
//                mIVPic.setVisibility(View.VISIBLE);
//                break;
//            case TYPE_PIC_GALLERY:
//                if (data == null) return;
//                Uri urlFrom = data.getData();
//                int sdkVersion = Build.VERSION.SDK_INT;
//                if (sdkVersion >= 19) {
//                    mPicPath = PictureUtils.getPath_above19(getActivity(), urlFrom);
//                } else {
//                    mPicPath = PictureUtils.getFilePath_below19(getActivity(), urlFrom);
//                }
//                mIVPic.setAdjustViewBounds(true);
//                mIVPic.setImageURI(Uri.parse(mPicPath));
////                mLyPicUpload.setVisibility(View.GONE);
//                mIVAdd.setVisibility(View.GONE);
//                mIVPic.setVisibility(View.VISIBLE);
//                break;
//            default:
//                break;
//        }
    }

    /**
     * 数据提交成功，退出页面
     */
    public void onSubmitSucc(String msg) {
//        SimpleDialogWithSingleBtn dialogWithSingleBtn = new SimpleDialogWithSingleBtn.Builder(getContext())
//                .setContent(String.format(getString(R.string.format_tip_recharge_submit_succ), msg))
//                .setmConfirmLabel(getString(R.string.label_confirm))
//                .setCancelable(true)
//                .setAutoDismissWhileClick(true)
//                .setOnSubmitListener(() -> NavHelper.startActivity(getActivity(), RechargeHistoryActivity.class)).build();
//        dialogWithSingleBtn.setOnDismissListener(dialog -> getActivity().finish());
//        dialogWithSingleBtn.show();


        getActivity().finish();
        NavHelper.startActivity(getActivity(), RechargeSuccessActivity.class);

    }


    public void onBankData(List<BankItemBean> bankItemBeans) {
        if (CollectionUtils.isNotEmpty(AppContext.getsInstance().getBankItemBeans())) {
            RechargeBankSelectDialog dialog = new RechargeBankSelectDialog.Builder(context).setCallback(new RechargeBankSelectDialog.Callback() {
                @Override
                public void onSelect(BankItemBean bean) {
                    mBankItemBeanSelected = bean;
                    resetBankInfo();
                }
            }).setBankItemBeans(bankItemBeans).setSelectBean(mBankItemBeanSelected).build();
            dialog.show();
        }
    }

    /**
     * 更新银行数据展示
     */
    private void resetBankInfo() {
        if (mBankItemBeanSelected != null) {
//            mCTVBank.setText(mBankItemBeanSelected.getPickerViewText());
//            mTVBankInfo.setText(mBankItemBeanSelected.getBankName() + "\n"
//                    + mBankItemBeanSelected.getBankNo() + "\n"
//                    + mBankItemBeanSelected.getBankAccount());
//            mTVBankInfo.setVisibility(View.VISIBLE);
//            mVDivideBankInfo.setVisibility(View.VISIBLE);
            mTvBankSelect.setText(mBankItemBeanSelected.getBankName() + " " + mBankItemBeanSelected.getBankNo());
            mTvBank.setText(mBankItemBeanSelected.getBankName());
            mTvName.setText(mBankItemBeanSelected.getBankAccount());
            mTvAccount.setText(mBankItemBeanSelected.getBankNo());
        } else {
//            mTVBankInfo.setVisibility(View.GONE);
//            mVDivideBankInfo.setVisibility(View.GONE);
//            mCTVBank.setText("");
        }
    }



    public static int MAX_COMPRESS_SIZE = 1024 * 1024;
    public static int MAX_COMPRESS_PIXEL = 1920;
    private TakePhoto takePhoto;  //TakePhoto对象
    private InvokeParam invokeParam;
    /**
     * 获取takePhoto对象
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        takePhoto.onEnableCompress(getCompressConfig(), true);
        return takePhoto;
    }

    /**
     * 获取图片压缩参数配置
     */
    public CompressConfig getCompressConfig() {
        return new CompressConfig.Builder()
                .setMaxSize(MAX_COMPRESS_SIZE)
                .setMaxPixel(MAX_COMPRESS_PIXEL)
                .create();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
            PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
        } catch (Exception e) {

        }

    }

    public Uri getImageUri() {
//        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
        File file = new File(context.getExternalFilesDir(DIRECTORY_PICTURES).getAbsolutePath(),  System.currentTimeMillis() + ".jpg");
        return Uri.fromFile(file);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void takeSuccess(TResult result) {
        mPicPath = result.getImage().getCompressPath();
        GlideUtils.loadImageView(context,mPicPath,mIVPic);
//        mIVPic.setImageURI(uri);
                mIVAdd.setVisibility(View.GONE);
                mIVPic.setVisibility(View.VISIBLE);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.e("takeFail",msg);
    }

    @Override
    public void takeCancel() {

    }
}
