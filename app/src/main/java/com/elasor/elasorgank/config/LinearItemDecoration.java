package com.elasor.elasorgank.config;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lify.elasor.ElasorUtil;

/**
 * @author Elasor
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration{

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int gap = ElasorUtil.dp2px(parent.getContext(), 10);

        int position = parent.getChildAdapterPosition(view);

        if(position == 0){
            outRect.set(gap, gap, gap, gap);
        }else{
            outRect.set(gap, 0, gap, gap);
        }
    }
}
