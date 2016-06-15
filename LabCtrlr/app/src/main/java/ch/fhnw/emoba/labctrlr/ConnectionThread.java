package ch.fhnw.emoba.labctrlr;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

import netP5.NetAddress;
import netP5.NetInfo;
import oscP5.OscMessage;
import oscP5.OscP5;
import oscP5.OscPacket;
import oscP5.OscProperties;

/**
 * Created by dardanbujupaj on 24.05.16.
 */
public class ConnectionThread extends HandlerThread {
    private OscP5 oscP5;
    private String ip;
    private int port;
    private Connector connection;
    private Activity caller;
    private long timer;

    private Handler handler;

    ConnectionThread(String name){
        super(name);
    }

    ConnectionThread(String ip, int port, Activity caller) {
        super("ConnectionThread");
        this.ip = ip;
        this.port = port;
        this.caller = caller;
        this.timer = System.currentTimeMillis();
    }

    public void prepareHandler(){
        this.handler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case 1:
                        Log.d("ConnectionThread", "msg.what == 1 called");
                        if(connection == null) {
                            connection = new Connector();
                        }
                        connection.run();
                        break;
                    case 2:
                        if(connection == null) {
                            Log.d("ConnectionThread","no Connection!");
                        }else{
                            Log.d("ConnectionThread","Message 2 received!");
                            int x=msg.getData().getInt("x");
                            int y=msg.getData().getInt("y");
                            Log.d("ConnectionThread", x + "/" + y);
                            if(System.currentTimeMillis() - timer >= 20) {
                                OscMessage m = new OscMessage("Lab");
                                m.add(0);
                                m.add(x);
                                m.add(1);
                                m.add(y);
                                oscP5.send(m);
                                timer = System.currentTimeMillis();
                            }
                        }
                        break;
                }
            }
        };
    }

    public void close(){


        System.out.println("disconnected");
    }

    public String toString(){ //to avoid exception...
        return "ConnectionThread";
    }

    public Handler getHandler(){
        return this.handler;
    }

    public class Connector extends Thread{
        @Override
        public void run(){
            //        super.run();
            OscProperties oscProperties = new OscProperties();
            long start = System.currentTimeMillis();

            oscProperties.setNetworkProtocol(OscProperties.TCP);
            oscProperties.setRemoteAddress(new NetAddress(ip, port));
            oscP5 = new OscP5(this, oscProperties);
            //NetInfo tmp = oscP5.netInfo();

            //Log.d("ConnectionThread: ", "wan: "+ tmp.wan() + " lan: " + tmp.lan());
            /*while(true) {
                sendCoordinates(pos.getX(), pos.getY());
                Log.d("ConnectionThread: ", "sending pos: " + pos.getX() + " / " + pos.getY());
            }*/
            //System.out.println(oscProperties);
            //System.out.println(oscP5);
           // System.out.println(System.currentTimeMillis() - start);
            //while
        }

        public void sendCoordinates(int x, int y){
            OscMessage OscMsg = new OscMessage("lab");
            OscMsg.add(0);
            OscMsg.add(x);
            OscMsg.add(1);
            OscMsg.add(y);
            Log.d("Connector", "Message sent");
            oscP5.send(OscMsg);
        }
    }
}
