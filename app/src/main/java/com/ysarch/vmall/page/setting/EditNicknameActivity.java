package com.ysarch.vmall.page.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.UserInfoManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNicknameActivity extends AppCompatActivity {
    @BindView(R.id.et_nickname)
    EditText mEtNickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nickname);
        ButterKnife.bind(this);
        mEtNickname.setText(UserInfoManager.getUser().getNickname());

    }

    @OnClick(R.id.ly_base_title_bar_right)
    public void onSave() {
        if(!TextUtils.isEmpty(mEtNickname.getText().toString())){
            Intent intent = new Intent();
            intent.putExtra("nickname",mEtNickname.getText().toString());
            setResult(RESULT_OK,intent);
            finish();
        }else {
            Toast.makeText(this, R.string.input_your_nickname, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ly_base_title_bar_left)
    public void onCancel() {
        finish();
    }


//        @Override
//    protected String getCustomTitle() {
//        return getString(R.string.label_edit_nickname);
//    }
//
//    @Override
//    protected Fragment createFragment() {
//        return new EditNicknameFragment();
//    }
}

