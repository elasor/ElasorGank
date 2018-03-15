package com.elasor.elasorgank.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.elasor.elasorgank.Constant;
import com.elasor.elasorgank.R;
import com.elasor.elasorgank.base.BaseActivity;
import com.elasor.elasorgank.base.BaseFragment;
import com.elasor.elasorgank.ui.content.image.ImageContentActivity;
import com.elasor.elasorgank.ui.main.common.CommonFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Elasor
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_main_title)
    TabLayout tlMainTitle;
    @BindView(R.id.iv_main_add)
    ImageView ivMainAdd;
    @BindView(R.id.vp_main_content)
    ViewPager vpMainContent;
    private List<BaseFragment> mList = new ArrayList<>();

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initControls() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        CommonFragment dailyFragment = new CommonFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("categoryName", "all");
        dailyFragment.setArguments(bundle1);
        dailyFragment.setMarkIdentifier(Constant.Other.CHANNEL_DAILY);

        CommonFragment welfareFragment = new CommonFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("categoryName", "福利");
        welfareFragment.setArguments(bundle2);
        welfareFragment.setMarkIdentifier(Constant.Other.CHANNEL_WELFARE);

        CommonFragment androidFragment = new CommonFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("categoryName", "Android");
        androidFragment.setArguments(bundle3);
        androidFragment.setMarkIdentifier(Constant.Other.CHANNEL_ANDROID);

        CommonFragment iosFragment = new CommonFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("categoryName", "iOS");
        iosFragment.setArguments(bundle4);
        iosFragment.setMarkIdentifier(Constant.Other.CHANNEL_IOS);

        CommonFragment videoFragment = new CommonFragment();
        Bundle bundle5 = new Bundle();
        bundle5.putString("categoryName", "休息视频");
        videoFragment.setArguments(bundle5);
        videoFragment.setMarkIdentifier(Constant.Other.CHANNEL_VIDEO);

        CommonFragment frontFragment = new CommonFragment();
        Bundle bundle6 = new Bundle();
        bundle6.putString("categoryName", "前端");
        frontFragment.setArguments(bundle6);
        frontFragment.setMarkIdentifier(Constant.Other.CHANNEL_FRONT);

        CommonFragment expandFragment = new CommonFragment();
        Bundle bundle7 = new Bundle();
        bundle7.putString("categoryName", "拓展资源");
        expandFragment.setArguments(bundle7);
        expandFragment.setMarkIdentifier(Constant.Other.CHANNEL_EXPAND);

        CommonFragment recommendFragment = new CommonFragment();
        Bundle bundle8 = new Bundle();
        bundle8.putString("categoryName", "瞎推荐");
        recommendFragment.setArguments(bundle8);
        recommendFragment.setMarkIdentifier(Constant.Other.CHANNEL_RECOMMEND);

        CommonFragment appFragment = new CommonFragment();
        Bundle bundle9 = new Bundle();
        bundle9.putString("categoryName", "App");
        appFragment.setArguments(bundle9);
        appFragment.setMarkIdentifier(Constant.Other.CHANNEL_APP);

        mList.add(dailyFragment);
        mList.add(welfareFragment);
        mList.add(androidFragment);
        mList.add(iosFragment);
        mList.add(frontFragment);
        mList.add(videoFragment);
        mList.add(expandFragment);
        mList.add(recommendFragment);
        mList.add(appFragment);

        tlMainTitle.setupWithViewPager(vpMainContent);
        vpMainContent.setAdapter(new MainAdapter(getSupportFragmentManager(), mList));
        vpMainContent.setOffscreenPageLimit(Integer.MAX_VALUE);
    }

    @Override
    protected void initControlListener() {

    }

    @OnClick(R.id.iv_main_add)
    public void editCategory() {
        Intent intent = new Intent(this, ImageContentActivity.class);
        startActivityWithAnimation(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeCategory() {

    }
}