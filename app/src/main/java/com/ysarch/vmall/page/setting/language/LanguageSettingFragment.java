package com.ysarch.vmall.page.setting.language;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.constant.CacheKeys;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.local.LanguageEntity;
import com.ysarch.vmall.helper.CacheHelper;
import com.ysarch.vmall.language.LocalManageUtil;
import com.ysarch.vmall.page.main.MainActivity;

import java.nio.file.FileStore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fysong on 16/09/2020
 **/
public class LanguageSettingFragment extends Fragment {

    @BindView(R.id.ly_km_language)
    LinearLayout mLyKM;

    @BindView(R.id.ly_cn_language)
    LinearLayout mLyCN;

    @BindView(R.id.ly_en_language)
    LinearLayout mLyEN;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_language, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        int lan = AppContext.getsInstance().getLanguageEntity().getLanId();

        resetUI(lan);
    }

    private void resetUI(int lan) {
        switch (lan) {
            case Constants.ID_LAN_KM:
                mLyKM.setSelected(true);
                mLyCN.setSelected(false);
                mLyCN.setSelected(false);
                break;

            case Constants.ID_LAN_ZH:
                mLyKM.setSelected(false);
                mLyCN.setSelected(true);
                mLyEN.setSelected(false);
                break;

            case Constants.ID_LAN_EN:
                mLyKM.setSelected(false);
                mLyCN.setSelected(false);
                mLyEN.setSelected(true);
                break;
        }
    }


    @OnClick({R.id.ly_km_language, R.id.ly_cn_language, R.id.ly_en_language})
    void onViewClick(View view) {
        int lan = 0;
        switch (view.getId()) {
            case R.id.ly_km_language:
                lan = Constants.ID_LAN_KM;
                break;

            case R.id.ly_cn_language:
                lan = Constants.ID_LAN_ZH;
                break;

            case R.id.ly_en_language:
                lan = Constants.ID_LAN_EN;
                break;
        }

//        if(AppContext.getsInstance().setLanguageId(lan)){
//            resetUI(lan);
//        }

        selectLanguage(lan);
    }


    private void selectLanguage(int select) {
        CacheHelper.putInt(CacheKeys.KEY_LAN_ID, select);
        AppContext.getsInstance().setLanguageEntity(new LanguageEntity(select));
        LocalManageUtil.saveSelectLanguage(getContext(), select);
        MainActivity.reStart(getContext());
        getActivity().finish();
    }
}
