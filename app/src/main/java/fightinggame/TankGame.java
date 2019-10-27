package fightinggame;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.concurrent.atomic.AtomicInteger;

public class TankGame extends Game{
    int[][][] map = new int[30][30][2];

    TankObject tank;
    @Override
    public void init(SurfaceView s) {
        tank = new TankObject(null,0,0,0,0);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                map[i][j][0] = 0;
                map[i][j][1] = 0;
            }
        }

        tank.map = map;
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
