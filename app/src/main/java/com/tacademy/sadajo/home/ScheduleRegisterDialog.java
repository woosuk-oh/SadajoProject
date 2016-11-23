package com.tacademy.sadajo.home;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.sadajo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleRegisterDialog extends Dialog {


    TextView titleTextView;
    Spinner countrySpinner;
    Spinner citySpinner;
    EditText departureEditText;
    EditText arriveEditText;
    Button registerButton;
    Button cancelButton;


    private String mode;
    private String text;

    public ScheduleRegisterDialog(Context context) {
        super(context);

    }

    public ScheduleRegisterDialog(Context context, int themeResId) {
        super(context, themeResId);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schedule_register_dialog);


        titleTextView = (TextView) findViewById(R.id.titleTextView);
        countrySpinner = (Spinner) findViewById(R.id.countrySpinner);
        citySpinner = (Spinner) findViewById(R.id.citySpinner);
        departureEditText = (EditText) findViewById(R.id.departureEditText);
        arriveEditText = (EditText) findViewById(R.id.arriveEditText);
        registerButton = (Button) findViewById(R.id.registerButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);


        departureEditText.setInputType(InputType.TYPE_NULL);

        cancelButton.setOnClickListener(onClickListener);
        registerButton.setOnClickListener(onClickListener);


        //
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy.MM.dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRENCH);
                departureEditText.setText(sdf.format(myCalendar.getTime()));


            }
        };
        //
        final Calendar arriveCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener arriveDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                arriveCalendar.set(Calendar.YEAR, year);
                arriveCalendar.set(Calendar.MONTH, monthOfYear);
                arriveCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy.MM.dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRENCH);
                arriveEditText.setText(sdf.format(arriveCalendar.getTime()));


            }
        };


        departureEditText.setOnClickListener(new View.OnClickListener() { //edittext 클릭 시 datepickerdialog
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getContext(), R.style.MyDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dp.show();
            }
        });

        arriveEditText.setOnClickListener(new View.OnClickListener() { //edittext 클릭 시 datepickerdialog
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getContext(), R.style.MyDialogTheme, arriveDate, arriveCalendar
                        .get(Calendar.YEAR), arriveCalendar.get(Calendar.MONTH),
                        arriveCalendar.get(Calendar.DAY_OF_MONTH));

                dp.show();
            }
        });


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
//

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.cancelButton:
                    dismiss();
                    break;
                case R.id.registerButton:
                    dismiss();
                    Toast.makeText(getContext(), "여행일정이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

}

