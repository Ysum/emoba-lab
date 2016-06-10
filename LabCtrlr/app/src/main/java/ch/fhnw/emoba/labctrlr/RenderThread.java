package ch.fhnw.emoba.labctrlr;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by lukasmusy on 04/05/16.
 */
public class RenderThread extends Thread{
    private SurfaceHolder surfaceHolder;
    TouchControlView touchControlView;
    private boolean running = false;

    public RenderThread(SurfaceHolder surfaceHolder, TouchControlView touchControlView) {
        this.surfaceHolder = surfaceHolder;
        this.touchControlView = touchControlView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
//                    touchControlView.drawJoystick(canvas);
                    touchControlView.postInvalidate();

                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }
        }
    }




}
