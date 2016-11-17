package com.tacademy.sadajo.search.searchlist;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Swipe를 위한 Custom View
 * Layout의 루트에 두어야 함
 */
public class CollapsingSwipeRefreshLayout extends SwipeRefreshLayout {
    private View myScrollableView = null;

    public CollapsingSwipeRefreshLayout(Context context) {
        super(context);
    }
    public CollapsingSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /*
     * 목적이 되는 스크롤뷰를 세팅
     */
    public void setTargetScrollableView(View view) {
        myScrollableView = view;
    }
    @Override
    public boolean canChildScrollUp() {

        if(myScrollableView == null) return false;
        /*
         * API14이하에서 호환을 맞추어 스크롤 하기 위함
         */
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (myScrollableView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) myScrollableView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 ||
                        absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return myScrollableView.getScrollY() > 0;
            }
        } else {
            /*
              수직으로 스크롤을 하고 있는지 체크
             */
            return ViewCompat.canScrollVertically(myScrollableView, -1);
        }
    }
}
