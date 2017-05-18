package com.example.mobileapplication11_;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

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

    private void init() {
        myPainter = (MyPainter)findViewById(R.id.myPainter);
        checkBox = (CheckBox)findViewById(R.id.checkBox);
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
                // myPainter.Save();
                break;
            case R.id.bOpen:
                break;
            case R.id.bRotate:
                checkBox.setChecked(true);
                break;
            case R.id.bMove:
                checkBox.setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.blur:
                break;
            case R.id.color:
                break;
            case R.id.pen_width:
                break;
            case R.id.pen_color_R:
                break;
            case R.id.pen_color_B:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
