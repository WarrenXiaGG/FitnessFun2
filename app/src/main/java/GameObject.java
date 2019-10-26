import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GameObject {
    public Bitmap image;
    public int width;
    public int height;
    public int x;
    public int y;

    public GameObject(Bitmap sprite, int x, int y){
        this.image = sprite;
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        this.height = image.getHeight();

    }
    public void draw(Canvas canvas)  {
        Bitmap bitmap = this.image;
        canvas.drawBitmap(bitmap,x, y, null);
    }
}
