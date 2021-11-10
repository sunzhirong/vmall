package com.ysarch.vmall.page.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.ysarch.uibase.base.BaseFragment;
import com.ysarch.vmall.R;
import com.ysarch.vmall.common.event.HailerFunctionDef;
import com.ysarch.vmall.domain.constant.BundleKey;
import com.ysarch.vmall.domain.constant.Constants;
import com.ysarch.vmall.domain.enums.SearchPageTag;
import com.ysarch.vmall.page.goods.GoodsDetailActivity;
import com.ysarch.vmall.page.search.presenter.SearchPresenter;
import com.ysarch.vmall.utils.NavHelper;
import com.ysarch.vmall.utils.SoftInputUtil;
import com.ysarch.vmall.utils.VMallUtils;
import com.yslibrary.event.hailer.FunctionHasParamNoResult;
import com.yslibrary.event.hailer.FunctionsManager;
import com.yslibrary.utils.FragmentTransUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fysong on 17/09/2020
 **/
public class SearchFragment extends BaseFragment<SearchPresenter> {
    @BindView(R.id.et_search)
    EditText mETKeyword;
    @BindView(R.id.iv_clear_keyword_search)
    ImageView mIVClear;

    private String mKeyword;

    private SearchVPFragment mSearchVPFragment;
    private int mPageLaunchType;
    private int mCateId;


    private FunctionHasParamNoResult<String> searchEvent = new FunctionHasParamNoResult<String>(HailerFunctionDef.SEARCH_KEYWORD) {
        @Override
        public void invokeFunction(String keyword) {
            resetKeyword(keyword);
        }
    };
    private boolean mShowKeyboard;

    @Override
    public void bindEvent() {
        super.bindEvent();
        FunctionsManager.getInstance().addFunctionHasParamNoResult(
                searchEvent);
    }

    public void resetKeyword(String keyword) {
        if (!TextUtils.isEmpty(keyword) && !keyword.equals(mKeyword)) {
            mKeyword = keyword;
            mETKeyword.setText(keyword);
            getPresenter().refreshHistoryCache(mKeyword);
            if (!checkCmdKeyword(mKeyword)) {
                if (mSearchVPFragment == null) {
                    gotoPage(SearchPageTag.SEARCH_CONTENT_PAGE);
                } else {
                    gotoPage(SearchPageTag.SEARCH_CONTENT_PAGE);
                    mSearchVPFragment.resetKeyword(mKeyword);
                }


            }
        }
    }

    @Override
    public void bindUI(View mRootView) {
        super.bindUI(mRootView);
//        if(mShowKeyboard) {
//            mETKeyword.setFocusable(true);
//            mETKeyword.setFocusableInTouchMode(true);
//            mETKeyword.requestFocus();
//        }

        mETKeyword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mKeyword = mETKeyword.getText().toString().trim();

                if (!TextUtils.isEmpty(mKeyword)) {
                    getPresenter().refreshHistoryCache(mKeyword);

                    if (!checkCmdKeyword(mKeyword)) {
                        if (mSearchVPFragment == null) {
                            gotoPage(SearchPageTag.SEARCH_CONTENT_PAGE);
                        } else {
                            mSearchVPFragment.resetKeyword(mKeyword);
                        }
                    }
                }
            }
            return false;
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mPageLaunchType = bundle.getInt(BundleKey.ARG_PAGE_LAUNCH_TYPE, 0);
            if (mPageLaunchType == SearchActivity.TYPE_SEARCH_KEYWORD) {
                mKeyword = bundle.getString(BundleKey.ARG_KEYWORD);
                mETKeyword.setText(mKeyword);
            } else if (mPageLaunchType == SearchActivity.TYPE_SEARCH_CATE_ID) {
                mCateId = bundle.getInt(BundleKey.ARG_CATE_ID);
            }

            mShowKeyboard = bundle.getBoolean(BundleKey.ARG_SHOW_KEYBORAD);
        }

        if (mPageLaunchType != SearchActivity.TYPE_NOR) {
            gotoPage(SearchPageTag.SEARCH_CONTENT_PAGE);
        } else {
            gotoPage(SearchPageTag.SEARCH_HISTORY_PAGE);
        }

        if(mShowKeyboard) {
            mETKeyword.setFocusable(true);
            mETKeyword.setFocusableInTouchMode(true);
            mETKeyword.requestFocus();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    private boolean checkCmdKeyword(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            String id = VMallUtils.extractID(keyword);
            if (!TextUtils.isEmpty(id)) {
                if (keyword.startsWith("tid")) {
                    NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
                            GoodsDetailActivity.getBundle(Long.parseLong(id), Constants.TYPE_PLATFORM_TB));
                    return true;
                }
                if (keyword.startsWith("aid")) {
                    NavHelper.startActivity(getActivity(), GoodsDetailActivity.class,
                            GoodsDetailActivity.getBundle(Long.parseLong(id), Constants.TYPE_PLATFORM_1688));
                    return true;
                }
            }
        }

        return false;
    }

    private void gotoPage(SearchPageTag targetTag) {
        FragmentManager fragmentManager = getChildFragmentManager();
        String tag = targetTag.getValue();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            switch (targetTag.getValue()) {
                case "search_history_page":
                    fragment = new SearchHistoryFragment();
                    break;
                case "search_content_page":
                    fragment = mSearchVPFragment = new SearchVPFragment();

//                    if(mPageLaunchType == SearchActivity.TYPE_SEARCH_CATE_ID){
//                        fragment.setArguments(SearchContentFragment.getBundle(mCateId));
//                    } else {
                    fragment.setArguments(SearchVPFragment.getBundle(mKeyword));
//                    }
                    break;
                default:
                    break;
            }
        }

        if (fragment != null) {
            if(targetTag.getValue().equals("search_content_page")) {
                mETKeyword.setFocusableInTouchMode(false);
                mETKeyword.setFocusable(false);
                SoftInputUtil.hideSoftInput(context, mETKeyword);
            }
            FragmentTransUtil.transFragment(fragmentManager, R.id.fl_container_search,
                    fragment, tag, false);
        }

    }

    @OnClick(R.id.iv_back_search)
    void onViewClick(View view) {
        getActivity().finish();
    }

    @OnClick(R.id.et_search)
    void onEtClick(View view) {
        mETKeyword.setFocusableInTouchMode(true);
        mETKeyword.setFocusable(true);
        mETKeyword.requestFocus();
        gotoPage(SearchPageTag.SEARCH_HISTORY_PAGE);
    }

    @Override
    public SearchPresenter newPresenter() {
        return new SearchPresenter();
    }
}
