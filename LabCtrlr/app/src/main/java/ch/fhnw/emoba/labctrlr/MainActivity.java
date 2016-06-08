package ch.fhnw.emoba.labctrlr;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

public class MainActivity extends Activity {
    TouchControlView view;
    ConnectionThread con;
    Handler conHandler;
    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO hide Titlebar
        super.onCreate(savedInstanceState);
        view = new TouchControlView(this);
        setContentView(view);//R.layout.activity_main);


        SharedPreferences settings = getSharedPreferences("lastConnection", 0);
        String ip=settings.getString("ipAddress", " ");
        int port=settings.getInt("portNumber", 0);


        Log.d("ConnectActivity: ", "connecting:" + ip + " " + port);
        con = new ConnectionThread(ip,port,this);
        con.start(); //start the thread
        con.prepareHandler(); //prepare Handler
        conHandler=con.getHandler(); //get the handler
        Message msg = conHandler.obtainMessage(); //get a message
        msg.what = 1; //set message code to 1
        msg.sendToTarget(); // send message to conHandler

    }


    @Override
    protected void onDestroy() {
        con.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(height==0||width==0){
            height=view.getHeight();
            width=view.getWidth();
        }

        System.out.println("main touch");
        float x = event.getX();
        float y = event.getY();

        //map x and y linear from viewsize to 0-180
        int x_scaled = Math.round(x * 180 / width);
        int y_scaled = Math.round(y * 180 / height);
        y_scaled=y_scaled>180?180:y_scaled;

        Message msg = conHandler.obtainMessage();
        msg.what = 2; //set message code to 1$
        Bundle b=new Bundle();
        b.putInt("x",x_scaled);
        b.putInt("y",y_scaled);
        msg.setData(b);


        msg.sendToTarget(); // send message to conHandler

        //print x/y for debugging
        System.out.println(x_scaled+"/"+y_scaled);
        return super.onTouchEvent(event);

    }
}

