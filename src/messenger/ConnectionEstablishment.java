package messenger;

public class ConnectionEstablishment {
	public enum Handshake {
		SYN, ACK, SYNACK
	}

	public static final long DEFAULT_TIMEOUT = 50 * 1000; //in milliseconds
	
	public static final int DEFAULT_LOOPS = 50;
}
