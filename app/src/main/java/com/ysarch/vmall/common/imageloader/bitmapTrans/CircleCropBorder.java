package com.ysarch.vmall.common.imageloader.bitmapTrans;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class CircleCropBorder extends BitmapTransformation {

    // The version of this transformation, incremented to correct an error in a previous version.
    // See #455.
    private static final int VERSION = 1;
    private static final String ID = "com.chufang.yiyoushuo.component.imageload.CircleCropBorder." + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private int mStrokeWidth;
    private int mBorderColor;

    public CircleCropBorder(int strokeWidth, int borderColor) {
        this.mStrokeWidth = strokeWidth;
        this.mBorderColor = borderColor;
    }


    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int diameter = Math.min(outWidth, outHeight);
        Bitmap circle = pool.get(diameter, diameter, Bitmap.Config.ARGB_8888);
        circle.setHasAlpha(true);

        Canvas canvas = new Canvas(circle);

        float r = diameter * 0.5F;
        final Rect rect = new Rect(0, 0, diameter, diameter);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(toTransform, rect, rect, paint);

        paint.setColor(mBorderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);

        canvas.drawCircle(r, r, r - mStrokeWidth * 0.5F, paint);
        return circle;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CircleCropBorder
                && this.mStrokeWidth == ((CircleCropBorder) o).mStrokeWidth
                && this.mBorderColor == ((CircleCropBorder) o).mBorderColor;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode(), Util.hashCode(mBorderColor, mStrokeWidth));
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
        byte[] radiusData = ByteBuffer.allocate(8).putInt(mStrokeWidth).putInt(mBorderColor).array();
        messageDigest.update(radiusData);
    }
}