import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Chris on 4/6/14.
 */
public class Brick {

    private int length, height;
    private int xPos=0, yPos=0;
    BufferedImage img;
    File imageFile;
    public boolean hit;

    public Brick(int x, int y){
        length = 50;
        height = 20;
        xPos = x;
        yPos = y;

        try {
            imageFile = new File("src/Brick.png");
            img = ImageIO.read(imageFile);

        } catch(Exception ex) {
            System.out.println("Error opening file!");
            ex.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(img, xPos, yPos, length, height, null);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setHit(boolean hit){
        this.hit = hit;
    }

    public boolean isHit(){
        return hit;
    }
}
