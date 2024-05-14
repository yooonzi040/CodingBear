package sku.jyj.example.silvia;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VoiceChoiceActivity extends AppCompatActivity {

    private Button btn_voiceadd, btn_setting01;
    private TextView textView45;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicechoice);

        btn_voiceadd = findViewById(R.id.btn_voiceadd);
        btn_setting01 = findViewById(R.id.btn_setting01);
        textView45 = findViewById(R.id.textView45);

        String content = textView45.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "막내 아들";
        int start = content.indexOf(word);
        int end = start + word.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF8C00")), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView45.setText(spannableString);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.format("TEXT %d", i));
        }

        RecyclerView recyclerView = findViewById(R.id.rv01);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // VoiceChoiceAdapter의 생성자에 리스트를 전달
        VoiceChoiceAdapter voiceChoiceAdapter = new VoiceChoiceAdapter(list);
        recyclerView.setAdapter(voiceChoiceAdapter);

        btn_voiceadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoiceChoiceActivity.this, VoiceInput01Activity.class);
                startActivity(intent);
            }
        });

        btn_setting01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoiceChoiceActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });



    }

}
