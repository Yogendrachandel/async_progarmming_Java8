package com.learn.async;

import static com.learn.async.LoggerUtil.log;
import org.apache.commons.lang3.time.StopWatch;

import static java.lang.Thread.sleep;


public class CommonUtil {

	public static StopWatch stopWatch = new StopWatch();

	public static void startTimer() {
		stopWatchReset();
		stopWatch.start();
	}

	public static void timeTaken() {
		stopWatch.stop();
		log("Total Time Taken : " + stopWatch.getTime());
	}

	public static void delay(long delayMilliSeconds) {
		try {
			sleep(delayMilliSeconds);
		} catch (Exception e) {
			log(e.getMessage());
		}

	}

	
	  public static void stopWatchReset(){ 
		  stopWatch.reset(); 
		  }
	 

}