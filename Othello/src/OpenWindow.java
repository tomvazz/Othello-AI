import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;

public class OpenWindow {

	private JFrame frame;
	private ImageIcon pvp;
	private ImageIcon pvc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpenWindow window = new OpenWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OpenWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("OTHELLO");
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(500, 300, 350, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//images
		pvp = new ImageIcon(this.getClass().getResource("/pvp.png"));
		pvc = new ImageIcon(this.getClass().getResource("/pvc.png"));
		
		Gameplay g = new Gameplay(0);
		
		JButton manvman = new JButton(pvp);
		manvman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int gamemode = 0;
				frame.dispose();
				g.game(gamemode);
			}
		});
		manvman.setBounds(17, 28, 312, 110);
		frame.getContentPane().add(manvman);
		
		JButton manvcomputer = new JButton(pvc);
		manvcomputer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int gamemode = 1;
				frame.dispose();
				g.game(gamemode);
			}
		});
		manvcomputer.setBounds(17, 150, 312, 110);
		frame.getContentPane().add(manvcomputer);
	}

}
