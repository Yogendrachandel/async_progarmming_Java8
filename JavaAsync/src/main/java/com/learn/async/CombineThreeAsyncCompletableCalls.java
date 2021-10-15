package com.learn.async;

import java.util.concurrent.CompletableFuture;

public class CombineThreeAsyncCompletableCalls {

	public static void main(String[] args) {

		CommonUtil.startTimer();
		CompletableFuture<String>wlcm=CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			return "Welcome ";
		});
		
		CompletableFuture<String>hello=CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			return "Hello ";
		});
		
		
		CompletableFuture<String>world=CompletableFuture.supplyAsync(()->{
			CommonUtil.delay(1000);
			return "World ";
		});
		
		
		//Now combine three Async calls together.
		
		String future =wlcm.thenCombine(hello, (previous,current)->{
			
			return previous+current;
		}).
		thenCombine(world,  (previous,current)->{
			
			return previous+current;
		}).
		join();
		
		CommonUtil.timeTaken();//If  all threees calls were sequential then it take more than 3000 milliseconds .
		LoggerUtil.log("Async calls result ="+future);
		LoggerUtil.log("done");
	}

}
