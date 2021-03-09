package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class Window extends JFrame {
	
	private static Character[] status = new Character[64];
	/*
	 * S = safe
	 * E = default
	 * X = flagged
	 * 1-8 = danger level
	 * Z = Triggered
	 */
	
	private static Character[] real = new Character[64];
	/*
	 * S = safe
	 * 1-8 danger level
	 * Q = bomb
	 */
	
	int avail = 54;
	JLabel hundred = new JLabel();
	JLabel tenth = new JLabel();
	JLabel one = new JLabel();
	JLabel count10 = new JLabel();
	JLabel count1 = new JLabel();
	
	JButton[] allTiles = new JButton[64];
	
	private boolean running = false;
	private int timeSec = 0, delta =0;
	
	private static int bombCount = 10;
	
	private static final long serialVersionUID = 1L;
	
	private static String name = "MineSweeper";
	private static int width = 1200, height = 800; 
	
	public Window() {
		super(name);
		setSize(width, height+35);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		//Game grid panel
		JPanel panel = new JPanel();
		GridLayout tiles = new GridLayout(8, 8);
		tiles.setHgap(0); tiles.setVgap(0);
		panel.setLayout(tiles);
		
		panel.setPreferredSize(new Dimension(800, 800));
		panel.setBackground(Color.black);
		
		for(int i = 0; i < 64; i++) {
			JButton tile = new JButton();
			tile.setIcon(new ImageIcon(Main.getImage("tiles", "emptytile")));
			status[i] = 'E';
			final int CURR = i;
			tile.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {	
					if(!running) {
						for(JButton t2 : allTiles) {
							t2.setIcon(new ImageIcon(Main.getImage("tiles", "emptytile")));
						}
						running = true;
						
						Random rng = new Random();
						int totalB = 0;
						while(totalB < 10) {
							int c = rng.nextInt(64);
							if(c == CURR) continue;
							try {
								if(real[c] == 'Q') continue;
							} catch (NullPointerException n) {
								real[c] = 'Q';
								totalB++;
							}
							
						}
						for(int b = 0; b < 64; b++) {
							status[b] = 'E';
							int surCount =0;
							if(real[b] != null) continue;
							for(int a : getSurround(1+b)) {
								try{
									if(real[a-1] == 'Q')
										surCount++;
								}catch (Exception p) {}
							}
							if(surCount == 0) real[b] = 'S';
							else {
								real[b] = (char) (surCount+'0');
							}
						}
						
					}
					if(e.getButton() == MouseEvent.BUTTON1) {
						
						
						try{
							if(status[CURR] == 'X') {
								bombCount++;
							changeBombCount();
							}
						}catch (Exception d) {}
							
						if(real[CURR] == 'S') {						
							status[CURR] = 'S';
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "safetile")));
						}else if(real[CURR] == '1') {
							status[CURR] = real[CURR];
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "onetile")));
						}else if(real[CURR] == '2') {
							status[CURR] = real[CURR];
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "twotile")));
						}else if(real[CURR] == '3') {
							status[CURR] = real[CURR];
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "threetile")));
						}else if(real[CURR] == '4') {
							status[CURR] = real[CURR];
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "fourtile")));
						}else if(real[CURR] == '5') {
							status[CURR] = real[CURR];
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "fivetile")));
						}else if(real[CURR] == '6') {
							status[CURR] = real[CURR];
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "sixtile")));
						}else if(real[CURR] == '7') {
							status[CURR] = real[CURR];
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "seventile")));
						}else if(real[CURR] == '8') {
							status[CURR] = real[CURR];
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "eighttile")));
						}else if(real[CURR] == 'Q') {
							status[CURR] = 'Z';
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "triggeredtile")));
							end();
						}
						avail--;
						if(avail == 0)
							end();
						
						
					}else if(e.getButton() == MouseEvent.BUTTON3) {
						if(status[CURR] == 'E') {
							status[CURR] = 'X';
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "flagtile")));
							bombCount--;
						}else if(status[CURR] == 'X') {
							status[CURR] = 'E';
							tile.setIcon(new ImageIcon(Main.getImage("tiles", "emptytile")));
							bombCount++;
						}

						changeBombCount();
					}
				}
			});
			
		
			allTiles[i] = tile;
			panel.add(tile);
			
		}

		//Control panel
		JPanel control = new JPanel();
		control.setBackground(Color.gray);
		control.setMinimumSize(new Dimension(400, 800));
		control.setLayout(new BoxLayout(control, BoxLayout.Y_AXIS));
		
			//timer panel (part of control panel)
		JPanel timer = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		timer.setBackground(Color.gray);
		timer.setPreferredSize(new Dimension(270, 162));
		hundred.setIcon(new ImageIcon(Main.getImage("time", "0")));
		tenth.setIcon(new ImageIcon(Main.getImage("time", "0")));
		one.setIcon(new ImageIcon(Main.getImage("time", "0")));
		timer.add(hundred);
		timer.add(tenth);
		timer.add(one);
		control.add(timer);
		
			//Bomb counter
		JPanel counter = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		counter.setBackground(Color.gray);
		counter.setPreferredSize(new Dimension(212, 101));
		JLabel bombImg = new JLabel();
		bombImg.setIcon(new ImageIcon(Main.getImage("tile", "truetile")));
		
		
		Image first, sec;
		if(bombCount == 10) {
			first = Main.getImage("time", "1");
		} else first = Main.getImage("time", "0");
		first = first.getScaledInstance(56, 100, Image.SCALE_SMOOTH);
		count10.setIcon(new ImageIcon(first));
		if(bombCount <= 0 || bombCount == 10) {
			sec = Main.getImage("time", "0");
		}else {
			sec = Main.getImage("time", String.valueOf(bombCount));
		}
		sec = sec.getScaledInstance(56, 100, Image.SCALE_SMOOTH);
		count1.setIcon(new ImageIcon(sec));
		
		
		
		
		counter.add(bombImg);
		counter.add(count10);
		counter.add(count1);
		control.add(counter);
		
			//Information
		JTextArea help = new JTextArea();
		help.setEditable(false);
		help.setBackground(Color.LIGHT_GRAY);
		help.setText("Left click to select\nRight click once to flag\nRight click again to unflag");
		help.setMaximumSize(new Dimension(350, 200));
		control.add(help);
		
		//Primary JFrame
		BoxLayout back = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
		getContentPane().setLayout(back);
		getContentPane().add(panel);
		getContentPane().add(control);
		
		
		pack();
		setVisible(true);
		validate();
		final Timer rel = new Timer(20, new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				repaint();
				if(running) {
					delta += 20;
					if(delta >= 1000) {
						if(timeSec <= 999) 
							timeSec++;
						delta = 0;
					}
					game();
				}
				
			}
			
		});
		rel.start();
		
	}
	
	public static void setStatus(int loc, char stat) {
		loc--;
		status[loc] = stat;
	}
	
	public void game() {
		String cob = String.valueOf(timeSec);
			if(cob.length() == 3) {
				String pri = String.valueOf(cob.charAt(0));
				hundred.setIcon(new ImageIcon(Main.getImage("time", pri)));
				String sec = String.valueOf(cob.charAt(1));
				tenth.setIcon(new ImageIcon(Main.getImage("time", sec)));
				String tri = String.valueOf(cob.charAt(2));
				one.setIcon(new ImageIcon(Main.getImage("time", tri)));
			}else if(cob.length() == 2) {
				String pri = String.valueOf(cob.charAt(0));
				tenth.setIcon(new ImageIcon(Main.getImage("time", pri)));
				String sec = String.valueOf(cob.charAt(1));
				one.setIcon(new ImageIcon(Main.getImage("time", sec)));
				hundred.setIcon(new ImageIcon(Main.getImage("time", "0")));
			}else {
				String pri = String.valueOf(cob.charAt(0));
				one.setIcon(new ImageIcon(Main.getImage("time", pri)));
				tenth.setIcon(new ImageIcon(Main.getImage("time", "0")));
				hundred.setIcon(new ImageIcon(Main.getImage("time", "0")));
				
			}
		
	}
	
	public ArrayList<Integer> getSurround(int p) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		if(p % 8 == 1) {
			if(p == 1) {
				list.add(2);
				list.add(p+8);
				list.add(p+9);
			}else if(p == 57) {
				list.add(58);
				list.add(p-7);
				list.add(p-8);
			}else {
				list.add(p-8);
				list.add(p-7);
				list.add(p+1);
				list.add(p+8);
				list.add(p+9);
			}
		}else if(p % 8 == 0) {
			if(p == 8) {
				list.add(7);
				list.add(p+7);
				list.add(p+8);
			}else if(p == 64) {
				list.add(63);
				list.add(p-8);
				list.add(p-9);
			}else {
				list.add(p-9);
				list.add(p-8);
				list.add(p-1);
				list.add(p+7);
				list.add(p+8);
			}
		}else {
			if(p-9 > 0)
				list.add(p-9);
			if(p-8 > 0)
				list.add(p-8);
			if(p-7 > 0)
				list.add(p-7);
			list.add(p-1);
			list.add(p+1);
			if(p+7 < 64) 
				list.add(p+7);
			if(p+8 < 64)
				list.add(p+8);
			if(p+9 < 64)
				list.add(p+9);
		}
		return list;
	}
	
	public void changeBombCount() {
		Image first, sec;
		if(bombCount == 10) {
			first = Main.getImage("time", "1");
		} else first = Main.getImage("time", "0");
		first = first.getScaledInstance(56, 100, Image.SCALE_SMOOTH);
		count10.setIcon(new ImageIcon(first));
		if(bombCount <= 0 || bombCount == 10) {
			sec = Main.getImage("time", "0");
		}else {
			sec = Main.getImage("time", String.valueOf(bombCount));
		}
		sec = sec.getScaledInstance(56, 100, Image.SCALE_SMOOTH);
		count1.setIcon(new ImageIcon(sec));
	}
	
//	SET FOR END
	public void end() {
		for(int i = 0; i < 64; i++) {
			if(status[i] == 'X' && real[i] != 'Q')
				allTiles[i].setIcon(new ImageIcon(Main.getImage("tiles", "falsetile")));
		}
		avail = 54;
		running = false;
		Arrays.fill(real, null);
		Arrays.fill(status, null);
		timeSec = 0;
		bombCount = 10;
	}
}
