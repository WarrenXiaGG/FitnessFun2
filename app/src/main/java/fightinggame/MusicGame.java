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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

public class MusicGame extends Game {
    Paint titlePaint = new Paint();
    Paint mainBackground = new Paint();
    Paint songBackground = new Paint();
    MediaPlayer media;
    MediaRecorder mRecorder;
    Queue<MusicBar> bars = new LinkedList<>();
    int score = 0;
    int scale = 100;
    int combo = 0;
    double conversion;
    boolean runonce = true;
    static int cival = 0;
    @Override
    public void init(SurfaceView s) {
        titlePaint.setColor(Color.parseColor("#fdf6e3"));
        titlePaint.setStyle(Paint.Style.FILL);
        mainBackground.setColor(Color.parseColor("#002b36"));
        songBackground.setColor(Color.parseColor("#073642"));
        media = MediaPlayer.create(((GameSurface)s).getContext(),R.raw.kbeats);
        titlePaint.setTextSize(24 * s.getContext().getResources().getDisplayMetrics().density);
        int total = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getResources().getAssets().open("kbeats.txt")));
            // Log.println(Log.ERROR,"",scanner.toString());
            String line = reader.readLine();
            total = Integer.parseInt(line);
            while((line = reader.readLine()) != null){
                String scan = line;
                StringTokenizer t = new StringTokenizer(scan);
                int first = Integer.parseInt(t.nextToken());
                int second = Integer.parseInt(t.nextToken());
                int third= Integer.parseInt(t.nextToken());
                Log.println(Log.ERROR,"",first+","+second);
                MusicBar bar = new MusicBar(null,first/scale,s.getHeight() - 100,0,0,(second-first)/scale,third);
                bars.add(bar);
            }
            reader.close();
        } catch (IOException e) {
            Log.println(Log.ERROR,"Failed file read","Failed file read");
            e.printStackTrace();
        }
        conversion = (double) total/media.getDuration();
       /* for(MusicBar bar: bars){
            bar.x+=s.getWidth();
        }*/
    }


    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),mainBackground);
        if(GameSurface.controllerFound) {
            canvas.drawText("Now Playing: newbetter.wav", canvas.getWidth() / 2, 280, titlePaint);
            canvas.drawText("Combo: " + combo + "x", 0, canvas.getHeight() - 380, titlePaint);
            canvas.drawLine(0, canvas.getHeight() - 350, canvas.getWidth(), canvas.getHeight() - 350, titlePaint);
            Iterator<MusicBar> baari = bars.iterator();
            int offset = (int) (media.getCurrentPosition() / scale * conversion);

            canvas.drawRect(0,canvas.getHeight() - 350,canvas.getWidth(),canvas.getHeight(),songBackground);

            while (baari.hasNext()) {
                MusicBar bar = baari.next();
                if (bar.x_orig - offset < canvas.getWidth())
                    bar.draw(canvas, offset);
                if (bar.x + bar.width < 0) {
                    if (!bar.failed) {
                        score++;
                    }
                    bars.remove(bar);
                    if(Math.abs(bar.height - cival)<12){
                        combo++;
                    }
                }

                if (bar.x_orig - offset > canvas.getWidth()) {
                    break;
                }
            }
            canvas.drawRect(0,canvas.getHeight() -(cival + 100),30,canvas.getHeight() -100,titlePaint);
            canvas.drawText("Score: " + score, 100, 280, titlePaint);
        }else{
            canvas.drawText("Please Connect Controller",canvas.getWidth()/2-200,canvas.getHeight()/2,titlePaint);
        }
    }

    @Override
    public void update(AtomicInteger c1, AtomicInteger c2, AtomicInteger c3) {
        cival = c1.get();
        if(GameSurface.controllerFound) {
            if(runonce) {
                media.start();
                runonce = false;
            }
            MusicBar frontBar = bars.peek();
            if (frontBar.x <= 0 && c1.get() != 0) {
                    frontBar.failed = false;
            } else if (!frontBar.failed) {
                frontBar.failed = true;
            }
        }
    }
}
