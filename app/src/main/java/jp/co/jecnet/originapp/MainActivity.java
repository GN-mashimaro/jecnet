package jp.co.jecnet.originapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.util.ArrayList;

import jp.co.jecnet.originapp.databases.CookingOpenHelper;
import jp.co.jecnet.originapp.ui.CookAddActivity;
import jp.co.jecnet.originapp.ui.CustomDialogFlagment;
import jp.co.jecnet.originapp.util.ActionBarParts;
import jp.co.jecnet.originapp.util.BitmapHelper;

public class MainActivity extends AppCompatActivity {

    private BootstrapButton mDialogButton;
    private BootstrapButton mAddButton;
    private TextView mSearchText;
    private ActionBar mActionBar;
    private CookingOpenHelper mCookingOpenHelper;
    private SQLiteDatabase mDatabase;
    private ArrayList cooking_date;
    private ArrayList cooking_memo;
    private ArrayList cooking_photo;
    private Bitmap mCookingBitmap;
    private BitmapHelper mBitmapHelper;
    private ImageView mMainCookImageView;
    private BootstrapEditText mMainCookEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        final LayoutInflater mInflater = LayoutInflater.from(this);
        ActionBarParts actionBarParts = new ActionBarParts();
        actionBarParts.initActionBar(mActionBar,mInflater);
        mAddButton = actionBarParts.btn_add;


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CookAddActivity.class);
                startActivity(intent);
            }
        });

        mDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ダイアログクラスをインスタンス化
                CustomDialogFlagment dialog = new CustomDialogFlagment();
                // 表示  getFagmentManager()は固定、sampleは識別タグ
                dialog.show(getSupportFragmentManager(),"Main");
            }
        });



    }

    private void init(){
        mDialogButton = findViewById(R.id.mainscreen_btn_dialog);
        mSearchText = findViewById(R.id.searchtext);
        mMainCookImageView = findViewById(R.id.main_imageview);
        mMainCookEditText = findViewById(R.id.main_cooking_memo);
        mActionBar = getSupportActionBar();
        mCookingOpenHelper = new CookingOpenHelper(MainActivity.this);
        mBitmapHelper = new BitmapHelper();
        mMainCookEditText.setVisibility(View.GONE);
        mMainCookImageView.setVisibility(View.GONE);
    }

    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
    public void setSearchData(int year,int month,int second) {
        cooking_date = new ArrayList();
        cooking_memo = new ArrayList();
        cooking_photo = new ArrayList();
        String nowData = year + "年" + month + "月" + second + "日";
        mSearchText.setText("日付 " + nowData);
        mDatabase = mCookingOpenHelper.getReadableDatabase();
        try {
            Cursor cur = mDatabase.query("COOK", new String[]{"cooking_photo_1,cooking_data,cooking_memo"}, "cooking_data=? ", new String[]{nowData}, null, null, null, null);
            while (cur.moveToNext()) {
                mCookingBitmap = mBitmapHelper.getImage(cur.getBlob(0));
                cooking_date.add(cur.getString(1));
                cooking_memo.add(cur.getString(2));
                mMainCookImageView.setVisibility(View.VISIBLE);
                mMainCookEditText.setVisibility(View.VISIBLE);
                mMainCookEditText.setFocusable(false);
                mMainCookImageView.setImageBitmap(mCookingBitmap);
                mMainCookEditText.setText(String.valueOf(cooking_memo.get(0)));
            }
        }catch (SQLException e){
            Log.e("MainActivity","SQLException");
        }finally {
            mDatabase.close();
        }
    }


}
