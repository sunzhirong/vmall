package com.ysarch.vmall.common.imageloader;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * Created by fysong on 2019-11-20
 **/
public class CustomGlideUrlLoader extends BaseGlideUrlLoader<CustomImageModel> {

    protected CustomGlideUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        this(concreteLoader, null);
    }

    protected CustomGlideUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader, @Nullable ModelCache<CustomImageModel, GlideUrl> modelCache) {
        super(concreteLoader, modelCache);
    }

    @Override
    protected String getUrl(CustomImageModel customImageModel, int width, int height, Options options) {
        String url = customImageModel.generateUrl(width, height, options);
        Log.e("imgurl", url+"");
        return url;
    }

    @Override
    public boolean handles(@NonNull CustomImageModel customImageModel) {
        return true;
    }


    public static final class Factory implements ModelLoaderFactory<CustomImageModel, InputStream> {

        Factory() {

        }

        @NonNull
        @Override
        public ModelLoader<CustomImageModel, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new CustomGlideUrlLoader(multiFactory.build(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {

        }
    }
}
