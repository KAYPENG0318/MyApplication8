package com.wanna.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    Button bt1;
    ImageView imageView;
    private File tempFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide(); //隱藏標題
        setContentView(R.layout.activity_main);
        this.tempFile = new File("/sdcard/a.jpg");
        //找尋Button按鈕
        bt1 = (Button)findViewById(R.id.petButton);
        //設定按鈕內文字
        //bt1.setText("選擇圖片");
        //設定按鈕監聽式
        bt1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //開啟Pictures畫面Type設定為image
                intent.setType("image/*");
                //使用Intent.ACTION_GET_CONTENT這個Action  //會開啟選取圖檔視窗讓您選取手機內圖檔
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("crop", "true");// crop=true 有這句才能叫出裁剪頁面.
                intent.putExtra("aspectX", 1);// 这兩項為裁剪框的比例.
                intent.putExtra("aspectY", 1);// x:y=1:1
                intent.putExtra("output", Uri.fromFile(tempFile));
                intent.putExtra("outputFormat", "JPEG");//返回格式
                //取得相片後返回本畫面
                startActivityForResult(Intent.createChooser(intent,"選擇圖片"),1);
            }

        });

        Spinner sp = (Spinner)findViewById(R.id.petKind);
        ArrayAdapter<CharSequence> choosekind = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.kind,
                android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(choosekind);

        Calendar mCal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy-MM-dd", mCal.getTime());    // kk:24小時制, hh:12小時制


        tv1=(TextView)findViewById(R.id.textView1);
         tv1.setText(String.valueOf(s.toString()));
    }


    //取得相片後返回的監聽式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        ImageView imageView = (ImageView) findViewById(R.id.petImage);

        //當使用者按下確定後
        if (resultCode == RESULT_OK) {

            // 設定到ImageView
            imageView.setImageDrawable(Drawable.createFromPath(tempFile.getAbsolutePath()));

        }
//        //當使用者按下確定後
//        if (resultCode == RESULT_OK) {
//            Uri uri = data.getData();//取得圖檔的路徑位置
//            Log.d("uri", uri.toString());//寫log
//            //抽象資料的接口
//            ContentResolver cr = this.getContentResolver();
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));//由抽象資料接口轉換圖檔路徑為Bitmap
//                imageView = (ImageView) findViewById(R.id.petImage);//取得圖片控制項ImageView
//                imageView.setImageBitmap(bitmap);// 將Bitmap設定到ImageView
//            } catch (FileNotFoundException e) {
//                Log.e("Exception", e.getMessage(),e);
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
