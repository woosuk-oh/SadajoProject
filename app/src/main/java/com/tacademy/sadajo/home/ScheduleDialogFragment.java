package com.tacademy.sadajo.home;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tacademy.sadajo.CityCodeHashMap;
import com.tacademy.sadajo.CountryCodeHashMap;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.network.Home.ScheduleEntityObject;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.util.Log.e;


public class ScheduleDialogFragment extends DialogFragment implements View.OnClickListener {

    Spinner countrySpinner; //국가명 스피너
    Spinner citySpinner; // 도시명 스피너

    EditText departureEditText; //출발해요 날짜
    EditText returnEditText; //돌아와요 날짜

    ImageButton registerButton; //등록하기 버튼
    ImageButton cancelButton; //취소하기 버튼

    ArrayAdapter<CharSequence> spinnerAdapter;
    ArrayAdapter<CharSequence> spinnerAdapter2;

    int userAccount;


    private ScheduleEntityObject entityObject;

    public static ScheduleDialogFragment newInstance() {
        ScheduleDialogFragment fragment = new ScheduleDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.CustomDialog);
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil();
        userAccount = sharedPreferenceUtil.getSharedPreference(this.getActivity(),"userAccount");
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


        if (entityObject == null) {
            entityObject = new ScheduleEntityObject();
        }
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


        //spinner adapter
        spinnerAdapter = new ArrayAdapter<CharSequence>(getActivity(),
                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.scheduleCountry)); // 스피너 레이아웃 기본으로 제공.

        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.city));
        citySpinner.setAdapter(spinnerAdapter2);

        countrySpinner.setAdapter(spinnerAdapter);


        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            CountryCodeHashMap countryCodeHashMap = new CountryCodeHashMap();

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String countryName = countrySpinner.getSelectedItem().toString();
                switch (i) {
                    case 1:
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.usaCity));
                        citySpinner.setAdapter(spinnerAdapter2);

                        break;
                    case 2 :
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.jpnCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;
                    case 3 :
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.hkgCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;
                    case 4 :
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.fraCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;
                    case 5 :
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.itaCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;

                    case 6 :
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.thaCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;
                    case 7 :
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.ausCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;
                    case 8 :
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.chnCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;
                    case 9 :
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.phnCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;
                    case 10:
                        spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.twnCity));
                        citySpinner.setAdapter(spinnerAdapter2);
                        break;
                }


                entityObject.country = countryCodeHashMap.getCountryCode(countryName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


                spinnerAdapter2 = new ArrayAdapter<CharSequence>(getActivity(),
                        R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.city));
                spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(spinnerAdapter2);

            }
        });




        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            CityCodeHashMap cityCodeHashMap = new CityCodeHashMap();



            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                entityObject.city = cityCodeHashMap.getCityCode(citySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // entityObject.city = cityCodeHashMap.getCityCode((spinnerAdapter2.getItem(0)).toString());
            }
        });


        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy.MM.dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                departureEditText.setText(sdf.format(myCalendar.getTime()));

            }
        };

        final Calendar arriveCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener arriveDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                arriveCalendar.set(Calendar.YEAR, year);
                arriveCalendar.set(Calendar.MONTH, monthOfYear);
                arriveCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy.MM.dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                returnEditText.setText(sdf.format(arriveCalendar.getTime()));



            }
        };


        departureEditText.setOnClickListener(new View.OnClickListener() { //edittext 클릭 시 datepickerdialog
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(myCalendar.getTimeInMillis()); //날짜제한
                dp.show();
            }
        });

        returnEditText.setOnClickListener(new View.OnClickListener() { //edittext 클릭 시 datepickerdialog
            @Override
            public void onClick(View v) {
                DatePickerDialog dp = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, arriveDate, arriveCalendar
                        .get(Calendar.YEAR), arriveCalendar.get(Calendar.MONTH),
                        arriveCalendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                dp.show();
            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.cancelButton:
                dismiss();
                break;
            case R.id.registerButton:


                entityObject.start = departureEditText.getText().toString();
                entityObject.end = returnEditText.getText().toString();

                if (entityObject.country == null || entityObject.country.length() <= 0) {
                    Toast.makeText(getActivity(), "여행국가를 선택해주세요!", Toast.LENGTH_SHORT).show();

                    return;
                } else if (entityObject.city == null || entityObject.city.length() <= 0) {
                    Toast.makeText(getActivity(), "여행도시를 선택해주세요!", Toast.LENGTH_SHORT).show();

                    return;
                } else if (entityObject.start == null || entityObject.start.length() <= 0) {
                    Toast.makeText(getActivity(), "출발날짜를 입력해주세요!", Toast.LENGTH_SHORT).show();

                    return;

                } else if (entityObject.end == null || entityObject.end.length() <= 0) {
                    Toast.makeText(getActivity(), "돌아오는날짜를 입력해주세요!", Toast.LENGTH_SHORT).show();

                    return;
                } else {
                    new AsyncScheduleInsert().execute(entityObject);

                }

                break;

        }

    }


    public class AsyncScheduleInsert extends AsyncTask<ScheduleEntityObject, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(ScheduleEntityObject... scheduleInfo) {
            boolean flag;
            String resultValue = "";
            ScheduleEntityObject reqParams = scheduleInfo[0];
            Response response = null;
            OkHttpClient toServer;

            toServer = OkHttpInitManager.getOkHttpClient();
            try {
                //요청 Form세팅
                RequestBody postBody = new FormBody.Builder()
                        .add("user", String.valueOf(userAccount))
                        .add("country", reqParams.country)
                        .add("city", reqParams.city)
                        .add("start", reqParams.start)
                        .add("end", reqParams.end)
                        .build();
                //요청 세팅(form(Query String) 방식의 포스트)
                Request request = new Request.Builder()
                        .url(NetworkDefineConstant.SERVER_URL_INSERT_SCHEDULE)
                        .post(postBody)
                        .build();
                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();
                String returedJSON;
                if (flag) { //성공했다면

                    returedJSON = response.body().string();
                    Log.e("resultJSON", returedJSON);
                    try {
                        JSONObject jsonObject = new JSONObject(returedJSON);
                        resultValue = jsonObject.optString("inserId"); //insertId 가져오기
                    } catch (JSONException jsone) {
                        Log.e("json에러", jsone.toString());
                    }
                } else {
                    //요청에러 발생시(http 에러)
                }

            } catch (UnknownHostException une) {
                e("aaa", une.toString());
            } catch (UnsupportedEncodingException uee) {
                e("bbb", uee.toString());
            } catch (Exception e) {
                e("ccc", e.toString());
            } finally {
                if (response != null) {
                    response.close(); //3.* 이상에서는 반드시 닫아 준다.
                }
            }

            return resultValue;
        }

        @Override
        protected void onPostExecute(String result) {
            //  progressDialog.dismiss();
            if (result != null) {
                if (result != null) {//insertId가 null이 아닐경우 토스트

                    //등록하기 버튼 눌렀을 때 이미지 토스트
                    Toast toast = new Toast(getActivity());
                    ImageView img = new ImageView(getActivity());
                    img.setImageResource(R.drawable.home_toast);
                    toast.setView(img);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    dismiss();
                } else {
                    // showDialog(NetworkDefineConstant.BLOOD_INSERT_DIALOG_FAIL, null);

                }
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("message", "입력 중 문제 발생[디버깅]!");
                // showDialog(NetworkDefineConstant.BLOOD_INSERT_DIALOG_FAIL, bundle);
            }
        }
    }
}


