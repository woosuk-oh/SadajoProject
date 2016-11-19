package com.tacademy.sadajo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.tacademy.sadajo.chatting.ChattingActivity;
import com.tacademy.sadajo.home.HomeActivity;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.search.SearchListActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListActivity;

/**
 * Created by EUNZY on 2016. 11. 20..
 */

public class BottomBarClickListner extends Activity implements View.OnClickListener {

    private  Activity activity;

    public BottomBarClickListner(Activity activity) {
      this.activity = activity;
    }



    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.homeBtn:
                intent = new Intent(activity, HomeActivity.class);
                activity.startActivity(intent);
                break;
            case R.id.searchBtn:

                intent = new Intent(activity, SearchListActivity.class);
                activity.startActivity(intent);

                break;
            case R.id.shoppingListBtn:

                intent = new Intent(activity, ShoppingListActivity.class);
                activity.startActivity(intent);
                break;
            case R.id.chattingBtn:
                intent = new Intent(activity, ChattingActivity.class);
                activity.startActivity(intent);
                break;
            case R.id.mypageBtn:

                intent = new Intent(activity, MyPageActivity.class);
                activity.startActivity(intent);
                break;
        }}



}

