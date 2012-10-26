package communicator;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;

public class CommunicationsBuilder implements ContextBuilder<Object> {

	@Override
	public Context build(Context<Object> context) {
		context.setId ("communicator");
		context.add(new Distribution());
		context.add(new Transmission());
		return context;
	}

}
