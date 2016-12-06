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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.sadajo.R;

/**
 * Created by EUNZY on 2016. 11. 30..
 */

public class ReviewDialogFragment extends DialogFragment implements View.OnClickListener {


    TextView reviewDialogUserName;
    EditText reviewDialogEditText;
    ImageButton reviewDialogButton;
    ImageButton reviewDialogCancelButton;

    public ReviewDialogFragment newInstance(){
        ReviewDialogFragment reviewDialogFragment = new ReviewDialogFragment();
        return  reviewDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.CustomDialog);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_dialog, container, false);


        reviewDialogUserName = (TextView)view.findViewById(R.id.reviewDialogUserName);
        reviewDialogEditText = (EditText)view.findViewById(R.id.reviewDialogEditText);
        reviewDialogButton = (ImageButton)view.findViewById(R.id.reviewDialogButton);
        reviewDialogCancelButton = (ImageButton)view.findViewById(R.id.reviewDialogCancelButton);

        reviewDialogButton.setOnClickListener(this);
        reviewDialogCancelButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reviewDialogButton :
                Toast toast = new Toast(getActivity());
                ImageView img = new ImageView(getActivity());
                img.setImageResource(R.drawable.my_review_toast);
                toast.setView(img);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                dismiss();
                break;
            case R.id.reviewDialogCancelButton:
                dismiss();
                break;
        }

    }
}
