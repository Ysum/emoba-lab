package ch.fhnw.emoba.labctrlr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    TouchControlView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO hide Titlebar
        super.onCreate(savedInstanceState);
        view = new TouchControlView(this);
        setContentView(view);//R.layout.activity_main);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("main touch");
        return super.onTouchEvent(event);

    }
}

