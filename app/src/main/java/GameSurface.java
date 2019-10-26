import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.bluetooth.R;



public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    GameObject player1;
    GameThread gameThread;
    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Bitmap sprite = BitmapFactory.decodeResource(this.getResources(), R.drawable.tw02_100704_img_01);
        player1 = new GameObject(sprite,54,0);
        this.gameThread = new GameThread(this,surfaceHolder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    public void update()  {
        player1.x++;
    }



    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

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
