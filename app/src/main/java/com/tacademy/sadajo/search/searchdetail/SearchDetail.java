package com.tacademy.sadajo.search.searchdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;

import java.util.ArrayList;

/**
 * Created by woosuk on 2016-11-14.
 */

public class SearchDetail extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    ViewPager searchDetailViewPager;
    TextView itemcount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail_body);

//        final ImageView itemImage = (ImageView) findViewById(R.id.item_image);
        final TextView itemID = (TextView) findViewById(R.id.search_detail_item_name);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("key"); // searchListActivity에서 넘겨준 키("key") + 값(itemName.getText().toString()) 을 "key"로 받아옴

        itemID.setText(id); //itemID로 셋팅한 TextView에 인텐트로부터 받아온 id를 setText해준다.

        itemcount = (TextView) findViewById(R.id.itemcount);
        itemcount.setText(String.valueOf(1)+"/4");
        searchDetailViewPager = (ViewPager) findViewById(R.id.search_detail_viewpager);
        searchDetailViewPager.addOnPageChangeListener(this);
        SearchDetailPagerAdapter searchDetailPagerAdapter = new SearchDetailPagerAdapter();
        searchDetailViewPager.setAdapter(searchDetailPagerAdapter);




    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        itemcount.setText(String.valueOf(position+1)+"/4");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class SearchDetailPagerAdapter extends PagerAdapter{
        ArrayList<Integer> imageList = new ArrayList<>();

        public SearchDetailPagerAdapter() {
            imageList.add(R.drawable.airport);
            imageList.add(R.drawable.flag);
            imageList.add(R.drawable.home_logo);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) { // onBindView
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.search_detail_view_pager_item, container, false);
            ImageView itemImg = (ImageView) view.findViewById(R.id.search_detail_viewpager_item_img);
            itemImg.setImageResource(imageList.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
