package cn.droidlover.xdroidmvp.domain;

import java.io.Serializable;

/**
 * 视频元数据
 * Created by fysong on 2020-02-24
 **/
public class VideoMetadata implements Serializable {
    private long mDuration;
    private int mWidth;
    private int mHeight;
    private String mPath;

    public VideoMetadata() {

    }

    public VideoMetadata(String path) {
        mPath = path;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }
}
