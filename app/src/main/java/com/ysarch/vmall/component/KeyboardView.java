package com.ysarch.vmall.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.ysarch.vmall.R;

import java.lang.reflect.Method;

import butterknife.ButterKnife;

public class KeyboardView extends RelativeLayout {
    protected Context mContext;
    private int[] commonButtonIds = new int[]{R.id.button00, R.id.button01, R.id.button02, R.id.button03,
            R.id.button04, R.id.button05, R.id.button06, R.id.button07, R.id.button08, R.id.button09};
    public KeyboardView(Context context) {
        super(context);
        init(context);
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.keyboadview,this);
        ButterKnife.bind(this, this);

    }

    public void setEditText(EditText editText){
        //①给数字键设置点击监听
        for (int i = 0; i < commonButtonIds.length; i++) {
            final Button button = findViewById(commonButtonIds[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int curSelection = editText.getSelectionStart();
                    int length = editText.getText().toString().length();
                    if (curSelection < length) {
                        String content = editText.getText().toString();
                        editText.setText(content.substring(0, curSelection) + button.getText() + content.subSequence(curSelection, length));
                        editText.setSelection(curSelection + 1);
                    } else {
                        editText.setText(editText.getText().toString() + button.getText());
                        editText.setSelection(editText.getText().toString().length());
                    }

                    Log.e("key",editText.getText()+"---");
                }
            });
        }
        //③给叉按键设置点击监听
        findViewById(R.id.buttonCross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = editText.getText().toString().length();
                int curSelection = editText.getSelectionStart();
                if (length > 0 && curSelection > 0 && curSelection <= length) {
                    String content = editText.getText().toString();
                    editText.setText(content.substring(0, curSelection - 1) + content.subSequence(curSelection, length));
                    editText.setSelection(curSelection - 1);
                }
                Log.e("key",editText.getText()+"---");
            }
        });
    }

//    /**
//     * 禁止系统默认的软键盘弹出
//     */
//    private void forbidDefaultSoftKeyboard() {
//        if (editText == null) {
//            return;
//        }
//        if (android.os.Build.VERSION.SDK_INT > 10) {//4.0以上，使用反射的方式禁止系统自带的软键盘弹出
//            try {
//                Class<EditText> cls = EditText.class;
//                Method setShowSoftInputOnFocus;
//                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
//                setShowSoftInputOnFocus.setAccessible(true);
//                setShowSoftInputOnFocus.invoke(editText, false);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
