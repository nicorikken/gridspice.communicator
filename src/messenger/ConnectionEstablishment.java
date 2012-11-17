package messenger;

public class ConnectionEstablishment {
	public enum Handshake {
		SYN, ACK, SYNACK
	}

        //Blahblah
	public static final long DEFAULT_TIMEOUT = 50 * 1000; //in milliseconds
	
	public static final String REMOTE_DISTRIBUTION_SCRIPT = "remote-distribution.sh";
	public static final String REMOTE_TRANSMISSION_SCRIPT = "remote-transmission.sh";
	public static final String PATH_TO_SCRIPTS = "/Users/jimmydu/git/gridspice.communicator/RepastCommunicationDemo/processes/";
}
