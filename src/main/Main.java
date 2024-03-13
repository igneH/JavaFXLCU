package main;

import javafx.application.Application;
import javafx.stage.Stage;
import viewController.lcuC;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        lcuC.show(primaryStage);
    }
}