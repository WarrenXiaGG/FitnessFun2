package fightinggame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.animation.Animation;

import com.example.bluetooth.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class FightingGame extends Game{
    Bitmap  player1Image;
    Bitmap aiImage;
    Paint paint = new Paint();
    Paint backgroundSet = new Paint();
    Paint player1HealthBar = new Paint();
    FighterObject player1;
    ComputerFighter ai;
    GameObject sky;
    Bitmap background;
    long player1Health; //health of player 1
    int detectConuter=-1;  //counter for detectSequence
    String movement;    //can be punch, defense, or grad
    int testIndex ;
    int previousForce;
    int aiActionCounter = -1;
    int originalForce = 0;
    boolean player1Death = false;
    boolean aiDeath = false;
    //for calibration
    boolean completedCalibration = false;
    int maxForce = 0;
    int midForce;
    int minForce = 0;
    long prevTime;
    int frame;
    int ticks;//600 ticks = 1 min
    Typeface type;//for font type
    String AiMovement;

    //arrays for all the animations
    ArrayList<Bitmap>pReady = new ArrayList<Bitmap>();
    ArrayList<Bitmap>pAttack1 = new ArrayList<Bitmap>();
    ArrayList<Bitmap>pAttack2 = new ArrayList<Bitmap>();
    //we are gonna need static variables for each spesefic animation, both for the user and AI


    @Override
    //where the variables are
    public void init(SurfaceView s) {
        //add bitmaps to array as frames
            //Player Ready
        pReady.add(BitmapFactory.decodeResource(s.getResources(), R.drawable.sprite_0ready));
        pReady.add(BitmapFactory.decodeResource(s.getResources(), R.drawable.sprite_1ready));

            //Player Attack1
        pAttack1.add(BitmapFactory.decodeResource(s.getResources(), R.drawable.sprite_1ready));
        pAttack1.add(BitmapFactory.decodeResource(s.getResources(), R.drawable.attack1_1));
        pAttack1.add(BitmapFactory.decodeResource(s.getResources(), R.drawable.attack1_0));
            //Player Attack1
        pAttack2.add(BitmapFactory.decodeResource(s.getResources(), R.drawable.attack2_0));
        pAttack2.add(BitmapFactory.decodeResource(s.getResources(), R.drawable.attack2_1));
        pAttack2.add(BitmapFactory.decodeResource(s.getResources(), R.drawable.attack2_2));

        background = BitmapFactory.decodeResource(s.getResources(), R.drawable.sky);
        Bitmap sprite = BitmapFactory.decodeResource(s.getResources(), R.drawable.tw02_100704_img_01);
        Bitmap  player1Image= BitmapFactory.decodeResource(s.getResources(), R.drawable.sprite_0ready);
//        Bitmap sky = BitmapFactory.decodeResource(s.getResources(), R.drawable.sky);
        Bitmap ground = BitmapFactory.decodeResource(s.getResources(), R.drawable.ground);
        Bitmap aiImage = BitmapFactory.decodeResource(s.getResources(), R.drawable.attack2_0);
        player1 = new FighterObject(player1Image,375,200,1,1);
        //then we get the next one
        ai = new ComputerFighter(aiImage, 625, 200, 1, 1);    //just default
        //sky = new GameObject(sky, 100,200,1,1);
        type = Typeface.createFromAsset(((GameSurface)s).getContext().getAssets(),"fonts/Old School Adventures.ttf");

        frame =0;
        ticks = 0;

        testIndex = 0;
        player1Health = 1000;   //health of player1
        paint.setColor(Color.RED);
        paint.setTextSize(12);
        player1HealthBar.setColor(Color.GREEN);
        prevTime = System.nanoTime();
        backgroundSet.setAntiAlias(false);
    }

    @Override
    public void render(Canvas canvas) {
        backgroundSet.setColor(Color.BLUE);//FIXME testing here
        Rect dstRect = new Rect();
        canvas.getClipBounds(dstRect); // get canvas size base screen size
        canvas.drawBitmap(background, null, dstRect, backgroundSet);//FIXME, try
        if(GameSurface.controllerFound) {   //1 is defense position, 2 is atttack, 3 is going backward
            //canvas.drawBitmap(background, 0, 0, null);
            if(movement.equals("punch")){
                ticks = 3;
                frame = 1;
            }else if(movement.equals("defense")){
                ticks = 3;
                frame = 2;
            }else if(ticks == 0){
                frame = 0;
                ticks = 3;
            }
            ticks--;
            Log.d("Frame", "FRAME NUMBER: " + ticks);
            player1.draw(canvas, pAttack1.get(frame), frame);
            ai.draw(canvas, pAttack2.get(frame), frame);
            //canvas.drawText(movement, 100, 100, paint);

            canvas.drawText("Player 1:  " + player1Health + "/1000",0,100,paint);
            canvas.drawRect(0, 35, (int)(300 * (player1Health/1000.0)), 45, player1HealthBar);

            canvas.drawText(ai.health + "/1000 :Player 2",canvas.getWidth()-550,100,paint);
            canvas.drawRect(canvas.getWidth()- (int)((ai.health/1000.0) * 300), 35, canvas.getWidth(), 45, player1HealthBar);
            //TODO death image
        }else{
            paint.setTypeface(type);
            paint.setTextSize(48f);//set the text big
            paint.setColor(Color.BLACK);
            canvas.drawText("Please connect a controller",canvas.getWidth()/4,canvas.getHeight() * 2/3,paint);
        }

    }

    /*public void Calibration(int strength){
        int steps = 0;
        int currentForce = strength;
        int time = 240; //4 seconds

        //get MaxForce
            //make button or know when the user/player is ready to start
        while(time != 0 && steps == 0){
            if(currentForce > maxForce){
                maxForce = currentForce;
                Log.d("FORCE1", "MAXFORCE: "+ maxForce);
            }
            time--;
        }
        steps++;
        time = 60;//1 second

        //minForce
        while(time != 0 && steps == 1){
            if(currentForce > minForce){
                minForce = currentForce;
                Log.d("FORCE2", "MINFORCE: "+ minForce);
                if(time < 40 && (minForce > 10 && minForce < 30)){//ten is realistically reasonable
                    break; //we now have a reasonable min
                }
            }
            time--;
        }
        steps++;

        midForce = (maxForce - minForce) / 2;
        Log.d("FORCE3", "MIDFORCE: "+ midForce);
        //checks if it has completed the calibration
        if(maxForce > 0 && midForce > 0 && minForce > 0){
            completedCalibration = true;
        }else{
            steps = 0;
        }
    }*///This is the calibrating system

    @Override
    //handle all the game, runs every frames
    public void update(AtomicInteger c1,AtomicInteger c2,AtomicInteger c3) {
        if(GameSurface.controllerFound){
            updateGame(c1,c2,c3);
        }
    }

    public void updateGame(AtomicInteger c1,AtomicInteger c2,AtomicInteger c3) {

        //runs calibration first

//        while(completedCalibration == false){
//            int strength = c1.get();//this is only nessesary for the Calibration
//            Log.d("CURRENT", "CURRENT: " + strength);
//            Calibration(strength);
//        }

        long curr = System.nanoTime();
        double delta = (curr - prevTime)/(double)prevTime;
        String action = ai.update();
        if (action.equals("Punch")) {
            aiActionCounter = 53;
            AiMovement = "punched";
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
                //decreases the health of the ai(opponent), if less than 0, set to 0
                ai.health = (ai.health - originalForce <= 0)? 0 : ai.health - originalForce;
                Log.println(Log.ERROR, "ogForce", "ORIGINAL FORCE: "+ originalForce);
                Log.println(Log.ERROR, "aiHealth", "AI HEALTH: "+ ai.health);
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
            //if life is going to be negative, set health to 0;
            player1Health = (player1Health - ai.damage <= 0)? 0 : player1Health - ai.damage;
            aiActionCounter = -1;
        }
        if(aiActionCounter > 0){
            aiActionCounter--;
        }

        //death
        if(player1Health <= 0)
        {
            player1Death = true;
        }else if(ai.health <= 0)
        {
            aiDeath = true;
        }
        //player1.update(delta);
        prevTime = curr;
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
