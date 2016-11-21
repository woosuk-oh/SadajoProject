package com.tacademy.sadajo.funtion;

/**
 * Created by woosuk on 2016-11-18.
 */

public class ContentRefresh {

/*


    //////////////////////////////
    //  Setup Swipe To Refresh  //
    //////////////////////////////
    *//**//*

    private static class CollapsingSwipeRefreshLayout extends RecyclerView {
        private CollapsingSwipeRefreshLayout swiper;

        RecyclerView mRecycler;

    swiper = (CollapsingSwipeRefreshLayout) findViewById(R.id.swipe_container);

*//*
          Swipe Icon 크기 및 컬러설정
         *//*

        swiper.setSize(SwipeRefreshLayout.LARGE);
        swiper.setColorSchemeResources(

                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light
        );
        //Swipe 발생시
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

*//*
                  발생시(2000초)
               *//*

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (itemsOfData != null && mAdapter != null) {

                            // 다시 네트워크로 부터 데이터 가져와야됌.


                            mAdapter.notifyDataSetChanged(); // 새로고침 후 데이터 셋 체인지 해준다.

                        }
                        swiper.setRefreshing(false); // 데이터셋 해주고 리프래쉬
                    }
                }, 2000);
            }
        });


        /////////////////////////////////////////////
        //  스크롤 리스터 등록 //
        /////////////////////////////////////////////
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            //스크롤의 수평/수직의 갯수--여기선 Vertical이므로 dy의 값은 수직의 값이 됨(+-값)
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 3) {
                    if (customBar.getVisibility() == View.VISIBLE)
                        hideCustomBar();

                } else if (dy < -3) {
                    if (customBar.getVisibility() == View.GONE)
                        showCustomBar();
                }
            }
        });

        mRecycler.setAdapter(mAdapter);

        //스크롤될 뷰(여기서는 리사이클러뷰)를 스위프에 등록
        swiper.setTargetScrollableView(mRecycler);

    }*/


}
