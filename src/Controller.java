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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Controller implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private Button playButton, pauseButton, stopButton, fileChooseButton, Playbtn;

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

    @FXML
    ImageView volIMG;
    // Image vol = new Image("C:\\Songs\\mute.jpg");
    // Image muted = new Image("C:\\Songs\\vol.jpg");

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        stage = new Stage();

        // filePath = "C:\\Users\\mahes\\Downloads\\parody.mp3";
        filePath = "C:\\Songs\\videoplayback.mp4";

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
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue() / 100));
                }
            }
        });

        // Volume Slider
        volSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                if (volSlider.isPressed()) {
                    mediaPlayer.setVolume(volSlider.getValue() / 100);
                }
            }
        });

        // volSlider.valueProperty().addListener(new InvalidationListener() {
        //     @Override
        //     public void invalidated(Observable arg0) {
        //         if (volSlider.getValue() == 0) {
        //             volIMG.setImage(muted);
        //         } else {
        //             volIMG.setImage(vol);
        //         }
        //     }
        // });

        Playbtn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                Status status = mediaPlayer.getStatus(); // To get the status of Player
                if (status == status.PLAYING) {

                    // If the status is Video playing
                    if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {

                        // If the player is at the end of video
                        mediaPlayer.seek(mediaPlayer.getStartTime()); // Restart the video
                        mediaPlayer.play();
                    } else {
                        // Pausing the player
                        mediaPlayer.pause();

                        Playbtn.setText(">");
                    }
                } // If the video is stopped, halted or paused
                if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
                    mediaPlayer.play(); // Start the video
                    Playbtn.setText("||");
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
                new ExtensionFilter("Media Files", "*mp3", "*.wav", "*.mp4"),
                new ExtensionFilter("Video Files", "*.mp4"),
                new ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            mediaPlayer.stop();
            setPlayer();
        }
    }

    protected void updatesValues() {
        Platform.runLater(new Runnable() {
            public void run() {
                // Updating to the new time value
                // This will move the slider while running your video
                timeSlider.setValue(mediaPlayer.getCurrentTime().toMillis() /
                        mediaPlayer.getTotalDuration().toMillis()
                        * 100);
            }
        });
    }
}