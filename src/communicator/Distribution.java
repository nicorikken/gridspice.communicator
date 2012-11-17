package communicator;

import messenger.ConnectionEstablishment;
import messenger.ConnectionEstablishment.Handshake;
import messenger.RabbitMessenger;
import remoteprocess.Scripts;
import repast.simphony.engine.schedule.ScheduledMethod;


public class Distribution {
	private String data;
	private RabbitMessenger localServer;
	private boolean connected;
	
	public Distribution() {
		String localQueueName = java.util.UUID.randomUUID().toString();
		String targetQueueName = java.util.UUID.randomUUID().toString();
		try {
			System.out.println("Local Distribution:");
			localServer = new RabbitMessenger(localQueueName, targetQueueName);
			//TODO: Call remoteprocess script to spawn a new remote process on sun grid engine.
			Runtime runtime = Runtime.getRuntime();
			String[] command = new String[]{"/bin/bash","-c", Scripts.DISTRIBUTION_COMMAND + " " 
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
	
	@ScheduledMethod ( start = 1 , interval = 1)
	public void step() throws Exception {
		if (connected) {
			String data = localServer.receive();
			this.data = data;
			System.out.println("Data received: " + data);
		} else {
			System.out.println("No connection.");
		}
	}
	
	public String getData() {
		return data;
	}
}
