package com.tacademy.sadajo.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.fonts.NanumRegularTextView;
import com.tacademy.sadajo.mypage.MyPageActivity;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


public class HomeActivity extends AppCompatActivity {

    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

   // ImageView profileImageView;
    TextView countryNameTv;
    TextView scheduleTv;
    TextView dateComeTv;
    TextView dateGoTv;
    TextView goTv;
    TextView comeTv;
    TextView cardView2Tv;
    TextView card2CountryTv;
    Button scheduleBtn;


    HomeUserRecyclerViewAdapter homeUserRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("");//툴바 타이틀명공백
         Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);

//           TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);



        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        homeBtn.setSelected(true);
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));


        dateGoTv = (TextView)findViewById(R.id.dateGoTv);
        dateComeTv = (TextView)findViewById(R.id.dateComeTv);
        scheduleBtn = (Button)findViewById(R.id.scheduleBtn);
        scheduleBtn.setTypeface(new NanumRegularTextView(getApplication()).getTypeface());
        goTv =(TextView)findViewById(R.id.goTv);
        comeTv =(TextView)findViewById(R.id.comeTv);

        cardView2Tv = (TextView)findViewById(R.id.cardView2Tv);
        card2CountryTv = (TextView)findViewById(R.id.card2CountryTv);


        countryNameTv = (TextView)findViewById(R.id.countryNameTv);
        scheduleTv = (TextView)findViewById(R.id.scheduleTv);

        //profileImageView = (ImageView)findViewById(R.id.profileImageView);
        //  profileImageView.setOnClickListener(onClickListener);

        scheduleBtn.setOnClickListener(onClickListener);



        LinearLayout linearLayout =(LinearLayout)findViewById(R.id.cardView2LL); //두번째 카드뷰 리니어레이아웃
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT);

        //차일드레이아웃 layoutparams
        ViewGroup.LayoutParams buttonParams =  new ViewGroup.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        //buttonParams

        LinearLayout linearLayout2 =new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(linearLayout2);



        Button button = new Button(this);
        button.setLayoutParams(buttonParams);

        button.setText("버트으은");
        linearLayout2.addView(button);


        //layout3
      //  CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(30);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this,4));
     //  recyclerView.addItemDecoration(decoration);
         homeUserRecyclerViewAdapter = new HomeUserRecyclerViewAdapter(HomeActivity.this, ShoppingListSample.shoppinList);
        recyclerView.setAdapter(homeUserRecyclerViewAdapter);



    }

    public static class HomeUserRecyclerViewAdapter
            extends RecyclerView.Adapter<HomeUserRecyclerViewAdapter.ViewHolder> {

        private ArrayList<ShoppingListData> shoppingListDatas;
        private Context context;

        public HomeUserRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
            this.context = context;
            this.shoppingListDatas = shoppingListDatas;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;

            public final TextView homeUserIdTextView;
            public final ImageView userProfileImageView;


            public ViewHolder(View view) {
                super(view);
                mView = view;

                homeUserIdTextView = (TextView) view.findViewById(R.id.homeUserIdTextView);
                userProfileImageView = (ImageView) view.findViewById(R.id.userProfileImageView);

            }
        }

        @Override
        public HomeUserRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.home_layout3_recyclerview_item, parent, false);

            return new HomeUserRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final HomeUserRecyclerViewAdapter.ViewHolder holder, final int position) {


//            holder.shoppinglistCountryNameTextView.setText(shoppingListDatas.get(position).getCountryName().toString());
//            holder.shoppinglistDateTextView.setText(shoppingListDatas.get(position).getTravelDate().toString());
//            holder.shoppinglistFolderImageView.setImageResource(R.drawable.mark);

            holder.homeUserIdTextView.setText("닉네임");

            holder.userProfileImageView.setImageResource(R.drawable.profile_empty);



//            Glide.with(GirlsApplication.getGirlsContext())
//                    .load(girlInfo)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .animate(android.R.anim.slide_in_left)
//                    .into(holder.girlsImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent intent = new Intent(GirlsApplication.getGirlsContext(), GirlsMemberDetailActivity.class);
//                    intent.putExtra("memberImage", girlsImages.get(position));
//                    intent.putExtra("memberName", holder.memberName.getText().toString());
//
//                    ActivityOptionsCompat options =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                    owner, holder.girlsImage, ViewCompat.getTransitionName(holder.girlsImage));
//
//                    ActivityCompat.startActivity(owner, intent, options.toBundle());
                    Intent intent = new Intent(context,MyPageActivity.class);
                    context.startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return shoppingListDatas.size();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.scheduleBtn:
                    ScheduleRegisterDialog dialog =new ScheduleRegisterDialog(HomeActivity.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    dialog.show();
                    break;

//                case R.id.profileImageView :
//                    Intent intent = new Intent(HomeActivity.this, MyPageActivity.class);
//                    startActivity(intent);
//                    break;

            }
        }
    };


}
