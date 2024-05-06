package sku.jyj.example.silvia;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class VoiceChoiceAdapter extends RecyclerView.Adapter<VoiceChoiceAdapter.ViewHolder> {
    private ArrayList<String> mData = null;


    // [윤지] 아이템 뷰를 저장하는 뷰 홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView voice_name, voice_date;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조 (hold strong reference)
            voice_name = itemView.findViewById(R.id.voice_name);;

        }
    }


    // 생성자에서 데이터 리스트 객체를 전달받음
    VoiceChoiceAdapter(ArrayList<String> list) {
        mData = list;
    }


    @NonNull
    @Override
    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.voicechoice_item, parent, false);
        VoiceChoiceAdapter.ViewHolder vh = new VoiceChoiceAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    public void onBindViewHolder(VoiceChoiceAdapter.ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.voice_name.setText(text);
    }

    // getItemCount() - 전체 데이터 갯수 리턴
    public int getItemCount() {
        return mData.size();
    }
}
