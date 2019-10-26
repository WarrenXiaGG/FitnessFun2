package fightinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class FighterObject extends GameObject {
    public int health = 100;

    public FighterObject(Bitmap sprite, int x, int y, int rowCount, int colCount) {
        super(sprite, x, y, rowCount, colCount);
    }
    public void draw(Canvas canvas){

    }
}
