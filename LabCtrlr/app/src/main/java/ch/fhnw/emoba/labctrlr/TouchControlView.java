package ch.fhnw.emoba.labctrlr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by dardanbujupaj on 03.05.16.
 */
public class TouchControlView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder;
    float width, x, mid_x;
    float height, y, mid_y;

    RenderThread thread;

    public TouchControlView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
//        } else
            if (event.getAction() == MotionEvent.ACTION_UP) {
            x = mid_x;
            y = mid_y;
        }
//        Log.d("TouchControlView: ", "touched: "+x+"/"+y);

        return true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        width =  this.getWidth();
        height = this.getHeight();

        mid_x = width/2;
        mid_y = height/2;
        x = mid_x;
        y = mid_y;

        setWillNotDraw(false);

        thread = new RenderThread(surfaceHolder, this);
        thread.setRunning(true);
        thread.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        drawJoystick(canvas);

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

    public void drawJoystick(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawCircle(mid_x, mid_y, 20, paint);
        paint.setStrokeWidth(5);
        canvas.drawLine(mid_x, mid_y, x, y, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, 10, paint);

    }


    public void resetJoystick(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawCircle(mid_x, mid_y, 20, paint);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mid_x, mid_y, 10, paint);

    }

}
