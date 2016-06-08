package ch.fhnw.emoba.labctrlr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import netP5.NetAddress;

public class ConnectActivity extends AppCompatActivity {
    volatile ConnectionThread con;
    public Handler conHandler;


    TextView statusText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        //Set Textfields to recent Values
        SharedPreferences settings = getSharedPreferences("lastConnection", 0);
        ((EditText) findViewById(R.id.textfield_ipaddress)).setText(settings.getString("ipAddress", " "));
        ((EditText) findViewById(R.id.textfield_port)).setText(String.valueOf(settings.getInt("portNumber", 0)));

    }



    public void onConnectClick(View v) {

        Intent intent;
        Switch theSwitch=(Switch)findViewById(R.id.theSwitch);
        if(theSwitch.isChecked()){
            intent = new Intent(this, SensorActivity.class);
        }else{
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
    }

    public void onConnectionError() {
        //TODO take argument and adjust status message correspondingly
        statusText = (TextView) findViewById(R.id.textView_status);
        statusText.setText("Failed to connect to " + "[IP]:[Port]");

    }

    protected void onStop() {
        super.onStop();


        //Save Textfieldvalues
        SharedPreferences settings = getSharedPreferences("lastConnection", 0);
        SharedPreferences.Editor editor = settings.edit();
        String ip=((EditText) findViewById(R.id.textfield_ipaddress)).getText().toString();
        int port= Integer.valueOf(((EditText) findViewById(R.id.textfield_port)).getText().toString());
        editor.putString("ipAddress", ip);
        editor.putInt("portNumber", port);
        editor.commit();

    }




}
