package com.tacademy.sadajo.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SharedPreferenceUtil;


public class NewMessageDialogFragment extends DialogFragment implements View.OnClickListener {


    int userAccount;
    ImageButton messageCheckButton;
    ImageButton messageCancelButton;

    public static NewMessageDialogFragment newInstance() {
        NewMessageDialogFragment fragment = new NewMessageDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.CustomDialog);
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getActivity());
        userAccount = sharedPreferenceUtil.getAccessToken();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_message_dialog, container, false);
        messageCheckButton = (ImageButton)view.findViewById(R.id.messageCheckButton);
        messageCancelButton = (ImageButton)view.findViewById(R.id.messageCancelButton);
        return  view;
    }

    @Override
    public void onClick(View view) {

    }
}
