package remoteprocess;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

import messenger.ConnectionEstablishment;
import messenger.RabbitMessenger;

public class RemoteTransmission {
	public static void main(String[] argv)
		      throws Exception {
		if (argv.length == 2) {
			String localQueueName = argv[0];
			String targetQueueName = argv[1];
		 	RabbitMessenger connection = new RabbitMessenger(localQueueName, targetQueueName);
			boolean success = connection.initiate();
			if (success) {
				receive(connection);
			} else {
				connection.close();
				System.out.println("Connection establishment failed.");
			}
		} else {
			System.out.println("Please provide the name of the send queue.");
		}
	}
	
	private static void receive(RabbitMessenger connection) throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		int numIdleLoops = ConnectionEstablishment.DEFAULT_LOOPS;
		while (numIdleLoops > 0) {
			numIdleLoops--;
			String data = connection.receive();
			if (data != null) {
				numIdleLoops = ConnectionEstablishment.DEFAULT_LOOPS;
				System.out.println(data);
			}
		}
	}
}
