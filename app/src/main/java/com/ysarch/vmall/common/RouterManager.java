package com.ysarch.vmall.common;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.IntDef;

import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.orders.OrderDetailActivity;
import com.ysarch.vmall.page.webview.CommonWebActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.StringUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;
import java.util.Map;

/**
 * 协议跳转路由
 */
public class RouterManager {
    public static final int TYPE_NAV_UNKNOWN = 0;
    public static final int TYPE_NAV_GAME = 1;
    public static final int TYPE_NAV_ARTICLE = 2;
    public static final int TYPE_NAV_WEB = 3;

    private static final String SCHEME = "vmall";

    //游戏跳转
    private static final String GOTO_GAME_DETAIL = "goto.game";
    //h5帖子详情跳转
    private static final String GOTO_ARTICLE_DETAIL = "goto.article";
    //原生帖子详情跳转
    private static final String GOTO_ARTICLE_DETAIL_NATIVE = "goto.article.native";

    private static final String GOTO_GOODS_DETAIL = "goto.goodsdetail";
    private static final String GOTO_ORDER_DETAIL = "goto.orderdetail";


    public static String generateOrderUrl(long orderId) {
        return SCHEME + "://" + GOTO_ORDER_DETAIL + "?id=" + orderId;
    }

    public static void handleRouter(Activity activity, String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            NavHelper.startActivity(activity, CommonWebActivity.class, CommonWebActivity.getBundle(url));
            return;
        } else if (url.startsWith(SCHEME + "://")) {

            URI uri = null;
            try {
                uri = URI.create(url);
            } catch (Exception e) {

            }
            if (uri == null) {
                return;
            }
            if (!SCHEME.equals(uri.getScheme())) {
                return;
            }
            Map<String, String> stringMap = StringUtil.extractParams(url);

            String id = stringMap.get("id");
            switch (uri.getHost()) {
                case GOTO_GOODS_DETAIL:
                    if (!TextUtils.isEmpty(id)) {
                        int source = stringMap.containsKey("source") ? Integer.parseInt(stringMap.get("source"))
                                : Constants.TYPE_PLATFORM_TB;
                        NavHelper.startActivity(activity, GoodsDetailActivity.class,
                                GoodsDetailActivity.getBundle(Long.parseLong(id), source,"-1"));
                    }
                    break;
                case GOTO_ORDER_DETAIL:
                    if (!TextUtils.isEmpty(id)) {
                        NavHelper.startActivity(activity, OrderDetailActivity.class,
                                OrderDetailActivity.getBundle(Long.parseLong(id)));
                    }
                    break;
                default:
                    break;
            }
        } else {
            String[] strs = url.split(".");
            if (strs.length >= 2) {
                NavHelper.startActivity(activity, CommonWebActivity.class, CommonWebActivity.getBundle("http://" + url));
            }
        }
    }


    /**
     * 获取跳转链接的内容类型，参见{@link TYPE_NAV_CONTENT}
     *
     * @param url
     * @return
     */
    public static int checkNavContentType(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return TYPE_NAV_WEB;
            }

            URI uri = null;
            try {
                uri = URI.create(url);
                if (uri == null || !"bee".equals(uri.getScheme())) {
                    return TYPE_NAV_UNKNOWN;
                }
                switch (uri.getHost()) {
                    case GOTO_GAME_DETAIL:
                        return TYPE_NAV_GAME;
                    case GOTO_ARTICLE_DETAIL:
                        return TYPE_NAV_ARTICLE;
                    default:
                        return TYPE_NAV_UNKNOWN;
                }
            } catch (Exception e) {

            }
        }


        return TYPE_NAV_UNKNOWN;
    }

    @IntDef({TYPE_NAV_UNKNOWN, TYPE_NAV_GAME, TYPE_NAV_ARTICLE, TYPE_NAV_WEB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE_NAV_CONTENT {

    }

}
