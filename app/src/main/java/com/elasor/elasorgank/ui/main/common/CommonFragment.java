package com.elasor.elasorgank.ui.main.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elasor.elasorgank.Constant;
import com.elasor.elasorgank.R;
import com.elasor.elasorgank.base.BaseFragment;
import com.elasor.elasorgank.bean.CommonBean;
import com.elasor.elasorgank.bean.CommonListBean;
import com.elasor.elasorgank.config.LinearItemDecoration;
import com.elasor.elasorgank.util.RetrofitApi;
import com.lify.elasor.http.ElasorHttpObserver;
import com.lify.elasor.http.ElasorRetrofit;
import com.lify.elasor.msg.ElasorLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Elasor
 */
public class CommonFragment extends BaseFragment {

    @BindView(R.id.rv_common_content)
    RecyclerView rvCommonContent;
    Unbinder unbinder;

    private CommonAdapter commonAdapter;
    private List<CommonListBean> mList = new ArrayList<>();

    private String mCategoryName;
    private int mCurrentPage = 1;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_main_common;
    }

    @Override
    protected void initControls() {
        unbinder = ButterKnife.bind(this, mView);

        commonAdapter = new CommonAdapter(getContext(), mList);
        rvCommonContent.setAdapter(commonAdapter);
        rvCommonContent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvCommonContent.addItemDecoration(new LinearItemDecoration());
    }

    @Override
    protected void initControlListener() {

    }

    @Override
    protected void initOther() {
        super.initOther();
        Bundle arguments = getArguments();
        mCategoryName = arguments.getString("categoryName", "all");
    }

    @Override
    protected void loadData() {

        if(!mHasCreated || !mIsVisible){
            return;
        }

        ElasorLog.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+mark());
        ElasorRetrofit.get(Constant.Url.BASE_URL, RetrofitApi.class)
                .requestData(mCategoryName, 10, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ElasorHttpObserver<CommonBean>(getContext(), false) {
                    @Override
                    public void onNext(@NonNull CommonBean commonBean) {
                        super.onNext(commonBean);

                        List<CommonListBean> results = commonBean.getResults();
                        mList.clear();
                        mList.addAll(results);
                        commonAdapter.notifyDataSetChanged();
                    }
                });
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//    }
}
