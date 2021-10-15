package com.learn.async;

import java.util.concurrent.CompletableFuture;

public class CombineTwoAsyncCompleteableFuture {

	static HelloWorld hw;
	public static void main(String[] args) {

		
		 hw=new HelloWorld();
		CombineTwoAsyncCompleteableFuture async=new CombineTwoAsyncCompleteableFuture();
		//async.normalSequentialCalls();
		async.multipleParallelAsyncCalls();
	}

	private void normalSequentialCalls() {

		CommonUtil.startTimer();
		
		String hello=hw.hello();
		String world=hw.world();
		CommonUtil.timeTaken();
		
		LoggerUtil.log(hello+world);
	}
	
	
	private void multipleParallelAsyncCalls() {

		CommonUtil.startTimer();
		
		CompletableFuture<String>hello =CompletableFuture.supplyAsync(()->hw.hello());//Create one Async call.
		CompletableFuture<String>world =CompletableFuture.supplyAsync(()->hw.world());//Created second async call.
		
		String combineResult=hello.thenCombine(world, (previous,current)->previous+current).
		thenApply((result)->result.toUpperCase()).
		join();
		
		//Total time will be less than normalSequentialCalls() because both call are parellel.
		CommonUtil.timeTaken();
		
		LoggerUtil.log(combineResult);
	}

}
