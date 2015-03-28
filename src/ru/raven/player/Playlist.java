package ru.raven.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.raven.properties.PropertiesManager;

public class Playlist {

    private final String path;
    public List<String> music = new ArrayList<>();

    public Playlist(String pathname) {
        path = pathname;
    }

    public boolean isPlaylist() {
        return path.endsWith("m3u") || path.endsWith("m3p");
    }

    public void save() {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            if (!music.isEmpty()) {
                music.forEach((String s) -> {
                    try {
                        bw.append(s).append(";");
                    } catch (IOException ex) {
                        Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
                Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, "Nothing to save!");
            }
        } catch (IOException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load() {
        try (FileInputStream fis = new FileInputStream(path)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line = br.readLine();
            String[] songs = line.split(";");
            music.addAll(Arrays.asList(songs));
        } catch (IOException ex) {
            Logger.getLogger(Playlist.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return path to latest layed song(-s).1 mp3 file as mark as playlist
     */
    public static Playlist getLatestPlaylist() {
        return new Playlist(PropertiesManager.getProperty("latestPlaylist"));
    }
}
