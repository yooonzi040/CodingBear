package sku.jyj.example.silvia;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LogoutActivity extends AsyncTask<Void, Void, Boolean> {
    private Context mContext;

    public LogoutActivity(Context context) {
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://pascal0124.iptime.org:5003/logout")
                    .build();
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // 로그아웃 성공 처리
            Toast.makeText(mContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            // 로그인 액티비티로 이동하도록 설정
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mContext.startActivity(intent);
        } else {
            // 로그아웃 실패 처리
            Toast.makeText(mContext, "로그아웃 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
