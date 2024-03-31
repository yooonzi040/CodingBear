package sku.jyj.example.silvia;


import static android.graphics.Color.rgb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
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


public class LoginActivity extends AppCompatActivity {
    private static final String urls = "http://192.168.0.4:5000/login"; // [★] Flask 서버 호출 URL
    private EditText login_id, login_pw;
    private Button btn_login, btn_register, btn_tts, btn_voice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        login_id = findViewById(R.id.input_id);
        login_pw = findViewById(R.id.input_pw);
        btn_login = findViewById(R.id.btn_logins);
        btn_tts = findViewById(R.id.btn_tts);
        btn_voice = findViewById(R.id.btn_voice);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get 해온다 (가져온다).
               sendServer();
            }
        });

        btn_tts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, VoiceChatBotActivity.class);
                startActivity(intent);
            }
        });

        btn_tts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    btn_tts.setBackgroundColor(Color.rgb(64, 87, 128));
                }
                else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    btn_tts.setBackgroundColor(Color.rgb(255, 140, 0));
                }
                return false;
            }
        });

        btn_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, VoiceInput01Activity.class);
                startActivity(intent);
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
                handleResponse(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    OkHttpClient client = new OkHttpClient(); // OkHttp를 사용하도록 OkHttpClient 객체를 생성

                    JSONObject jsonInput = new JSONObject();  // JSON 객체 생성
                    jsonInput.put("login_id", login_id.getText().toString()); // JSON 객체에 데이터 추가 (id)
                    jsonInput.put("login_pw", login_pw.getText().toString()); // JSON 객체에 데이터 추가 (pw)

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
        // 서버 응답을 처리하는 부분입니다.
        // 예를 들어, 서버에서 "success" 키가 true이면 로그인 성공, 그렇지 않으면 실패로 간주할 수 있습니다.
        // 실제 서버 응답에 맞게 코드를 수정해주세요.
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success"); // flask에서 json 응답을 받아온다. key값이 success인 값을 받아온다.

            if (success) { // success == True 인 경우
                // 로그인 성공 시 MainActivity로 이동
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // 현재 액티비티 종료
            } else { // success == False 인 경우
                // 로그인 실패
                Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}