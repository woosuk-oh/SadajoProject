package com.tacademy.sadajo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.tacademy.sadajo.chatting.ChattingActivity;
import com.tacademy.sadajo.home.HomeActivity;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.search.searchlist.SearchListActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListActivity;

/**
 * Created by EUNZY on 2016. 11. 20..
 */

public class BottomBarClickListener extends Activity implements View.OnClickListener {

    private Activity activity;


    public BottomBarClickListener(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onClick(View view) {

        Intent intent;
        view.setSelected(true);
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
                intent.putExtra("viewType", 0);
                activity.startActivity(intent);


                break;
        }
    }


}

