package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 13/10/2020
 **/
public class TBShareCmdResult implements Serializable {

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * dataType : tklAnalyze
         * appCode : tbAppTklAnalyze
         * version : 1.0
         * retcode : 0000
         * hasNext : true
         * page : 0
         * data : {"createAppkey":"21646297","validDate":"1604498965318","isTaoFriend":"false","weakShow":"0","taopwdOwnerId":"708033015","title":"淘口令","templateId":"item","extendInfo":"{\"iconColor\":\"#999999\",\"genAppKey\":\"21646297\",\"price\":\"17.9-32.8\"}","myTaopwdToast":"","content":"这个#聚划算团购#宝贝不错:好巴食豆腐干麻辣五香辣条豆干小包装儿时辣味小零食小吃散装整箱(分享自@手机淘宝android客户端)","url":"https://a.m.taobao.com/i609351408166.htm?price=17.9-32.8&sourceType=item&sourceType=item&suid=3288539c-27de-497a-b5b7-f8590731d0f6&shareUniqueId=3998638433&ut_sk=1.XbCIzWtWMZMDABlikLGF1y26_21646297_1601906958504.Copy.1&un=d061c75f7d0ae74e3dd3af49e2902fce&share_crt_v=1&spm=a2159r.13376460.0.0&umpChannel=juhuasuan&u_channel=juhuasuan&sp_tk=dHRUMWNUT05uN1Y=&bxsign=tcd/7uV1d6+31Pt4ahbEpkfVsimbAAw5R11HeBuZv4WkdQAS3PgyIQfcDCEqq+eM53E4IXy7OXweVOnRuW9hyH586OZtk0Y+HHP6WCje8boNH4=","target":"copy","backflowTemplateId":"detail","picUrl":"http://gw.alicdn.com/bao/uploaded/i1/3796402366/O1CNA1RHPofp1TLfia02zO4_!!3796402366-0-psf.jpg","password":"ttT1cTONn7V","ownerName":"沙世Song","backgroundImg":"https://gw.alicdn.com/tfs/TB1nIKQCeH2gK0jSZJnXXaT1FXa-582-946.png","price":"17.90","ownerFace":"https://tblifecdn.taobao.com/default_full_male.png","bizId":"1","ownerFaceType":"1","popType":""}
         */

        private String dataType;
        private String appCode;
        private String version;
        private String retcode;
        private boolean hasNext;
        private int page;
        private TBShareCmdBean data;

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getAppCode() {
            return appCode;
        }

        public void setAppCode(String appCode) {
            this.appCode = appCode;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getRetcode() {
            return retcode;
        }

        public void setRetcode(String retcode) {
            this.retcode = retcode;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public TBShareCmdBean getData() {
            return data;
        }

        public void setData(TBShareCmdBean data) {
            this.data = data;
        }
    }


}
