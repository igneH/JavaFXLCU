package viewController;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class lcuC {

public static void show(Stage stage) throws IOException {
    try {
        FXMLLoader loader = new FXMLLoader(lcuC.class.getResource("lcuV.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("LCU Shit");
        stage.setScene(scene);
        stage.show();
    }
    catch (IOException e){
        e.printStackTrace();
        Platform.exit();
    }

    }

}
