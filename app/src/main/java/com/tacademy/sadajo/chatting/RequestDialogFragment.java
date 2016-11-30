package com.tacademy.sadajo.chatting;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tacademy.sadajo.CountryCodeHashMap;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.network.NetworkDefineConstant;
import com.tacademy.sadajo.network.OkHttpInitManager;
import com.tacademy.sadajo.network.chatting.RequestEntityObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.util.Log.e;


public class RequestDialogFragment extends DialogFragment implements View.OnClickListener {


    Spinner requestCountrySpinner; //국가명

    EditText requestProductNameEditText; //상품명
    EditText requestProductPriceEditText; //상품가격
    EditText requestProductOptEditText; //추가요청사항

    ImageButton requestButton; //요청하기버튼
    ImageButton requestCancelButton; //취소버튼

    private RequestEntityObject requestEntityObject;
    ArrayAdapter<CharSequence> countrySpinnerAdapter;

    public static RequestDialogFragment newInstance() {
        RequestDialogFragment fragment = new RequestDialogFragment();

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

        View view = inflater.inflate(R.layout.fragment_request_dialog, container, false);

        if (requestEntityObject == null) {
            requestEntityObject = new RequestEntityObject();
        }

        //spinner adapter
        countrySpinnerAdapter = new ArrayAdapter<CharSequence>(getActivity(),
                R.layout.select_scheduledialog_item, getResources().getStringArray(R.array.scheduleCountry)); // 스피너 레이아웃 기본으로 제공.
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        requestCountrySpinner = (Spinner) view.findViewById(R.id.requestCountrySpinner);
        requestCountrySpinner.setAdapter(countrySpinnerAdapter);

        requestCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CountryCodeHashMap countryCodeHashMap = new CountryCodeHashMap();
                String countryCode = countryCodeHashMap
                        .getCountryCode(requestCountrySpinner.getSelectedItem().toString());
                requestEntityObject.countryName = countryCode;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        requestProductNameEditText = (EditText) view.findViewById(R.id.requestProductNameEditText);
        requestProductPriceEditText = (EditText) view.findViewById(R.id.requestProductPriceEditText);
        requestProductOptEditText = (EditText) view.findViewById(R.id.requestProductOptEditText);

        requestButton = (ImageButton) view.findViewById(R.id.requestButton);
        requestCancelButton = (ImageButton) view.findViewById(R.id.requestCancelButton);

        requestButton.setOnClickListener(this);
        requestCancelButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestButton:
                requestEntityObject.productName = requestProductNameEditText.getText().toString();
                requestEntityObject.productPrice = requestProductPriceEditText.getText().toString();
                requestEntityObject.productOpt = requestProductOptEditText.getText().toString();

                if (requestEntityObject.countryName == null || requestEntityObject.countryName.length() <= 0) {
                    Toast.makeText(getActivity(), "국가명을 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (requestEntityObject.productName == null || requestEntityObject.productName.length() <= 0) {
                    Toast.makeText(getActivity(), "상품명을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (requestEntityObject.productPrice == null || requestEntityObject.productPrice.length() <= 0) {
                    Toast.makeText(getActivity(), "상품가격을 입력해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    requestButton.setVisibility(View.GONE);
                    requestCancelButton.setVisibility(View.GONE);
                    new AsyncRequestInsert().execute(requestEntityObject);
                }

                break;
            case R.id.requestCancelButton:
                dismiss();
                break;

        }
    }

    public class AsyncRequestInsert extends AsyncTask<RequestEntityObject, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(RequestEntityObject... requestInfo) {
            boolean flag;
            String resultValue = "";
            RequestEntityObject reqParams = requestInfo[0];
            Response response = null;
            OkHttpClient toServer;

            toServer = OkHttpInitManager.getOkHttpClient();
            try {
                //요청 Form세팅
                RequestBody postBody = new FormBody.Builder()
                        .add("req", "1") //요청하는 userCode
                        .add("carr", "3") //요청받는 userCode
                        .add("country", reqParams.countryName)// 상품국가명
                        .add("goods", reqParams.productName) //상품명
                        .add("price", reqParams.productPrice) //상품가격
                        .add("detail", reqParams.productOpt) //추가요청사항
                        .build();
                //요청 세팅(form(Query String) 방식의 포스트)
                Request request = new Request.Builder()
                        .url(NetworkDefineConstant.SERVER_URL_INSERT_REQUEST)
                        .post(postBody)
                        .build();
                //동기 방식
                response = toServer.newCall(request).execute();

                flag = response.isSuccessful();
                String returedJSON;
                if (flag) { //성공했다면

                    returedJSON = response.body().string();
                    e("resultJSON", returedJSON);
                    try {
                        JSONObject jsonObject = new JSONObject(returedJSON);
                        resultValue = jsonObject.optString("inserId"); //insertId 가져오기
                    } catch (JSONException jsone) {
                        e("json에러", jsone.toString());
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

                    //Todo : 토스트 이미지 받아서 바꿔주기
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
