import processing.core.PVector;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class TropicalFish {

    private PVector loc,vel;
    private int bodyW,bodyH;
    private double scale;
    private Color color = new Color(135,206,235);

    private double angle;
    private int dir = 1;
    private int hasEat;
    private double random;


    public TropicalFish(PVector loc, int bodyW, int bodyH, double scale) {
        this.loc = loc;
        this.vel = new PVector(1, 1);;
        this.bodyW = bodyW;
        this.bodyH = bodyH;
        this.scale = scale;
        random = Math.random();
    }

    public void drawMe(Graphics2D g2) {
        AffineTransform at = g2.getTransform();
		g2.translate(loc.x, loc.y);
        g2.scale(scale, scale);
        float angle = vel.heading();
        System.out.println("ANGLE:"+angle);
        g2.rotate(angle);

        // body
        g2.setColor(color);
        int triangleX[] = {bodyW/2,-bodyW/4,-bodyW/4};
        int triangleY[] = {0,bodyH/2,-bodyH/2};
        g2.fillPolygon(triangleX,triangleY,3);

        // tail
        int[] triangleX2 = {-bodyW/4, -bodyW/2, -bodyW/2};
        int[] triangleY2 = {0, bodyH/4, -bodyH/4};
        g2.fillPolygon(triangleX2,triangleY2,3);

        g2.setColor(Color.BLACK);
        g2.drawPolygon(triangleX,triangleY,3);
        g2.drawPolygon(triangleX2,triangleY2,3);

        // back
        g2.drawLine(0,bodyH/3,0,-bodyH/3);

        // random feature
        if (random > 0.5){
            g2.drawLine(-bodyW/8,5*bodyH/12,-bodyW/8,-5*bodyH/12);
            g2.fillOval(-bodyW/2,0,bodyW/10,bodyW/10);
        }


        if (angle > Math.PI/2 && angle < Math.PI*3/2
             || angle < -Math.PI/2 && angle > -Math.PI*3/2){
            // eye
            g2.drawOval(3*bodyW/10,0,bodyH/10,bodyH/10);
            g2.fillOval(3*bodyW/10,0,bodyH/20,bodyH/20);

            // mouse
            g2.drawLine(bodyW*3/10,-2*bodyH/15,bodyW/5,-1/15*bodyH);
        }else {

            // eye
            g2.drawOval(3*bodyW/10,-bodyH/10,bodyH/10,bodyH/10);
            g2.fillOval(3*bodyW/10,-bodyH/10,bodyH/20,bodyH/20);

            g2.drawLine(bodyW*3/10,2*bodyH/15,bodyW/5,1/15*bodyH);

        }

        // Eat
        if(hasEat > 0) {
            g2.fillOval(bodyW*3/10,-2*bodyH/15,bodyW/25*hasEat,bodyW/25*hasEat);
            hasEat--;
        }

        g2.setTransform(at);
    }

    boolean eat(PVector targ) {
        boolean reach = false;

        // calculate the path to target point
        PVector path = PVector.sub(targ, loc);

        // returns the direction as angle
        float angle = path.heading();

        // make a vel that points toward the target and then move
        vel = PVector.fromAngle(angle);
        vel.mult(3);

        // check if bug reaches target
        if (path.mag()- bodyW / 2 <= 10 ) {
            reach = true;
            hasEat = 20;
            vel.div(3);
        }

        return reach;
    }

    public void move() {
        loc.add(vel);

        wallDetect();

//        //make it walk randomly
//        angle += 0.04 * dir;
//        if ( Math.random()*32 < 1) {
//            dir *= -1;
//        }
//        vel.set((float) (1* Math.cos(angle)), (float) (1* Math.sin(angle)));
    }

    private void wallDetect() {
        // Collision against right edge of garden
        if (loc.x + (bodyW / 2 + bodyW / 4) > (Ocean.OCEAN_X + Ocean.OCEAN_W)) {
            loc.x = Ocean.OCEAN_X + Ocean.OCEAN_W - (bodyW / 2 + bodyW / 4);
            vel.x = -vel.x;
        }

        // Collision against left edge of garden
        if (loc.x - (bodyW / 2 + bodyW / 4) < Ocean.OCEAN_X) {
            loc.x = Ocean.OCEAN_X + (bodyW / 2 + bodyW / 4);
            vel.x = -vel.x;
        }

        // Collision against top or bottom edge of garden
        if (loc.y + (bodyW / 2 + bodyW / 4) > (Ocean.OCEAN_Y + Ocean.OCEAN_H)) {
            loc.y = Ocean.OCEAN_Y + Ocean.OCEAN_H - (bodyW / 2 + bodyW / 4);
            vel.y = -vel.y;
        }
        if (loc.y - (bodyW / 2 + bodyW / 4) < Ocean.OCEAN_Y) {
            loc.y = Ocean.OCEAN_Y + (bodyW / 2 + bodyW / 4);
            vel.y = -vel.y;
        }
    }


    public void setScale(double scale) {
        if (scale <= 1.5) {
            this.scale = scale;
        }else {
            this.scale = 1.5;
        }
    }

    public double getScale() {
        return scale;
    }
}
