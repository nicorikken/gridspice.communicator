package remoteprocess;

import messenger.RabbitMessenger;

public class RemoteDistribution {
	
	private static final String TEST_STRING = "Hello Simulation World";
	
	public static void main(String[] argv)
		      throws Exception {
		if (argv.length == 2) {
			String localQueueName = argv[0];
			String targetQueueName = argv[1];
			System.out.println("Remote Distribution");
			RabbitMessenger connection = new RabbitMessenger(localQueueName, targetQueueName);
			boolean success = connection.initiate();
			if (success) {
				send(connection);
			} else {
				connection.close();
				System.out.println("Connection establishment failed.");
			}
		} else {
			System.out.println(argv.length);
			System.out.println("Please provide the name of the send queue.");
		}
	}
	
	private static void send(RabbitMessenger connection) throws Exception {
		connection.send(TEST_STRING);
	}
}
