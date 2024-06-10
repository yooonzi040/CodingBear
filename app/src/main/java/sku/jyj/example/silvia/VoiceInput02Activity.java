package sku.jyj.example.silvia;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class VoiceInput02Activity extends AppCompatActivity {
    private Button btn_repeat, btn_check, btn_next;
    private ImageButton btn_record;

    private TextView textView36, textView3 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_input02);

        btn_record = findViewById(R.id.btn_record);
        btn_repeat = findViewById(R.id.btn_repeat);
        btn_check = findViewById(R.id.btn_check);
        btn_next = findViewById(R.id.btn_next);

        // [윤지] textView36 중 특정 글자 문자열 바꾸기
        textView36 = findViewById(R.id.textView36);

        String content1 = textView36.getText().toString();
        SpannableString spannableString = new SpannableString(content1);

        String word1 = "원하는 목소리";
        int start = content1.indexOf(word1);
        int end = start + word1.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF8C00")), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView36.setText(spannableString);

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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VoiceInput02Activity.this, VoiceInput03Activity.class);
                startActivity(intent);
            }
        });

        Toolbar voice_toolbar2 = findViewById(R.id.voice_toolbar2);
        setSupportActionBar(voice_toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


}
