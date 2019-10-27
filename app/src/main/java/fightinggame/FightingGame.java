package fightinggame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.example.bluetooth.R;

import java.util.concurrent.atomic.AtomicInteger;

public class FightingGame extends Game{
    Paint paint = new Paint();
    Paint player1HealthBar = new Paint();
    FighterObject player1;
    ComputerFighter ai;
    long player1Health; //health of player 1
    int detectConuter=-1;  //counter for detectSequence
    final int DETECT_MAX = 5;   //how long after we should start look for 0
    String movement;    //can be punch, defense, or grad
    int[] testArray = new int[1000];    //FIXME to test how long it takes
    int testIndex ;
    int testMax;
    int testDifference;
    int previousForce;
    int aiActionCounter = -1;
    int originalForce = 0;
    boolean countered = false;

    @Override
    //where the variables are
    public void init(SurfaceView s) {
        Bitmap sprite = BitmapFactory.decodeResource(s.getResources(), R.drawable.tw02_100704_img_01);
        player1 = new FighterObject(sprite,54,0,1,1);
        ai = new ComputerFighter(sprite, 54, 0, 1, 1);    //just default
        testIndex = 0;
        paint.setColor(Color.RED);
        player1HealthBar.setColor(Color.GREEN);
    }

    @Override
    public void render(Canvas canvas) {
        player1.draw(canvas);
        canvas.drawText(movement,100,100,paint);
        canvas.drawRect(0, 0, 300, 50, player1HealthBar);
    }

    @Override
    //handle all the game, runs every frames
    public void update(AtomicInteger c1,AtomicInteger c2,AtomicInteger c3) {
        String action = ai.update();
        if(action.equals("Punch")){
            aiActionCounter = 53;
            movement = "ai punched";
        }
        int force = c1.get();   //the force player presses
        if(force > 0 && previousForce == 0){
            detectConuter = 22;//refractory period
            originalForce = c1.get();
        }
        if(detectConuter > 0){
            detectConuter--;
        }else if(detectConuter == 0){
            if(force == 0)
            {
                Log.println(Log.ERROR,"ds","Punched");
                movement = "punch";
                //decreases the health of the ai(opponent)
                ai.health -= originalForce;
                Log.d("GETFORCE", "ORIGINAL FORCE: "+ originalForce);
                Log.d("AI", "AI HEALTH: "+ ai.health);
                //refactory period when pucnching and defending//
            }
            //otherwise
            else
            {
                Log.println(Log.ERROR,"ds","Defense");
                movement = "defense";
                if(aiActionCounter > 0){
                    movement = "blocked";
                }
            }
            detectConuter = -1;
        }
        previousForce = force;

        if(!movement.equals("blocked") && aiActionCounter == 0){
            movement = "hurt";
            aiActionCounter = -1;
        }
        if(aiActionCounter > 0){
            aiActionCounter--;
        }

        //if the force is over the high threshold, go into defence, it checks if it's defense

        //if the force is over the low threshold, then it is attacking

        //5% chance of activating, grabs another person and deals damage

        //if the health goes into negative, that person is dead, another player win



    }

    /*two conditions
     * 1. attack successfully, if other player's defense is off during the interval
     * 2. attack partially fail, if other player's defense is on
     */
   /* public void attack(FighterObject attacker, long attackForce)
    {
        //within the attack interval, the defense is on, attack fails
        long interval;  //attack time occurs mimus current time

        //punch success;
        if(attacker == player1)
        {
            ai.health -= attackForce;
        }
    }*/

    /*detectSequence
     *if the force goes down to 0, it would be label as a punch
     * otherwise, it's a defense
     */
    /*public void detectSequence(Integer force)
    {
        //increment deterCounter
        detectConuter++;
        //after certain time, we start look to see if it's a punch or defense
        if(detectConuter > 30)
        {
            //movement is punch when at some point force is 0
            if(force == 0)
            {
                movement = "punch";
            }
            //otherwise
            else
            {
                movement = "";
            }
        }
    }*/
}
