package com.tacademy.sadajo.home;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tacademy.sadajo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class ScheduleDialogFragment extends DialogFragment implements View.OnClickListener {

    Spinner countrySpinner; //국가명 스피너
    Spinner citySpinner; // 도시명 스피너

    EditText departureEditText; //출발해요 날짜
    EditText returnEditText; //돌아와요 날짜

    ImageButton registerButton; //등록하기 버튼
    ImageButton cancelButton; //취소하기 버튼


    public static ScheduleDialogFragment newInstance() {
        ScheduleDialogFragment fragment = new ScheduleDialogFragment();

        return fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule_dialog, container, false);

        countrySpinner = (Spinner) view.findViewById(R.id.countrySpinner);
        citySpinner = (Spinner) view.findViewById(R.id.citySpinner);
        departureEditText = (EditText) view.findViewById(R.id.departureEditText);
        returnEditText = (EditText) view.findViewById(R.id.returnEditText);
        registerButton = (ImageButton) view.findViewById(R.id.registerButton);
        cancelButton = (ImageButton) view.findViewById(R.id.cancelButton);


        departureEditText.setInputType(InputType.TYPE_NULL); //edittext 입력제한
        returnEditText.setInputType(InputType.TYPE_NULL);

        cancelButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        departureEditText.setOnClickListener(this);




        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
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
                returnEditText.setText(sdf.format(arriveCalendar.getTime()));


            }
        };


        departureEditText.setOnClickListener(new View.OnClickListener() { //edittext 클릭 시 datepickerdialog
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dp.show();
            }
        });

        returnEditText.setOnClickListener(new View.OnClickListener() { //edittext 클릭 시 datepickerdialog
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, arriveDate, arriveCalendar
                        .get(Calendar.YEAR), arriveCalendar.get(Calendar.MONTH),
                        arriveCalendar.get(Calendar.DAY_OF_MONTH));

                dp.show();
            }
        });



        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (view.getId()) {
            case R.id.cancelButton:
                dismiss();
                break;
            case R.id.registerButton:

                //등록하기 버튼 눌렀을 때 이미지 토스트
                Toast toast = new Toast(getActivity());
                ImageView img = new ImageView(getActivity());
                img.setImageResource(R.drawable.home_toast);
                toast.setView(img);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                dismiss();
                break;
            case R.id.departureEditText:
                break;


        }

    }

}


