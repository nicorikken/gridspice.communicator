package repastrunner;

import java.io.File;

import communicator.Distribution;

import repast.simphony.engine.environment.RunState;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;

public class RunTest {
	public static void main(String[] argv)
		      throws java.io.IOException,
		             java.lang.InterruptedException {
		 run("Blah");
	}
	 
	public static void run(String message){
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
			
			initializeSchedules(runner, message);
//			RunEnvironment.getInstance().endAt(endTime);

			for (int j = 0; j < 5; j++) {
			//while (runner.getActionCount() > 0){  // loop until last action is left
				if (runner.getModelActionCount() == 0) {
					runner.setFinishing(true);
				}
				runner.step();  // execute all scheduled actions at next tick

			//}
			}

			runner.stop();          // execute any actions scheduled at run end
			runner.cleanUpRun();
		}
		runner.cleanUpBatch();    // run after all runs complete
	}
	
	
	private static void initializeSchedules(RepastRunner runner, String message) {
		ScheduleParameters param = ScheduleParameters.createRepeating(1, 2);
		runner.getSchedule().schedule(param, new Distribution(null), "step", message);
	}
	
}
