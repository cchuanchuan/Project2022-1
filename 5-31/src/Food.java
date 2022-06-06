import processing.core.PVector;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Food {
    private final PVector pos;

    public Food() {
        pos = new PVector((float) (Ocean.OCEAN_X + Math.random() * Ocean.OCEAN_W),
                (float) (Ocean.OCEAN_Y + Math.random() * Ocean.OCEAN_H));

    }


    public PVector getPos() {
        return pos;
    }

    public void drawFood(Graphics2D g2) {

        AffineTransform at = g2.getTransform();
        g2.translate(pos.x, pos.y);
        g2.setColor(Color.yellow);
        g2.fillOval(-5, -5, 5, 5);
        g2.fillOval(-2, -3, 5, 5);
        g2.fillOval(0, 0, 5, 5);
        g2.fillOval(3, 2, 5, 5);
        g2.fillOval(5, 5, 5, 5);
        g2.setTransform(at);

    }
}
