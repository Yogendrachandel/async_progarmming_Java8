package com.learn.async;

import static com.learn.async.LoggerUtil.log;

import java.util.concurrent.CompletableFuture;

public class CompeltableThenApply {

	public static void main(String[] args) {
		
		HelloWorld hw = new HelloWorld();

		/*
		 * 1 create Asynchronous computation by using factory method -take supplier Functional Interface.
		 * 2 thenApply() completationStage method  -take Function Functional Interface
		 */
		CompletableFuture.supplyAsync(() -> hw.helloWorld()).
		thenApply((result)-> result.length()). //takes input for transformation
	    thenAccept((result)->{
	    	log("Length of String  ="+result);
	    }).join();
		
		log("Main thread Completed!!!");
		
		
		/* If delay will not created than Main thread will completed and we dont get
		any information of Thread created by Completable future otherwise we can use join()*/
		
		//delay(1000);
	}

}
