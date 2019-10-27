package fightinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;


import java.util.concurrent.atomic.AtomicInteger;

public class GameObject {
    public Bitmap image;
    public int width;
    public int height;
    public int x;
    public int y;
    public int rowCount;
    public int colCount;
    public long lastDrawNanoTime;
    public int scale = 1;

    public GameObject(Bitmap sprite, int x, int y, int rowCount, int colCount) {
        this.image = sprite;
        this.x = x;
        this.y = y;
        if(sprite == null){
            this.width = 1;
            this.height = 1;
        }else {
            this.width = image.getWidth();
            this.height = image.getHeight();
        }
        this.rowCount = rowCount;
        this.colCount = colCount;
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.image;
        canvas.drawBitmap(bitmap, x, y, null);
        this.lastDrawNanoTime= System.nanoTime();
    }

    protected Bitmap createSubImageAt(int row, int col) {
        // createBitmap(bitmap, x, y, width, height).
        Bitmap subImage = Bitmap.createBitmap(image, col * width, row * height, width, height);
        return subImage;

    }

    public void setScale(int scale){
        this.scale = scale;
    }
    public  void update(AtomicInteger c1, AtomicInteger c2, AtomicInteger c3)
    {

    }
}
