import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Chris on 4/6/14.
 */
public class Paddle{

    private int length, height;
    private int xPos, yPos;
    BufferedImage img;
    File imageFile;

    public Paddle(){
        length = 100;
        height = 10;
        xPos = 0;
        yPos = 0;

        try {
            imageFile = new File("src/Paddle.png");
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

    public int getX() {
        return xPos;
    }

    public void setX(int x) {
        this.xPos = x;
    }

    public int getY() {
        return yPos;
    }

    public void setY(int y) {
        this.yPos = y;
    }
}
