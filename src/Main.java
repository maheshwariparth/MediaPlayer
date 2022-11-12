
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mediaplayer.fxml"));
        Scene scene = new Scene(root, Color.BLACK);

        primaryStage.setTitle("Media Player");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.widthProperty().addListener((o, oldValue, newValue) -> {
            if (newValue.intValue() < 1280) {
                primaryStage.setResizable(false);
                primaryStage.setWidth(1280);
                primaryStage.setResizable(true);
            }
        });
        primaryStage.heightProperty().addListener((o, oldValue, newValue) -> {
            if (newValue.intValue() < 720) {
                primaryStage.setResizable(false);
                primaryStage.setHeight(720);
                primaryStage.setResizable(true);
            }
        });
    }
}