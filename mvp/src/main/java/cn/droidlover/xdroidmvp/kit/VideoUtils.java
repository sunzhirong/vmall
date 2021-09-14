package cn.droidlover.xdroidmvp.kit;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.io.File;

import cn.droidlover.xdroidmvp.domain.VideoMetadata;

/**
 * 视频文件相关处理
 * Created by fysong on 2020-02-24
 **/
public class VideoUtils {


    /**
     * 解析视频信息
     * @param context
     * @param path
     * @return
     */
    public static VideoMetadata analyzeVideoProperties(Context context, String path) {
        File file = new File(path);
        if (!file.exists() && file.isDirectory()) {
            return null;
        }

        VideoMetadata videoMetadata = new VideoMetadata(file.getAbsolutePath());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        try {
            mmr.setDataSource(file.getAbsolutePath());
            String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高

            videoMetadata.setDuration(Long.parseLong(duration));
            videoMetadata.setWidth(Integer.parseInt(width));
            videoMetadata.setWidth(Integer.parseInt(height));

        } catch (Exception ex) {
            Log.e("TAG", "MediaMetadataRetriever exception " + ex);
        } finally {
            mmr.release();
            return videoMetadata;
        }
    }
}
