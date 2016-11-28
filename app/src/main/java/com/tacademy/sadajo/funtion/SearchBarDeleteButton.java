package com.tacademy.sadajo.funtion;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by woosuk on 2016-11-18.
 */

public class SearchBarDeleteButton {

    public static void setRemovableET(final EditText et, final Button resetIB) {

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && et.getText().toString().length() > 0)
                    resetIB.setVisibility(View.VISIBLE);
                else
                    resetIB.setVisibility(View.INVISIBLE);
            }
        });

        resetIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                resetIB.setVisibility(View.INVISIBLE);
            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    resetIB.setVisibility(View.VISIBLE);
                }else{
                    resetIB.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}
