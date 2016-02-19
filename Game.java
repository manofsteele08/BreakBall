import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Chris on 4/6/14.
 */
public class Game extends JFrame implements MouseListener, WindowListener{

    private final int WIDTH = 1000, HEIGHT = 600;
    JFrame frame;
    JFrame info = new JFrame("About");
    JFrame play = new JFrame("How To Play");
    Game game;

    JTextArea how;

    Game_Board panel;

    JMenuBar menuBar = new JMenuBar();
    JMenu file, help;
    JMenuItem newGame, exit, about, howToPlay;

    public Game(){

        frame = new JFrame("Penghancur Bata");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        panel = new Game_Board();

        file = new JMenu("File");
        help = new JMenu("Help");

        newGame = new JMenuItem("New Game");
        exit = new JMenuItem("Exit");
        about = new JMenuItem("About");
        howToPlay = new JMenuItem("How-to-play");

        setContentPane(panel);

        newGame.addMouseListener(this);
        exit.addMouseListener(this);
        about.addMouseListener(this);
        howToPlay.addMouseListener(this);


        file.add(newGame);
        file.add(exit);

        help.add(about);
        help.add(howToPlay);

        menuBar.add(file);
        menuBar.add(help);

        panel.setVisible(true);
        panel.addMouseListener(this);
        panel.addBrick();
        panel.addPaddle();
        panel.addBall();

        frame.add(panel);
        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void mousePressed(MouseEvent e){

        if(e.getSource() == exit){
            System.exit(0);
        }

        if(e.getSource() == newGame){
                panel.redrawTimer.stop();
                frame.dispose();
                game = new Game();
        }

        if(e.getSource() == about){
            panel.redrawTimer.stop();
            info.setSize(200,100);
            info.setLocationRelativeTo(null);
            info.setBackground(Color.DARK_GRAY);
            info.add(new JLabel("<html>Penghancur Bata <br> By: Chris Steele <br> A01073344 </html>"));
            info.setVisible(true);
            info.setResizable(false);
            info.addWindowListener(this);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        if(e.getSource() == howToPlay){
            panel.redrawTimer.stop();
            how = new JTextArea("***Welcome to Penghancur Bata!***" +
                    "\n\nPenghancur Bata is Malay for 'Brick Destroyer'. " +
                    "The rules of the game are simple. Use the mouse to control the paddle at the bottom of the screen, left to right. " +
                    "You can bounce the ball back up towards the bricks. But, don't get too comfy. The ball will bounce off at a random angle at a random speed." +
                    "When the ball collides with a brick, the brick is destroyed and your points increase. " +
                    "Be careful though, if you miss catching the ball, you will lose a life. " +
                    "Once you have lost all your lives, game over dude. " +
                    "Best of luck to ya and may the bricks be ever in your favor.");
            how.setEditable(false);
            how.setLineWrap(true);
            how.setWrapStyleWord(true);
            how.setBackground(Color.DARK_GRAY);
            how.setForeground(Color.ORANGE);

            play.setSize(400, 200);
            play.setLocationRelativeTo(null);
            play.setBackground(Color.DARK_GRAY);
            play.add(how);
            play.setVisible(true);
            play.setResizable(false);
            play.addWindowListener(this);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {

        info.dispose();
        play.dispose();
        panel.redrawTimer.start();
    }

    public void windowClosed(WindowEvent e) {

        frame.setEnabled(true);
        frame.isActive();
        frame.setAlwaysOnTop(true);
        frame.repaint();
    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

        info.setAlwaysOnTop(true);
        play.setAlwaysOnTop(true);
        frame.setEnabled(false);
    }

    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args){

       Game game = new Game();
    }
}
