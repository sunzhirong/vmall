package com.ysarch.vmall.helper;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ysarch.vmall.common.context.CustomActivityManager;
import com.ysarch.vmall.component.dialog.ClipSearchDialog;
import com.ysarch.vmall.component.dialog.TBShareCmdDialogNew;
import com.ysarch.vmall.domain.bean.TBShareCmdResult;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.search.SearchActivity;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.SystemUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 粘贴板内容处理
 * Created by fysong on 2020/10/15
 **/
public class ClipContentHelper {
    private Handler handler;
    private String mContent;

    public ClipContentHelper() {
        handler = new Handler();
    }

    public void checkClipBoard(Activity activity) {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (!activity.isFinishing() && !activity.isDestroyed()) {
                String content = SystemUtil.getCopy(CustomActivityManager.getInstance().getCurrentActivity());

                if (!TextUtils.isEmpty(content)) {
                    if (content.contains("https")) {
                        SystemUtil.clearClipboard(CustomActivityManager.getInstance().getCurrentActivity());
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("content", content);
                        analyzeTBShareCmd(jsonObject.toString());
                        return;
                    }
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("content", content);
                    analyzeTBShareCmd(jsonObject.toString());
                    showSearchContentDialog(content);
                }
//                }
            }
        }, 1500);
    }

    private void analyzeTBShareCmd(String content) {
        GoodsLoader.getInstance().analyzeTBShareCmd(content)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.errorBody() != null) {
//                            showTs(activity, response.errorBody().toString());
                            return;
                        }
                        if (response.body() != null) {
                            try {
                                String content = response.body().string();
                                Log.e("analyzeTBShareCmd", content);
                                if (TextUtils.isEmpty(content))
                                    return;

                                Gson gson = new Gson();
                                TBShareCmdResult tbShareCmdResult =
                                        gson.fromJson(content, TBShareCmdResult.class);

                                if (tbShareCmdResult.getCode() == 200 && tbShareCmdResult.getData() != null
                                        && tbShareCmdResult.getData().getData() != null) {
                                    TBShareCmdDialogNew dialog = new TBShareCmdDialogNew.Builder(CustomActivityManager.getInstance().getCurrentActivity())
                                            .setTBShareCmdBean(tbShareCmdResult.getData().getData())
                                            .setCallback(new TBShareCmdDialogNew.Callback() {
                                                @Override
                                                public void onNavGoodsDetail(long goodsId) {
                                                    NavHelper.startActivity(CustomActivityManager.getInstance().getCurrentActivity(), GoodsDetailActivity.class,
                                                            GoodsDetailActivity.getBundle(goodsId));
                                                }
                                            })
                                            .build();
                                    dialog.show();
                                } else {
//                                    showSearchContentDialog(content);
                                }

//                                if (tbShareCmdResult.getCode() == 200 && tbShareCmdResult.getData() != null &&
//                                        !TextUtils.isEmpty(tbShareCmdResult.getData().getData())) {
//
                            } catch (IOException e) {
                                e.printStackTrace();
                                showSearchContentDialog(content);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

    private void showSearchContentDialog(String content) {
//        new SimpleDialogWithSingleBtn.Builder(CustomActivityManager.getInstance().getCurrentActivity())
//                .setContent(String.format(
//                        CustomActivityManager.getInstance().getCurrentActivity()
//                                .getString(R.string.format_search_clipboard_content), content))
//                .setCancelable(true)
//                .setmConfirmLabel(CustomActivityManager.getInstance().getCurrentActivity().getString(R.string.label_confirm))
//                .setAutoDismissWhileClick(true)
//                .setOnSubmitListener(() -> {
//                    SystemUtil.clearClipboard(CustomActivityManager.getInstance().getCurrentActivity());
//                    if (CustomActivityManager.getInstance().getCurrentActivity() instanceof SearchActivity) {
//                        ((SearchActivity) CustomActivityManager.getInstance().getCurrentActivity()).setKeyword(content);
//                    } else {
//                        NavHelper.startActivity(CustomActivityManager
//                                .getInstance().getCurrentActivity(), SearchActivity.class,
//                                SearchActivity.getBundle(content, false));
//                    }
//                })
//                .build()
//                .show();

        new ClipSearchDialog.Builder(CustomActivityManager.getInstance().getCurrentActivity())
                .setContent(content)
                .setCallback(new ClipSearchDialog.Callback() {
                    @Override
                    public void onSearch() {
                        SystemUtil.clearClipboard(CustomActivityManager.getInstance().getCurrentActivity());
                        if (CustomActivityManager.getInstance().getCurrentActivity() instanceof SearchActivity) {
                            ((SearchActivity) CustomActivityManager.getInstance().getCurrentActivity()).setKeyword(content);
                        } else {
                            NavHelper.startActivity(CustomActivityManager
                                            .getInstance().getCurrentActivity(), SearchActivity.class,
                                    SearchActivity.getBundle(content, false));
                        }
                    }
                })
                .build()
                .show();
    }


    private void showTs(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
