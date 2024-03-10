package sku.jyj.example.silvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView name, birth, phoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        birth = findViewById(R.id.birth);
        phoneNo = findViewById(R.id.phoneNo);

        Intent intent = getIntent();
        String memberName = intent.getStringExtra("member_name");
        String memberBirth = intent.getStringExtra("member_birth");
        String memberPhoneNo = intent.getStringExtra("member_phone_no");

        name.setText(memberName);
        birth.setText(memberBirth);
        phoneNo.setText(memberPhoneNo);

    }
}