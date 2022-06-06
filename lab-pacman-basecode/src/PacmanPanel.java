import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PacmanPanel extends JPanel implements ActionListener {
	
	private Pacman ball;
	private Pacman ball2;
	private Timer t; 
	private Dimension size;
//	private int bodyW;
	
	public PacmanPanel(Dimension initialSize) {
		super();
		
		size = initialSize;
		ball = new Pacman(initialSize.width/2, initialSize.height/2,
				Math.min(initialSize.width,initialSize.height)/10, 
				(int)Util.random(-10, 10),  (int)Util.random(-10, 10), Util.randomColor());
		//ball2
		ball2=new Pacman(initialSize.width/4, initialSize.height/4,
				Math.min(initialSize.width,initialSize.height)/10, 
				(int)Util.random(-10, 10),  (int)Util.random(-10, 10), Util.randomColor());
		
		t = new Timer(33, this);
		t.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		size = getSize();
		setBackground(Color.DARK_GRAY);
		ball.draw(g2);
		ball2.draw(g2);		
		//ball2.draw(g2);
		}
		
			
		


	@Override
	public void actionPerformed(ActionEvent e) {
		ball.move();
		ball.checkCollision(size);
		ball2.move();
		ball2.checkCollision(size);
		
		if(ball.approach(ball2.getPos())){
			ball2 = null; //set it blank when hito
			//System.out.println("123");
			
			ball2=new Pacman(300, 400,50, (int)Util.random(-10, 10),  (int)Util.random(-10, 10), Util.randomColor());
			ball.growup();
		}
		
		repaint();
	}
}
	
	
//
	
