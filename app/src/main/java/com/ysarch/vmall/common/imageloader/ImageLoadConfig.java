package com.ysarch.vmall.common.imageloader;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.ysarch.vmall.common.imageloader.bitmapTrans.CircleCropBorder;
import com.ysarch.vmall.utils.ResUtils;
import com.ysarch.vmall.utils.SizeUtils;
import com.yslibrary.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * glide 相关配置
 * Created by fysong on 2019-11-20
 **/
public class ImageLoadConfig {

    public static final int EMPTY_COLOR = -1;
    private static int sLastIdx = 0;
    private static int[] sPresetColor = null;

    static {
        sPresetColor = new int[]{
                Color.parseColor("#ff4b4b5a"),
                Color.parseColor("#ff5d655f"),
                Color.parseColor("#ff5E3D50"),
                Color.parseColor("#ff566C73"),
                Color.parseColor("#ff826b48"),
                Color.parseColor("#ff64483d"),
                Color.parseColor("#ff134857"),
                Color.parseColor("#ff734440")
        };
    }

    private String mUrl;
    private int mSizeMode = CustomImageModel.SIZE_CENTER_CROP;
    private boolean bSlimImg = true;
    private int mRadius = 0;
    private List<Transformation<Bitmap>> mTransforms = new ArrayList<>();
    private boolean enableAnimation = true;
    private Drawable mPlaceholder;
    private int mPlaceHolderColor = EMPTY_COLOR;
    private boolean bOval;
    private int mTransitionDuration = 100;

    private ImageLoadConfig(String url) {
        mUrl = url;
    }

    public String getUrl(){
        return this.mUrl;
    }

    private static int getRandomColor() {
        int color = sPresetColor[sLastIdx];

        sLastIdx += 1;
        if (sLastIdx > sPresetColor.length - 1) {
            sLastIdx = 0;
        }

        return color;
    }

    public static ImageLoadConfig create(String url) {
        return new ImageLoadConfig(url);
    }

    public int getTransitionDuration() {
        return mTransitionDuration;
    }

    public boolean isEnableAnimation() {
        return enableAnimation;
    }

    public ImageLoadConfig setEnableAnimation(boolean enableAniation) {
        this.enableAnimation = enableAniation;
        return this;
    }

    public ImageLoadConfig enabeTransistion(int transitionDuration) {
        this.mTransitionDuration = transitionDuration;
        this.enableAnimation = true;
        return this;
    }

    public ImageLoadConfig fixWidth() {
        mSizeMode = CustomImageModel.SIZE_FIX_WIDTH;
        return this;
    }

    public ImageLoadConfig fixHeight() {
        mSizeMode = CustomImageModel.SIZE_FIX_HEIGHT;
        return this;
    }

    public ImageLoadConfig centerCrop() {
        mSizeMode = CustomImageModel.SIZE_CENTER_CROP;
        return this;
    }

    public ImageLoadConfig fitCenter() {
        mSizeMode = CustomImageModel.SIZE_FIX_CENTER;
        return this;
    }

    public ImageLoadConfig originalSize() {
        mSizeMode = CustomImageModel.SIZE_ORIGINAL;
        return this;
    }


    public ImageLoadConfig circle() {
        mTransforms.add(new CircleCrop());
        bOval = true;
        return this;
    }

    public ImageLoadConfig circleBorder(int stroke, int color) {
        mTransforms.add(new CircleCropBorder(stroke, color));
        bOval = true;
        return this;
    }


    /**
     * 圆角图片
     *
     * @param radiusDP
     * @return
     */
    public ImageLoadConfig roundedCornerDimen(int radiusDP) {
        //emmm...略纠结，这是不是要采用服务端切圆角，还是使用transform，
        // 在多个地方展示同一尺寸图片时，前者在缓存上消耗更多，目前问题不明显
        // 后续综合考虑下，性能和缓存上选择折中方案 --- fys
        mRadius = SizeUtils.dp2px(radiusDP);
        bOval = false;
        return this;
    }

    /**
     * 圆角图片
     *
     * @param radius
     * @return
     */
    public ImageLoadConfig roundedCorner(int radius) {
        //emmm...略纠结，这是不是要采用服务端切圆角，还是使用transform，
        // 在多个地方展示同一尺寸图片时，前者在缓存上消耗更多，目前问题不明显
        // 后续综合考虑下，性能和缓存上选择折中方案 --- fys
        mRadius = radius;
        bOval = false;
        return this;
    }


    public ImageLoadConfig placeholderColor(@ColorInt int color) {
        mPlaceHolderColor = color;
        mPlaceholder = null;
        return this;
    }


    public ImageLoadConfig randomPlaceHolder() {
        return placeholderColor(getRandomColor());
    }


    public ImageLoadConfig placeHolder(@DrawableRes int drawableResId) {
        mPlaceholder = ResUtils.getDrawable(drawableResId);
        mPlaceHolderColor = EMPTY_COLOR;
        return this;
    }

    public ImageLoadConfig placeHolder(Drawable drawable) {
        mPlaceholder = drawable;
        mPlaceHolderColor = EMPTY_COLOR;
        return this;
    }

    /**
     * 压缩图片
     *
     * @param slimImg
     * @return
     */
    public ImageLoadConfig setSlimImg(boolean slimImg) {
        this.bSlimImg = slimImg;
        return this;
    }

    public int getPlaceHolderColor() {
        return mPlaceHolderColor;
    }

    public boolean isOval() {
        return bOval;
    }

    public int getRadius() {
        return mRadius;
    }


    public Drawable getPlaceholder() {
        return mPlaceholder;
    }

    public Transformation<Bitmap>[] getTransforms() {
        Transformation<Bitmap>[] arr = new Transformation[CollectionUtils.size(mTransforms)];
        for (int i = 0; i < mTransforms.size(); i++) {
            arr[i] = mTransforms.get(i);
        }
        return arr;
    }


    public CustomImageModel getImageModel() {
        CustomImageModel model;
        switch (mSizeMode) {
            case CustomImageModel.SIZE_FIX_WIDTH:
                model = CustomImageModel.fixWidth(mUrl);
                break;
            case CustomImageModel.SIZE_FIX_HEIGHT:
                model = CustomImageModel.fixHeight(mUrl);
                break;
            case CustomImageModel.SIZE_FIX_CENTER:
                model = CustomImageModel.fixCenter(mUrl);
                break;
            case CustomImageModel.SIZE_ORIGINAL:
                model = CustomImageModel.originalSize(mUrl);
                break;
            case CustomImageModel.SIZE_CENTER_CROP:
            default:
                model = CustomImageModel.centerCrop(mUrl);
                break;
        }

        model.setSlimImage(bSlimImg);

        return model;
    }
}
