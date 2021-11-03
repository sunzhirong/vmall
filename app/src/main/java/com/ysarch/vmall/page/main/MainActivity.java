package com.ysarch.vmall.page.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.vector.update_app.UpdateAppManager;
import com.ysarch.uibase.tab.HomeBottomTab;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.context.CustomActivityManager;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.enums.MainPageTag;
import com.ysarch.vmall.page.main.shoye.MainShouYeFragment;
import com.ysarch.vmall.utils.LanguageUtils;
import com.ysarch.vmall.utils.UpdateAppHttpUtil;
import com.yslibrary.utils.FragmentTransUtil;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends AppCompatActivity {


    private long mLastClickTime = 0;


    public static Bundle getBundle(MainPageTag mainPageTag) {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.ARG_TARGET_PAGE, mainPageTag.getValue());
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarCompat.translucentStatusBar(this, true);
        StatusBarCompat.changeToLightStatusBar(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        if (getIntent().getExtras() != null) {
            String targetPage = getIntent().getExtras().getString(BundleKey.ARG_TARGET_PAGE);
            if (!TextUtils.isEmpty(targetPage)) {
                gotoFragment(targetPage, false);
                ((HomeBottomTab) findViewById(getTabViewId(targetPage))).setChecked(true);
                return;
            }
        }

        onTabChange(findViewById(R.id.tb_sy_main_tab));

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                new UpdateAppManager
//                        .Builder()
//                        //当前Activity
//                        .setActivity(MainActivity.this)
//                        //更新地址
//                        .setUpdateUrl("https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt")
//                        //实现httpManager接口的对象
//                        .setHttpManager(new UpdateAppHttpUtil())
//                        .build()
//                        .update();
//            }
//        },5000);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            String targetPage = intent.getExtras().getString(BundleKey.ARG_TARGET_PAGE);
            if (!TextUtils.isEmpty(targetPage)) {
                gotoFragment(targetPage, false);
                ((HomeBottomTab) findViewById(getTabViewId(targetPage))).setChecked(true);
                return;
            }
        }
    }


    private void gotoFragment(String tag, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        /**
         *   MAIN_PAGE_FIRST_PAGE_TAG("main_page_first_page_tag"),
         *     MAIN_PAGE_CATE_TAG("main_page_cate_tag"),
         *     MAIN_PAGE_DISCOVER_TAG("main_page_discover_tag"),
         *     MAIN_PAGE_CART_TAG("main_page_cart_tag"),
         *     MAIN_PAGE_MINE_TAG("main_page_mine_tag");
         */
        if (fragment == null) {
            switch (tag) {

                case "main_page_cate_tag":
                    fragment = new MainCateFragment();
                    fragment.setArguments(getIntent().getExtras());
                    break;
                case "main_page_discover_tag":
                    fragment = new MainDiscoverFragment();
                    fragment.setArguments(getIntent().getExtras());
                    break;
                case "main_page_cart_tag":
                    fragment = new MainCartFragment();
                    fragment.setArguments(getIntent().getExtras());
                    break;
                case "main_page_mine_tag":
                    fragment = new MainMineFragment();
                    fragment.setArguments(getIntent().getExtras());
                    break;
                case "main_page_first_page_tag":
                default:
                    fragment = new MainShouYeFragment();
                    fragment.setArguments(getIntent().getExtras());
                    break;
            }

        }

        if (fragment != null) {
            FragmentTransUtil.transFragment(fragmentManager, R.id.fl_fragment_container_main_page,
                    fragment, tag, addToBackStack);
        }

        twinkleStatusBarMode(tag);
    }


    private int getTabViewId(String pageTag) {
        int tabViewId = R.id.tb_sy_main_tab;
        switch (pageTag) {

            case "main_page_cate_tag":
                tabViewId = R.id.tb_cate_main_tab;
                break;
            case "main_page_discover_tag":
                tabViewId = R.id.tb_discover_main_tab;
                break;
            case "main_page_cart_tag":
                tabViewId = R.id.tb_cart_main_tab;
                break;
            case "main_page_mine_tag":
                tabViewId = R.id.tb_mine_main_tab;
                break;
            case "main_page_first_page_tag":
            default:
                tabViewId = R.id.tb_sy_main_tab;
                break;
        }

        return tabViewId;
    }

    /**
     * 页面切换
     *
     * @param targetTag
     * @param addToBackStack
     */
    private void gotoFragment(MainPageTag targetTag, boolean addToBackStack) {
        gotoFragment(targetTag.getValue(), addToBackStack);
    }

    /**
     * 状态栏暗亮模式切换
     *
     * @param tabValue
     */
    private void twinkleStatusBarMode(String tabValue) {
        switch (tabValue) {
            case "main_page_first_page_tag":
            case "main_page_mine_tag":
                StatusBarCompat.cancelLightStatusBar(this);
                break;
            case "main_page_cate_tag":
            case "main_page_discover_tag":
            case "main_page_cart_tag":
                StatusBarCompat.changeToLightStatusBar(this);
                break;

            default:
                break;
        }
    }

    @OnClick({R.id.tb_sy_main_tab, R.id.tb_cate_main_tab, R.id.tb_discover_main_tab,
            R.id.tb_cart_main_tab, R.id.tb_mine_main_tab})
    void onTabChange(View tabView) {
        final int tabId = tabView.getId();
        ((HomeBottomTab) tabView).setChecked(true);
        switch (tabId) {
            case R.id.tb_sy_main_tab:
                gotoFragment(MainPageTag.MAIN_PAGE_FIRST_PAGE_TAG, false);
                break;
            case R.id.tb_cate_main_tab:
                gotoFragment(MainPageTag.MAIN_PAGE_CATE_TAG, false);
                break;
            case R.id.tb_discover_main_tab:
                gotoFragment(MainPageTag.MAIN_PAGE_DISCOVER_TAG, false);
                break;
            case R.id.tb_cart_main_tab:
                gotoFragment(MainPageTag.MAIN_PAGE_CART_TAG, false);
                break;
            case R.id.tb_mine_main_tab:
                gotoFragment(MainPageTag.MAIN_PAGE_MINE_TAG, false);
                break;

            default:
                break;

        }
    }


    @Override
    public void onBackPressed() {
        long clickTime = System.currentTimeMillis();
        if ((clickTime - mLastClickTime) < 2000) {
            CustomActivityManager.getInstance().finishAll();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            mLastClickTime = clickTime;
            Toast.makeText(this, R.string.exit_app_tips, Toast.LENGTH_SHORT).show();
        }
    }



    public static void reStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}