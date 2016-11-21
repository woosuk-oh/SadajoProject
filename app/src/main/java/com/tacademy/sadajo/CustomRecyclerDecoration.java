package com.tacademy.sadajo;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by EUNZY on 2016. 11. 20..
 */

public class CustomRecyclerDecoration extends RecyclerView.ItemDecoration {
    private final int divHeight;

    public CustomRecyclerDecoration(int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = divHeight;
    }
}