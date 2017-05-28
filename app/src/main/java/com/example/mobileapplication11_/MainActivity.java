package com.example.mobileapplication11_;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox;
    MyPainter myPainter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        myPainter = (MyPainter)findViewById(R.id.myPainter);
    }

    private void checkPer() {//권한 확인 후 수락여부 확인
        int permissioninfo = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(!(permissioninfo == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
    }

    public String  getExternalPath(){//외부 메모리 쓰기 위해 경로 가져오기
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        }
        else{
            sdPath = getFilesDir()+"mymemo/";
        }
        return sdPath;
    }

    private void makeDir() {//외부 메모리에 디렉토리 만들기
        String path = getExternalPath();
        File file = new File(path + "mymemo");
        file.mkdir();
        Toast.makeText(this,"디렉토리 생성",Toast.LENGTH_SHORT).show();
    }


    private void init() {
        myPainter = (MyPainter)findViewById(R.id.myPainter);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                myPainter.setCheckbox(b);
                if(myPainter.blur == true){
                    myPainter.Blur(true);
                }
                else{
                    myPainter.Blur(false);
                }
                if(myPainter.color == true) {
                    myPainter.Color(true);
                }
                else{
                    myPainter.Color(false);
                }
            }
        });
        checkPer();
        makeDir();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bEraser:
                myPainter.Eraser();
                break;
            case R.id.bSave:
                myPainter.Save(getExternalPath() + "sample.jpg");
                break;
            case R.id.bOpen:
                myPainter.Open(getExternalPath() + "sample.jpg");
                break;
            case R.id.bRotate:
                checkBox.setChecked(true);
                myPainter.setCheckbox(true);
                myPainter.Rotate();
                break;
            case R.id.bMove:
                checkBox.setChecked(true);
                myPainter.setCheckbox(true);
                myPainter.Trans();
                break;
            case R.id.bScale:
                checkBox.setChecked(true);
                myPainter.setCheckbox(true);
                myPainter.Scale();
                break;
            case R.id.bSkew:
                checkBox.setChecked(true);
                myPainter.setCheckbox(true);
                myPainter.Skew();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.blur:
                if(item.isChecked()){
                    item.setChecked(false);
                    myPainter.Blur(false);
                }
                else{
                    item.setChecked(true);
                    myPainter.Blur(true);
                }
                break;
            case R.id.color:
                if(item.isChecked()) {
                    item.setChecked(false);
                    myPainter.Color(false);
                }
                else{
                    item.setChecked(true);
                    myPainter.Color(true);
                }
                break;
            case R.id.pen_width:
                if(item.isChecked()) {
                    item.setChecked(false);
                    myPainter.setPenWidth(3);
                }
                else{
                    item.setChecked(true);
                    myPainter.setPenWidth(5);
                }
                break;
            case R.id.pen_color_R:
                myPainter.setPenColor("red");
                break;
            case R.id.pen_color_B:
                myPainter.setPenColor("blue");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
