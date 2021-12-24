package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tendcloud.tenddata.TCAgent;
import com.ysarch.vmall.BuildConfig;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.TBShareCmdBean;
import com.ysarch.vmall.domain.bean.UpdateBean;
import com.ysarch.vmall.utils.Log;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.VMallUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateDialog extends Dialog {
    @BindView(R.id.tv_update_version)
    TextView mTvUpdateVersion;
    @BindView(R.id.tv_update_info)
    TextView mTvUpdateInfo;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.tv_update_title)
    TextView mTvUpdateTitle;



    private UpdateBean mUpdateBean;

    private UpdateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init(){
        View view = View.inflate(getContext(), R.layout.dialog_update, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(false);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
        Log.e("UpdateDialog","init");

    }



    public void setUpdateBean(UpdateBean updateBean) {
        mUpdateBean = updateBean;
        Log.e("UpdateDialog","setUpdateBean");
        mTvUpdateVersion.setText("V"+updateBean.getVersionName());
        mTvUpdateInfo.setText(updateBean.getModifyContent());
        mIvClose.setVisibility(updateBean.isForceUpdate()?View.GONE:View.VISIBLE);
        mTvUpdateTitle.setText(updateBean.getTitle());
    }

    @OnClick({R.id.tv_update, R.id.iv_close})
    void onViewClick(View view){
        switch (view.getId()){
            case R.id.tv_update:
                if("play.google.com".equals(VMallUtils.getMeta(getContext()))){
                    launchAppDetail(BuildConfig.APPLICATION_ID,"com.android.vending");
                }else {
                    NavHelper.startWeb(getContext(),mUpdateBean.getPageUrl());
                }
                if(mUpdateBean.isForceUpdate()){return;}
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }



    public void launchAppDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;

            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public interface Callback{
        void onNavGoodsDetail(long goodsId);
    }


    public static class Builder {
        private Context mContext;
        private UpdateBean mUpdateBean;
        public Builder(Context context){
            mContext = context;
        }


        public UpdateDialog.Builder setUpdateBean(UpdateBean updateBean) {
            mUpdateBean = updateBean;
            return this;
        }


        public UpdateDialog build() {
            UpdateDialog dialog = new UpdateDialog(mContext, R.style.CenterDialog);
            dialog.setUpdateBean(mUpdateBean);
            return dialog;
        }


    }
    @Override
    public void show() {
        super.show();
        TCAgent.onPageStart(getContext(),"版本升级弹窗");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        TCAgent.onPageEnd(getContext(),"版本升级弹窗");
    }


}
