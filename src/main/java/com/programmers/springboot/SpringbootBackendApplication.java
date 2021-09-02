package com.programmers.springboot;

import com.programmers.springboot.controller.UIApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootBackendApplication {

	public static void main(String[] args) {
		Application.launch(UIApplication.class, args);
	}
}