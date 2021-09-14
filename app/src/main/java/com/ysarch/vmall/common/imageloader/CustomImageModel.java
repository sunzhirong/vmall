package com.ysarch.vmall.common.imageloader;

import androidx.annotation.IntDef;

import com.bumptech.glide.load.Options;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by fysong on 2019-11-19
 **/
public class CustomImageModel {
    public static final int SIZE_FIX_WIDTH = 0x01;
    public static final int SIZE_FIX_HEIGHT = 0x02;
    public static final int SIZE_FIX_CENTER = 0x4;
    public static final int SIZE_CENTER_CROP = 0x8;
    public static final int SIZE_ORIGINAL = 0x16;
    private String mUrl;
    private int mSizeType = SIZE_FIX_WIDTH;
    private boolean bSlimImage = true;
    private boolean bHasInsertParam = false;

    /**
     * 固定宽度，按比例缩放
     *
     * @param url
     * @return
     */
    public static CustomImageModel fixWidth(String url) {
        CustomImageModel model = new CustomImageModel();
        model.mSizeType = SIZE_FIX_WIDTH;
        model.mUrl = url;
        return model;
    }

    /**
     * 固定高度，按比例缩放
     *
     * @param url
     * @return
     */
    public static CustomImageModel fixHeight(String url) {
        CustomImageModel model = new CustomImageModel();
        model.mSizeType = SIZE_FIX_HEIGHT;
        model.mUrl = url;
        return model;
    }

    /**
     * 原始尺寸
     *
     * @param url
     * @return
     */
    public static CustomImageModel originalSize(String url) {
        CustomImageModel model = new CustomImageModel();
        model.mSizeType = SIZE_ORIGINAL;
        model.mUrl = url;
        return model;
    }

    /**
     * 按比例缩放图片，使图片最短边适应设定值
     *
     * @param url
     * @return
     */
    public static CustomImageModel centerCrop(String url) {
        CustomImageModel model = new CustomImageModel();
        model.mSizeType = SIZE_CENTER_CROP;
        model.mUrl = url;
        return model;
    }

    /**
     * 按比例缩放图片，使图片不超出边界
     *
     * @param url
     * @return
     */
    public static CustomImageModel fixCenter(String url) {
        CustomImageModel model = new CustomImageModel();
        model.mSizeType = SIZE_FIX_CENTER;
        model.mUrl = url;
        return model;
    }

    public String getUrl() {
        return mUrl;
    }

    /**
     * 是否压缩图片，图片画质基本没影响
     *
     * @param slimImage
     */
    public void setSlimImage(boolean slimImage) {
        this.bSlimImage = slimImage;
    }

    public String generateUrl(int width, int height, Options options) {
        return mUrl;
//        if (StringUtil.isTrimEmpty(mUrl)) {
//            return mUrl;
//        }
//
//        StringBuilder stringBuilder = new StringBuilder(mUrl);
//        if (!mUrl.contains("?")) {
//            stringBuilder.append("?");
//        } else if (mUrl.contains("vframe/png/offset")) {
//            stringBuilder.append("|");    //七牛视频拼接的封面图链接    --- 2020.02.25 fys
//        } else {
//            stringBuilder.append("&");
//        }
//
//        bHasInsertParam = false;
//        switch (mSizeType) {
//            case SIZE_FIX_WIDTH:
//                if (width > 0) {
//                    appendParams(stringBuilder, "imageMogr2/thumbnail/" + width + "x");
//                }
//                break;
//
//            case SIZE_FIX_HEIGHT:
//                if (height > 0) {
//                    appendParams(stringBuilder, "imageMogr2/thumbnail/x" + height);
//                }
//                break;
//
//            case SIZE_FIX_CENTER:
//                if (width > 0 && height > 0) {
//                    appendParams(stringBuilder, "imageView2/2/w/" + width + "/h/" + height);
//                }
//                break;
//            case SIZE_CENTER_CROP:
//                if (width > 0 && height > 0) {
//                    appendParams(stringBuilder, "imageView2/1/w/" + width + "/h/" + height);
////                    appendParams(stringBuilder, "imageView2/1/h/" + height);
//                }
//                break;
//        }
//
//
//        //图片圆角等配置
//        parseOptions(options, stringBuilder);
//
//        if (bSlimImage) {
//            appendParams(stringBuilder, "imageslim");
//        }
//
//        //图片处理失败则加载原图
//        appendParams(stringBuilder, "imageMogr2/ignore-error/1");
//        return stringBuilder.toString();
    }

    private void appendParams(StringBuilder stringBuilder, String param) {
        if (bHasInsertParam) {
            stringBuilder.append("|");
        }
        stringBuilder.append(param);
        bHasInsertParam = true;
    }

    /**
     * 相关图片处理options解析在此处处理
     * 圆角、
     *
     * @param options
     * @param stringBuilder
     */
    private void parseOptions(Options options, StringBuilder stringBuilder) {
        int radius = options.get(CustomGlideOptions.OPTION_RADIUS);
        if (radius > 0) {
            appendParams(stringBuilder, "roundPic/radius/" + radius);
        }
    }

    @IntDef({SIZE_FIX_WIDTH, SIZE_FIX_HEIGHT, SIZE_FIX_CENTER, SIZE_CENTER_CROP, SIZE_ORIGINAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SizeMode {
    }
}
