package fightinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.concurrent.atomic.AtomicInteger;

public class TankObject extends GameObject {
    int angle = 0;
    int speed = 5;
    int[][][] map;
    Paint paint = new Paint();
    public TankObject(Bitmap sprite, int x, int y, int rowCount, int colCount) {
        super(sprite, x, y, rowCount, colCount);
        paint.setColor(Color.BLUE);
    }
    public void draw(Canvas canvas){
        canvas.rotate(angle, x+10, y+10);
        canvas.drawRect(x,y,x+20,y+20,paint);
        canvas.restore();
    }
    public void update(AtomicInteger c1, AtomicInteger c2, AtomicInteger c3){
        if(c1.get()==0){
            angle++;
            angle %= 360;
        }else{
            x+= Math.cos(Math.toRadians(angle)) * speed;
            y+= Math.sin(Math.toRadians(angle)) * speed;
        }
    }
}
