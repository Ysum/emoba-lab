package ch.fhnw.emoba.labctrlr;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;

/**
 * Created by dardanbujupaj on 03.05.16.
 */
public class TouchControlView extends SurfaceView {

    public TouchControlView(Context context) {
        super(context);
    }

    public boolean onTouchEvent(MotionEvent event){
        //TODO touchevents verarbeiten
        System.err.println(event.getX()+"/"+event.getY());
        return false;
    }

    //TODO Neigung der Spielfläche visualisieren

}
