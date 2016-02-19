import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Chris on 4/6/14.
 */
public class Ball {

    private int xPos, yPos;
    BufferedImage img;
    File imageFile;
    private double xSpeed, ySpeed;

    public Ball(){
        xPos = 0;
        yPos = 0;
        xSpeed = 0;
        ySpeed = 0;

        try {
            imageFile = new File("src/ball.png");
            img = ImageIO.read(imageFile);

        } catch(Exception ex) {
            System.out.println("Error opening file!");
            ex.printStackTrace();
        }
    }

    public void move() {
        xPos += xSpeed;
        yPos += ySpeed;
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(img, xPos, yPos, 20, 20, null);
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int value) {
        xPos = value;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int value) {
        yPos = value;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(double value) {
        xSpeed = value;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(double value) {
        ySpeed = value;
    }
}
