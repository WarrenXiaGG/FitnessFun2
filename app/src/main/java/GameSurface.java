import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bluetooth.R;

import java.util.concurrent.atomic.AtomicInteger;

import a5.com.a5bluetoothlibrary.A5Device;


public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{
    GameObject player1;
    GameThread gameThread;
    Paint paint = new Paint();

    AtomicInteger controller1 = new AtomicInteger();
    AtomicInteger controller2 = new AtomicInteger();
    AtomicInteger controller3 = new AtomicInteger();
    public String controller1Name;
    public String controller2Name;
    public String controller3Name;
    public GameSurface(Context context)  {
        super(context);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Bitmap sprite = BitmapFactory.decodeResource(this.getResources(), R.drawable.tw02_100704_img_01);
        player1 = new GameObject(sprite,54,0,1,1);
        this.gameThread = new GameThread(this,surfaceHolder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    public void update()  {
        player1.x += controller1.get()/10;
        player1.x -= controller2.get()/10;
    }

    public void manageReceiveIsometric(A5Device this_device, int thisValue) {
        if(this_device.getDevice().getAddress().equals(controller1Name)) {
            controller1.set(thisValue);
        }else if(this_device.getDevice().getAddress().equals(controller2Name)){
            controller2.set(thisValue);
        }else if(this_device.getDevice().getAddress().equals(controller3Name)){
            controller3.set(thisValue);
        }
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        canvas.drawText("Controller 1: " + controller1.get(),0,10, paint);
        canvas.drawText("Controller 2: " + controller2.get(),0,20, paint);
        canvas.drawText("Controller 3: " + controller3.get(),0,30, paint);
        this.player1.draw(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }
}
