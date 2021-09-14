package com.ysarch.vmall.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * Passcode EditText
 * Created by neil on 2018/11/18.
 */

public class PwdEditText extends AppCompatEditText {

    private Paint sidePaint, backPaint, textPaint;
    private String mText;
    private List<RectF> rectFS;
    private int StrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,1,getResources().getDisplayMetrics()),
            spzceX =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics()),
            spzceY =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics()),
            textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,16,getResources().getDisplayMetrics());
//    private int checkedColor = 0xffF94956, defaultColor = 0xffd0d0d0, backColor = 0xffffffff, textColor = 0xFF444444, waitInputColor = 0xFF444444;
    private int checkedColor = 0xffd0d0d0, defaultColor = 0xffd0d0d0, backColor = 0xffffffff, textColor = 0xFF444444, waitInputColor = 0xFF444444;
    private int textLength = 6;
    private int Circle =(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,7,getResources().getDisplayMetrics());
    private int Round = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics());
    private boolean isPwd = true, isWaitInput = false;

    public PwdEditText(Context context) {
        super(context);

        init();
    }

    public PwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }




    private void init() {
        setTextColor(0X00ffffff);
//        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        setInputType(InputType.TYPE_NULL);
        sidePaint = new Paint();
        backPaint = new Paint();
        textPaint = new Paint();

        rectFS = new ArrayList<>();
        mText = "";

        this.setBackgroundDrawable(null);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCursorVisible(false);



    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mText == null) {
            return;
        }

        if (text.toString().length() <= textLength) {
            mText = text.toString();
        } else {
            setText(mText);
            setSelection(getText().toString().length());
//            setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        }
        if (OnTextChangeListener != null) OnTextChangeListener.onTextChange(mText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
                break;
            case View.MeasureSpec.AT_MOST:
                heightSize = widthSize / textLength;
                break;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        sidePaint.setAntiAlias(true);
        sidePaint.setStrokeWidth(StrokeWidth);
        sidePaint.setStyle(Paint.Style.STROKE);
        sidePaint.setColor(defaultColor);

        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(backColor);

        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);

        int Wide = Math.min(getMeasuredHeight(), getMeasuredWidth() / textLength);

        for (int i = 0; i < textLength; i++) {

            if (mText.length() >= i) {
                sidePaint.setColor(checkedColor);
            } else {
                sidePaint.setColor(defaultColor);
            }

            RectF rect = new RectF(i * Wide + spzceX, spzceY, i * Wide + Wide - spzceX, Wide - spzceY); //四个值，分别代表4条线，距离起点位置的线
            canvas.drawRoundRect(rect, Round, Round, backPaint);
            canvas.drawRoundRect(rect, Round, Round, sidePaint);
            rectFS.add(rect);

            if (isWaitInput && i == mText.length()) {
                Paint l = new Paint();
                l.setStrokeWidth(3);
                l.setStyle(Paint.Style.FILL);
                l.setColor(waitInputColor);
                canvas.drawLine(i * Wide + Wide / 2, Wide / 2 - Wide / 5, i * Wide + Wide / 2, Wide / 2 + Wide / 5, l);
            }
        }

        for (int j = 0; j < mText.length(); j++) {
            if (isPwd) {
                canvas.drawCircle(rectFS.get(j).centerX(), rectFS.get(j).centerY(), Circle, textPaint);
            } else {
                canvas.drawText(mText.substring(j, j + 1), rectFS.get(j).centerX() - (textSize - spzceX) / 2, rectFS.get(j).centerY() + (textSize - spzceY) / 2, textPaint);
            }
        }
    }


    /**
     * set listener
     */
    public interface OnTextChangeListener {
        void onTextChange(String pwd);
    }

    private OnTextChangeListener OnTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener OnTextChangeListener) {
        this.OnTextChangeListener = OnTextChangeListener;
    }

    /**
     * clear content
     */
    public void clearText() {
        setText("");
//        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
    }



}


