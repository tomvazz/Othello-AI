import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Gameplay {

	private JFrame frame;
	
	private JLabel background;
	private JLabel blackscore;
	private JLabel whitescore;
	private JLabel whitecircletext;
	private JLabel blackcircletext;
	private JLabel diamonds1;
	private JLabel diamonds2;
	private int blackcount;
	private int whitecount;
	private int isspecial = 0;
	private JLabel restarttext;
	private JLabel exittext;
	private JButton restartbutton;
	private JButton exitbutton;
	private JLabel[][] moveindicator = new JLabel[8][8];
	private JLabel[][] piece = new JLabel[8][8];
	private JButton[][] space = new JButton[8][8];
	
	private ImageIcon whiteblock;
	
	private ImageIcon backdropdark;
	private ImageIcon emptydark;
	private ImageIcon blackblock;
	private ImageIcon backdroplight;
	private ImageIcon lightblock;
	private ImageIcon backdropblue;
	private ImageIcon emptyblue;
	private ImageIcon blueblock;
	private ImageIcon backdroppurple;
	private ImageIcon emptypurple;
	private ImageIcon purpleblock;
	private ImageIcon backdropwood;
	private ImageIcon emptywood;
	private ImageIcon backdroptrees;
	private ImageIcon emptytrees;
	private ImageIcon blackl;
	private ImageIcon bluel;
	private ImageIcon purplel;
	private ImageIcon woodl;
	private ImageIcon redl;
	private ImageIcon whitel;
	private JButton darktheme;
	private JButton lighttheme;
	private JButton woodtheme;
	private JButton purpletheme;
	private JButton bluetheme;
	private JButton redtheme;
	
	public String[][] pos = new String[8][8];
	private String turn = "black";
	public static String char00 = " ";
	
	private int gameover = 0;
	public static int mode = 1;
	private int firstmove = 1;
	public static int compindex1;
	public static int compindex2;
	public static int gamestatus;

	//timer
	public JLabel timerones;
	public JLabel timertens;
	public JLabel colon;
	public JLabel timerminutes;
	public int onespassed = 0;
	public int tenspassed = 0;
	public int minutespassed = 0;
	Timer t = new Timer();
	TimerTask tt = new TimerTask() {
		@Override
		public void run() {
			if (gameover == 0) {
				onespassed++;
				if (onespassed == 10) {
					onespassed = 0;
					tenspassed++;
					if (tenspassed == 6) {
						tenspassed = 0;
						minutespassed++;
						timerminutes.setText(String.valueOf(minutespassed));
					}
					timertens.setText(String.valueOf(tenspassed));
				}
				timerones.setText(String.valueOf(onespassed));
			}
		}
	};
	
	//for ai delay
	public int numspassed = 0; 
	public int timenum = -1;
	Timer t1 = new Timer();
	TimerTask tt1 = new TimerTask() {
		@Override
		public void run() {
			numspassed++;
			if (numspassed == timenum) {
				if (gamestatus == 1) {
					gameoverchecker();
					pos[0][0] = char00;
					gamestatus = 0;
				}
				possiblemoves("X");
				posapplication();
				scoreupdate();
				//resets moveindicator
				for (int a = 0; a < 8; a++) {
					for (int b = 0; b < 8; b++) {
						moveindicator[a][b].setText("");
					}
				}
				moveindicator[compindex1][compindex2].setText("n");
			}
		}
	};

	/**
	 * Launch the application.
	 */
	public void game(int gamemode) {
		mode = gamemode;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gameplay window = new Gameplay(gamemode);
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
	public Gameplay(int gamemode) {
		mode = gamemode;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("OTHELLO");
		frame.setBounds(200, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//images
		whiteblock = new ImageIcon(this.getClass().getResource("/whiteblock.png"));
		
		backdroplight = new ImageIcon(this.getClass().getResource("/backdroplight.png"));
		lightblock = new ImageIcon(this.getClass().getResource("/lightblock.png"));
		
		backdropdark = new ImageIcon(this.getClass().getResource("/backdropdark.png"));
		emptydark = new ImageIcon(this.getClass().getResource("/emptyspace.png"));
		blackblock = new ImageIcon(this.getClass().getResource("/blackblock.png"));
		
		backdropblue = new ImageIcon(this.getClass().getResource("/backdropblue.png"));
		emptyblue = new ImageIcon(this.getClass().getResource("/emptyblue.png"));
		blueblock = new ImageIcon(this.getClass().getResource("/blueblock.png"));
		
		backdroppurple = new ImageIcon(this.getClass().getResource("/backdroppurple.png"));
		emptypurple = new ImageIcon(this.getClass().getResource("/emptypurple.png"));
		purpleblock = new ImageIcon(this.getClass().getResource("/purpleblock.png"));
		
		backdropwood = new ImageIcon(this.getClass().getResource("/backdropwood.png"));
		emptywood = new ImageIcon(this.getClass().getResource("/emptywood.png"));
		
		backdroptrees = new ImageIcon(this.getClass().getResource("/backdroptrees.png"));
		emptytrees = new ImageIcon(this.getClass().getResource("/emptytrees.png"));
		
		blackl = new ImageIcon(this.getClass().getResource("/blackl.png"));
		bluel = new ImageIcon(this.getClass().getResource("/bluel.png"));
		purplel = new ImageIcon(this.getClass().getResource("/purplel.png"));
		woodl = new ImageIcon(this.getClass().getResource("/woodl.png"));
		redl = new ImageIcon(this.getClass().getResource("/redl.png"));
		whitel = new ImageIcon(this.getClass().getResource("/whitel.png"));
		
		
		//timer labels
		timerones = new JLabel(String.valueOf(onespassed));
		timerones.setFont(new Font("Futura", Font.PLAIN, 23));
		timerones.setHorizontalAlignment(SwingConstants.CENTER);
		timerones.setForeground(Color.WHITE);
		timerones.setBounds(510, 688, 25, 30);
		frame.getContentPane().add(timerones);
		timertens = new JLabel(String.valueOf(tenspassed));
		timertens.setFont(new Font("Futura", Font.PLAIN, 23));
		timertens.setHorizontalAlignment(SwingConstants.CENTER);
		timertens.setForeground(Color.WHITE);
		timertens.setBounds(493, 688, 25, 30);
		frame.getContentPane().add(timertens);
		colon = new JLabel(String.valueOf(":"));
		colon.setFont(new Font("Futura", Font.PLAIN, 23));
		colon.setHorizontalAlignment(SwingConstants.CENTER);
		colon.setForeground(Color.WHITE);
		colon.setBounds(478, 686, 20, 30);
		frame.getContentPane().add(colon);
		timerminutes = new JLabel(String.valueOf(minutespassed));
		timerminutes.setFont(new Font("Futura", Font.PLAIN, 23));
		timerminutes.setHorizontalAlignment(SwingConstants.RIGHT);
		timerminutes.setForeground(Color.WHITE);
		timerminutes.setBounds(440, 688, 40, 30);
		frame.getContentPane().add(timerminutes);
		
		start();
		if (mode == 1) {
			aitimedelay();
		}
		
		//array all set to " "
		for (int i = 0; i < 8; i++){
            for (int e = 0; e < 8; e++){
                pos[i][e] = " ";
            }
        }
		
		//bottom panel setup
		diamonds1 = new JLabel("v");
		diamonds1.setHorizontalAlignment(SwingConstants.CENTER);
		diamonds1.setForeground(Color.DARK_GRAY);
		diamonds1.setFont(new Font("Wingdings", Font.PLAIN, 74));
		diamonds1.setBounds(38, 673, 75, 75);
		frame.getContentPane().add(diamonds1);
		
		blackcircletext = new JLabel(" ");
		blackcircletext.setHorizontalAlignment(SwingConstants.CENTER);
		blackcircletext.setForeground(Color.GREEN);
		blackcircletext.setFont(new Font("Futura", Font.PLAIN, 20));
		blackcircletext.setBounds(162, 670, 75, 75);
		frame.getContentPane().add(blackcircletext);
		
		JLabel blackcircle = new JLabel("n");
		blackcircle.setHorizontalAlignment(SwingConstants.CENTER);
		blackcircle.setForeground(Color.BLACK);
		blackcircle.setFont(new Font("Webdings", Font.PLAIN, 74));
		blackcircle.setBounds(162, 670, 75, 75);
		frame.getContentPane().add(blackcircle);
		
		blackscore = new JLabel("2");
		blackscore.setHorizontalAlignment(SwingConstants.LEFT);
		blackscore.setForeground(Color.WHITE);
		blackscore.setFont(new Font("Futura", Font.PLAIN, 50));
		blackscore.setBounds(255, 674, 75, 75);
		frame.getContentPane().add(blackscore);
		
		diamonds2 = new JLabel("v");
		diamonds2.setHorizontalAlignment(SwingConstants.CENTER);
		diamonds2.setForeground(Color.DARK_GRAY);
		diamonds2.setFont(new Font("Wingdings", Font.PLAIN, 74));
		diamonds2.setBounds(886, 673, 75, 75);
		frame.getContentPane().add(diamonds2);
		
		whitecircletext = new JLabel(" ");
		whitecircletext.setHorizontalAlignment(SwingConstants.CENTER);
		whitecircletext.setForeground(Color.GREEN);
		whitecircletext.setFont(new Font("Futura", Font.PLAIN, 20));
		whitecircletext.setBounds(762, 670, 75, 75);
		frame.getContentPane().add(whitecircletext);
		
		JLabel whitecircle = new JLabel("n");
		whitecircle.setHorizontalAlignment(SwingConstants.CENTER);
		whitecircle.setForeground(Color.LIGHT_GRAY);
		whitecircle.setFont(new Font("Webdings", Font.PLAIN, 74));
		whitecircle.setBounds(762, 670, 75, 75);
		frame.getContentPane().add(whitecircle);
		
		whitescore = new JLabel("2");
		whitescore.setHorizontalAlignment(SwingConstants.RIGHT);
		whitescore.setForeground(Color.WHITE);
		whitescore.setFont(new Font("Futura", Font.PLAIN, 50));
		whitescore.setBounds(668, 670, 75, 75);
		frame.getContentPane().add(whitescore);
		
		JLabel clock = new JLabel("clock");
		clock.setFont(new Font("Futura", Font.PLAIN, 19));
		clock.setHorizontalAlignment(SwingConstants.CENTER);
		clock.setForeground(Color.DARK_GRAY);
		clock.setBounds(467, 662, 61, 30);
		frame.getContentPane().add(clock);
		
		JLabel dashdesign = new JLabel("____");
		dashdesign.setFont(new Font("Futura", Font.PLAIN, 19));
		dashdesign.setHorizontalAlignment(SwingConstants.CENTER);
		dashdesign.setForeground(Color.DARK_GRAY);
		dashdesign.setBounds(468, 701, 61, 30);
		frame.getContentPane().add(dashdesign);
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				moveindicator[a][b] = new JLabel("");
				moveindicator[a][b].setHorizontalAlignment(SwingConstants.CENTER);
				moveindicator[a][b].setForeground(Color.RED);
				moveindicator[a][b].setFont(new Font("Webdings", Font.PLAIN, 8));
				moveindicator[a][b].setBounds((210 + (a*74)), (555 - (b*74)), 61, 61);
				frame.getContentPane().add(moveindicator[a][b]);
			}
		}
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				piece[a][b] = new JLabel("");
				piece[a][b].setHorizontalAlignment(SwingConstants.CENTER);
				piece[a][b].setForeground(Color.DARK_GRAY);
				piece[a][b].setFont(new Font("Webdings", Font.PLAIN, 60));
				piece[a][b].setBounds((210 + (a*74)), (555 - (b*74)), 61, 61);
				frame.getContentPane().add(piece[a][b]);
			}
		}
		//inital setup
		if (firstmove == 1) {
			pos[3][3] = "O";
			pos[3][4] = "X";
			pos[4][4] = "O";
			pos[4][3] = "X";

			possiblemoves("X");
			posapplication();
			firstmove = 0;
		}
        

		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				space[a][b] = new JButton(emptydark);
				space[a][b].setBounds((210 + (a*74)), (555 - (b*74)), 61, 61);
				frame.getContentPane().add(space[a][b]);
			}
		}
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				int x = a;
				int y = b;
				space[a][b].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonactioncontent(x, y);
					}
				});
			}
		}
		
		restarttext = new JLabel("");
		restarttext.setFont(new Font("Futura", Font.PLAIN, 30));
		restarttext.setHorizontalAlignment(SwingConstants.CENTER);
		restarttext.setForeground(Color.DARK_GRAY);
		restarttext.setBounds(25, 552, 150, 70);
		frame.getContentPane().add(restarttext);
		
		restartbutton = new JButton(blackblock);
		restartbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamestatus = 0;
				onespassed = 0;
				tenspassed = 0;
				minutespassed = 0;
				gameover = 0;
				//array all set to " "
				for (int i = 0; i < 8; i++){
		            for (int o = 0; o < 8; o++) {
		                pos[i][o] = " ";
		            }
		        }
				
				firstmove = 1;
				//inital setup
				if (firstmove == 1) {
					piece[3][3].setText("n");
					piece[3][3].setForeground(Color.BLACK);
					piece[3][4].setText("n");
					piece[3][4].setForeground(Color.LIGHT_GRAY);
					piece[4][4].setText("n");
					piece[4][4].setForeground(Color.BLACK);
					piece[4][3].setText("n");
					piece[4][3].setForeground(Color.LIGHT_GRAY);
					pos[3][3] = "O";
					pos[3][4] = "X";
					pos[4][4] = "O";
					pos[4][3] = "X";
					possiblemoves("X");
			        posapplication();
					firstmove = 0;
				}
		        
		        blackscore.setText("2");
		        whitescore.setText("2");
		        
		        timerones.setText("0");;
		    	timertens.setText("0");;
		    	timerminutes.setText("0");
		    	
		    	whitecircletext.setText("");
				diamonds2.setForeground(Color.DARK_GRAY);
				blackcircletext.setText("");
				diamonds1.setForeground(Color.DARK_GRAY);
				
				themechange(backdropdark, blackblock, emptydark);
				themelbl(blackl);
				
				//resets moveindicator
				for (int a = 0; a < 8; a++) {
					for (int b = 0; b < 8; b++) {
						moveindicator[a][b].setText("");
					}
				}
				
				turn = "black";
				
			}
		});
		restartbutton.setFont(new Font("Futura", Font.PLAIN, 30));
		restartbutton.setForeground(Color.DARK_GRAY);
		restartbutton.setBounds(25, 552, 150, 70);
		frame.getContentPane().add(restartbutton);
		
		exittext = new JLabel("");
		exittext.setFont(new Font("Futura", Font.PLAIN, 30));
		exittext.setHorizontalAlignment(SwingConstants.CENTER);
		exittext.setForeground(Color.DARK_GRAY);
		exittext.setBounds(825, 552, 150, 70);
		frame.getContentPane().add(exittext);
		
		exitbutton = new JButton(blackblock);
		exitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitbutton.setFont(new Font("Futura", Font.PLAIN, 30));
		exitbutton.setForeground(Color.DARK_GRAY);
		exitbutton.setBounds(825, 552, 150, 70);
		frame.getContentPane().add(exitbutton);
		
		JLabel[] themelabel = new JLabel[6];
		for (int d = 0; d < 6; d++) {
			themelabel[d] = new JLabel("n");
			themelabel[d].setHorizontalAlignment(SwingConstants.CENTER);
			themelabel[d].setFont(new Font("Webdings", Font.PLAIN, 13));
			themelabel[d].setBounds((390 + (40*d)), 739, 17, 17);
			frame.getContentPane().add(themelabel[d]);
		}
		themelabel[0].setForeground(Color.DARK_GRAY);
		themelabel[1].setForeground(Color.LIGHT_GRAY);
		themelabel[2].setForeground(new Color(160, 82, 45));
		themelabel[3].setForeground(new Color(72, 209, 204));
		themelabel[4].setForeground(new Color(106, 90, 205));
		themelabel[5].setForeground(new Color(255, 0, 0));
		
		darktheme = new JButton(blackl);
		darktheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				themechange(backdropdark, blackblock, emptydark);
				themelbl(blackl);
			}
		});
		darktheme.setBounds(390, 739, 17, 17);
		frame.getContentPane().add(darktheme);
		
		lighttheme = new JButton(blackl);
		lighttheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isspecial = 1;
				themechange(backdroplight, lightblock, whiteblock);
				themelbl(whitel);
			}
		});
		lighttheme.setBounds(430, 739, 17, 17);
		frame.getContentPane().add(lighttheme);
		
		woodtheme = new JButton(blackl);
		woodtheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isspecial = 1;
				themechange(backdropwood, lightblock, emptywood);
				themelbl(woodl);
			}
		});
		woodtheme.setBounds(470, 739, 17, 17);
		frame.getContentPane().add(woodtheme);
		
		bluetheme = new JButton(blackl);
		bluetheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				themechange(backdropblue, blueblock, emptyblue);
				themelbl(bluel);
			}
		});
		bluetheme.setBounds(510, 739, 17, 17);
		frame.getContentPane().add(bluetheme);
		
		purpletheme = new JButton(blackl);
		purpletheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				themechange(backdroppurple, purpleblock, emptypurple);
				themelbl(purplel);
			}
		});
		purpletheme.setBounds(550, 739, 17, 17);
		frame.getContentPane().add(purpletheme);
		
		redtheme = new JButton(blackl);
		redtheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isspecial = 1;
				themechange(backdroptrees, lightblock, emptytrees);
				themelbl(redl);
			}
		});
		redtheme.setBounds(590, 739, 17, 17);
		frame.getContentPane().add(redtheme);
		
		//backdrop
		background = new JLabel(backdropdark);
		background.setForeground(Color.DARK_GRAY);
		background.setBounds(0, 0, 1000, 800);
		frame.getContentPane().add(background);
		
	}
	public void themelbl(ImageIcon theme) {
		darktheme.setIcon(theme);
		lighttheme.setIcon(theme);
		woodtheme.setIcon(theme);
		purpletheme.setIcon(theme);
		bluetheme.setIcon(theme);
		redtheme.setIcon(theme);
	}
	public void themechange(ImageIcon background1, ImageIcon erblock, ImageIcon spaceicon) {
		background.setIcon(background1);
		restartbutton.setIcon(erblock);
		exitbutton.setIcon(erblock);
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				space[a][b].setIcon(spaceicon);
			}
		}
		
		if (isspecial == 0) {
			restarttext.setText("");
			exittext.setText("");
		} else if (isspecial == 1) {
			restarttext.setText("restart");
			exittext.setText("exit");
			isspecial = 0;
		}
		
	}
	
	//timer start
	public void start() {
		t.schedule(tt, 1000, 1000);
	}
	public void aitimedelay(){
		t1.schedule(tt1, 1000, 1000);
	}
	
	public void buttonactioncontent(int x, int y) {
		if (turn == "black") {
			buttonclicked("X", x, y);
			turn = "white";
			if (mode == 1) {
				char00 = pos[0][0];
				timenum = numspassed + 2; //incorporates delay by updating board and score (posapplication, scoreupdate) after 2 seconds, code is at the top
				Ai i = new Ai();
				i.scoretracker(pos, "O");
				turn = "black";
			}
		} else if (turn == "white" && mode == 0) {
			buttonclicked("O", x, y);
			turn = "black";
		} 
	}
	
	public void buttonclicked(String xoro, int index1, int index2) {
		
		int validmove = 0;
		if (pos[index1][index2] == ".") {
			validmove = 1;
		}
		
		//resets moveindicator
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				moveindicator[a][b].setText("");
			}
		}
		
		if (validmove == 1) {
			pos[index1][index2] = xoro;
			moveindicator[index1][index2].setText("n");
			directionalscan(index1, index2, xoro);
			if (xoro == "X" && mode == 0) {
				possiblemoves("O");
			} else if (xoro == "O" && mode == 0) {
				possiblemoves("X");
			}
			if (mode == 1) {
				for (int x = 0; x < 8; x++){
		            for (int y = 0; y < 8; y++){
		                if (pos[x][y] == "."){
		                    pos[x][y] = " ";
		                }
		            }
		        }
			}
			posapplication();
			scoreupdate();
		}
	}
	
	public void aiimplementation(String[][]aipos, int i1, int i2, int gamex) {
		
		gamestatus = gamex;
		compindex1 = i1;
		compindex2 = i2;
		
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				if (gamestatus == 0) {
					pos[a][b] = aipos[a][b];
				}
			}
		}
	}
	
	public void posapplication() {

		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				if (pos[a][b] == "X") {
					piece[a][b].setText("n");
					piece[a][b].setForeground(Color.BLACK);
					piece[a][b].setFont(new Font("Webdings", Font.PLAIN, 60));
				} else if (pos[a][b] == "O") {
					piece[a][b].setText("n");
					piece[a][b].setForeground(Color.LIGHT_GRAY);
					piece[a][b].setFont(new Font("Webdings", Font.PLAIN, 60));
				} else if (pos[a][b] == ".") {
					piece[a][b].setText("n");
					piece[a][b].setForeground(Color.GREEN);
					piece[a][b].setFont(new Font("Webdings", Font.PLAIN, 20));
				} else if (pos[a][b] == " ") {
					piece[a][b].setText("");
				}
			}
		}
		
	}
	
	public void scoreupdate() {
		blackcount = 0;
		whitecount = 0;
		for (int a = 0; a < 8; a++) {
			for (int b = 0; b < 8; b++) {
				if (pos[a][b] == "O") {
					whitecount++;
				} else if (pos[a][b] == "X") {
					blackcount++;
				}
			}
		}
		blackscore.setText(String.valueOf(blackcount));
		whitescore.setText(String.valueOf(whitecount));
		
	}
	
	public void directionalscan(int index1, int index2, String xoro){
        /*
        Each of these scanners
        1. scan their respective direction to see if there is an X
        2. if an x is hit, it scans everything inbetween to see if there is a space or another X
        3. if there are no spaces or X's in between (only O's), then each O inbetween is converted to an X
         */

        // scans north
        int ncount = 0;
        int nrand = 0;
        for (int a = index2+2; a <= 7; a++){
            if(pos[index1][a] == xoro) {
                ncount = a;
                nrand = 1;
                break;
            }
        }
        int nEmptyOrXCount = 2;
        if (nrand > 0){
            for (int b = index2+1; b < ncount; b++ ){
                if ((pos[index1][b] == " ") || (pos[index1][b] == xoro) || (pos[index1][b] == ".")){
                    nEmptyOrXCount = 1;
                    break;
                } else {
                    nEmptyOrXCount = 0;
                }
            }
        }
        if (nEmptyOrXCount == 0){
            for (int b = index2+1; b < ncount; b++ ){
                pos[index1][b] = xoro;
            }
        }

        // scans south
        int scount = 0;
        int srand = 0;
        for (int a = index2-2; a >= 0; a--){
            if(pos[index1][a] == xoro) {
                scount = a;
                srand = 1;
                break;
            }
        }
        int sEmptyOrXCount = 2;
        if (srand > 0){
            for (int b = index2-1; b > scount; b-- ){
                if ((pos[index1][b] == " ") || (pos[index1][b] == xoro) || (pos[index1][b] == ".")){
                    sEmptyOrXCount = 1;
                    break;
                } else {
                    sEmptyOrXCount = 0;
                }
            }
        }
        if (sEmptyOrXCount == 0){
            for (int b = index2-1; b > scount; b-- ){
                pos[index1][b] = xoro;
            }
        }

        // scans east
        int ecount = 0;
        int erand = 0;
        for (int a = index1+2; a <= 7; a++){
            if(pos[a][index2] == xoro) {
                ecount = a;
                erand = 1;
                break;
            }
        }
        int eEmptyOrXCount = 2;
        if (erand > 0){
            for (int b = index1+1; b < ecount; b++ ){
                if ((pos[b][index2] == " ") || (pos[b][index2] == xoro) || (pos[b][index2] == ".")){
                    eEmptyOrXCount = 1;
                    break;
                } else {
                    eEmptyOrXCount = 0;
                }
            }
        }
        if (eEmptyOrXCount == 0){
            for (int b = index1+1; b < ecount; b++ ){
                pos[b][index2] = xoro;
            }
        }

        // scans west
        int wcount = 0;
        int wrand = 0;
        for (int a = index1-2; a >= 0; a--){
            if(pos[a][index2] == xoro) {
                wcount = a;
                wrand = 1;
                break;
            }
        }
        int wEmptyOrXCount = 2;
        if (wrand > 0){
            for (int b = index1-1; b > wcount; b-- ){
                if ((pos[b][index2] == " ") || (pos[b][index2] == xoro) || (pos[b][index2] == ".")){
                    wEmptyOrXCount = 1;
                    break;
                } else {
                    wEmptyOrXCount = 0;
                }
            }
        }
        if (wEmptyOrXCount == 0){
            for (int b = index1-1; b > wcount; b-- ){
                pos[b][index2] = xoro;
            }
        }

        // scans northeast
        if ((index1 <= 5) && (index2 <= 5)) {
            int necount1a = index1 + 2;
            int necount2a = index2 + 2;
            int nehasx = 0;
            for (int a = necount1a; a <= 7; a++) {
                if ((pos[a][necount2a]) == xoro) {
                    necount1a = a;
                    nehasx = 1;
                    break;
                }
                if (necount2a == 7) {
                    break;
                }
                necount2a++;
            }
            if (nehasx == 1) {
                int necount1b = index1 + 1;
                int necount2b = index2 + 1;
                int neEmptyOrXCount = 2;
                for (int b = necount1b; b < necount1a; b++) {
                    if ((pos[b][necount2b] == " ") || (pos[b][necount2b] == xoro) || (pos[b][necount2b] == ".")) {
                        neEmptyOrXCount = 1;
                        break;
                    } else {
                        neEmptyOrXCount = 0;
                    }
                    if (necount2b == necount2a - 1) {
                        break;
                    }
                    necount2b++;
                }
                necount1b = index1 + 1;
                necount2b = index2 + 1;
                if (neEmptyOrXCount == 0) {
                    for (int b = necount1b; b < necount1a; b++) {
                        pos[b][necount2b] = xoro;

                        if (necount2b == necount2a - 1) {
                            break;
                        }
                        necount2b++;
                    }
                }
            }
        }

        // scans northwest
        if ((index1 >= 2) && (index2 <= 5)) {
            int nwcount1a = index1 - 2;
            int nwcount2a = index2 + 2;
            int nwhasx = 0;
            for (int a = nwcount1a; a >= 0; a--) {
                if ((pos[a][nwcount2a]) == xoro) {
                    nwcount1a = a;
                    nwhasx = 1;
                    break;
                }
                if (nwcount2a == 7) {
                    break;
                }
                nwcount2a++;
            }
            if (nwhasx == 1) {
                int nwcount1b = index1 - 1;
                int nwcount2b = index2 + 1;
                int nwEmptyOrXCount = 2;
                for (int b = nwcount1b; b > nwcount1a; b--) {
                    if ((pos[b][nwcount2b] == " ") || (pos[b][nwcount2b] == xoro) || (pos[b][nwcount2b] == ".")) {
                        nwEmptyOrXCount = 1;
                        break;
                    } else {
                        nwEmptyOrXCount = 0;
                    }
                    if (nwcount2b == nwcount2a - 1) {
                        break;
                    }
                    nwcount2b++;
                }
                nwcount1b = index1 - 1;
                nwcount2b = index2 + 1;
                if (nwEmptyOrXCount == 0) {
                    for (int b = nwcount1b; b > nwcount1a; b--) {
                        pos[b][nwcount2b] = xoro;
                        if (nwcount2b == nwcount2a - 1) {
                            break;
                        }
                        nwcount2b++;
                    }
                }
            }
        }

        //scans southeast
        if ((index1 <= 5) && (index2 >= 2)) {
            int secount1a = index1 + 2;
            int secount2a = index2 - 2;
            int sehasx = 0;
            for (int a = secount1a; a <= 7; a++) {
                if ((pos[a][secount2a]) == xoro) {
                    secount1a = a;
                    sehasx = 1;
                    break;
                }
                if (secount2a == 0) {
                    break;
                }
                secount2a--;
            }
            if (sehasx == 1) {
                int secount1b = index1 + 1;
                int secount2b = index2 - 1;
                int seEmptyOrXCount = 2;
                for (int b = secount1b; b < secount1a; b++) {
                    if ((pos[b][secount2b] == " ") || (pos[b][secount2b] == xoro) || (pos[b][secount2b] == ".")) {
                        seEmptyOrXCount = 1;
                        break;
                    } else {
                        seEmptyOrXCount = 0;
                    }
                    if (secount2b == secount2a + 1) {
                        break;
                    }
                    secount2b--;
                }
                secount1b = index1 + 1;
                secount2b = index2 - 1;
                if (seEmptyOrXCount == 0) {
                    for (int b = secount1b; b < secount1a; b++) {
                        pos[b][secount2b] = xoro;
                        if (secount2b == secount2a + 1) {
                            break;
                        }
                        secount2b--;
                    }
                }
            }
        }

        // scans southwest
        if ((index1 >= 2) && (index2 >= 2)) {
            int swcount1a = index1 - 2;
            int swcount2a = index2 - 2;
            int swhasx = 0;
            for (int a = swcount1a; a >= 0; a--) {
                if ((pos[a][swcount2a]) == xoro) {
                    swcount1a = a;
                    swhasx = 1;
                    break;
                }
                if (swcount2a == 0) {
                    break;
                }
                swcount2a--;
            }
            if (swhasx == 1) {
                int swcount1b = index1 - 1;
                int swcount2b = index2 - 1;
                int swEmptyOrXCount = 2;
                for (int b = swcount1b; b > swcount1a; b--) {
                    if ((pos[b][swcount2b] == " ") || (pos[b][swcount2b] == xoro) || (pos[b][swcount2b] == ".")) {
                        swEmptyOrXCount = 1;
                        break;
                    } else {
                        swEmptyOrXCount = 0;
                    }
                    if (swcount2b == swcount2a + 1) {
                        break;
                    }
                    swcount2b--;
                }
                swcount1b = index1 - 1;
                swcount2b = index2 - 1;
                if (swEmptyOrXCount == 0) {
                    for (int b = swcount1b; b > swcount1a; b--) {
                        pos[b][swcount2b] = xoro;
                        if (swcount2b == swcount2a + 1) {
                            break;
                        }
                        swcount2b--;
                    }
                }
            }
        }

    }
	
	public void possiblemoves(String xoro) {
		
		//take away all "."'s from previous move
		for (int x = 0; x < 8; x++){
            for (int y = 0; y < 8; y++){
                if (pos[x][y] == "."){
                    pos[x][y] = " ";
                }
            }
        }
		
		
		for (int index1 = 0; index1 < 8; index1++) {
            for (int index2 = 0; index2 < 8; index2++) {
                if ((pos[index1][index2] == " ") || (pos[index1][index2] == ".")) {
                    int possiblemove = 0;

                    // scans north
                    int ncount = 0;
                    int nrand = 0;
                    for (int a = index2+2; a <= 7; a++){
                        if(pos[index1][a] == xoro) {
                            ncount = a;
                            nrand = 1;
                            break;
                        }
                    }
                    int nEmptyOrXCount = 2;
                    if (nrand > 0){
                        for (int b = index2+1; b < ncount; b++ ){
                            if ((pos[index1][b] == " ") || (pos[index1][b] == xoro) || (pos[index1][b] == ".")){
                                nEmptyOrXCount = 1;
                                break;
                            } else {
                                nEmptyOrXCount = 0;
                            }
                        }
                    }
                    if (nEmptyOrXCount == 0){
                        for (int b = index2+1; b < ncount; b++ ){
                            possiblemove++;
                        }
                    }

                    // scans south
                    int scount = 0;
                    int srand = 0;
                    for (int a = index2-2; a >= 0; a--){
                        if(pos[index1][a] == xoro) {
                            scount = a;
                            srand = 1;
                            break;
                        }
                    }
                    int sEmptyOrXCount = 2;
                    if (srand > 0){
                        for (int b = index2-1; b > scount; b-- ){
                            if ((pos[index1][b] == " ") || (pos[index1][b] == xoro) || (pos[index1][b] == ".")){
                                sEmptyOrXCount = 1;
                                break;
                            } else {
                                sEmptyOrXCount = 0;
                            }
                        }
                    }
                    if (sEmptyOrXCount == 0){
                        for (int b = index2-1; b > scount; b-- ){
                            possiblemove++;
                        }
                    }

                    // scans east
                    int ecount = 0;
                    int erand = 0;
                    for (int a = index1+2; a <= 7; a++){
                        if(pos[a][index2] == xoro) {
                            ecount = a;
                            erand = 1;
                            break;
                        }
                    }
                    int eEmptyOrXCount = 2;
                    if (erand > 0){
                        for (int b = index1+1; b < ecount; b++ ){
                            if ((pos[b][index2] == " ") || (pos[b][index2] == xoro) || (pos[b][index2] == ".")){
                                eEmptyOrXCount = 1;
                                break;
                            } else {
                                eEmptyOrXCount = 0;
                            }
                        }
                    }
                    if (eEmptyOrXCount == 0){
                        for (int b = index1+1; b < ecount; b++ ){
                            possiblemove++;
                        }
                    }

                    // scans west
                    int wcount = 0;
                    int wrand = 0;
                    for (int a = index1-2; a >= 0; a--){
                        if(pos[a][index2] == xoro) {
                            wcount = a;
                            wrand = 1;
                            break;
                        }
                    }
                    int wEmptyOrXCount = 2;
                    if (wrand > 0){
                        for (int b = index1-1; b > wcount; b-- ){
                            if ((pos[b][index2] == " ") || (pos[b][index2] == xoro) || (pos[b][index2] == ".")){
                                wEmptyOrXCount = 1;
                                break;
                            } else {
                                wEmptyOrXCount = 0;
                            }
                        }
                    }
                    if (wEmptyOrXCount == 0){
                        for (int b = index1-1; b > wcount; b-- ){
                            possiblemove++;
                        }
                    }

                    // scans northeast
                    if ((index1 <= 5) && (index2 <= 5)) {
                        int necount1a = index1 + 2;
                        int necount2a = index2 + 2;
                        int nehasx = 0;
                        for (int a = necount1a; a <= 7; a++) {
                            if ((pos[a][necount2a]) == xoro) {
                                necount1a = a;
                                nehasx = 1;
                                break;
                            }
                            if (necount2a == 7) {
                                break;
                            }
                            necount2a++;
                        }
                        if (nehasx == 1) {
                            int necount1b = index1 + 1;
                            int necount2b = index2 + 1;
                            int neEmptyOrXCount = 2;
                            for (int b = necount1b; b < necount1a; b++) {
                                if ((pos[b][necount2b] == " ") || (pos[b][necount2b] == xoro) || (pos[b][necount2b] == ".")) {
                                    neEmptyOrXCount = 1;
                                    break;
                                } else {
                                    neEmptyOrXCount = 0;
                                }
                                if (necount2b == necount2a - 1) {
                                    break;
                                }
                                necount2b++;
                            }
                            necount1b = index1 + 1;
                            necount2b = index2 + 1;
                            if (neEmptyOrXCount == 0) {
                                for (int b = necount1b; b < necount1a; b++) {
                                    possiblemove++;
                                    if (necount2b == necount2a - 1) {
                                        break;
                                    }
                                    necount2b++;
                                }
                            }
                        }
                    }

                    // scans northwest
                    if ((index1 >= 2) && (index2 <= 5)) {
                        int nwcount1a = index1 - 2;
                        int nwcount2a = index2 + 2;
                        int nwhasx = 0;
                        for (int a = nwcount1a; a >= 0; a--) {
                            if ((pos[a][nwcount2a]) == xoro) {
                                nwcount1a = a;
                                nwhasx = 1;
                                break;
                            }
                            if (nwcount2a == 7) {
                                break;
                            }
                            nwcount2a++;
                        }
                        if (nwhasx == 1) {
                            int nwcount1b = index1 - 1;
                            int nwcount2b = index2 + 1;
                            int nwEmptyOrXCount = 2;
                            for (int b = nwcount1b; b > nwcount1a; b--) {
                                if ((pos[b][nwcount2b] == " ") || (pos[b][nwcount2b] == xoro) || (pos[b][nwcount2b] == ".")) {
                                    nwEmptyOrXCount = 1;
                                    break;
                                } else {
                                    nwEmptyOrXCount = 0;
                                }
                                if (nwcount2b == nwcount2a - 1) {
                                    break;
                                }
                                nwcount2b++;
                            }
                            nwcount1b = index1 - 1;
                            nwcount2b = index2 + 1;
                            if (nwEmptyOrXCount == 0) {
                                for (int b = nwcount1b; b > nwcount1a; b--) {
                                    possiblemove++;
                                    if (nwcount2b == nwcount2a - 1) {
                                        break;
                                    }
                                    nwcount2b++;
                                }
                            }
                        }
                    }

                    //scans southeast
                    if ((index1 <= 5) && (index2 >= 2)) {
                        int secount1a = index1 + 2;
                        int secount2a = index2 - 2;
                        int sehasx = 0;
                        for (int a = secount1a; a <= 7; a++) {
                            if ((pos[a][secount2a]) == xoro) {
                                secount1a = a;
                                sehasx = 1;
                                break;
                            }
                            if (secount2a == 0) {
                                break;
                            }
                            secount2a--;
                        }
                        if (sehasx == 1) {
                            int secount1b = index1 + 1;
                            int secount2b = index2 - 1;
                            int seEmptyOrXCount = 2;
                            for (int b = secount1b; b < secount1a; b++) {
                                if ((pos[b][secount2b] == " ") || (pos[b][secount2b] == xoro) || (pos[b][secount2b] == ".")) {
                                    seEmptyOrXCount = 1;
                                    break;
                                } else {
                                    seEmptyOrXCount = 0;
                                }
                                if (secount2b == secount2a + 1) {
                                    break;
                                }
                                secount2b--;
                            }
                            secount1b = index1 + 1;
                            secount2b = index2 - 1;
                            if (seEmptyOrXCount == 0) {
                                for (int b = secount1b; b < secount1a; b++) {
                                    possiblemove++;
                                    if (secount2b == secount2a + 1) {
                                        break;
                                    }
                                    secount2b--;
                                }
                            }
                        }
                    }

                    // scans southwest
                    if ((index1 >= 2) && (index2 >= 2)) {
                        int swcount1a = index1 - 2;
                        int swcount2a = index2 - 2;
                        int swhasx = 0;
                        for (int a = swcount1a; a >= 0; a--) {
                            if ((pos[a][swcount2a]) == xoro) {
                                swcount1a = a;
                                swhasx = 1;
                                break;
                            }
                            if (swcount2a == 0) {
                                break;
                            }
                            swcount2a--;
                        }
                        if (swhasx == 1) {
                            int swcount1b = index1 - 1;
                            int swcount2b = index2 - 1;
                            int swEmptyOrXCount = 2;
                            for (int b = swcount1b; b > swcount1a; b--) {
                                if ((pos[b][swcount2b] == " ") || (pos[b][swcount2b] == xoro) || (pos[b][swcount2b] == ".")) {
                                    swEmptyOrXCount = 1;
                                    break;
                                } else {
                                    swEmptyOrXCount = 0;
                                }
                                if (swcount2b == swcount2a + 1) {
                                    break;
                                }
                                swcount2b--;
                            }
                            swcount1b = index1 - 1;
                            swcount2b = index2 - 1;
                            if (swEmptyOrXCount == 0) {
                                for (int b = swcount1b; b > swcount1a; b--) {
                                    possiblemove++;
                                    if (swcount2b == swcount2a + 1) {
                                        break;
                                    }
                                    swcount2b--;
                                }
                            }
                        }
                    }
                    if (possiblemove > 0){
                        pos[index1][index2] = ".";
                    } else if (possiblemove == 0){
                        pos[index1][index2] = " ";
                    }

                }
            }
        }
        int dotcount = 0;
        for (int t = 0; t < 8; t++){
            for (int z = 0; z < 8; z++){
                if (pos[t][z] == "."){
                    dotcount++;
                }
            }
        }
        if (dotcount == 0){
            gameoverchecker();
        }
	}
	
	
	
	public void gameoverchecker() {
		
		scoreupdate();
		gameover = 1;
		
		restarttext.setText("restart");
		exittext.setText("exit");
		restartbutton.setIcon(whiteblock);
		exitbutton.setIcon(whiteblock);
		
		if (whitecount > blackcount) {
			whitecircletext.setText("winner");
			diamonds2.setForeground(Color.GREEN);
		} else if (whitecount < blackcount) {
			blackcircletext.setText("winner");
			diamonds1.setForeground(Color.GREEN);
		} else {
			whitecircletext.setText("tie");
			blackcircletext.setText("tie");
		}
		
	}
	
	
}
