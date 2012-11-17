package communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import messenger.ConnectionEstablishment;
import messenger.RabbitMessenger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import remoteprocess.Scripts;
import repast.simphony.context.Context;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.collections.IndexedIterable;

public class Transmission {

	private String data;
	private RabbitMessenger localServer;
	private boolean connected;
	
	public Transmission() {
		String localQueueName = java.util.UUID.randomUUID().toString();
		String targetQueueName = java.util.UUID.randomUUID().toString();
		try {
			localServer = new RabbitMessenger(localQueueName, targetQueueName);
			System.out.println("Transmission: " + localServer.getLocalQueueName());
			//TODO: Call remoteprocess script to spawn a new remote process on sun grid engine.
			Runtime runtime = Runtime.getRuntime();
			String[] command = new String[]{"/bin/bash","-c", Scripts.TRANSMISSION_COMMAND + " " 
					+ targetQueueName + " " + localQueueName};
			for (int i = 0; i< command.length; i++) {
				System.out.print(command[i]);
				System.out.print(" ");
			}
			System.out.println("");
			runtime.exec(command);
			connected = localServer.listen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Watch ( watcheeClassName = "communicator.Distribution",
			watcheeFieldNames = "data",
			whenToTrigger = WatcherTriggerSchedule.IMMEDIATE )
	public void callScript(Distribution dist) throws Exception {
		if (connected) {
			localServer.send(dist.getData());
			System.out.println("Sent message to remote transmission: " + dist.getData());
		}
	}
}
