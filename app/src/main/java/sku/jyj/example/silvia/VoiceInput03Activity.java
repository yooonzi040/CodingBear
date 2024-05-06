package sku.jyj.example.silvia;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VoiceInput03Activity extends AppCompatActivity {

    private Button btn_repeat, btn_check, btn_next;
    private ImageButton btn_record;
    private TextView textView40, textView42;
    Dialog dialog_voice_input;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_input03);

        btn_record = findViewById(R.id.btn_record);
        btn_repeat = findViewById(R.id.btn_repeat);
        btn_check = findViewById(R.id.btn_check);
        btn_next = findViewById(R.id.btn_next);

        dialog_voice_input = new Dialog(VoiceInput03Activity.this);
        dialog_voice_input.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_voice_input.setContentView(R.layout.dialog_voice_input);


        // [윤지] textView36 중 특정 글자 문자열 바꾸기
        textView40 = findViewById(R.id.textView40);

        String content1 = textView40.getText().toString();
        SpannableString spannableString = new SpannableString(content1);

        String word1 = "원하는 목소리";
        int start = content1.indexOf(word1);
        int end = start + word1.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF8C00")), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView40.setText(spannableString);

        // [윤지] textView3 중 특정 글자 문자열 바꾸기
        textView42 = findViewById(R.id.textView42);

        String content2 = textView42.getText().toString();
        SpannableString spannableString1 = new SpannableString(content2);

        String word2 = "문장 읽어보기";
        int start1 = content2.indexOf(word2);
        int end1 = start1 + word1.length();

        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF8C00")), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView42.setText(spannableString1);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }
    public void showDialog(){
        dialog_voice_input.show();

        Button Btn_no = dialog_voice_input.findViewById(R.id.btn_no);
        Btn_no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog_voice_input.dismiss(); // 다이얼로그 닫기
            }
        });
        Button Btn_yes = dialog_voice_input.findViewById(R.id.btn_yes);
        Btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VoiceChatBotActivity.class);
                startActivity(intent);
            }
        });
    }

}
