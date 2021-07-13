package kr.co.softcampus.covid19;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RowListView extends LinearLayout {

    TextView textview, textview2, textview3;

    public RowListView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RowListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.row,this,true);

        textview = findViewById(R.id.textView2);
        textview2 = findViewById(R.id.textView3);
        textview3 = findViewById(R.id.textView4);
    }

    public void setRssItem(RssItem item){
        String accdefrate = item.getAccDefRate();
        textview3.setText(accdefrate);

        String accexamcnt = item.getAccExamCnt();
        textview.setText(accexamcnt);

        String accexamcompcnt = item.getAccExamCompCnt();
        textview2.setText(accexamcompcnt);
    }
}
