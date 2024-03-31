package sku.jyj.example.silvia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Register2Activity extends AppCompatActivity {

    private Button btn_next2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Toolbar toolbar5 = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar5);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_next2 = findViewById(R.id.btn_next2);

        btn_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
                startActivity(intent);
            }
        });
    }

}
