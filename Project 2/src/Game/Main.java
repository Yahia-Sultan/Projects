package Game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

	
public class Main {
	
	private static HashMap<String, Image> tiles = new HashMap<String, Image>();
	private static HashMap<String, Image> time = new HashMap<String, Image>();
	
	public static void main(String[] args) {
		loadImages();
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new Window();
			}
		});
	}
	
	public static void loadImages() {
		Image img = null;
		File[] origin = new File("Tiles").listFiles();
		
		for(int i = 0; i < origin.length; i++) {
			try {
				img = ImageIO.read(origin[i]);
				img = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				tiles.put(origin[i].getName().toLowerCase(), img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		File[] times = new File("Timer").listFiles();
		for(int i = 0; i < times.length; i++) {
			try {
				img = ImageIO.read(times[i]);
				img = img.getScaledInstance(90, 162, Image.SCALE_SMOOTH);
				time.put(times[i].getName().toLowerCase(), img);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Image getImage(String file, String name) {
		Image img;
		name += ".png";
		if(file.toLowerCase().matches("times") || file.toLowerCase().matches("time")) {
			img = time.get(name);
		}else {
			img = tiles.get(name);
		}
		return img;
	}
	
}
