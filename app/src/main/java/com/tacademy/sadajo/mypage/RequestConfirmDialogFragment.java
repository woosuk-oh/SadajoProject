package com.tacademy.sadajo.mypage;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.sadajo.R;

/**
 * Created by EUNZY on 2016. 11. 30..
 */

public class RequestConfirmDialogFragment extends DialogFragment implements View.OnClickListener {

    TextView requestConfirmCountryNameTextView; //국가명
    TextView requestConfirmProductNameTextView; //상품명
    TextView requestConfirmProductPriceTextView; //가격
    TextView requestConfirmProductOptTextView; //추가요청사항

    ImageButton requestConfirmButton; //수락하기버튼
    ImageButton requestConfirmCancelButton; //취소

    public RequestConfirmDialogFragment newInstance(){
        RequestConfirmDialogFragment fragment = new RequestConfirmDialogFragment();

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.6f;
        window.setAttributes(params);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.CustomDialog);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request_confirm_dialog, container, false);

        requestConfirmCountryNameTextView = (TextView)view.findViewById(R.id.requestConfirmCountryNameTextView);
        requestConfirmProductNameTextView = (TextView)view.findViewById(R.id.requestConfirmProductNameTextView);
        requestConfirmProductPriceTextView = (TextView)view.findViewById(R.id.requestConfirmProductPriceTextView);
        requestConfirmProductOptTextView = (TextView)view.findViewById(R.id.requestConfirmProductOptTextView);

        requestConfirmButton = (ImageButton) view.findViewById(R.id.requestConfirmButton);
        requestConfirmCancelButton = (ImageButton) view.findViewById(R.id.requestConfirmCancelButton);

        requestConfirmButton.setOnClickListener(this);
        requestConfirmCancelButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Toast toast = new Toast(getActivity());
        ImageView img = new ImageView(getActivity());
        switch (view.getId()){
            case R.id.requestConfirmButton:


                img.setImageResource(R.drawable.my_accept_toast);
                toast.setView(img);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                dismiss();
                break;
            case R.id.requestConfirmCancelButton :
                img.setImageResource(R.drawable.my_reject_toast);
                toast.setView(img);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                dismiss();
                break;
        }

    }
}
