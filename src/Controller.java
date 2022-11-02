import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Controller implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private Button playButton, pauseButton, stopButton;

    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private String filePath;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        filePath = "C:\\Users\\mahes\\Downloads\\parody.mp3";

        file = new File(filePath);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

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
}
