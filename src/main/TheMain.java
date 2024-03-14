package main;

import connectToLCU.Request;
import javafx.application.Application;
import javafx.stage.Stage;
import viewController.lcuC;


public class TheMain extends Application {

    @Override
    public void init() throws Exception {
        new Request("DELETE", "/lol-lobby/v2/lobby").createRequest();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        lcuC.show(primaryStage);
    }
}