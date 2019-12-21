package jp.co.jecnet.originapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import jp.co.jecnet.originapp.ui.CustomDialogFlagment;

public class MainActivity extends AppCompatActivity {

    private BootstrapButton dialogbutton;
    private TextView searchtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogbutton = findViewById(R.id.mainscreen_btn_dialog);
        dialogbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ダイアログクラスをインスタンス化
                CustomDialogFlagment dialog = new CustomDialogFlagment();
                // 表示  getFagmentManager()は固定、sampleは識別タグ
                dialog.show(getSupportFragmentManager(),"Main");
            }
        });
        searchtext = findViewById(R.id.searchtext);


    }

    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
    public void setSearchData(int year,int month,int second){
        searchtext.setText("日付 "+year+"年"+month+"月"+second+"日");
    }

}
