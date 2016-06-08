package ch.fhnw.emoba.labctrlr;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by dardanbujupaj on 08.06.16.
 */
public class SensorActivity extends Activity
        implements SensorEventListener{


    private ConnectionThread con;
    Handler conHandler;

    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;


    public SensorActivity() {
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }

    protected void onCreate(Bundle savedInstanceState) {
        //TODO hide Titlebar
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sensor);


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

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        Log.d("Sensor",event.values[0]+"/"+event.values[1]+"/"+event.values[2]);

    }


}
