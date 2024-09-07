package com.in28minutes.rest.webservices.restful_web_services.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@RequestMapping(method = RequestMethod.GET, path="hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path = "hello-world2")
	public String helloWorld2() {
		return "Hello World2";
	}

}
