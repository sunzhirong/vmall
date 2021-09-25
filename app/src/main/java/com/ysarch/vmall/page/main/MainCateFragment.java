package com.ysarch.vmall.page.main;

import android.os.Bundle;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.uibase.recyclerview.scroller.LinearTopSnapSmoothScroller;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.adapter.CateMenusAdapter;
import com.ysarch.vmall.common.adapter.CatesLevel2Adapter;
import com.ysarch.vmall.common.context.AppContext;
import com.ysarch.vmall.domain.bean.CateLevelBean;
import com.ysarch.vmall.page.main.presenter.MainCatePresenter;
import com.ysarch.vmall.page.search.SearchActivity;
import com.ysarch.vmall.utils.NavHelper;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分类
 * Created by fysong on 24/08/2020
 **/
public class MainCateFragment extends BaseFragment<MainCatePresenter> {
    @BindView(R.id.rcy_menu_cate_main)
    RecyclerView mRcyLeft;
    @BindView(R.id.rcy_sub_cate_main)
    RecyclerView mRcyRight;
    private int mRcyHeight;

    private int selectPos = 0;

    private CateMenusAdapter mCateMenusAdapter;
    private CatesLevel2Adapter mCatesLevel2Adapter;
    private LinearTopSnapSmoothScroller mSmoothScroller;
    private LinearLayoutManager mLayoutManager;
    private boolean bLeftInvokeScrolling;

    @Override
    public void initData(Bundle savedInstanceState) {


        getPresenter().requestCateDatas();
//        if (CollectionUtils.isNotEmpty(AppContext.getsInstance().getCateLevelBeans())) {
//            resetCatesData();
//        } else {
//            getPresenter().requestCateDatas();
//        }
////
//        String da = "{\"code\":200,\"message\":\"操作成功\",\"data\":[{\"id\":1,\"parentId\":0,\"name\":\"服装\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"服装\",\"children\":[{\"id\":7,\"parentId\":1,\"name\":\"外套\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1," +
//                "\"showStatus\":1,\"sort\":0,\"icon\":\"\",\"keywords\":\"外套\",\"children\":[{\"id\":7,\"parentId\":1,\"name\":\"外套\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"\",\"keywords\":\"外套\",\"children\":[]},{\"id\":8,\"parentId\":1,\"name\":\"T恤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png\",\"keywords\":\"T恤\"," +
//                "\"children\":[]},{\"id\":9,\"parentId\":1,\"name\":\"休闲裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"休闲裤\",\"children\":[]},{\"id\":10,\"parentId\":1,\"name\":\"牛仔裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"牛仔裤\",\"children\":[]},{\"id\":11,\"parentId\":1,\"name\":\"衬衫\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0," +
//                "\"keywords\":\"衬衫\",\"children\":[]},{\"id\":29,\"parentId\":1,\"name\":\"男鞋\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]},{\"id\":8,\"parentId\":1,\"name\":\"T恤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png\",\"keywords\":\"T恤\",\"children\":[{\"id\":7,\"parentId\":1,\"name\":\"外套\"," +
//                "\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"\",\"keywords\":\"外套\",\"children\":[]},{\"id\":8,\"parentId\":1,\"name\":\"T恤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png\",\"keywords\":\"T恤\",\"children\":[]},{\"id\":9,\"parentId\":1,\"name\":\"休闲裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1," +
//                "\"showStatus\":1,\"sort\":0,\"keywords\":\"休闲裤\",\"children\":[]},{\"id\":10,\"parentId\":1,\"name\":\"牛仔裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"牛仔裤\",\"children\":[]},{\"id\":11,\"parentId\":1,\"name\":\"衬衫\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"衬衫\",\"children\":[]},{\"id\":29,\"parentId\":1,\"name\":\"男鞋\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0," +
//                "\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]},{\"id\":9,\"parentId\":1,\"name\":\"休闲裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"休闲裤\",\"children\":[{\"id\":7,\"parentId\":1,\"name\":\"外套\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"\",\"keywords\":\"外套\",\"children\":[]},{\"id\":8,\"parentId\":1,\"name\":\"T恤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\"," +
//                "\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png\",\"keywords\":\"T恤\",\"children\":[]},{\"id\":9,\"parentId\":1,\"name\":\"休闲裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"休闲裤\",\"children\":[]},{\"id\":10,\"parentId\":1,\"name\":\"牛仔裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"牛仔裤\",\"children\":[]}," +
//                "{\"id\":11,\"parentId\":1,\"name\":\"衬衫\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"衬衫\",\"children\":[]},{\"id\":29,\"parentId\":1,\"name\":\"男鞋\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]},{\"id\":10,\"parentId\":1,\"name\":\"牛仔裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"牛仔裤\",\"children\":" +
//                "[{\"id\":7,\"parentId\":1,\"name\":\"外套\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"\",\"keywords\":\"外套\",\"children\":[]},{\"id\":8,\"parentId\":1,\"name\":\"T恤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png\",\"keywords\":\"T恤\",\"children\":[]},{\"id\":9,\"parentId\":1,\"name\":\"休闲裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"休闲裤\"," +
//                "\"children\":[]},{\"id\":10,\"parentId\":1,\"name\":\"牛仔裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"牛仔裤\",\"children\":[]},{\"id\":11,\"parentId\":1,\"name\":\"衬衫\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"衬衫\",\"children\":[]},{\"id\":29,\"parentId\":1,\"name\":\"男鞋\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]},{\"id\":29,\"parentId\":1,\"name\":\"男鞋\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":7,\"parentId\":1,\"name\":\"外套\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"\"," +
//                "\"keywords\":\"外套\",\"children\":[]},{\"id\":8,\"parentId\":1,\"name\":\"T恤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png\",\"keywords\":\"T恤\",\"children\":[]},{\"id\":9,\"parentId\":1,\"name\":\"休闲裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"休闲裤\",\"children\":[]},{\"id\":10,\"parentId\":1,\"name\":\"牛仔裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"牛仔裤\",\"children\":[]},{\"id\":11,\"parentId\":1,\"name\":\"衬衫\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1," +
//                "\"sort\":0,\"keywords\":\"衬衫\",\"children\":[]},{\"id\":29,\"parentId\":1,\"name\":\"男鞋\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":2,\"parentId\":0,\"name\":\"手机数码\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"手机数码\",\"children\":[{\"id\":19,\"parentId\":2,\"name\":\"手机通讯\",\"level\":1,\"productCount\":0,\"productUnit\":\"件\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"手机通讯\",\"children\":[{\"id\":7,\"parentId\":1,\"name\":\"外套\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"\",\"keywords\":\"外套\",\"children\":[]},{\"id\":8,\"parentId\":1,\"name\":\"T恤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png\",\"keywords\":\"T恤\",\"children\":[]},{\"id\":9,\"parentId\":1,\"name\":\"休闲裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"休闲裤\",\"children\":[]},{\"id\":10,\"parentId\":1,\"name\":\"牛仔裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"牛仔裤\",\"children\":[]},{\"id\":11,\"parentId\":1,\"name\":\"衬衫\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"衬衫\",\"children\":[]},{\"id\":29,\"parentId\":1,\"name\":\"男鞋\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]},{\"id\":30,\"parentId\":2,\"name\":\"手机配件\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"手机配件\",\"children\":[{\"id\":7,\"parentId\":1,\"name\":\"外套\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"\",\"keywords\":\"外套\",\"children\":[]},{\"id\":8,\"parentId\":1,\"name\":\"T恤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"icon\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180522/web.png\",\"keywords\":\"T恤\",\"children\":[]},{\"id\":9,\"parentId\":1,\"name\":\"休闲裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"休闲裤\",\"children\":[]},{\"id\":10,\"parentId\":1,\"name\":\"牛仔裤\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"牛仔裤\",\"children\":[]},{\"id\":11,\"parentId\":1,\"name\":\"衬衫\",\"level\":1,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":0,\"keywords\":\"衬衫\",\"children\":[]},{\"id\":29,\"parentId\":1,\"name\":\"男鞋\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":3,\"parentId\":0,\"name\":\"家用电器\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":31,\"parentId\":0,\"name\":\"汽车\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":3,\"parentId\":0,\"name\":\"家用电器\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":33,\"parentId\":0,\"name\":\"保健\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":4,\"parentId\":0,\"name\":\"药品\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":5,\"parentId\":0,\"name\":\"化妆品\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":5,\"parentId\":0,\"name\":\"宠物\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":6,\"parentId\":0,\"name\":\"网络课程\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":7,\"parentId\":0,\"name\":\"电子\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":8,\"parentId\":0,\"name\":\"儿童\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":9,\"parentId\":0,\"name\":\"女人装\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]},{\"id\":10,\"parentId\":0,\"name\":\"男人装\",\"level\":0,\"productCount\":100,\"productUnit\":\"件\",\"navStatus\":1,\"showStatus\":1,\"sort\":1,\"keywords\":\"家用电器\",\"children\":[{\"id\":35,\"parentId\":3,\"name\":\"电视\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[{\"id\":36,\"parentId\":3,\"name\":\"空调\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":37,\"parentId\":3,\"name\":\"洗衣机\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":38,\"parentId\":3,\"name\":\"冰箱\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":39,\"parentId\":3,\"name\":\"厨卫大电\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":40,\"parentId\":3,\"name\":\"厨房小电\",\"level\":1," +
//                "\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":41,\"parentId\":3,\"name\":\"生活电器\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]},{\"id\":42,\"parentId\":3,\"name\":\"个护健康\",\"level\":1,\"productCount\":0,\"productUnit\":\"\",\"navStatus\":0,\"showStatus\":0,\"sort\":0,\"icon\":\"\",\"keywords\":\"\",\"children\":[]}]}]}]}";
//        CateTestBean simpleResponse = new Gson().fromJson(da, CateTestBean.class);
//
    }

    public LinearTopSnapSmoothScroller getSmoothScroller() {
        if (mSmoothScroller == null) {
            mSmoothScroller = new LinearTopSnapSmoothScroller(getContext());
        }
        return mSmoothScroller;
    }

    private void initAdapter() {
        if (mCateMenusAdapter == null) {
            mCateMenusAdapter = new CateMenusAdapter(getContext());
            mCateMenusAdapter.setOnItemClickListener((position, data) -> {
                bLeftInvokeScrolling = true;
                mCateMenusAdapter.setSelPosition(position);
                getSmoothScroller().setTargetPosition(0);
                mRcyRight.getLayoutManager().startSmoothScroll(mSmoothScroller);
                selectPos = position;
                mCatesLevel2Adapter.refreshData(AppContext.getsInstance().getCateLevelBeans(),selectPos);
            });

            mRcyLeft.setLayoutManager(new LinearLayoutManager(getContext()));
            mRcyLeft.setAdapter(mCateMenusAdapter);


            mCatesLevel2Adapter = new CatesLevel2Adapter(getContext());
            mCatesLevel2Adapter.setClickListener((position, data) -> {
                if(data instanceof CateLevelBean) {
                    CateLevelBean bean = (CateLevelBean) data;
                    NavHelper.startActivity(getActivity(), SearchActivity.class,
                            SearchActivity.getBundle(bean.getKeywords(), true));
                    return;
                }
//                if(data instanceof String){
//                    NavHelper.startActivity(getActivity(), SearchActivity.class,
//                            SearchActivity.getBundle((String)data, true));
//                }
            });

            mLayoutManager = new LinearLayoutManager(getContext());
            mRcyRight.setLayoutManager(mLayoutManager);
//            int gap = SizeUtils.dp2px(20);
//            mRcyRight.addItemDecoration(new LinearVerDecoration(0, gap, gap, gap));
            mRcyRight.setAdapter(mCatesLevel2Adapter);
//            mRcyRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//
//                    if (newState != SCROLL_STATE_IDLE) {
//                        return;
//                    }
//                    bLeftInvokeScrolling = false;
//
//                }
//
//
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    if (bLeftInvokeScrolling)
//                        return;
//                    int firstVisiblePosition = mLayoutManager.findFirstVisibleItemPosition();
//                    int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
//
////                    View lastView= mRcyRight.getChildAt(lastVisiblePosition);
//                    if (dy > 0) {
//                        if (!mRcyRight.canScrollVertically(1)) {
//                            rcyLeftScrollToPosition(mCateMenusAdapter.getItemCount() - 1);
//                            return;
//                        }
//                    }
//                    if (mRcyHeight == 0) {
//                        mRcyHeight = mRcyRight.getHeight();
//                    }
//
//                    int childCount = lastVisiblePosition - firstVisiblePosition + 1;
//                    if (childCount == 1)
//                        return;
//
//                    for (int i = 0; i < childCount; i++) {
//                        View view = mRcyRight.getChildAt(i);
////                        mRcyRight.getChildAt(i).getLocalVisibleRect(rect);
//
//                        int childHeight = view.getHeight();
//                        float childYPos = view.getY();
//
//                        float visibleHeight = childYPos + childHeight;
//                        if (childYPos == 0) {
//
////                            Log.e("MainCateFragment", "setSelPosition 1");
//                            rcyLeftScrollToPosition(mLayoutManager.getPosition(view));
//                            break;
//                        } else if (childYPos < 0) {
//                            if (visibleHeight >= mRcyHeight * 1.0f / 2) {
////                                Log.e("MainCateFragment", "setSelPosition 2");
//                                rcyLeftScrollToPosition(mLayoutManager.getPosition(view));
//                                break;
//                            }
//                        } else if (childYPos <= mRcyHeight * 1.0f / 2) {
//                            rcyLeftScrollToPosition(mLayoutManager.getPosition(view));
//                            break;
//                        } else if (dy > 0 && i == childCount - 1) {
//                            rcyLeftScrollToPosition(mLayoutManager.getPosition(view));
//                            break;
//                        }
//                    }
//                }
//            });
        }
    }


//    private void rcyLeftScrollToPosition(int position) {
//        mCateMenusAdapter.setSelPosition(position);
//        mRcyLeft.smoothScrollToPosition(position);
//    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_cate_main;
    }

    @Override
    public MainCatePresenter newPresenter() {
        return new MainCatePresenter();
    }

    public void onCateDatasSucc(List<CateLevelBean> cateLevelBeans) {
        resetCatesData();
    }

    @OnClick(R.id.search_bar_cate_main)
    void onViewClick() {
//        NavHelper.startActivity(getActivity(), SearchActivity.class);
        NavHelper.startActivity(getActivity(), SearchActivity.class,
                SearchActivity.getBundle(true));
    }

    private void resetCatesData() {
        initAdapter();
        mCateMenusAdapter.refreshData(AppContext.getsInstance().getCateLevelBeans());
        mCatesLevel2Adapter.refreshData(AppContext.getsInstance().getCateLevelBeans(),selectPos);
    }

    public void onCateDatasFail() {
    }
}
