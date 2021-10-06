/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programmers.springboot.controller;

import com.programmers.springboot.controller.UIApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @author e-emgarza
 */

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    
    @Value("classpath:/view/EmployeesView.fxml")
    private Resource employeesResource;
    private String applicationTitle;
    private ApplicationContext applicationContext;
    
    public StageInitializer(@Value("${spring.application.ui.title}")String applicationTitle,
            ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext=applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(employeesResource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 600, 450));
            stage.setTitle(applicationTitle);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
    
}
