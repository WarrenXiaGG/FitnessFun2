package fightinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class ComputerFighter extends GameObject{
    public int health = 1000;
    public int counter =100;
    public int damage = 0;
    Paint paint = new Paint();

    public ComputerFighter(Bitmap sprite, int x, int y, int rowCount, int colCount) {
        super(sprite, x, y, rowCount, colCount);
//        Matrix m = new Matrix();
//        m.preScale(-1, 1);
//        Bitmap src = sprite;
//        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
//        this.image = dst;
//        paint.setColor(Color.BLUE);
    }

//    public int damageGenerator(int probablity){
//        int randProb = (int)(Math.random() * 150);
//        if(randProb < probablity){
//            return 200;
//        }else{
//            return randProb;
//        }
//    }
    public void draw(Canvas canvas, Bitmap bitmap, int frame){
        //canvas.drawRect(x,y,x+width,y+height,paint);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap src = bitmap;
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        this.image = dst;
        if(frame == 0)
        {
            //this.x += 1;
            canvas.drawBitmap(this.image,new Rect(0,0,width,height),new Rect(x,y,x+(width)*10,y+(height)*10),null);
        }
        else if(frame == 1)
        {
            canvas.drawBitmap(this.image,new Rect(0,0,width,height),new Rect(x,y,x+(width)*10,y+(height)*10),null);
            //this.x -= 1;
        }
        canvas.drawBitmap(this.image,new Rect(0,0,width,height),new Rect(x,y,x+(width)*10,y+(height)*10),null);

    }

    public String update(){
        counter--;
        if(counter == 0){
            counter = 100+ (int)(Math.random() * 20);
            if(Math.random() > 0.5)
            {
                damage = (int)(Math.random() * 100) + 50;
                if(Math.random() > 0.9)
                {
                    damage = 200;
                }
                return"Punch";
            }
            return "Defense";
        }
        return "";
    }
}
