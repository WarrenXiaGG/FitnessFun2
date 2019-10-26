package fightinggame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceView;

import com.example.bluetooth.R;

import java.util.concurrent.atomic.AtomicInteger;

public class FightingGame extends Game{
    FighterObject player1;
    FighterObject ai;
    @Override
    public void init(SurfaceView s) {
        Bitmap sprite = BitmapFactory.decodeResource(s.getResources(), R.drawable.tw02_100704_img_01);
        player1 = new FighterObject(sprite,54,0,1,1);
    }

    @Override
    public void render(Canvas canvas) {
        player1.draw(canvas);
    }

    @Override
    public void update(AtomicInteger c1,AtomicInteger c2,AtomicInteger c3) {
        player1.x = c1.get()/10;
    }
}
