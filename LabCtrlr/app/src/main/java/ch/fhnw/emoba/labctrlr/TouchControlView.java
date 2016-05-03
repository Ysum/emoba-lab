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
        //map x and y linear from viewsize to 0-180
        float x =event.getX()*180/this.getWidth();
        float y =event.getY()*180/this.getHeight();

        //TODO show orientation ond display (drawJoystick(x,y)???)

        //TODO send x and y to device

        //print x/y for debugging
        System.out.println(Math.round(x)+"/"+Math.round(y));
        return false;
    }

    //TODO Neigung der Spielfläche visualisieren

}
