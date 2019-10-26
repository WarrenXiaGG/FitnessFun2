package fightinggame;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.concurrent.atomic.AtomicInteger;

public class TankGame extends Game{
    TankObject tank;
    @Override
    public void init(SurfaceView s) {
        tank = new TankObject(null,0,0,0,0);
    }

    @Override
    public void render(Canvas canvas) {
        tank.draw(canvas);
    }

    @Override
    public void update(AtomicInteger c1, AtomicInteger c2, AtomicInteger c3) {
        tank.update(c1,c2,c3);
    }
}
