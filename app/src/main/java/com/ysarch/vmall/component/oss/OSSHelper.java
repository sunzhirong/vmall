package com.ysarch.vmall.component.oss;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.OssBean;
import com.ysarch.vmall.utils.StringUtil;
import com.yslibrary.utils.DIDMD5Util;

/**
 * Created by fysong on 26/10/2020
 **/
public class OSSHelper {

    private static final String ENDPOINT = "oss-cn-hongkong.aliyuncs.com";
    private static final String BUCKET_NAME = "sabay";
    public boolean isInited = false;
    private OSS oss;

    private OSSHelper() {
    }

    public static OSSHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initOss(OssBean ossBean) {
        if (oss != null) return;
        isInited = true;
        // 在移动端建议使用STS的方式初始化OSSClient。
        //String accessKeyId, String secretKeyId, String securityToken
        OSSCredentialProvider credentialProvider
                = new OSSStsTokenCredentialProvider(ossBean.getAccessKeyId(),
                ossBean.getAccessKeySecret(), ossBean.getSecurityToken());


        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

        oss = new OSSClient(AppContext.getsInstance().getAppliactionContext(), ENDPOINT, credentialProvider, conf);

    }


    public void uploadImage(String imagePath, Callback callback) {
        final String img = "wallet/" + DIDMD5Util.MD5(imagePath) + "." + StringUtil.getExtensionName(imagePath);
        PutObjectRequest put = new PutObjectRequest(BUCKET_NAME,
                img, imagePath);

        put.setProgressCallback((request, currentSize, totalSize) ->
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize));

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                if (callback != null) {
                    callback.onUploadSucc("https://sabay." + ENDPOINT + "/" + img);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                if (callback != null) {
                    callback.onUploadFail();
                }
            }
        });
    }

    public interface Callback {
        void onUploadSucc(String url);

        void onUploadFail();
    }

    static class SingletonHolder {
        static OSSHelper INSTANCE = new OSSHelper();
    }
}
