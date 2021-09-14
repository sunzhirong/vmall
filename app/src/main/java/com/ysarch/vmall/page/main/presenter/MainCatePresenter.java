package com.ysarch.vmall.page.main.presenter;

import com.ysarch.uibase.base.BasePresenter;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.domain.services.GoodsLoader;
import com.ysarch.vmall.page.main.MainCateFragment;
import com.ysarch.vmall.page.main.shoye.MainShouYeFragment;

import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * 分类
 * Created by fysong on 24/08/2020
 **/
public class MainCatePresenter extends BasePresenter<MainCateFragment> {
    public void requestCateDatas() {
        GoodsLoader.getInstance().requestCateTree()
                .compose(showLoadingDialog())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<List<CateLevelBean>>(getV()) {
                    @Override
                    public void onSuccess(List<CateLevelBean> cateLevelBeans) {
                        AppContext.getsInstance().setCateLevelBeans(cateLevelBeans);
                        getV().onCateDatasSucc(cateLevelBeans);
                    }


                    @Override
                    protected void onFail(NetError error) {
                        super.onFail(error);
                        getV().onCateDatasFail();
                    }
                });
    }
// [{"children":[{"enName":"Shirt","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201224/%E8%A1%AC%E8%A1%AB.jpg","id":11,"keywords":"女士衬衫","khName":" អាវក្រណាត់","level":2,"name":"衬衫","navStatus":1,"operateType":0,"parentId":58,"productCount":100,"productUnit":"","selected":false,"showStatus":1,"sort":100},{"enName":"Dress","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201225/连衣裙.png","id":8,"keywords":"连衣裙","khName":"រ៉ូប សំពត់","level":2,"name":"连衣裙","navStatus":1,"operateType":0,"parentId":58,"productCount":100,"productUnit":"件","selected":false,"showStatus":1,"sort":80},{"enName":"Panties","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201225/（女士）内裤.jpg","id":78,"keywords":"女士内裤","khName":"ខោទ្រនាប់","level":2,"name":"内裤","navStatus":1,"operateType":0,"parentId":66,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":75},{"enName":"Bra","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201225/文胸.jpg","id":81,"keywords":"文胸","khName":"អាវទ្រនាប់","level":2,"name":"文胸","navStatus":1,"operateType":0,"parentId":67,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":80},{"enName":"skirt","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201225/半身裙.jpg","id":9,"keywords":"半身裙","khName":"ឈុតបុរាណ","level":2,"name":"半身裙","navStatus":1,"operateType":0,"parentId":58,"productCount":100,"productUnit":"件","selected":false,"showStatus":1,"sort":70},{"enName":"Casual trousers","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201225/（女士）休闲裤.jpg","id":79,"keywords":"女士休闲裤","khName":"ខោធម្មតា","level":2,"name":"休闲裤","navStatus":1,"operateType":0,"parentId":66,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":70},{"enName":"Bra set","icon":"","id":82,"keywords":"文胸套装","khName":"ឈុតអាវទ្រនាប់ ","level":2,"name":"文胸套装","navStatus":1,"operateType":0,"parentId":67,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":75},{"enName":"Cheongsam","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201225/旗袍.jpg","id":10,"keywords":"旗袍","khName":"ឈុតបុរាណ","level":2,"name":"旗袍","navStatus":1,"operateType":0,"parentId":58,"productCount":100,"productUnit":"件","selected":false,"showStatus":1,"sort":60}],"enName":"women","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20210118/hahhaha.jpg","id":1,"keywords":"女装","khName":"សម្លៀកបំពាក់នារី","level":0,"name":"女装","navStatus":1,"operateType":0,"parentId":0,"productCount":100,"productUnit":"件","selected":false,"showStatus":1,"sort":100},{"children":[{"enName":"manunderwear","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201224/timg.jpg","id":72,"keywords":"男士内裤","khName":"manunderwear","level":2,"name":"男士内裤","navStatus":1,"operateType":0,"parentId":69,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":99},{"enName":"leisiwx","icon":"","id":73,"keywords":"蕾丝文胸","khName":"leisiwx","level":2,"name":"蕾丝文胸","navStatus":1,"operateType":0,"parentId":68,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":0}],"enName":"underwear","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20210114/QQ截图20210114132719.png","id":5,"keywords":"内衣","khName":"អាវក្នុង","level":0,"name":"内衣","navStatus":1,"operateType":0,"parentId":0,"productCount":100,"productUnit":"件","selected":false,"showStatus":1,"sort":92},{"children":[{"enName":"玻璃奶瓶","icon":"","id":76,"keywords":"玻璃奶瓶","khName":"玻璃奶瓶","level":2,"name":"玻璃奶瓶","navStatus":1,"operateType":0,"parentId":74,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":2},{"enName":"塑料奶瓶","icon":"","id":75,"keywords":"塑料奶瓶","khName":"塑料奶瓶","level":2,"name":"塑料奶瓶","navStatus":1,"operateType":0,"parentId":74,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":1}],"enName":"Kid&amp;Toys","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20210116/手机配件.jpg","id":2,"keywords":"母婴玩具","khName":"ម្តាយនិងទារក","level":0,"name":"母婴","navStatus":1,"operateType":0,"parentId":0,"productCount":100,"productUnit":"件","selected":false,"showStatus":1,"sort":80},{"children":[],"enName":"Men","icon":"","id":3,"keywords":"男装","khName":"សម្លៀកបំពាក់បុរស","level":0,"name":"男装","navStatus":1,"operateType":0,"parentId":0,"productCount":100,"productUnit":"件","selected":false,"showStatus":1,"sort":70},{"children":[],"enName":"Accessories","icon":"","id":4,"keywords":"饰品","khName":"គ្រឿងលំអ","level":0,"name":"饰品","navStatus":1,"operateType":0,"parentId":0,"productCount":100,"productUnit":"件","selected":false,"showStatus":1,"sort":60},{"children":[{"enName":"大米","icon":"http://sabay.oss-cn-hongkong.aliyuncs.com/admin/20201223/timg.jpg","id":65,"keywords":"泰国大米","khName":"大米","level":2,"name":"大米","navStatus":1,"operateType":0,"parentId":64,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":1}],"enName":"Food","icon":"","id":63,"keywords":"女装","khName":"អាហារ","level":0,"name":"食品","navStatus":1,"operateType":0,"parentId":0,"productCount":0,"productUnit":"","selected":false,"showStatus":1,"sort":0}]

}
