package main;

import javafx.application.Application;
import javafx.stage.Stage;
import viewController.LcuC;


public class TheMain extends Application {

    @Override
    public void init(){

    }

    @Override
    public void start(Stage primaryStage) {
        LcuC.show(primaryStage);
    }
}