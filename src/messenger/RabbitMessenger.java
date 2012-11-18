package messenger;

import messenger.ConnectionEstablishment.Handshake;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class RabbitMessenger {
	private Connection connection;
	private Channel channel;
	private String targetQueueName;
	private String localQueueName;
	private QueueingConsumer consumer;

	public RabbitMessenger(String localQueueName, String targetQueueName) throws Exception {
	    ConnectionFactory factory = new ConnectionFactory();
	    String hostname = java.net.InetAddress.getLocalHost().getHostAddress();
	    System.out.println("Host: " + hostname);
	    factory.setHost(hostname);
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    this.targetQueueName = targetQueueName;
	    channel.queueDeclare(localQueueName, false, false, false, null);
	    this.localQueueName = localQueueName; 
	    consumer = new QueueingConsumer(channel);
	    channel.basicConsume(localQueueName, true, consumer);
	    
	    System.out.println("Local: " + localQueueName);
		System.out.println("Target: " + targetQueueName);
	}
	
	public boolean initiate() throws Exception {
		send(Handshake.SYN.toString());
		System.out.println(localQueueName + ": Sent SYN to: " + targetQueueName);
		String response = receive();
		if (!Handshake.ACK.toString().equals(response)) return false;
		System.out.println(localQueueName + ": Received ACK. Sent SYNACK to: " + targetQueueName);
		send(Handshake.SYNACK.toString());
		return true;
	}
	
	public boolean listen() throws Exception {
		String request = receive();
		if (!Handshake.SYN.toString().equals(request)) return false;
		System.out.println(localQueueName + ": Received SYN. Sent ACK to: " + targetQueueName);
		send(Handshake.ACK.toString());
		boolean result = Handshake.SYNACK.toString().equals(receive());
		if (result) 
			System.out.println(localQueueName + ": Received SYNACK.");
		return result;
	}

	public void send(String message) throws Exception {     
	    BasicProperties props = new BasicProperties
	                                .Builder()
	                                .replyTo(localQueueName)
	                                .build();
	    
	    channel.basicPublish("", targetQueueName, props, message.getBytes());
	}
	
	public String receive() throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		String response = null;
		long timeout = ConnectionEstablishment.DEFAULT_TIMEOUT;
		QueueingConsumer.Delivery delivery = null;
        delivery = consumer.nextDelivery(timeout);
        if (delivery != null) {
        	response = new String(delivery.getBody());
        }
        System.out.println("Received response: " + response);
	    return response; 
	}

	public void close() throws Exception {
	    connection.close();
	}
	
	public String getLocalQueueName() {
		return localQueueName;
	}
	
	public String getTargetQueueName() {
		return targetQueueName;
	}
	
}