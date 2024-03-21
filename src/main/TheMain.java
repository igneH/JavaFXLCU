package main;

import javafx.application.Application;
import javafx.stage.Stage;
import viewController.LcuC;


public class TheMain extends Application {

    @Override
    public void init() throws Exception {


    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        LcuC.show(primaryStage);
    }
}