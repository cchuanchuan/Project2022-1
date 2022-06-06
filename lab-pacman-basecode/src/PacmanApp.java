import javax.swing.JFrame;

public class PacmanApp extends JFrame {

	public PacmanApp(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800,600);
		
		//instantiating our BallPanel
		PacmanPanel panel = new PacmanPanel(this.getSize());
		
		//adding it to the current frame
		this.add(panel);
		
		//displaying the frame
		this.setVisible(true);
	}

	public static void main(String[] args) {
		PacmanApp app = new PacmanApp("My Pacman App");
	}

}
