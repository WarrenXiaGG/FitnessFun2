package fightinggame;

import android.graphics.Bitmap;

public class ComputerFighter extends GameObject{
    public int health = 100;
    public int counter =100;
    public ComputerFighter(Bitmap sprite, int x, int y, int rowCount, int colCount) {
        super(sprite, x, y, rowCount, colCount);
    }

    public String update(){
        counter--;
        if(counter == 0){
            counter = 100+ (int)(Math.random() * 20);
            return Math.random() > 0.5? "Punch":"Defense";
        }
        return "";
    }
}
