import javax.swing.ImageIcon;
import java.awt.*;

public class Sprite
{
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;


    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(String imageName){
        ImageIcon ii = new ImageIcon(imageName);
        this.image = ii.getImage();
    }

    public void getImageDimensions(){
        // width
        // height
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Image getImage(){
        return this.image;
    }

    public boolean isVisibl(){
        return this.visible;
    }

    public void setVisible(boolean v){
        this.visible = v;
    }

}