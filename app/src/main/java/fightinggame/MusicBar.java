package fightinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bluetooth.R;

public class MusicBar extends GameObject{

    public int width;
    public final int height = 10;

    public boolean failed;
    public Paint fail = new Paint();
    public Paint good = new Paint();

    public MusicBar(Bitmap sprite, int x, int y, int rowCount, int colCount,int width) {
        super(null, x, y, rowCount, colCount);
        fail.setColor(Color.RED);
        fail.setStyle(Paint.Style.FILL);
        good.setColor(Color.GREEN);
        good.setStyle(Paint.Style.FILL);
        this.width = width;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(x,y,x+width,y+height,failed?fail:good);
    }
}
