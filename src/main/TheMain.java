package main;

import javafx.application.Application;
import javafx.stage.Stage;
import viewController.lcuC;


public class TheMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        lcuC.show(primaryStage);
    }
}