package ru.raven.player.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import ru.raven.player.PlayerController;
import ru.raven.player.Playlist;
import ru.raven.player.SpectrumListener;
import ru.raven.player.SpectrumPane;
import ru.raven.properties.PropertiesManager;

public class Main extends Application {

    private MediaPlayer mediaPlayer;
    private PlayerController mediaControl;
    public Playlist mainPlaylist;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RM3P");
        Group root = new Group();
        Scene scene = new Scene(root, 540, 241);
        scene.setOnDragOver((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });
        scene.setOnDragDropped((DragEvent event) -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                try {
                    List<String> list = new ArrayList<>();
                    db.getFiles().forEach((f) -> {
                        if (f.toString().endsWith("mp3")) {
                            list.add(f.toURI().toString());
                        }
                    });
                    File f = new File("playlist.m3p");
                    if (!f.exists()) {
                        f.createNewFile();
                    }
                    mainPlaylist = new Playlist(f.toString());
                    mainPlaylist.music.addAll(list);
                    mainPlaylist.save();
                    //TODO
                    initPlayer(scene, mainPlaylist.music.get(0));
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            event.setDropCompleted(true);
            event.consume();
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initPlayer(Scene scene, String mp3) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer(new Media(mp3));
            mediaControl = new PlayerController(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
            SpectrumPane spectrumPane = new SpectrumPane(mediaPlayer);
            spectrumPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            mediaControl.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            BorderPane pane=new BorderPane();
            pane.setCenter(spectrumPane);
            pane.setBottom(mediaControl);
            String volume = PropertiesManager.getProperty("volume");
            mediaPlayer.setVolume(volume == null ? 1 : Double.valueOf(volume));
            mediaPlayer.setAudioSpectrumListener(new SpectrumListener(spectrumPane));
            scene.setRoot(pane);
        }
    }

    public static void main(String[] args) {
        init0();
        launch(args);
    }

    private static void init0() {
        if (!PropertiesManager.getPropertiesFile().exists()) {
            try {
                PropertiesManager.getPropertiesFile().createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
