package sku.jyj.example.silvia;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class VoiceChatBotActivity extends AppCompatActivity {
    private static final String urls = "http://172.17.0.1:7126/chatting"; // [★] Flask 서버 호출 URL
    private String savedUserInput; // [hyeonseok] userInputEditText에 입력받은 텍스트를 임시로 저장해놓기 위한 변수
    SpeechRecognizer mRecognizer;
    MediaPlayer mplayer;
    EditText userInputEditText;
    ImageButton btn_sttStart;
    Button btn_voiceplay, btn_settings;
    RecyclerView chatRecyclerView;
    ChatAdapter chatAdapter;
    final int PERMISSION = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicechatbot);


        if (Build.VERSION.SDK_INT >= 23) {
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        btn_sttStart = findViewById(R.id.btn_sttStart);
        btn_voiceplay = findViewById(R.id.btn_voiceplay);
        btn_settings = findViewById(R.id.btn_settings);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatAdapter = new ChatAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);

        userInputEditText = findViewById(R.id.userInputEditText);

        // RecognizerIntent 객체 생성
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");


        // 버튼을 클릭 이벤트 - 객체에 Context와 listener를 할당한 후 실행
        btn_sttStart.setOnClickListener(v -> {
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(intent);
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoiceChatBotActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // 텍스트 입력 전송 버튼 클릭 시
        Button btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(v -> {
            String userMessage = userInputEditText.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                savedUserInput = userMessage; // [hyeonseok] userInputEditText에 입력받은 텍스트를 임시로 저장
                // 사용자 입력을 RecyclerView에 추가
                chatAdapter.addMessage(new ChatMessage(userMessage, true));
                userInputEditText.setText("");
                ClickButton1(v); // [hyeonseok] 서버로 데이터 전송하기 위한 함수 호출
                // 여기다 백엔드 코드 만들기
                // chatAdapter.addMessage(new ChatMessage("챗봇 응답", false));
            }
        });

        // [yunzi] Android studio에서 wav 파일을 재생
        btn_voiceplay.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.alarm01);
                mediaPlayer.start();
            }
        });
    }

    // DTO 모델 - PostResult Class 선언
    public class PostResult {

        @SerializedName("userId")
        private int userId;

        @SerializedName("id")
        private int id;
        // @SerializedName으로 일치시켜 주지않을 경우엔 클래스 변수명이 일치해야함

        private String title;
        // @SerializedName()로 변수명을 입치시켜주면 클래스 변수명이 달라도 알아서 매핑시켜줌

        @SerializedName("body")
        private String bodyValue;

        // toString()을 Override 해주지 않으면 객체 주소값을 출력함
        @Override
        public String toString() {
            return "PostResult{" +
                    "userId=" + userId +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", bodyValue='" + bodyValue + '\'' +
                    '}';
        }
    }

    //Interface 정의 - 사용할 메소드 선언
    public interface RetrofitService {

        //@GET (EndPoint - 자원위치 (URL))
        @GET("chatting/{chat}")
        Call<PostResult> getPosts(@Path("chat")String post);
    }


    // [hyeonseok] Android studio에서 flask로 데이터 전송
    public void ClickButton1(View v){ // 버튼 클릭 리스너
        // 버튼 클릭 시 서버로 데이터 전송
        Log.d("ClickButton1", "Button clicked");  // Log 추가
        sendServer(savedUserInput);
    }

    public void sendServer(String userMessage){ // 서버로 데이터 전송하기 위한 함수
        class sendData extends AsyncTask<Void, Void, String> { // 백그라운드 쓰레드 생성

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // 백그라운드 작업 전에 실행되는 부분
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);

                // 응답 데이터를 처리하는 코드
                if (response != null) {
                    try { //  JSON 형식으로 파싱
                        JSONObject jsonResponse = new JSONObject(response);
                        String aiResponse = jsonResponse.getString("AIChating");

                        // AI의 응답을 RecyclerView에 추가
                        chatAdapter.addMessage(new ChatMessage(aiResponse, false)); // AI의 응답을 RecyclerView에 추가하는 부분

                        // RecyclerView 갱신
                        chatAdapter.notifyDataSetChanged(); //  RecyclerView를 갱신하여 변경된 데이터를 화면에 반영하는 부분
                    } catch (JSONException e) {
                        e.printStackTrace();

                        // JSON 파싱 실패 시 단순 문자열로 처리
                        //chatAdapter.addMessage(new ChatMessage(response, false));
                        //chatAdapter.notifyDataSetChanged();

                        Log.e("onPostExecute", "JSON 파싱 오류: " + e.getMessage());
                    }
                } else {
                    Log.e("onPostExecute", "서버 응답이 null입니다.");
                }
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
                    jsonInput.put("chat",  userMessage); // [vscode-app.py] JSON 객체에 데이터 추가 (id)
                    Log.d("SendServer", "JSON Input: " + jsonInput.toString());

                    RequestBody reqBody = RequestBody.create(
                            jsonInput.toString(),
                            MediaType.parse("application/json; charset=utf-8")
                    );

                    Request request = new Request.Builder()
                            .post(reqBody)
                            .url(urls)
                            .build();

                    Response responses = client.newCall(request).execute(); // 요청을 실행 (동기 처리 : execute(), 비동기 처리 : enqueue())
                    // 서버로부터 받은 응답을 문자열로 변환
                    String responseBody = responses.body().string();

                    return responseBody;
                    //System.out.println(responses.body().string());

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



    // STT매서드
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {}

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러가 발생하였습니다. : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 RecyclerView에 메시지로 추가
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (matches != null && !matches.isEmpty()) {
                String spokenText = matches.get(0);
                chatAdapter.addMessage(new ChatMessage(matches.get(0), true));
                // STT 결과를 Flask 서버로 전송
                sendServer(spokenText);
            }
        }

        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    };
}