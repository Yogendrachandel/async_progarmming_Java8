package com.learn.async;

import static com.learn.async.LoggerUtil.log;

import java.util.concurrent.CompletableFuture;
public class CompletableFutureAsyncExample {


	public static void main(String[] args) {

		HelloWorld hw = new HelloWorld();

		/*
		 * 1 create Asynchronous computation by using factory method -take supplier Functional Interface.
		 * 2 thenAccept() completationStage method  -take Consumer Functional Interface
		 */
		CompletableFuture.supplyAsync(() -> hw.helloWorld()).thenAccept((result) -> {
			log("Result is =" + result);
		}).join();

		
		log("Main thread Completed!!!");
		
		
		/* If delay will not created than Main thread will completed and we dont get
		any information of Thread created by Completable future otherwise we can use join()*/
		
		//delay(1000);
	}

}
