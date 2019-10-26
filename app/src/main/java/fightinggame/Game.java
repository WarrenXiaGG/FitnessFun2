package fightinggame;

import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceView;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Game {
    public abstract void init(SurfaceView s);
    public abstract void render(Canvas canvas);
    public abstract void update(AtomicInteger c1, AtomicInteger c2, AtomicInteger c3);
}
