import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import processing.core.PVector;

public class Pacman {
	
	private int size;
	private double scale;
	private PVector speed;
	private Color color;
	private PVector pos;
	
	//private PVector otherpos;
	
	public Pacman(int x, int y, int size, int speedx, int speedy, Color c) {
		this.pos = new PVector(x,y);
		this.size = size;
		this.speed = new PVector(speedx, speedy);
		this.color = c;
		this.scale = 1.25;
		//float distance = PVector.dist(pos, otherpos);
	}

	public void draw(Graphics2D g) {
		AffineTransform af = g.getTransform();
		g.translate(pos.x, pos.y);
		g.rotate(speed.heading());
		g.scale(scale,scale);
		if(speed.x<0)g.scale(1,-1);
		
		g.setColor(color);
		g.fillArc(-size/2, -size/2, size, size, 30, 300);
		
		//eye
		g.setColor(Color.black);
		g.fillOval(0, -size/4, size/10, size/10);
		g.setTransform(af);
		
		
	}
	
	public void move() {
	    pos.add(speed);
	    

	}
	
	public void growup() {
		size=(int)(size *1.1);
	}
	
	
	boolean approach(PVector targ) {
		boolean reach = false;

		// calculate the path to target point
	    PVector path = PVector.sub(targ, pos);
			
				// check if bug reaches target
		if (path.mag()- size / 2 <= 15 ) {
			reach = true;
		}

		return reach;
	}
	public PVector getPos() {
		// TODO Auto-generated method stub
		return pos;
	}

	

	public void checkCollision(Dimension panelSize) {
		if ((pos.x < size/2*scale) || (pos.x > panelSize.width - size/2*scale)) speed.x *= -1;
		if ((pos.y < size/2*scale) || (pos.y > panelSize.height - size/2*scale)) speed.y *= -1;
		
	}
	

}
