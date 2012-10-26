package communicator;

import repast.simphony.engine.schedule.ScheduledMethod;

public class Distribution {
	private boolean called;

	public Distribution() {
		
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step() {
		called = true;
		System.out.println("Distribution agent called");
	}
}
