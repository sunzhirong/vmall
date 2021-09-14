package com.ysarch.uibase.textview;

/**
 * Created by vincent on 2018/3/2.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import com.ysarch.vmall.R;


/**
 * @author Free
 * @version 1.0
 * @since 2017/5/27
 */
public class CompatTextView extends androidx.appcompat.widget.AppCompatTextView {

    private int mLeftWidth;
    private int mLeftHeight;
    private int mTopWidth;
    private int mTopHeight;
    private int mRightWidth;
    private int mRightHeight;
    private int mBottomWidth;
    private int mBottomHeight;

    public CompatTextView(Context context) {
        super(context, null);
    }

    public CompatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
//        setGravity(Gravity.CENTER);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CompatTextView);

        mLeftWidth = typedArray.getDimensionPixelOffset(R.styleable.CompatTextView_drawableLeftWidth, 0);
        mLeftHeight = typedArray.getDimensionPixelOffset(R.styleable.CompatTextView_drawableLeftHeight, 0);
        mTopWidth = typedArray.getDimensionPixelOffset(R.styleable.CompatTextView_drawableTopWidth, 0);
        mTopHeight = typedArray.getDimensionPixelOffset(R.styleable.CompatTextView_drawableTopHeight, 0);
        mRightWidth = typedArray.getDimensionPixelOffset(R.styleable.CompatTextView_drawableRightWidth, 0);
        mRightHeight = typedArray.getDimensionPixelOffset(R.styleable.CompatTextView_drawableRightHeight, 0);
        mBottomWidth = typedArray.getDimensionPixelOffset(R.styleable.CompatTextView_drawableBottomWidth, 0);
        mBottomHeight = typedArray.getDimensionPixelOffset(R.styleable.CompatTextView_drawableBottomHeight, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setDrawablesSize();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setDrawableLeft(Drawable left, int iconWidth, int iconHeight) {
        mLeftWidth = iconWidth;
        mLeftHeight = iconHeight;
        setDrawableLeft(left);
    }

    public void setDrawableLeft(Drawable left) {
        setDrawableBounds(left, mLeftWidth, mLeftHeight);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(left, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }

    public void setDrawableRight(Drawable right, int iconWidth, int iconHeight) {
        mRightWidth = iconWidth;
        mRightHeight = iconHeight;
        setDrawableLeft(right);
    }

    public void setDrawableRight(Drawable right) {
        setDrawableBounds(right, mRightWidth, mRightHeight);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], right, compoundDrawables[3]);
    }

    public void setDrawableTop(Drawable top) {
        setDrawableTop(top, mTopWidth, mTopHeight);
    }

    public void setDrawableTop(Drawable top, int iconWidth, int iconHeight) {
        setDrawableBounds(top, iconWidth, iconHeight);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0], top, compoundDrawables[2], compoundDrawables[3]);
    }

    public int getLeftWidth() {
        return mLeftWidth;
    }

    public int getLeftHeight() {
        return mLeftHeight;
    }

    private void setDrawablesSize() {
        Drawable[] compoundDrawables = getCompoundDrawables();
        for (int i = 0; i < compoundDrawables.length; i++) {
            switch (i) {
                case 0:
                    setDrawableBounds(compoundDrawables[0], mLeftWidth, mLeftHeight);
                    break;
                case 1:
                    setDrawableBounds(compoundDrawables[1], mTopWidth, mTopHeight);
                    break;
                case 2:
                    setDrawableBounds(compoundDrawables[2], mRightWidth, mRightHeight);
                    break;
                case 3:
                    setDrawableBounds(compoundDrawables[3], mBottomWidth, mBottomHeight);
                    break;
                default:

                    break;
            }

        }
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
    }


    private void setDrawableBounds(Drawable drawable, int width, int height) {
        if (drawable != null && width != 0 && height != 0) {
            drawable.setBounds(0, 0, width, height);
        }
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}