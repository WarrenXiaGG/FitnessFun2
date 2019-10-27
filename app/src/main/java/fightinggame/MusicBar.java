package fightinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.bluetooth.R;

public class MusicBar extends GameObject{

    public int x_orig;
    public int y_orig;
    public int width;
    public final int height_mult = 60;

    public boolean failed;
    public Paint fail = new Paint();
    public Paint good = new Paint();

    public MusicBar(Bitmap sprite, int x, int y, int rowCount, int colCount,int width,int height) {
        super(null, x, y, rowCount, colCount);
        this.x_orig = x;
        this.y_orig = y;
        fail.setColor(Color.parseColor("#dc322f"));
        fail.setStyle(Paint.Style.FILL);
        good.setColor(Color.parseColor("#859900"));
        good.setStyle(Paint.Style.FILL);
        this.width = width;
        this.height = height_mult * height;
    }

    public void draw(Canvas canvas,int offset){
        x = x_orig-offset;
        y = y_orig;
        canvas.drawRect(x_orig-offset,y_orig,x_orig+width-offset,y_orig-height,failed?fail:good);
    }
}

