import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PinBall {
    // Create window
    private Frame frame = new Frame("PinBall Game");

    // Set table height and width
    private final int TABLE_WIDTH = 300;
    private final int TABLE_HEIGHT = 400;

    // Set racket height and width
    private final int RACKET_WIDTH = 60;
    private final int RACKET_HEIGHT = 20;

    // Set ball size
    private final int BALL_SIZE = 16;

    // Balls' position
    private int ballX = 120;
    private int ballY = 20;

    // Ball's speed on x and y axis
    private int speedY = 10;
    private int speedX = 5;

    // Racket's position
    private int racketX = 120;
    private final int racketY = 340;

    // Check if game over
    private boolean isOver = false;

    // Declare timer
    private Timer timer;

    // Custom Canvas
    private class MyCanvas extends Canvas{
        @Override
        public void paint(Graphics g) {
            //TODO Draw contents

            if (isOver){ // game over
                g.setColor(Color.BLUE);
                g.setFont(new Font("Times",Font.BOLD,30));
                g.drawString("Game Over！",50,200);
            } else{ //Game
                // Draw ball
                g.setColor(Color.RED);
                g.fillOval(ballX,ballY,BALL_SIZE,BALL_SIZE);

                // Draw racket
                g.setColor(Color.PINK);
                g.fillRect(racketX,racketY,RACKET_WIDTH,RACKET_HEIGHT);
            }
        }
    }

    // Create draw area
    MyCanvas drawArea = new MyCanvas();

    public void init(){
        // Construct the view and game control logic

        // Implement movements for racket
        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Get the key pressed
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT){
                    //<-  Move left
                    if (racketX>0){
                        racketX -=10;
                    }
                }
                if (keyCode == KeyEvent.VK_RIGHT){
                    //->  Move right
                    if (racketX < (TABLE_WIDTH-RACKET_WIDTH)){
                        racketX+=10;
                    }
                }
            }
        };

        // Register listeners for frame and drawArea
        frame.addKeyListener(listener);
        drawArea.addKeyListener(listener);

        // Implement movements for ball
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Adjust speed depending on border
                if (ballX<=0 || ballX >=(TABLE_WIDTH-BALL_SIZE)){
                    speedX = -speedX;
                }

                if( ballY <=0 || ( ballY > racketY-BALL_SIZE && ballX>racketX && ballX < racketX+RACKET_WIDTH)){
                    speedY = -speedY;
                }

                if (ballY > racketY-BALL_SIZE && ( ballX < racketX || ballX > racketX+RACKET_WIDTH)){
                    // If the ball goes beyond racket then game over
                    // Stop the timer
                    timer.stop();
                    isOver = true;
                    // repaint the interface
                    drawArea.repaint();
                }

                // update balls' position and repaint the interface
                ballX+=speedX;
                ballY+=speedY;
                drawArea.repaint();
            }
        };
        timer = new Timer(100,task);
        timer.start();


        drawArea.setPreferredSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        frame.add(drawArea);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new PinBall().init();
    }
}
