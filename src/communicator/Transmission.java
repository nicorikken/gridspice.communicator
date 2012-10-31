package communicator;

import java.io.IOException;
import java.util.Iterator;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import repast.simphony.context.Context;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.collections.IndexedIterable;

public class Transmission {

	private final static String QUEUE_NAME = "Process B";
	
	public Transmission() {
		
	}

	@Watch ( watcheeClassName = "communicator.Distribution",
			watcheeFieldNames = "message",
			whenToTrigger = WatcherTriggerSchedule.IMMEDIATE )
	public void callScript(Distribution dist) throws IOException {
		send(dist.getMessage());
	}
	
	private void send(String message) throws IOException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	    System.out.println(" [x] Sent '" + message + "'");
	    
	    channel.close();
	    connection.close();
	}
}
