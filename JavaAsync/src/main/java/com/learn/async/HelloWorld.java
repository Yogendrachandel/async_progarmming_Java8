package com.learn.async;

import static com.learn.async.LoggerUtil.log;
import static com.learn.async.CommonUtil.delay;
public class HelloWorld {

	

	public String helloWorld() {

		delay(1000);
		log("Inside the Hello world.");
		return "hello world.";
	}

	
	public String hello() {

		delay(1000);
		log("Inside the Hello ");
		return "hello";
	}
	
	
	public String world() {

		delay(1000);
		log("Inside the world.");
		return "world";
	}
	

}
