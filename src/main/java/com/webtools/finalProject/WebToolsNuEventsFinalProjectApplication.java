package com.webtools.finalProject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ServletComponentScan
@ComponentScan({"com.webtools.finalProject.Controller","com.webtools.finalProject.Dao","com.webtools.finalProject.Exception", "com.webtools.finalProject.Pojo", "com.webtools.finalProject.Validator", "com.webtools.finalProject.Util"})
public class WebToolsNuEventsFinalProjectApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

	@Value("Stripe.api.key")
	private String stripeApiKey;
	
	@PostConstruct
	public void setup(){
		Stripe.apiKey =  "sk_test_51MypY8FEcNCCTm9vE6X6OOBwf0hznFxTt85JS4BK9or5v8S1iSUViVEBcNhYRu0LUHSJYmyfYbdwRws2I8rivpnX00elVpOJ4Q";

	}
	public static void main(String[] args) {
		SpringApplication.run(WebToolsNuEventsFinalProjectApplication.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("welcome");
	 }


}
