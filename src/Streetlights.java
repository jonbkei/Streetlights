import java.util.logging.Logger;


public class Streetlights extends Plugin {

	public static Logger mcLogger = Logger.getLogger("Minecraft");
	
	public StreetLightsListener listener = new StreetLightsListener();
	
	@Override
	public void disable() {
		mcLogger.info("[Streetlights]: Disabling Streetlights!");
		if(LightsUpdater.writeToConfigFile()) {
			mcLogger.info("[Streetlights]: Successfully disabled Streetlights!");
		} else {
			mcLogger.severe("[Streetlights]: Could not save to \"streetlights.txt\"!");
		}
	}

	@Override
	public void enable() {
		mcLogger.info("[Streetlights]: Enabling Streetlights!");
		boolean success = true;
		Exception e = LightsUpdater.readFromConfigFile();
		if(e != null) {
			success = false;
			mcLogger.severe("Could not load \"streetlights.txt\": " + e);
		}
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.WEATHER_CHANGE, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.TIME_CHANGE, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_UPDATE, listener, this, PluginListener.Priority.MEDIUM);
		if(success)
			mcLogger.info("[Streetlights]: Streetlights has been enabled Successfully!");
	}

}
