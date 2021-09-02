/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.programmers.springboot.controller;

import com.programmers.springboot.SpringbootBackendApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
/**
 *
 * @author e-emgarza
 */

public class UIApplication extends Application{
    private ConfigurableApplicationContext applicationContext;
    
    @Override
    public void init() throws Exception{
        applicationContext = new SpringApplicationBuilder(SpringbootBackendApplication.class).run(); //inicializa springboot
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }
    @Override
    public void stop() {
        applicationContext.close(); //cierra springboot
        Platform.exit();
    }
    
    static class StageReadyEvent extends ApplicationEvent{
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
        
    }
    
}
