package repastrunner;

import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String[] args){
		String filelocation = "./communicator.rs"; 
		File file = new File(filelocation); // the scenario dir
		RepastRunner runner = new RepastRunner();

		try {
			runner.load(file);     // load the repast scenario
		} catch (Exception e) {
			e.printStackTrace();
		}

//		double endTime = 1000.0;  // some arbitrary end time

		// Run the sim a few times to check for cleanup and init issues.
		for(int i=0; i<2; i++){

			runner.runInitialize();  // initialize the run

//			RunEnvironment.getInstance().endAt(endTime);

			while (runner.getActionCount() > 0){  // loop until last action is left
				if (runner.getModelActionCount() == 0) {
					runner.setFinishing(true);
				}
				runner.step();  // execute all scheduled actions at next tick

			}

			runner.stop();          // execute any actions scheduled at run end
			runner.cleanUpRun();
		}
		runner.cleanUpBatch();    // run after all runs complete
	}
}
