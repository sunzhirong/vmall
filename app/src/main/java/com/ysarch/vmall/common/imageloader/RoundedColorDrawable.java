package com.ysarch.vmall.common.imageloader;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * 参照ColorDrawable，自定义简易的可定制圆角的Drawable
 * Created by fysong on 2019-11-25
 **/
public class RoundedColorDrawable extends Drawable {
    private static final String TAG = "RoundedColorDrawable";
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private RoundColorDrawableState mDrawableState;

    private boolean mMutated;


    private RoundedColorDrawable(RoundColorDrawableState state) {
        mDrawableState = state;
    }

    public RoundedColorDrawable(@ColorInt int color, @IntRange(from = 0) int radius) {
        mDrawableState = new RoundColorDrawableState();

        mDrawableState.mRadius = radius;
        mDrawableState.mBaseColor = mDrawableState.mModifiedColor = color;

        invalidateSelf();
    }


    public RoundedColorDrawable(@ColorInt int color, boolean isOval) {
        mDrawableState = new RoundColorDrawableState();
        mDrawableState.bOval = isOval;
        mDrawableState.mBaseColor = mDrawableState.mModifiedColor = color;

        invalidateSelf();
    }


    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | mDrawableState.getChangingConfigurations();
    }

    /**
     * @return This drawable.
     */
    @Override
    public Drawable mutate() {
        if (!mMutated && super.mutate() == this) {
            mDrawableState = new RoundColorDrawableState(mDrawableState);
            mMutated = true;
        }
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        final ColorFilter colorFilter = mPaint.getColorFilter();
        if ((mDrawableState.mModifiedColor >>> 24) != 0 || colorFilter != null) {

            mPaint.setColor(mDrawableState.mModifiedColor);
//
            if (mDrawableState.bOval) {
                int radiusX = (getBounds().right - getBounds().left) / 2;
                int radiusY = (getBounds().bottom - getBounds().top) / 2;
                canvas.drawRoundRect(new RectF(getBounds()), radiusX, radiusY, mPaint);
            } else {
                canvas.drawRoundRect(new RectF(getBounds()), mDrawableState.mRadius, mDrawableState.mRadius, mPaint);
            }


            // Restore original color filter.
            mPaint.setColorFilter(colorFilter);
        }
    }

    /**
     * @return
     */
    @ColorInt
    public int getColor() {
        return mDrawableState.mModifiedColor;
    }

    public void setColor(@ColorInt int color) {
        if (mDrawableState.mBaseColor != color || mDrawableState.mModifiedColor != color) {
            mDrawableState.mBaseColor = mDrawableState.mModifiedColor = color;
            invalidateSelf();
        }
    }

    public void setRadius(@IntRange(from = 0) int radius) {
        if (mDrawableState.mRadius != radius) {
            mDrawableState.mRadius = radius;
            invalidateSelf();
        }
    }

    @Override
    public int getAlpha() {
        return mDrawableState.mModifiedColor >>> 24;
    }

    @Override
    public void setAlpha(int alpha) {
        alpha += alpha >> 7;   // make it 0..256
        final int baseAlpha = mDrawableState.mBaseColor >>> 24;
        final int useAlpha = baseAlpha * alpha >> 8;
        final int useColor = (mDrawableState.mBaseColor << 8 >>> 8) | (useAlpha << 24);
        if (mDrawableState.mModifiedColor != useColor) {
            mDrawableState.mModifiedColor = useColor;
            invalidateSelf();
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public boolean isStateful() {
        return mDrawableState.mTint != null && mDrawableState.mTint.isStateful();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public boolean canApplyTheme() {
        return false;
    }

    @Override
    public void applyTheme(Resources.Theme t) {
        super.applyTheme(t);
    }

    @Override
    public ConstantState getConstantState() {
        return mDrawableState;
    }


    static class RoundColorDrawableState extends ConstantState {
        private ColorStateList mTint = null;
        private int mRadius = 0;
        private int mChangingConfigurations;
        private int mModifiedColor;
        private int mBaseColor;
        private PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;
        private boolean bOval = false;


        public RoundColorDrawableState() {
        }

        public RoundColorDrawableState(RoundColorDrawableState state) {
            this.mBaseColor = state.mBaseColor;
            this.mModifiedColor = state.mModifiedColor;
            this.mTint = state.mTint;
            this.mChangingConfigurations = state.mChangingConfigurations;
            this.mTintMode = state.mTintMode;
            this.mRadius = state.mRadius;
            this.bOval = state.bOval;
        }

        @NonNull
        @Override
        public Drawable newDrawable() {
            return new RoundedColorDrawable(this);
        }

        @Override
        public int getChangingConfigurations() {
            return mChangingConfigurations;
        }
    }

}
