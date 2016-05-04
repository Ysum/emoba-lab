package ch.fhnw.emoba.labctrlr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConnectActivity extends AppCompatActivity {
    TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    public void onConnectClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onConnectionError() {
        //TODO take argument and adjust status message correspondingly
        statusText = (TextView) findViewById(R.id.textView_status);
        statusText.setText("Failed to connect to "+ "[IP]:[Port]");

    }



}
