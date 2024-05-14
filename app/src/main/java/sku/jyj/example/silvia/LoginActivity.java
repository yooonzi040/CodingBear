package sku.jyj.example.silvia;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class LoginActivity extends AppCompatActivity {
    private static final String urls = "http://lovelace0124.asuscomm.com:5003/login"; // [★] Flask 서버 호출 URL
    private EditText input_loginName, input_loginBirth, input_loginPhoneNo;
    private Button btn_loginToTTS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        input_loginName = findViewById(R.id.input_loginName);
        input_loginBirth = findViewById(R.id.input_loginBirth);
        input_loginPhoneNo = findViewById(R.id.input_loginPhoneNo);
        btn_loginToTTS = findViewById(R.id.btn_loginToTTS); // [윤지] '로그인' 버튼

        // 아래 버튼들은 voicechatbot 화면쪽으로 넘어가야 함

        //[윤지] 로그인 버튼 클릭시 sendServer 호출 및 VoiceChatBotActivity로 이동
        btn_loginToTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get 해온다 (가져온다).
//                Intent intent = new Intent(LoginActivity.this, VoiceChatBotActivity.class);
//                startActivity(intent);
                // 버튼 클릭시 서버로 데이터 전송 후 로그인 정보가 맞을시 sendServer();에서 VoiceChatBotActivity로 이동
                sendServer();
            }
        });
    }


    public void sendServer() { // 서버로 데이터 전송하기 위한 함수
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

                // 서버로부터의 응답을 처리
                if (s != null) {
                    handleResponse(s);
                } else {
                    // 서버 응답이 없을 때 처리
                    Toast.makeText(LoginActivity.this, "서버 응답이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient(); // OkHttp를 사용하도록 OkHttpClient 객체를 생성

                    JSONObject jsonInput = new JSONObject();  // JSON 객체 생성
                    jsonInput.put("loginName", input_loginName.getText().toString());
                    jsonInput.put("loginBirth", input_loginBirth.getText().toString());
                    jsonInput.put("loginPhoneNo", input_loginPhoneNo.getText().toString());

                    RequestBody reqBody = RequestBody.create(
                            jsonInput.toString(),
                            MediaType.parse("application/json; charset=utf-8")
                    );

                    Request request = new Request.Builder()
                            .post(reqBody)
                            .url(urls)
                            .build();

                    Response responses = client.newCall(request).execute(); // 요청을 실행 (동기 처리 : execute(), 비동기 처리 : enqueue())
                    return responses.body().string();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    // IOException 발생 시, null 반환하여 onPostExecute에서 처리
                    return null;
                }
                return null;
            }
        }

        // AsyncTask 실행
        sendData sendData = new sendData();
        sendData.execute(); // 웹 서버에 데이터 전송
    }

    // 서버 응답 처리
    private void handleResponse(String response) {
        try {
            if (response != null) { // flask에서 json 응답을 받아온다. key값이 success인 값을 받아온다.
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");

                if (success) { // success == True 인 경우
                    // 로그인 성공 시 VoiceChatBotActivity로 이동
                    Intent intent = new Intent(LoginActivity.this, VoiceChatBotActivity.class);
                    startActivity(intent);
                    finish();
                } else { // success == False 인 경우
                    // 로그인 실패
                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}