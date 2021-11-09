package com.ysarch.vmall.common.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;
import com.ysarch.vmall.utils.GlideUtils;
import com.yslibrary.utils.ArrayUtils;


/**
 * 项目加载图片建议使用入口
 * Created by fysong on 2019-11-20
 **/
public class BeeGlide {


    private GlideRequests mGlideRequests;

    private Context context;

    public Context getContext(){
        return context;
    }


    private BeeGlide(Context context) {
        try {
            mGlideRequests = GlideApp.with(context);
            this.context = context.getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BeeGlide(Fragment fragment) {
        try {
            mGlideRequests = GlideApp.with(fragment);
            this.context = fragment.getActivity().getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BeeGlide(android.app.Fragment fragment) {
        try {
            mGlideRequests = GlideApp.with(fragment);
            this.context = fragment.getActivity().getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BeeGlide(Activity activity) {
        try {
            mGlideRequests = GlideApp.with(activity);
            this.context = activity.getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BeeGlide(FragmentActivity activity) {
        try {
            mGlideRequests = GlideApp.with(activity);
            this.context = activity.getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BeeGlide(View view) {
        try {
            mGlideRequests = GlideApp.with(view);
            this.context = view.getContext().getApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //     ------------------------------------------------------------
    //     很多构建方式似乎可以统一，但没全细看源码实现，暂时按照GlideApp的方式
    //     全部列出来吧，后续再确认是否可以部分统一、合并   ----fys 2019.11.20
    //     ------------------------------------------------------------

    public static BeeGlide with(Context context) {
        return new BeeGlide(context);
    }

    public static BeeGlide with(Fragment fragment) {
        return new BeeGlide(fragment);
    }

    public static BeeGlide with(Activity activity) {
        return new BeeGlide(activity);
    }

    public static BeeGlide with(android.app.Fragment fragment) {
        return new BeeGlide(fragment);
    }

    public static BeeGlide with(FragmentActivity activity) {
        return new BeeGlide(activity);
    }

    public static BeeGlide with(View view) {
        return new BeeGlide(view);
    }


    public GlideRequest load(ImageLoadConfig loadConfig) {
        RequestOptions options = new RequestOptions();

        Transformation<Bitmap>[] transformations = loadConfig.getTransforms();
        if (ArrayUtils.isNotEmpty(transformations)) {
            options.transform(transformations);
        }


        if (loadConfig.getPlaceHolderColor() != ImageLoadConfig.EMPTY_COLOR) {
            if (loadConfig.isOval()) {
                options.placeholder(new RoundedColorDrawable(loadConfig.getPlaceHolderColor(), true));
            } else {
                options.placeholder(new RoundedColorDrawable(loadConfig.getPlaceHolderColor(), loadConfig.getRadius()));
            }
        } else if (loadConfig.getPlaceholder() != null) {
            options.placeholder(loadConfig.getPlaceholder());
//            glideRequest.thumbnail(mGlideRequests.load(loadConfig.getPlaceholder())
//                    .apply(new RequestOptions().transform(new RoundedCorners(20))));
        }


        CustomImageModel model = loadConfig.getImageModel();
        GlideRequest<Drawable> glideRequest = mGlideRequests
                .load(model)
                .apply(options);

        if (loadConfig.getRadius() > 0) {
            //圆角还是交给transform来处理，减少缓存文件数量   --- fysong 2019.12.09
            if (false && checkIfQNiuCDN(model.getUrl())) {
                glideRequest.roundedCorners(loadConfig.getRadius());
            } else {
                glideRequest.roundedCornersTran(loadConfig.getRadius());
            }
        }

//        if (loadConfig.isEnableAnimation()) {
//            DrawableCrossFadeFactory factory =
//                    new DrawableCrossFadeFactory.Builder(100).setCrossFadeEnabled(false).build();
//            glideRequest.transition(withCrossFade(factory));
//        }

        return glideRequest;
    }

    public void load(ImageLoadConfig loadConfig, ImageView imageView) {
        GlideRequest glideRequest = load(loadConfig);
        glideRequest.into(imageView);

//        if(loadConfig.getPlaceholder()!=null) {
//            GlideUtils.loadImageView(context, loadConfig.getUrl(), imageView,loadConfig.getPlaceholder());
//        }else {
//            GlideUtils.loadImageView(context, loadConfig.getUrl(), imageView);
//        }
    }

    /**
     * <pre>
     *     项目似乎存在不走cdn的图片资源，这时候需要特殊处理圆角等相关裁切缩放效果
     *     判断链接是否走的七牛服务器
     *     仅仅简单判断域名，以后如果改了链接域名，切记及时调整
     * </pre>
     *
     * @return
     */
    private boolean checkIfQNiuCDN(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.contains("img.qumeng666.com");
    }
}
