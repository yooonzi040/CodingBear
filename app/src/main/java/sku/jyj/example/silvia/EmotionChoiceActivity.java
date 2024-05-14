package sku.jyj.example.silvia;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class EmotionChoiceActivity extends AppCompatActivity {

    private TextView textView53;

    private Button emotionHappy, emotionSad, emotionSurprise, emotionHorror, emotionAngry, emotionDisgust;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);

        TableLayout tableLayout = findViewById(R.id.TableLayout);
        final ImageView imageView = findViewById(R.id.emotion_image);
        final TextView textView = findViewById(R.id.textView54);


        // [윤지] Toolbar에 뒤로가기 버튼 만들기
        Toolbar toolbar7 = findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar7);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // [윤지] textView53 중 특정 글자 문자열 바꾸기
        TextView textView53 = findViewById(R.id.textView53);  // TextView 타입 선언 추가

        String content1 = textView53.getText().toString();
        SpannableString spannableString = new SpannableString(content1);

        String word1 = "기분";
        int start = content1.indexOf(word1);
        int end = start + word1.length();

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF8C00")), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView53.setText(spannableString);

        // 리스너 정의
        View.OnTouchListener handleTouch = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 버튼을 누르면 emotion_press.xml 배경으로 변경
                    view.setBackgroundResource(R.drawable.emotion_press);

                    String buttonName = getResources().getResourceEntryName(view.getId());
                    int imageId = getResources().getIdentifier(buttonName + "_image", "drawable", getPackageName());
                    imageView.setImageResource(imageId);

                    String buttonText = view.getTag().toString();
                    textView.setText(buttonText);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    // 버튼에서 손을 떼면 emotion_unpress.xml 배경으로 변경
                    view.setBackgroundResource(R.drawable.emotion_unpress);
                }
                return true;  // 터치 이벤트를 처리했으므로 true를 반환
            }
        };

        // TableLayout의 모든 TableRow 순회
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View child = tableLayout.getChildAt(i);

            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;

                // TableRow의 모든 자식 뷰 순회
                for (int j = 0; j < row.getChildCount(); j++) {
                    View rowChild = row.getChildAt(j);

                    if (rowChild instanceof Button) { // 자식 뷰가 Button인 경우
                        Button button = (Button) rowChild;
                        button.setOnTouchListener(handleTouch); // 리스너 설정
                    }
                }
            }
        }
    }
}