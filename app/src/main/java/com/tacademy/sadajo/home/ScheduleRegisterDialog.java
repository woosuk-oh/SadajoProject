package com.tacademy.sadajo.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.tacademy.sadajo.R;

public class ScheduleRegisterDialog extends Dialog {

    public ScheduleRegisterDialog(Context context) {
        super(context);
    }


    TextView titleTextView;
    Spinner countrySpinner;
    Spinner citySpinner;
    DatePicker departureDatePicker;
    DatePicker arriveDatePicker;
    Button registerButton;


    private String mode;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule_register_dialog);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
        citySpinner = (Spinner) findViewById(R.id.citySpinner);
        departureDatePicker = (DatePicker) findViewById(R.id.departureDatePicker);
        arriveDatePicker = (DatePicker) findViewById(R.id.arriveDatePicker);
        registerButton = (Button) findViewById(R.id.registerButton);

//
//        registerButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                //text = txt_modify_edit.getText().toString();
//                mode = titleTextView.getText().toString();
//                dismiss();
//                return false;
//            }
//        });
//
//    }
//    public String getMode() {
//        return mode;
//    }
//
//    public void setMode(String mode) {
//        this.mode = mode;
//        titleTextView.setText(mode);
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//     //   txt_modify_edit.setText(text);
//       // txt_modify_edit.setFocusable(true);
//    }
    }
}