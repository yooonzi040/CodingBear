package sku.jyj.example.silvia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView name, birth, phoneNo;
    private Button btn_main_settings, btn_main_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        birth = findViewById(R.id.birth);
        phoneNo = findViewById(R.id.phoneNo);
        btn_main_settings = findViewById(R.id.btn_main_settings);
        btn_main_logout = findViewById(R.id.btn_main_Logout);

        Intent intent = getIntent();
        String memberName = intent.getStringExtra("member_name");
        String memberBirth = intent.getStringExtra("member_birth");
        String memberPhoneNo = intent.getStringExtra("member_phone_no");

        name.setText(memberName);
        birth.setText(memberBirth);
        phoneNo.setText(memberPhoneNo);

        btn_main_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // 로그아웃 버튼 클릭 이벤트 처리
        btn_main_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃을 처리하기 위해 LogoutActivity를 실행
                new LogoutActivity(MainActivity.this).execute();
                // 현재 엑티비티를 닫음
                finish();
            }
        });
    }
}
