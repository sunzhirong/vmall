package com.ysarch.vmall.common.imageloader;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;

/**
 * 定义
 * Created by fysong on 2019-11-15
 **/
@GlideExtension
public class CustomGlideExtent {


    private CustomGlideExtent() {

    }

    /**
     * 利用transform处理圆角图片, 七牛的图片建议采用{@link #roundedCorners(BaseRequestOptions, int)}
     *
     * @param options
     * @param radius
     * @return
     */
    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> roundedCornersTran(BaseRequestOptions<?> options, int radius) {
        options.transform(new RoundedCorners(radius));
        return options;
    }


    /**
     * 网络端裁切圆角图片，区别于{@link #roundedCornersTran(BaseRequestOptions, int)}
     *
     * @param options
     * @param radius
     * @return
     */
    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> roundedCorners(BaseRequestOptions<?> options, int radius) {
        options.getOptions().set(CustomGlideOptions.OPTION_RADIUS, radius);
        return options;
    }

    /**
     * 图片质量
     *
     * @param options
     * @param quality
     * @return
     */
    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> quality(BaseRequestOptions<?> options, @IntRange(from = 1, to = 100) int quality) {
        options.getOptions().set(CustomGlideOptions.OPTION_QUALITY, quality);
        return options;
    }


//    public static BaseRequestOptions<?> placeHolder
}
