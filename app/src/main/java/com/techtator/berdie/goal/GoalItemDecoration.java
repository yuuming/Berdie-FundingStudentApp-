package com.techtator.berdie.goal;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by user on 2018-04-15.
 */

public class GoalItemDecoration extends RecyclerView.ItemDecoration {
    private int bottom;

    public GoalItemDecoration(int bottom) {
        this.bottom = bottom;
    }

    public static GoalItemDecoration createDefaultDecoration(int bottom) {
        return new GoalItemDecoration(bottom);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = bottom;
    }
}
