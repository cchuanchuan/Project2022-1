import processing.core.PVector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ocean extends JPanel implements ActionListener {

    public final static int OCEAN_X = 50;
    public final static int OCEAN_Y = 50;
    public final static int OCEAN_W = 500;
    public final static int OCEAN_H = 300;

    private Timer timer;
    private int foodGenTimer = 0; // custom timer used to generate a food after 5 seconds

    private TropicalFish fish;
    private Food food;

    private Color color;

    public Ocean() {
        setPreferredSize(new Dimension(600, 450));
        timer = new Timer(33, this);
        timer.start();
        double scale = 1.0;
        PVector loc = new PVector(OCEAN_X + 50, OCEAN_Y + OCEAN_H / 2);
        fish = new TropicalFish(loc, 45, 40, 1.0);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call JPanel's method to clear the background
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 200, 0));
        g2.fillRect(OCEAN_X, OCEAN_Y, OCEAN_W, OCEAN_H);

        g2.setColor(new Color(244,164,96));
        g2.drawOval(OCEAN_X, OCEAN_Y+OCEAN_H*3 / 4, OCEAN_W*4/7, OCEAN_H / 4);
        g2.fillOval(OCEAN_X, OCEAN_Y+OCEAN_H*3 / 4, OCEAN_W*4/7, OCEAN_H / 4);

        g2.drawOval(OCEAN_X+OCEAN_W*4/7, OCEAN_Y+OCEAN_H*3 / 4, OCEAN_W*3/7, OCEAN_H / 4);
        g2.fillOval(OCEAN_X+OCEAN_W*4/7, OCEAN_Y+OCEAN_H*3 / 4, OCEAN_W*3/7, OCEAN_H / 4);

        // flag
        g2.setColor(Color.RED);
        int[] triangleX = {OCEAN_X + OCEAN_W*2/7, OCEAN_X + OCEAN_W*2/7, OCEAN_X + OCEAN_W*11/28};
        int[] triangleY = {OCEAN_Y+OCEAN_H / 2,OCEAN_Y+OCEAN_H*5 / 8, OCEAN_Y+OCEAN_H*9 / 16};
        g2.fillPolygon(triangleX,triangleY,3);

        g2.setColor(Color.BLACK);
        g.drawLine(OCEAN_X + OCEAN_W*2/7, OCEAN_Y+OCEAN_H*7 / 8,OCEAN_X + OCEAN_W*2/7,OCEAN_Y+OCEAN_H / 2);
        g2.drawPolygon(triangleX,triangleY,3);



        for (int i = 0; i < 2; i++) {
            g2.setColor(Color.BLACK);
            int r = (int) (Math.random()*OCEAN_H/10);
            int x = (int) (OCEAN_X + Math.random()*(OCEAN_W-r));
            int y = (int) (OCEAN_Y + Math.random()*(OCEAN_H-r));
            g2.drawOval(x,y,r,r);
        }

        fish.drawMe(g2);

        // draw food
        if (food != null) {
            food.drawFood(g2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fish.move();
        System.out.println(foodGenTimer);
        if (foodGenTimer < 151) // increase only when it's less than 10 seconds
            foodGenTimer++;
        else {
            foodGenTimer = 0;
            food = new Food(); // produce a food when it's 10 seconds since program launches
        }

        if (food != null && fish.eat(food.getPos())) { // Only when food is NOT NULL, it makes sense to approach it
            food = null; // If fish catches food eat it by setting it to null so that it will be garbage
            // collected by system

            // fish become bigger
//            fish.setScale(fish.getScale()+0.1);
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TropicalFish in Ocean");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ocean ocean = new Ocean();
        frame.add(ocean);
        frame.pack();
        frame.setVisible(true);
    }
}
