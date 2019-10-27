package fightinggame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.util.Log;
import android.view.SurfaceView;

import com.example.bluetooth.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

public class MusicGame extends Game {
    Paint titlePaint = new Paint();
    MediaPlayer media;
    MediaRecorder mRecorder;
    Queue<MusicBar> bars = new LinkedList<>();
    @Override
    public void init(SurfaceView s) {
        titlePaint.setColor(Color.RED);
        titlePaint.setStyle(Paint.Style.FILL);
        media = MediaPlayer.create(((GameSurface)s).getContext(), R.raw.good);
        /*try {
            Scanner scanner = new Scanner(s.getResources().getAssets().open("big.txt"));
           // Log.println(Log.ERROR,"",scanner.toString());
            while(scanner.hasNext()){
                String scan = scanner.nextLine();
                StringTokenizer t = new StringTokenizer(scan);
                int first = Integer.parseInt(t.nextToken());
                int second = Integer.parseInt(t.nextToken());
                Log.println(Log.ERROR,"",first+","+second);
                MusicBar bar = new MusicBar(null,first,40,0,0,second-first);
                bars.add(bar);
            }
        } catch (IOException e) {
            Log.println(Log.ERROR,"Failed file read","Failed file read");
            e.printStackTrace();
        }*/
        for(MusicBar bar: bars){
            bar.x+=s.getWidth();
        }
        media.start();
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return  (mRecorder.getMaxAmplitude());
        else
            return 0;

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawText("Now Playing: GenericSong.wav",canvas.getWidth()/2,20,titlePaint);
        canvas.drawLine(0,0,0,100,titlePaint);
        Iterator<MusicBar> baari = bars.iterator();
        while(baari.hasNext()){
            MusicBar bar = baari.next();
            if(bar.x < canvas.getWidth())
                bar.draw(canvas);
            bar.x-=1;
            if(bar.x + bar.width < 0){
                bars.remove(bar);
            }
        }
    }

    @Override
    public void update(AtomicInteger c1, AtomicInteger c2, AtomicInteger c3) {

    }
}
