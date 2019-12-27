package jp.co.jecnet.originapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import jp.co.jecnet.originapp.R;
import jp.co.jecnet.originapp.databases.CookingOpenHelper;
import jp.co.jecnet.originapp.model.CookingData;
import jp.co.jecnet.originapp.util.BitmapHelper;

public class CookAddActivity extends AppCompatActivity {
    private Bitmap defalutImage;
    private ImageView cooking_photo_1;
   // private ImageView cooking_photo_2;
    private TextView cooking_data;
    private BootstrapEditText cooking_memo;
    private BootstrapButton cooking_btn_add;
    private int REQUEST_GALLERY = 0001;
    private Bitmap cooking_bitmap;
    private CookingOpenHelper mCookingOpenHelper;
    private BitmapHelper mBitmapHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_add);
        setTitle("追加画面");
        if (Build.VERSION.SDK_INT >= 23) {
            checkFilePermission();
        }
        mCookingOpenHelper = new CookingOpenHelper(CookAddActivity.this);
        mBitmapHelper = new BitmapHelper();
        cooking_photo_1 = findViewById(R.id.imageView);
        cooking_photo_1.setVisibility(View.GONE);
        //cooking_photo_2 = findViewById(R.id.imageView2);
        cooking_data = findViewById(R.id.textView6);
        cooking_memo = findViewById(R.id.textView7);
        cooking_btn_add = findViewById(R.id.cook_main_btn_add);
        cooking_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cooking_memo.getText().toString().equals("")
                        && cooking_bitmap != null){
                    CookingData cookingData = new CookingData();
                    cookingData.setCooking_photo_1(mBitmapHelper.getBytes(cooking_bitmap));
                    cookingData.setCooking_data(cooking_data.getText().toString());
                    cookingData.setCooking_memo(cooking_memo.getText().toString());
                    mCookingOpenHelper.insertCookDate(cookingData);
                    finish();
                } else {
                    Toasty.error(CookAddActivity.this,"未入力がございます。").show();
                }
            }
        });
        cooking_memo.setMinLines(5);
        cooking_memo.setMaxLines(7);

        cooking_photo_1.setImageBitmap(defalutImage);
        //cooking_photo_2.setImageBitmap(defalutImage);
        cooking_data.setText(getNowDate());

    }

    public static String getNowDate(){
        final DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    // ストレージの確認
    public void checkFilePermission() {
        // 既に許可している
        if (ContextCompat.checkSelfPermission(CookAddActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(CookAddActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
        }
        // 拒否していた場合
        else {
            requestLocationPermission();
        }
    }
    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(CookAddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(CookAddActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CookAddActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 参照するリソースは上でリソースファイルに付けた名前と同じもの
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // メニューが選択されたときの処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem1:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALLERY);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                cooking_bitmap = BitmapFactory.decodeStream(in);
                cooking_photo_1.setVisibility(View.VISIBLE);
                cooking_photo_1.setImageBitmap(cooking_bitmap);
                in.close();
            } catch (Error e) {

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
