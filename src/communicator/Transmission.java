package communicator;

import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;

public class Transmission {

	public Transmission() {
		
	}

	@Watch ( watcheeClassName = "communicator.Distribution",
			watcheeFieldNames = "called",
			whenToTrigger = WatcherTriggerSchedule.IMMEDIATE )
	public void callScript() {
		System.out.println("Transmission agent called.");
	}
}
