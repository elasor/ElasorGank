package com.elasor.elasorgank.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.elasor.elasorgank.base.BaseFragment;

import java.util.List;

/**
 * @author Elasor
 */
public class MainAdapter extends FragmentStatePagerAdapter{

    private List<BaseFragment> mList;

    public MainAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).mark();
    }
}
