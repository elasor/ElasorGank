package com.elasor.elasorgank.ui.category;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.elasor.elasorgank.R;
import com.elasor.elasorgank.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCategoryActivity extends BaseActivity {


    @BindView(R.id.iv_category_back)
    ImageView ivCategoryBack;
    @BindView(R.id.tv_category_edit)
    TextView tvCategoryEdit;
    @BindView(R.id.tv_category_title)
    TextView tvCategoryTitle;
    @BindView(R.id.rv_category_mine)
    RecyclerView rvCategoryMine;
    @BindView(R.id.rv_category_more)
    RecyclerView rvCategoryMore;

    @Override
    protected int layoutResId() {
        return R.layout.activity_edit_category;
    }

    @Override
    protected void initControls() {
        ButterKnife.bind(this);

        tvCategoryTitle.setText("栏目");
        tvCategoryEdit.setText("编辑");

        rvCategoryMine.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void initControlListener() {

    }
}
