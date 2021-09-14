package com.ysarch.vmall.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.imageloader.BeeGlide;
import com.ysarch.vmall.common.imageloader.ImageLoadConfig;
import com.ysarch.vmall.domain.bean.TBShareCmdBean;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 13/10/2020
 **/
public class TBShareCmdDialogNew extends Dialog {
    @BindView(R.id.riv_tb_cmd_dialog)
    RoundedImageView mRivCover;
    @BindView(R.id.tv_title_tb_cmd_dialog)
    TextView mTVTitle;
    @BindView(R.id.tv_price_tb_cmd_dialog)
    TextView mTVPrice;
    private BeeGlide mBeeGlide;

    private TBShareCmdBean mTBShareCmdBean;
    private Callback mCallback;

    private TBShareCmdDialogNew(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init(){
        mBeeGlide = BeeGlide.with(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_tb_cmd_new, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setCancelable(true);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(attributes);
    }


    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void setTBShareCmdBean(TBShareCmdBean tbShareCmdBean) {
        mTBShareCmdBean = tbShareCmdBean;
        mBeeGlide.load(ImageLoadConfig.create(mTBShareCmdBean.getPicUrl()).randomPlaceHolder(), mRivCover);

//        if(!TextUtils.isEmpty(mTBShareCmdBean.getOwnerName())){
//            mTVName.setVisibility(View.VISIBLE);
//            mTVShareTip.setVisibility(View.VISIBLE);
//            mTVName.setText(mTBShareCmdBean.getOwnerName());
//            if(!TextUtils.isEmpty(mTBShareCmdBean.getOwnerFace())){
//                mRivAvatar.setVisibility(View.VISIBLE);
//                mBeeGlide.load(ImageLoadConfig.create(mTBShareCmdBean.getOwnerFace()).randomPlaceHolder(), mRivAvatar);
//            } else {
//                mRivAvatar.setVisibility(View.GONE);
//            }
//        } else {
//            mRivAvatar.setVisibility(View.GONE);
//            mTVName.setVisibility(View.GONE);
//            mTVShareTip.setVisibility(View.GONE);
//        }

        mTVTitle.setText(mTBShareCmdBean.getContent());
        mTVPrice.setText(mTBShareCmdBean.getPrice());

    }

    @OnClick({R.id.ly_root_tb_cmd_dialog, R.id.tv_nav_detail_tb_cmd_dialog,
            R.id.iv_close_tb_cmd_dialog})
    void onViewClick(View view){
        switch (view.getId()){
            case R.id.tv_nav_detail_tb_cmd_dialog:
                if(mCallback != null){
                    mCallback.onNavGoodsDetail(mTBShareCmdBean.getGoodsId());
                }
                dismiss();
                break;
            case R.id.iv_close_tb_cmd_dialog:
                dismiss();
                break;
        }
    }


    public interface Callback{
        void onNavGoodsDetail(long goodsId);
    }


    public static class Builder {
        private Context mContext;
        private TBShareCmdBean mTBShareCmdBean;
        private Callback mCallback;
        public Builder(Context context){
            mContext = context;
        }


        public Builder setTBShareCmdBean(TBShareCmdBean TBShareCmdBean) {
            mTBShareCmdBean = TBShareCmdBean;
            return this;
        }

        public Builder setCallback(Callback callback) {
            mCallback = callback;
            return this;
        }


        public TBShareCmdDialogNew build() {
            TBShareCmdDialogNew dialog = new TBShareCmdDialogNew(mContext, R.style.CenterDialog);
            dialog.setTBShareCmdBean(mTBShareCmdBean);
            dialog.setCallback(mCallback);
            return dialog;
        }


    }

}
