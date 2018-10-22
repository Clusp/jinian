package com.aoao.jinian;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.*;
import android.graphics.*;

/**
 * Created by asus on 2018/9/8.
 */


public class SurfView extends SurfaceView
        implements SurfaceHolder.Callback {
    private WindowManager wm;
    private boolean canDraw;
    private SurfaceHolder sfh;
    private Paint paint;
    private Canvas canvas;
    private DrawThread dt;
    private Bitmap bt;
    @Override

    public void surfaceCreated(SurfaceHolder p1) {
        canDraw = true;
        dt = new DrawThread();
        dt.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4) {
        // TODO: Implement this method
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder p1) {
        canDraw = false;
        try {
            dt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SurfView(Context context) {
        super(context);
        init();
    }
    public SurfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public SurfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        bt = Bitmap.createBitmap(BitmapFactory.decodeResource
                (getResources(), R.drawable.ic_robot));
    }

    class DrawThread extends Thread {
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int i = 0;
        int j = 0;
        int v = 5;
        int s;


        @Override
        public void run() {
            while (canDraw) {
                canvas = sfh.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.BLACK);
                    canvas.drawBitmap(bt, i, j, null);
                    sfh.unlockCanvasAndPost(canvas);
                    if (s == 0) {
                        if (j <= height - bt.getHeight()) {
                            j += v;
                        } else {
                            if (i <= width - bt.getWidth()) {
                                i += v;
                            } else {
                                s = 1;
                            }
                        }
                    } else {
                        if (j >= 0) {
                            j -= v;
                        } else {
                            if (i >= 0) {
                                i -= v;
                            } else {
                                s = 0;
                            }
                        }
                    }

                }
            }

        }
    }

}
