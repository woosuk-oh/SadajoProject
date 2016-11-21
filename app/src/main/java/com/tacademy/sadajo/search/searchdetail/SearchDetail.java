package com.tacademy.sadajo.search.searchdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.R;

/**
 * Created by woosuk on 2016-11-14.
 */

public class SearchDetail extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail_body);

        final ImageView itemImage = (ImageView) findViewById(R.id.item_image);
        final TextView itemID = (TextView) findViewById(R.id.search_detail_item_name);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("key"); // searchListActivity에서 넘겨준 키("key") + 값(itemName.getText().toString()) 을 "key"로 받아옴

        itemID.setText(id); //itemID로 셋팅한 TextView에 인텐트로부터 받아온 id를 setText해준다.




    }
}
