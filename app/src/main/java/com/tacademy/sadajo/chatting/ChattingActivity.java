package com.tacademy.sadajo.chatting;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.sadajo.BottomBarClickListener;
import com.tacademy.sadajo.CustomRecyclerDecoration;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.shoppinglist.ShoppingListData;
import com.tacademy.sadajo.shoppinglist.ShoppingListSample;

import java.util.ArrayList;

import static com.tacademy.sadajo.SadajoContext.getContext;

public class ChattingActivity extends AppCompatActivity {
///push test
    ImageButton homeBtn;
    ImageButton searchBtn;
    ImageButton shoppingListBtn;
    ImageButton chattingBtn;
    ImageButton mypageBtn;

    ChattingRecyclerViewAdapter chattingRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        setTitle("");//툴바 타이틀명공백
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);




        homeBtn = (ImageButton)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new BottomBarClickListener(this));
        searchBtn = (ImageButton)findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new BottomBarClickListener(this));
        shoppingListBtn = (ImageButton)findViewById(R.id.shoppingListBtn);
        shoppingListBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn = (ImageButton)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new BottomBarClickListener(this));
        chattingBtn.setSelected(true);
        mypageBtn = (ImageButton)findViewById(R.id.mypageBtn);
        mypageBtn.setOnClickListener(new BottomBarClickListener(this));


        CustomRecyclerDecoration decoration = new CustomRecyclerDecoration(10);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        chattingRecyclerViewAdapter = new ChattingRecyclerViewAdapter(getContext(), ShoppingListSample.shoppinList);
        recyclerView.setAdapter(chattingRecyclerViewAdapter);




    }
 //   ImageButton.OnClickListener  mClickListener = new View.OnClickListener() {
//        Intent intent;
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.homeBtn :
//                    chattingBtn.setSelected(false);
//                    intent =  new Intent(ChattingActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.searchBtn :
//
//
//                    intent =  new Intent(ChattingActivity.this, SearchListActivity.class);
//
//                    chattingBtn.setSelected(false);
//
//                    startActivity(intent);
//                    break;
//                case R.id.shoppingListBtn :
//                    chattingBtn.setSelected(false);
//                    intent =  new Intent(ChattingActivity.this, ShoppingListActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.chattingBtn:
//                    intent =  new Intent(ChattingActivity.this, ChattingActivity.class);
//                    startActivity(intent);
//                    break;
//                case R.id.mypageBtn :
//                    chattingBtn.setSelected(false);
//                    intent =  new Intent(ChattingActivity.this, MyPageActivity.class);
//                    startActivity(intent);
//                    break;
//            }
//        }
//    };

//

    public static class ChattingRecyclerViewAdapter
            extends RecyclerView.Adapter<ChattingRecyclerViewAdapter.ViewHolder> {

        private ArrayList<ShoppingListData> shoppingListDatas;
        private Context context;

        public ChattingRecyclerViewAdapter(Context context, ArrayList<ShoppingListData> shoppingListDatas) {
            this.context = context;
            this.shoppingListDatas = shoppingListDatas;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView chattingTitleTextView;
            public final TextView chattingContentTextView;
            public final TextView chattingDateTextView;
            public final ImageView userProfileImageView;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                chattingTitleTextView = (TextView) view.findViewById(R.id.chattingTitleTextView);
                chattingContentTextView = (TextView) view.findViewById(R.id.chattingContentTextView);
                chattingDateTextView = (TextView) view.findViewById(R.id.chattingDateTextView);
                userProfileImageView = (ImageView) view.findViewById(R.id.userProfileImageView);

            }
        }

        @Override
        public ChattingRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.chatting_recyclerview_item, parent, false);

            return new ChattingRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ChattingRecyclerViewAdapter.ViewHolder holder, final int position) {


//            holder.shoppinglistCountryNameTextView.setText(shoppingListDatas.get(position).getCountryName().toString());
//            holder.shoppinglistDateTextView.setText(shoppingListDatas.get(position).getTravelDate().toString());
//            holder.shoppinglistFolderImageView.setImageResource(R.drawable.mark);

            holder.chattingTitleTextView.setText("채팅제목");
            holder.chattingContentTextView.setText("안녕하세요");
            holder.chattingDateTextView.setText("2016.11.20");
            holder.userProfileImageView.setImageResource(R.drawable.profile_empty);



//            Glide.with(GirlsApplication.getGirlsContext())
//                    .load(girlInfo)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .animate(android.R.anim.slide_in_left)
//                    .into(holder.girlsImage);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
//                    Intent intent = new Intent(GirlsApplication.getGirlsContext(), GirlsMemberDetailActivity.class);
//                    intent.putExtra("memberImage", girlsImages.get(position));
//                    intent.putExtra("memberName", holder.memberName.getText().toString());
//
//                    ActivityOptionsCompat options =
//                            ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                    owner, holder.girlsImage, ViewCompat.getTransitionName(holder.girlsImage));
//
//                    ActivityCompat.startActivity(owner, intent, options.toBundle());

                }
            });

        }

        @Override
        public int getItemCount() {
            return shoppingListDatas.size();
        }
    }
}
