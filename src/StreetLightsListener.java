
public class StreetLightsListener extends PluginListener {

	private LightState lightStates;
	private boolean raining;
	private boolean night;
	
	public boolean onCommand(Player player, String[] split) {
		HitBlox hb = new HitBlox(player);
		Block b = hb.getTargetBlock();
		if(split[0].equalsIgnoreCase("/createSL") && player.canUseCommand("/createSL")) {
			if(LightsUpdater.isRedstoneLamp(b)) {
				if(LightsUpdater.isRegisteredStreetLight(b)) {
					player.sendMessage("This Block is already registered!");
					return false;
				}
				LightsUpdater.addLight(new LightPosition(b)); //Success!
				player.sendMessage("Streetlight successfully created!");
				return true;
			} else {
				player.sendMessage("This Block is not a Redstone Lamp!");
				return false;
			}
		}
		
		else if(split[0].equalsIgnoreCase("/removeSL") && player.canUseCommand("/removeSL")) {
			if(LightsUpdater.isRedstoneLamp(b)) {
				if(LightsUpdater.isRegisteredStreetLight(b)) {
					LightsUpdater.removeLight(new LightPosition(b)); //Success!
					player.sendMessage("Streetlight successfully unregistered!");
					return true;
				} else {
					player.sendMessage("This Block is not a registerd Streetlight!");
					return false;
				}
			} else {
				player.sendMessage("This Block is not a Redstone Lamp!");
				return false;
			}
		}
		
		else if(split[0].equalsIgnoreCase("/isSL") && player.canUseCommand("/isSL")) {
			if(LightsUpdater.isRegisteredStreetLight(b)) {
				player.sendMessage("This Block is a registered Streetlight!");
			} else {
				player.sendMessage("This Block is not a registered Streetlight!");
			}
			return true;
		}
		
		else if(split[0].equalsIgnoreCase("/countSL") && player.canUseCommand("/countSL")) {
			player.sendMessage(LightsUpdater.getRegisteredLights().size() + " registered StreetLights!");
			return true;
		}
		return false;
	}
	
	
	public boolean onWeatherChange(World world, boolean newRaining) {
		if(newRaining) { //It's raining man!
			this.raining = true;
		} else {
			this.raining = false;
		}
		this.generateLightState();
		LightsUpdater.updateLights(world, this.lightStates);
		return false;
	}
	
	
	public boolean onTimeChange(World world, long newTime) {
		if (newTime > 23000 || newTime < 13000) { //Burn Skeleton, Burn!
			this.night = false;
		} else {
			this.night = true;
		}
		this.generateLightState();
		LightsUpdater.updateLights(world, this.lightStates);
		return false;
	}
	
	public boolean onBlockUpdate(Block oldBlock, int newBlockID) {
		if(LightsUpdater.isRedstoneLamp(oldBlock)) {
			if(LightsUpdater.isRegisteredStreetLight(oldBlock)) {
				LightsUpdater.updateLights(etc.getServer().getDefaultWorld(), lightStates);
				return true;
			}
		}
		
		return false;
	}
	
	private void generateLightState() {
		if(this.night || this.raining) {
			this.lightStates = LightState.ON;
		} else {
			this.lightStates = LightState.OFF;
		}
	}
}
