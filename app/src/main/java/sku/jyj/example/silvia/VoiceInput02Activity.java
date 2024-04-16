package sku.jyj.example.silvia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class VoiceInput02Activity extends AppCompatActivity {
    private Button btn_record, btn_repeat, btn_check, btn_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_input02);

        btn_record = findViewById(R.id.btn_record);
        btn_repeat = findViewById(R.id.btn_repeat);
        btn_check = findViewById(R.id.btn_check);
        btn_next = findViewById(R.id.btn_next);

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
