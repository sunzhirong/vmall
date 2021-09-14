package com.ysarch.vmall.domain.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fysong on 13/10/2020
 **/
public class TBShareCmdBean implements Serializable {
    /**
     * createAppkey : 21646297
     * validDate : 1604498965318
     * isTaoFriend : false
     * weakShow : 0
     * taopwdOwnerId : 708033015
     * title : 淘口令
     * templateId : item
     * extendInfo : {"iconColor":"#999999","genAppKey":"21646297","price":"17.9-32.8"}
     * myTaopwdToast :
     * content : 这个#聚划算团购#宝贝不错:好巴食豆腐干麻辣五香辣条豆干小包装儿时辣味小零食小吃散装整箱(分享自@手机淘宝android客户端)
     * url : https://a.m.taobao.com/i609351408166.htm?price=17.9-32.8&sourceType=item&sourceType=item&suid=3288539c-27de-497a-b5b7-f8590731d0f6&shareUniqueId=3998638433&ut_sk=1.XbCIzWtWMZMDABlikLGF1y26_21646297_1601906958504.Copy.1&un=d061c75f7d0ae74e3dd3af49e2902fce&share_crt_v=1&spm=a2159r.13376460.0.0&umpChannel=juhuasuan&u_channel=juhuasuan&sp_tk=dHRUMWNUT05uN1Y=&bxsign=tcd/7uV1d6+31Pt4ahbEpkfVsimbAAw5R11HeBuZv4WkdQAS3PgyIQfcDCEqq+eM53E4IXy7OXweVOnRuW9hyH586OZtk0Y+HHP6WCje8boNH4=
     * target : copy
     * backflowTemplateId : detail
     * picUrl : http://gw.alicdn.com/bao/uploaded/i1/3796402366/O1CNA1RHPofp1TLfia02zO4_!!3796402366-0-psf.jpg
     * password : ttT1cTONn7V
     * ownerName : 沙世Song
     * backgroundImg : https://gw.alicdn.com/tfs/TB1nIKQCeH2gK0jSZJnXXaT1FXa-582-946.png
     * price : 17.90
     * ownerFace : https://tblifecdn.taobao.com/default_full_male.png
     * bizId : 1
     * ownerFaceType : 1
     * popType :
     */

    private String createAppkey;
    private String validDate;
    private String isTaoFriend;
    private String weakShow;
    private String taopwdOwnerId;
    private String title;
    private String templateId;
    private String extendInfo;
    private String myTaopwdToast;
    private String content;
    private String url;
    private String target;
    private String backflowTemplateId;
    private String picUrl;
    private String password;
    private String ownerName;
    private String backgroundImg;
    private String price;
    private String ownerFace;
    private String bizId;
    private String ownerFaceType;
    private String popType;

    @SerializedName("id")
    private long goodsId = -1;

    public long getGoodsId() {
        if(goodsId == -1){
            if(!TextUtils.isEmpty(url)){
                String reg = "com/i(\\d*).htm";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(url);
                if (matcher.find()){
                    String goodsIdsStr = matcher.group(1);
                    goodsId = Long.parseLong(goodsIdsStr);
                }
            }
        }
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCreateAppkey() {
        return createAppkey;
    }

    public void setCreateAppkey(String createAppkey) {
        this.createAppkey = createAppkey;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getIsTaoFriend() {
        return isTaoFriend;
    }

    public void setIsTaoFriend(String isTaoFriend) {
        this.isTaoFriend = isTaoFriend;
    }

    public String getWeakShow() {
        return weakShow;
    }

    public void setWeakShow(String weakShow) {
        this.weakShow = weakShow;
    }

    public String getTaopwdOwnerId() {
        return taopwdOwnerId;
    }

    public void setTaopwdOwnerId(String taopwdOwnerId) {
        this.taopwdOwnerId = taopwdOwnerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getMyTaopwdToast() {
        return myTaopwdToast;
    }

    public void setMyTaopwdToast(String myTaopwdToast) {
        this.myTaopwdToast = myTaopwdToast;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getBackflowTemplateId() {
        return backflowTemplateId;
    }

    public void setBackflowTemplateId(String backflowTemplateId) {
        this.backflowTemplateId = backflowTemplateId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOwnerFace() {
        return ownerFace;
    }

    public void setOwnerFace(String ownerFace) {
        this.ownerFace = ownerFace;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getOwnerFaceType() {
        return ownerFaceType;
    }

    public void setOwnerFaceType(String ownerFaceType) {
        this.ownerFaceType = ownerFaceType;
    }

    public String getPopType() {
        return popType;
    }

    public void setPopType(String popType) {
        this.popType = popType;
    }
}
