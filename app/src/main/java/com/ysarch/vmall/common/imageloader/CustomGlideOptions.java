package com.ysarch.vmall.common.imageloader;

import com.bumptech.glide.load.Option;

/**
 * 图片特殊处理配置，如圆角
 * Created by fysong on 2019-11-20
 **/
public class CustomGlideOptions {
    /**
     * 图片圆角，水平、垂直方向大小一致，单位：像素
     */
    public static final Option<Integer> OPTION_RADIUS =
            Option.memory("com.market.bee.common.imageloader.CustomGlideOptions.radius", 0);
    /**
     * 图片质量，取值[1,100], 默认75
     */
    public static final Option<Integer> OPTION_QUALITY =
            Option.memory("com.market.bee.common.imageloader.CustomGlideOptions.quality", 75);
}
