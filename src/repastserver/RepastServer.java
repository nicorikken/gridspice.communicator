package repastserver;

import java.io.File;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RepastServer {
	  public static void main(String[] argv)
	      throws java.io.IOException,
	             java.lang.InterruptedException {
		  String filelocation = "./communicator.rs"; 
			File file = new File(filelocation); // the scenario dir
			RepastRunner runner = new RepastRunner();

			try {
				runner.load(file);     // load the repast scenario
			} catch (Exception e) {
				e.printStackTrace();
			}
//			double endTime = 1000.0;  // some arbitrary end time

			// Run the sim a few times to check for cleanup and init issues.
			//for(int i=0; i<2; i++){

				runner.runInitialize();  // initialize the run
				
//				RunEnvironment.getInstance().endAt(endTime);

				for (int j = 0; j < 2; j++) {
				//while (runner.getActionCount() > 0){  // loop until last action is left
					if (runner.getModelActionCount() == 0) {
						runner.setFinishing(true);
					}
					runner.step();  // execute all scheduled actions at next tick

				//}
				}

				runner.stop();          // execute any actions scheduled at run end
				runner.cleanUpRun();
			//}
			runner.cleanUpBatch();    // run after all runs complete
	    
	  }
}
