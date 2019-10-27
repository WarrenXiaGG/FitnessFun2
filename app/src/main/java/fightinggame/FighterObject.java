package fightinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class FighterObject extends GameObject {
    public int health = 100;
    int xRow = 0;
    int yRow = 0;
    Paint paint = new Paint();
    int x_Initial = 0;
    int y_Initial = 0;


    public FighterObject(Bitmap sprite, int x, int y, int rowCount, int colCount) {
        super(sprite, x, y, rowCount, colCount);
        this.scale = 10;
        paint.setFilterBitmap(false);
        paint.setAntiAlias(false);
        paint.setDither(false);
        y_Initial = y;
        x_Initial = x;
    }

    public void draw(Canvas canvas, Bitmap bitmap, int frame){
//        Bitmap sub = createSubImageAt(0,frame);
        if(frame == 0)
        {
            //this.x += 1;
            canvas.drawBitmap(bitmap,new Rect(0,0,this.width,this.height),new Rect(x,y,x+(this.width*scale),y+(this.height*scale)),paint);
        }
        else if(frame == 1)
        {
            canvas.drawBitmap(bitmap,new Rect(0,0,this.width,this.height),new Rect(x,y,x+(this.width*scale),y+(this.height*scale)),paint);
            //this.x -= 1;
        }
        canvas.drawBitmap(bitmap,new Rect(0,0,this.width,this.height),new Rect(x,y,x+(this.width*scale),y+(this.height*scale)),paint);
    }
   /* public void update(double delta){
        this.x = x_Initial + (int)(4*Math.sin(delta));
        this.y = y_Initial + (int)(4*Math.sin(delta));
    }*/


}
