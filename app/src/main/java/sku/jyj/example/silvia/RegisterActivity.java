package sku.jyj.example.silvia;

import static sku.jyj.example.silvia.R.id.toolbar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import androidx.appcompat.widget.Toolbar;

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
    private static final String urls = "http://lovelace0124.iptime.org:5003/userJoin";
    private EditText input_guardianName, input_guardianPhoneNo, input_Name, input_PhoneNo, input_Birth;
    private Button bt_register, btn_next1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //아이디 값 찾아주기
        input_guardianName = findViewById(R.id.input_guardianName);
        input_guardianPhoneNo = findViewById(R.id.input_guardianPhoneNo);
        input_Name = findViewById(R.id.input_Name);
        input_PhoneNo = findViewById(R.id.input_PhoneNo);
        input_Birth = findViewById(R.id.input_Birth);
        bt_register = findViewById(R.id.bt_register);
        btn_next1 = findViewById(R.id.btn_next1);


        btn_next1.setOnClickListener(new View.OnClickListener() { //다음으로 버튼을 클릭 시 수행
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
                startActivity(intent);
            }
        });

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

    public void onRegisterClicked(View view){ // 버튼 클릭 리스너
        // 버튼 클릭 시 서버로 데이터 전송
        Log.d("ClickButton1", "Button clicked");  // Log 추가
        sendServer();
    }

    public void sendServer(){ // 서버로 데이터 전송하기 위한 함수
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
                    jsonInput.put("memberBirth", input_Birth.getText().toString());
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
