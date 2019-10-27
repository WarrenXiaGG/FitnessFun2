package fightinggame;

import android.graphics.Bitmap;

public class ComputerFighter extends GameObject{
    public int health = 1000;
    public int counter =100;
    public int damage = 0;
    public ComputerFighter(Bitmap sprite, int x, int y, int rowCount, int colCount) {
        super(sprite, x, y, rowCount, colCount);
    }

//    public int damageGenerator(int probablity){
//        int randProb = (int)(Math.random() * 150);
//        if(randProb < probablity){
//            return 200;
//        }else{
//            return randProb;
//        }
//    }

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
