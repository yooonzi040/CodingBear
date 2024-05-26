package sku.jyj.example.silvia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;


public class RegisterActivity extends AppCompatActivity {
    private EditText input_Name, input_PhoneNo, input_Birth;
    private Button btn_InputGuardian, btn_regist_userToTTS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Toolbar toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar.getNavigationIcon().setColorFilter(Color.parseColor("#343A3F"), PorterDuff.Mode.SRC_ATOP);



        //아이디 값 찾아주기
        input_Name = findViewById(R.id.input_Name);
        input_PhoneNo = findViewById(R.id.input_PhoneNo);
        input_Birth = findViewById(R.id.input_Birth);
        btn_regist_userToTTS = findViewById(R.id.btn_regist_userToTTS);
        btn_InputGuardian = findViewById(R.id.btn_InputGuardian);
        btn_InputGuardian.setText(Html.fromHtml("<u>보호자 정보 가입하기</u>"));

        btn_InputGuardian.setOnClickListener(new View.OnClickListener() { //다음으로 버튼을 클릭 시 수행
            @Override
            public void onClick(View view) {
                // 사용자 입력을 가져옵니다.
                String name = input_Name.getText().toString();
                String birth = input_Birth.getText().toString();
                String phoneNo = input_PhoneNo.getText().toString();

                // Register2Activity에 데이터를 전달하기 위해 Intent를 생성합니다.
                Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
                intent.putExtra("name", name);
                intent.putExtra("birth", birth);
                intent.putExtra("phoneNo", phoneNo);

                // Register2Activity를 시작합니다.
                startActivity(intent);
            }
        });

        btn_regist_userToTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, VoiceChatBotActivity.class);
                startActivity(intent);
            }
        });

        // 전화번호 입력값 최대 길이 제한
        /*input_PhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter.LengthFilter(11); // et_age의 입력 최대길이 제한
                input_PhoneNo.setFilters(filters);
            }

            @Override
            public void afterTextChanged(Editable s){}
        });
    }

         */
}}

