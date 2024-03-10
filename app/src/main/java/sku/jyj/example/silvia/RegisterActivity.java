package sku.jyj.example.silvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class RegisterActivity extends AppCompatActivity {

    // Flask 서버 호출
    private static final String urls = "http://pascal0124.iptime.org:5003/userJoin";
    public Button btn_birth;
    private String input_Birth;
    private EditText input_guardianName, input_guardianPhoneNo, input_Name, input_PhoneNo;
    private Button bt_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //아이디 값 찾아주기
        input_guardianName = findViewById(R.id.input_guardianName);
        input_guardianPhoneNo = findViewById(R.id.input_guardianPhoneNo);
        input_Name = findViewById(R.id.input_Name);
        input_PhoneNo = findViewById(R.id.input_PhoneNo);
        btn_birth = findViewById(R.id.btn_birth);
        bt_register = findViewById(R.id.bt_register);

        // 전화번호 입력값 최대 길이 제한
        input_PhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(11); // et_age의 입력 최대길이 제한
                input_PhoneNo.setFilters(filters);
            }

            @Override
            public void afterTextChanged(Editable s){}
        });
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        input_Birth = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(this,"Date: "+input_Birth,Toast.LENGTH_SHORT).show();

        sendServer(input_Birth);
    }

    public void onBitrhdayClicked (View view) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onRegisterClicked(View view){ // 버튼 클릭 리스너
        // 버튼 클릭 시 서버로 데이터 전송
        Log.d("ClickButton1", "Button clicked");  // Log 추가
        sendServer(input_Birth);
    }

    public void sendServer(final String memberBirth){ // 서버로 데이터 전송하기 위한 함수
        class sendData extends AsyncTask<Void, Void, String> { // 백그라운드 쓰레드 생성

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // 백그라운드 작업 전에 실행되는 부분
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // 백그라운드 작업이 끝난 후에 실행되는 부분
                // LoginActivity로 이동
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                // 백그라운드 작업 중 갱신이 필요한 경우 실행되는 부분
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
                // 백그라운드 작업이 취소된 경우 실행되는 부분
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                // 백그라운드 작업이 취소된 경우 실행되는 부분
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient(); // OkHttp를 사용하도록 OkHttpClient 객체를 생성

                    JSONObject jsonInput = new JSONObject();  // JSON 객체 생성

                    jsonInput.put("memberName", input_Name.getText().toString());
                    jsonInput.put("memberPhoneNo", input_PhoneNo.getText().toString());
                    jsonInput.put("memberBirth", memberBirth);
                    jsonInput.put("guardianName", input_guardianName.getText().toString());
                    jsonInput.put("guardianPhoneNo", input_guardianPhoneNo.getText().toString());

                    RequestBody reqBody = RequestBody.create(
                            jsonInput.toString(),
                            MediaType.parse("application/json; charset=utf-8")
                    );

                    Request request = new Request.Builder()
                            .post(reqBody)
                            .url(urls)
                            .build();

                    Response responses = client.newCall(request).execute(); // 요청을 실행 (동기 처리 : execute(), 비동기 처리 : enqueue())
                    System.out.println(responses.body().string());

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        // AsyncTask 실행
        sendData sendData = new sendData();
        sendData.execute(); // 웹 서버에 데이터 전송
    }
}
