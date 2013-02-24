import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;


public class LightsUpdater {
	
	//private static Logger mcLogger = Streetlights.mcLogger;
	
	private static ArrayList<LightPosition> lights = new ArrayList<LightPosition>();
	
	public static void updateLights(World w, LightState lightstates) {
		//World w = etc.getServer().getDefaultWorld();
		
		for(int i = 0; i < lights.size(); i++) {
			LightPosition lp = lights.get(i);
			//Block b = w.getBlockAt(lp.x, lp.y, lp.z);
			
			//if(!isRedstoneLamp(b)) {
				//mcLogger.log(Level.WARNING, "Light @ Position " + lp + ", has been removed/changed without unregistering!");
				//removeLight(lp);
			//} else {
				w.setBlockAt(lightstates.getBlockID(), lp.x, lp.y, lp.z);
			//}
		}
	}
	
	
	public static boolean isRedstoneLamp(Block b) {
		return b.getType() == Block.Type.RedstoneLampOff.getType() || b.getType() == Block.Type.RedstoneLampOn.getType();
	}
	
	public static void addLight(LightPosition lp) {
		if(lights.contains(lp)) {
			return;
		}
		lights.add(lp);
	}
	
	public static boolean isRegisteredStreetLight(LightPosition lp) {
		return lights.contains(lp);
	}
	public static boolean isRegisteredStreetLight(Block b) {
		return lights.contains(new LightPosition(b));
	}
	
	public static void removeLight(LightPosition lp) {
		lights.remove(lp);
	}
	
	public static ArrayList<LightPosition> getRegisteredLights() {
		return lights;
	}
	
	
	
	public static boolean writeToConfigFile() {
		boolean success = false;
		try {
			FileWriter fw = new FileWriter(new File("/Streetlights/streetlights.txt"));
			for(LightPosition lp : lights) {
				fw.append(lp.x +" " + lp.y + " " + lp.z + "\n");
			}
			fw.flush();
			fw.close();
			success = true;
		} catch (IOException e) {
			success = false;
		}
		
		return success;
	}
	
	public static Exception readFromConfigFile() {
		boolean success = true;
		String cause = "";
		if(!lights.isEmpty()) {
			return new Exception("ArrayList of lights is not empty!");
		}
		if(!new File("/Streetlights/streetlights.txt").exists()) {
			return new Exception("streetlights.txt doesn't exist!");
		}
		try {
			java.util.Scanner sc = new java.util.Scanner(new File("/Streetlights/streetlights.txt"));
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] split = line.split(" ");
				if (split.length == 3) {
					int x = Integer.parseInt(split[0]);
					int y = Integer.parseInt(split[1]);
					int z = Integer.parseInt(split[2]);
					LightPosition lp = new LightPosition(x,y,z);
					lights.add(lp);
				} else {
					success = false;
					cause = "Could not parse line!";
				}
				
				
			}
			sc.close();
			if(sc.ioException() != null) {
				success = false;
				cause = "IOException!";
			}
		} catch (FileNotFoundException e) {
			success = false;
			cause = "IOException!";
		} catch ( NumberFormatException e) {
			success = false;
			cause = "NumberFormatException!";
		}
		
		if(!success) {
			return new Exception(cause);
		}
		return null;
	}

}
