package sku.jyj.example.silvia;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VoiceInput01Activity extends AppCompatActivity {

    // Flask 서버 호출 URL
    private static final String urls = "http://lovelace0124.asuscomm.com:5003/hyeonseokzangzang";

    //오디오 파일 녹음 관련 변수
    private MediaRecorder mediaRecorder;
    private String audioFileName; // 오디오 녹음 생성 파일 이름
    private boolean recording = false; // 현재 녹음 중인지 확인
    private URL audioUrl = null; // 오디오 파일 URL

    // 오디오 파일 재생 관련 변수
    private MediaPlayer mediaPlayer = null;
    private Boolean playing = false;

    final int PERMISSION = 1;
    private Button  btn_repeat, btn_check, btn_next;
    private ImageButton btn_record;
    private TextView textView34, textView3;
    private Intent intent;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_input01);

        btn_record = findViewById(R.id.btn_record);
        btn_repeat = findViewById(R.id.btn_repeat);
        btn_check = findViewById(R.id.btn_check);
        btn_next = findViewById(R.id.btn_next);

        // [윤지] textView34 중 특정 글자 문자열 바꾸기
        textView34 = findViewById(R.id.textView34);

        String content1 = textView34.getText().toString();
        SpannableString spannableString = new SpannableString(content1);

        String word1 = "원하는 목소리";
        int start = content1.indexOf(word1);
        int end = start + word1.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF8C00")), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView34.setText(spannableString);

        // [윤지] textView3 중 특정 글자 문자열 바꾸기
        textView3 = findViewById(R.id.textView3);

        String content2 = textView3.getText().toString();
        SpannableString spannableString1 = new SpannableString(content2);

        String word2 = "문장 읽어보기";
        int start1 = content2.indexOf(word2);
        int end1 = start1 + word1.length();

        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF8C00")), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView3.setText(spannableString1);


        //퍼미션 체크
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO}, PERMISSION);
        }

        Toolbar voice_toolbar1 = findViewById(R.id.voice_toolbar1);
        setSupportActionBar(voice_toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // '녹음' 버튼을 클릭하면 녹음 시작
        btn_record.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View v) {
                if (!recording) { //녹음 시작
                    StartRecord();
                    Toast.makeText(getApplicationContext(), "녹음을 시작하겠습니다.", Toast.LENGTH_SHORT).show();
                }
                else { //이미 녹음 중이면 녹음 중지
                    StopRecord();
                }
            }
        });

        // '다음' 버튼 클릭하면 다음 페이지로 이동
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoiceInput01Activity.this, VoiceInput02Activity.class);
                startActivity(intent);
            }
        });

        // '다시 말하기' 버튼 클릭하면 최근 녹음된 파일 들려주기
        btn_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playing) { //재생 시작
                    //PlayRecord(file);
                    Toast.makeText(getApplicationContext(), "녹음을 재생하겠습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    StopAudio();
                }
            }
        });

    }

    // 녹음 시작 (녹음 파일 저장 위치 지정 안함)
    private void StartRecord() {

        recording = true;
        //파일의 외부 경로 확인
        String recordPath = getExternalFilesDir("/").getAbsolutePath();
        // 파일 이름 변수를 현재 날짜가 들어가도록 초기화. 그 이유는 중복된 이름으로 기존에 있던 파일이 덮어 쓰여지는 것을 방지하고자 함.
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        audioFileName = recordPath + "/" +"RecordExample_" + timeStamp + "_"+"audio.mp4";

        //Media Recorder 생성 및 설정
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //오디오 소스 설정
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);  // 녹음된 오디오 출력 방식
        mediaRecorder.setOutputFile(audioFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //녹음 시작
        mediaRecorder.start();
    }

    // 녹음 중지
   private void StopRecord() {
       recording = false;

       // 녹음 종료
       mediaRecorder.stop();
       mediaRecorder.release();
       mediaRecorder = null;

       // 파일 경로(String) 값을 Url로 변환해서 저장
      try {
           audioUrl = new URL("file://" + audioFileName);
       } catch (MalformedURLException e) {
           e.printStackTrace();
       }

       Toast.makeText(getApplicationContext(), "음성 기록을 중지합니다.", Toast.LENGTH_SHORT).show();
   }

   // 녹음 재생 (재생시킬 파일 위치 지정 안함)
    private void PlayRecord(File file) {
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playing = true;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                StopAudio();
            }
        });
    }

    // 녹음 재생 중지
    private void StopAudio() {
        playing = false;
        mediaPlayer.stop();
    }
}

