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

    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer;


    public SensorActivity() {

    }

    protected void onCreate(Bundle savedInstanceState) {
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


        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        for(Sensor s:mSensorManager.getSensorList(Sensor.TYPE_ALL)){
            System.out.println(s.getStringType()+" "+s.getName()+" "+s.getType());
        };
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if(co!=null) con.close();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {

        int y=(int)event.values[2]*2+90;
        int x=(int)event.values[1]*2+90;
        y=y>180?180:(y<0?0:y);

        x=x>180?180:(x<0?0:x);

        Message msg = conHandler.obtainMessage();
        msg.what = 2; //set message code to 1$
        Bundle b=new Bundle();
        b.putInt("x",x);
        b.putInt("y",y);
        msg.setData(b);


        msg.sendToTarget();

    }


}
