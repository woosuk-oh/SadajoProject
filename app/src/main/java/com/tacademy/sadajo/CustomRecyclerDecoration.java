package com.tacademy.sadajo;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by EUNZY on 2016. 11. 20..
 */

public class CustomRecyclerDecoration extends RecyclerView.ItemDecoration {
    private final int divHeight;
    private final String type;


//    private final static int TOP = 0;
//    private final static int BOTTOM = 1;
//    private final static int RIGHT = 2;
//    private final static int LEFT = 3;
//



    public CustomRecyclerDecoration(int divHeight,String type) {
        this.divHeight = divHeight;
        this.type =type;

    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if(type.equals("top")){
            outRect.top = divHeight;
        }else if(type.equals("left")){
            outRect.left = divHeight;
        }else if(type.equals("right")){
            outRect.right = divHeight;
        }else{
            outRect.bottom = divHeight;
        }

    }
}