package communicator;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduleParameters;

public class CommunicationsBuilder implements ContextBuilder<Object> {

	@Override
	public Context build(Context<Object> context) {
		context.setId ("communicator");
		Distribution dist = new Distribution("Dist 1");
		Transmission trans = new Transmission();
		context.add(dist);
		context.add(trans);
		return context;
	}

}
