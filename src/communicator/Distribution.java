package communicator;

import repast.simphony.engine.schedule.ScheduledMethod;

public class Distribution {
	private String message;
	private String name;
	
	public Distribution(String name) {
		message = "";
		this.name = "default";
		if (name != null) {
			this.name = name;
		}
	}
	
	public void step(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
