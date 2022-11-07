import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Controller implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private Button playButton, pauseButton, stopButton, fileChooseButton;

    private Stage stage;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private String filePath;
    private FileChooser fileChooser;

    @FXML
    Slider timeSlider = new Slider();

    @FXML
    Slider volSlider = new Slider();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        stage = new Stage();

        filePath = "C:\\Users\\mahes\\Downloads\\parody.mp3";
        // filePath = "C:\\Songs\\videoplayback.mp4";

        file = new File(filePath);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
    }
    
    public void setPlayer() {
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.seek(mediaPlayer.getStartTime());
        volSlider.setValue(100);
        timeSlider.setValue(0);
        mediaPlayer.setOnReady(() -> stage.sizeToScene());
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        // Time Slider
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ob) {
                updatesValues();
            }
        });

        // Jump to particular time
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable ob) {
                if (timeSlider.isPressed()) {
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue()/100));
                }
            }
        });

        // Volume Slider
        volSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                if (volSlider.isPressed()) {
                    mediaPlayer.setVolume(volSlider.getValue()/100);
                }
            }
        });
    }
    
    public void playMedia() {
        mediaPlayer.play();
    }

    public void pauseMedia() {
        mediaPlayer.pause();
    }

    public void stopMedia() {
        mediaPlayer.stop();
    }

    public void fileChoose() {
        mediaPlayer.pause();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Video Files", "*.mp4"),
            new ExtensionFilter("Audio Files", "*.mp3","*.wav"));
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            mediaPlayer.stop();
            setPlayer();
        }
    }

    protected void updatesValues()
    {
        Platform.runLater(new Runnable() {
            public void run()
            {
                // Updating to the new time value
                // This will move the slider while running your video
                timeSlider.setValue(mediaPlayer.getCurrentTime().toMillis() * 100);
            }
        });
    }
}
