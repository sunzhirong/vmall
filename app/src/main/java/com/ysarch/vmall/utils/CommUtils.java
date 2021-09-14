package com.ysarch.vmall.utils;//package com.ysarch.calendar.utils;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.meituan.android.walle.ChannelInfo;
//import com.meituan.android.walle.WalleChannelReader;
//
//import java.util.Map;
//
///**
// * Author: Ly
// * Data：2018/3/30-17:06
// * Description:
// */
//public class CommUtils {
//
//    /**
//     * 异常的渠道信息或者
//     *
//     * @param context
//     * @return
//     */
//    public static String getChannelId(Context context) {
//
//        ChannelInfo info = WalleChannelReader.getChannelInfo(context);
//        if (info != null) {
//            Map<String, String> map = info.getExtraInfo();
//            String channelId = map.get("channelid");
//            if (TextUtils.isEmpty(channelId) || channelId.equals("0"))
//                channelId = "10000";
//            return channelId;
//        }
//
//        return "10000";
//    }
//
//}
