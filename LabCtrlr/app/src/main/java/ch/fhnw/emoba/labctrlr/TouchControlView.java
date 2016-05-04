package ch.fhnw.emoba.labctrlr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by dardanbujupaj on 03.05.16.
 */
public class TouchControlView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    float width, x;
    float height, y;

    RenderThread thread;
    boolean running = false;

    public TouchControlView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        x = event.getX();
        y = event.getY();

        //map x and y linear from viewsize to 0-180
        float x_scaled = Math.round(x*180/width);
        float y_scaled = Math.round(y*180/height);

        //TODO send x and y to device

        //print x/y for debugging
        System.out.println(x_scaled+"/"+y_scaled);
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        width =  this.getWidth();
        height = this.getHeight();

        x = width/2;
        y = height/2;

        setWillNotDraw(false);

        thread = new RenderThread(surfaceHolder, this);
        thread.setRunning(true);
        thread.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        //test background draw
        canvas.drawColor(Color.GREEN);
//        drawJoystick(x, y);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            thread.setRunning(false);
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    //WIP
    public void drawJoystick(Canvas canvas, float touch_x, float touch_y) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width/2, height/2, 10, paint);

    }

    //TODO Neigung der Spielfläche visualisieren

}
