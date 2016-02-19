import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Random;

/**
 * Created by Chris on 4/6/14.
 */
public class Game_Board extends JPanel implements ActionListener, MouseMotionListener {

    private Ball ball = null;
    private Brick [] bricks = null;
    private Paddle paddle = null;
    protected Timer redrawTimer;
    private int mXPos = 0, hitCount = 0, score = 0, lives = 4, randNumX = 0, randNumY = 0;
    private final int GB_WIDTH = 830, GB_HEIGHT = 600;
    private final int WIDTH = 1000, HEIGHT = 600;
    boolean paused = false, muted = false;
    JButton pause, sound;
    JLabel back;
    JTextArea stats, gameBorder;
    Font f;
    AudioInputStream audioInputStream;
    Clip clip1, clip2, clip3, clip4;
    ImageIcon icon1;

    public Game_Board(){

        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File("src/Bumper.wav").getAbsoluteFile());
            clip1 = AudioSystem.getClip();
            clip1.open(audioInputStream);

            audioInputStream = AudioSystem.getAudioInputStream(new File("src/Ting.wav").getAbsoluteFile());
            clip2 = AudioSystem.getClip();
            clip2.open(audioInputStream);

            audioInputStream = AudioSystem.getAudioInputStream(new File("src/Impact.wav").getAbsoluteFile());
            clip3 = AudioSystem.getClip();
            clip3.open(audioInputStream);

            audioInputStream = AudioSystem.getAudioInputStream(new File("src/Drops.wav").getAbsoluteFile());
            clip4 = AudioSystem.getClip();
            clip4.open(audioInputStream);

            icon1 = new ImageIcon("src/HURD.jpg");
            Image image = icon1.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            icon1.setImage(image);
            back = new JLabel(icon1);


        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

        back.setLocation(0,0);
        back.setSize(WIDTH, HEIGHT);

        pause = new JButton("<html>Pause<br>Resume</html>");
        pause.setForeground(Color.BLACK);
        pause.setBackground(Color.GREEN);
        pause.setSize(100,50);
        pause.setLocation(865,410);
        pause.addActionListener(this);

        sound = new JButton("<html>Sound <br>On/Off</html>");
        sound.setForeground(Color.BLACK);
        sound.setBackground(Color.GREEN);
        sound.setSize(100,50);
        sound.setLocation(865,470);
        sound.addActionListener(this);

        this.setLayout(null);

        f = new Font("Dialog", Font.BOLD, 24);

        stats = new JTextArea("\n  Score: 0" + "\n  Lives: " + lives);
        stats.setEditable(false);
        stats.setSize(168, 400);
        stats.setOpaque(false);
        stats.setLocation(831,0);
        stats.setFont(f);

        Border border = BorderFactory.createLineBorder(Color.BLACK,5);
        gameBorder = new JTextArea();
        gameBorder.setSize(830,600);
        gameBorder.setLocation(0,0);
        gameBorder.setOpaque(false);
        gameBorder.setBorder(border);
        gameBorder.addMouseMotionListener(this);
        gameBorder.setEditable(false);

        redrawTimer = new Timer(1, this);
        redrawTimer.start();

        clip4.start();

        this.add(stats);
        this.add(pause);
        this.add(sound);
        this.add(gameBorder);
        this.add(back);
        this.setSize(WIDTH,HEIGHT);
    }

    public void paint(Graphics g) {

        super.paint(g);

        Image bufferedImage = createImage(getWidth(), getHeight());
        Graphics2D buffer = (Graphics2D) bufferedImage.getGraphics();

        super.paint(buffer);

        if (ball != null && bricks != null && paddle != null){
            for(int i = 0; i<bricks.length; i++){
                bricks[i].draw(buffer);
            }
            paddle.draw(buffer);

            ball.paint(buffer);
        }
        g.drawImage(bufferedImage, 0, 0, this);
    }

    @Override
    public synchronized void actionPerformed(ActionEvent arg0) {  //Handles the moving ball, bricks being hit, pausing game and muting sounds
        if(arg0.getSource() == pause){
            if(paused){
                redrawTimer.start();
                paused = false;
                pause.setBackground(Color.GREEN);
            } else {
                redrawTimer.stop();
                paused = true;
                pause.setBackground(Color.RED);
            }
        }

        if(arg0.getSource() == sound){
            if(muted){
                clip2.setFramePosition(0);
                clip2.start();

                try {
                    audioInputStream = AudioSystem.getAudioInputStream(new File("src/Bumper.wav").getAbsoluteFile());
                    clip1 = AudioSystem.getClip();
                    clip1.open(audioInputStream);

                    audioInputStream = AudioSystem.getAudioInputStream(new File("src/Impact.wav").getAbsoluteFile());
                    clip3 = AudioSystem.getClip();
                    clip3.open(audioInputStream);

                    audioInputStream = AudioSystem.getAudioInputStream(new File("src/Drops.wav").getAbsoluteFile());
                    clip4 = AudioSystem.getClip();
                    clip4.open(audioInputStream);
                    clip4.start();

                } catch(Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                sound.setBackground(Color.GREEN);

                muted = false;
            } else {
                muted = true;
                clip1.close();
                clip3.close();
                clip4.close();
                sound.setBackground(Color.RED);
            }
        }

        if (ball != null){
            brickHit(bricks);
            moveBall(ball);
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {  //Syncs the paddle with the mouse

        mXPos = e.getX();
        paddle.setX(mXPos);

        if(mXPos > (GB_WIDTH-100)){
            paddle.setX(GB_WIDTH - paddle.getLength());
        }
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void addBrick(){
        bricks = new Brick[30];

        for(int i = 0; i<bricks.length; i++){
            if(i>14){
                bricks[i] = new Brick(((i%15*53)+10),40);
            }
            if(i<15){
                bricks[i] = new Brick(((i*53)+10), 10);
            }
        }
    }

    public void addPaddle(){
        paddle = new Paddle();
        int x = 450;
        int y = 500;

        paddle.setX(x);
        paddle.setY(y);
    }

    public void addBall(){

        ball = new Ball();

        ball.setXPos(GB_WIDTH/2);
        ball.setYPos(GB_HEIGHT/2);

        ball.setXSpeed(1);
        ball.setYSpeed(-1);
    }

    public void moveBall(Ball ball) {
        ball.move();

        Random rand = new Random();
        randNumY = rand.nextInt(6-2) + 2;
        randNumX = rand.nextInt(6-2) + 2;

        if ((ball.getXPos()+20) >= GB_WIDTH || ball.getXPos() < 0) {
            ball.setXSpeed(-ball.getXSpeed());
        }

        if ((ball.getYPos() + 20) >= GB_HEIGHT || ball.getYPos() < 0) {
            if((ball.getYPos() + 20) >= GB_HEIGHT){
                ball.setXPos(GB_WIDTH/2);
                ball.setYPos(GB_HEIGHT/2);
                ball.setXSpeed(1);
                ball.setYSpeed(1);
                lives--;
                stats.setText("\n  Score: " + score +"\n  Lives: " + lives);
                if(lives == 0){                                                 //Check for game over due to loss of lives
                    redrawTimer.stop();
                    int mc = JOptionPane.WARNING_MESSAGE;
                    JOptionPane.showMessageDialog (null, "You Lost!\nGo to File->New Game to start a new game.", "Game Over!", mc);
                }
            }
            ball.setYSpeed(-ball.getYSpeed());
        }

        if(paddle != null) {
            if ((ball.getYPos()+20) >= paddle.getY() && ball.getYPos() <= (paddle.getY()+10)) {                 //Checking for paddle collision
                if (ball.getXPos()+20 >= paddle.getX() && ball.getXPos() <= paddle.getX()+50) {
                    ball.setYSpeed(0);
                    ball.setYSpeed(-randNumY);
                    ball.setXSpeed(0);
                    ball.setXSpeed(-randNumX);
                    clip1.setFramePosition(0);
                    clip1.start();

                } else if (ball.getXPos() >= paddle.getX()+50 && ball.getXPos() <= paddle.getX()+100) {
                    ball.setYSpeed(0);
                    ball.setYSpeed(-randNumY);
                    ball.setXSpeed(0);
                    ball.setXSpeed(randNumX);
                    clip1.setFramePosition(0);
                    clip1.start();
                }
            }
        }

        for(int i = 0; i < bricks.length; i++){
            if(bricks[i] != null){                                                                                         //Checking for collision with bricks
                if((ball.getXPos()) >= bricks[i].getxPos() && (ball.getXPos()) <= (bricks[i].getxPos() + 50)){
                    if((ball.getYPos() + 20) >= bricks[i].getyPos() && (ball.getYPos()+1) <= bricks[i].getyPos() + 20){
                        ball.setYSpeed(-ball.getYSpeed());
                        bricks[i].setHit(true);
                        score+=20;
                        hitCount++;
                        stats.setText("\n  Score: " + score +"\n  Lives: " + lives);
                        System.out.println("\n  Score: " + score +"\n  Lives: " + lives);
                        clip3.setFramePosition(0);
                        clip3.start();
                        if(hitCount == bricks.length){
                            redrawTimer.stop();
                            int mc = JOptionPane.INFORMATION_MESSAGE;
                            JOptionPane.showMessageDialog (null, "You Win!\nGo to File->New Game to start a new game.", "Congratulations!", mc);
                        }
                    }
                } else if((ball.getYPos()+9) >= bricks[i].getyPos() && (ball.getYPos()) <= (bricks[i].getyPos()+20)){
                    if((ball.getXPos()+20) >= bricks[i].getxPos() && ball.getXPos() <= bricks[i].getxPos() + 50){
                        ball.setXSpeed(-ball.getXSpeed());
                        bricks[i].setHit(true);
                        score+=20;
                        hitCount++;
                        stats.setText("\n  Score: " + score +"\n  Lives: " + lives);
                        System.out.println("\n  Score: " + score +"\n  Lives: " + lives);
                        clip3.setFramePosition(0);
                        clip3.start();
                        if(hitCount == bricks.length){
                            redrawTimer.stop();
                            int mc = JOptionPane.INFORMATION_MESSAGE;
                            JOptionPane.showMessageDialog (null, "You Win!\nGo to File->New Game to start a new game.", "Congratulations!", mc);

                        }
                    }
                }
            }
        }
    }

    public void brickHit(Brick bricks[]){
        for(int i = 0; i < bricks.length; i++){
            if (bricks[i] != null){
                if(bricks[i].isHit()){
                    bricks[i].setyPos(0);
                    bricks[i].setxPos(1100);
                }
            }
        }
    }
}
