package ch.fhnw.emoba.labctrlr;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import netP5.NetAddress;
import oscP5.OscP5;
import oscP5.OscProperties;

/**
 * Created by dardanbujupaj on 24.05.16.
 */
public class ConnectionThread extends HandlerThread {
    private OscP5 oscP5;
    private NetAddress address;

    private Handler handler;





    ConnectionThread(NetAddress address) {
        super("ConnectionThread");
        this.handler= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Message m=new Message();
                //msg.getData().getBinder("")

            }
        };

        this.address=address;

    }
    public Handler getHandler(){
        return this.handler;
    }

    @Override
    public void run() {
        super.run();

        OscProperties oscProperties = new OscProperties();
        long start = System.currentTimeMillis();

        oscProperties.setNetworkProtocol(OscProperties.TCP);
        oscProperties.setRemoteAddress(address);
        oscP5 = new OscP5(this, oscProperties);

        //System.out.println(oscProperties);
        //System.out.println(oscP5);
        System.out.println(System.currentTimeMillis() - start);

        while
    }
}
