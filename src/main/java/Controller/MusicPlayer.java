package Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Responsible for managing the playback of background music.
 * @author Joel Eriksson Sinclair
 */
public class MusicPlayer {
    private static final String MUSIC_DIRECTORY_PATH = "src/main/resources/Music"; //this.getClass().getResource()?

    private static final double UNMUTE_FADE_TIME = 2.0d; // Time in seconds
    private static final double MUTE_FADE_TIME = 0.1d; // Time in seconds

    private final Media[] playlist;

    private int currentSongIdx = 0;
    private MediaPlayer activeMediaPlayer;
    private final double volume; // 0.0 to 1.0
    private final SimpleBooleanProperty playbackStateProperty = new SimpleBooleanProperty(false);

    private Timeline volumeTimeline;

    private void setActiveMediaPlayer(MediaPlayer newPlayer) {
        if(activeMediaPlayer != null) activeMediaPlayer.dispose();
        activeMediaPlayer = newPlayer;
    }

    public MusicPlayer(double volume) throws IOException {
        this.volume = volume;

        playlist = loadSongFiles();

        activeMediaPlayer = createMediaPlayer(playlist[currentSongIdx]);
    }

    public boolean play() {
        playbackStateProperty.set(true);

        if(volumeTimeline != null) volumeTimeline.stop();

        activeMediaPlayer.play();
        volumeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(UNMUTE_FADE_TIME),
                        new KeyValue(activeMediaPlayer.volumeProperty(), volume)));

        volumeTimeline.play();

        return playbackStateProperty.get();
    }

    public boolean pause() {
        playbackStateProperty.set(false);

        if(volumeTimeline != null) volumeTimeline.stop();

        volumeTimeline = new Timeline(
                new KeyFrame(Duration.seconds(MUTE_FADE_TIME),
                        new KeyValue(activeMediaPlayer.volumeProperty(), 0)));
        volumeTimeline.setOnFinished(actionEvent -> activeMediaPlayer.pause());

        volumeTimeline.play();

        return playbackStateProperty.get();
    }

    /**
     * Toggle playback-state.
     * @return The new playback-state.
     */
    public boolean togglePlayback() {
       if(playbackStateProperty.get()) {
            return pause();
        } else {
            return play();
        }
    }

    public boolean getPlaybackState() {
        return playbackStateProperty.get();
    }

    public BooleanProperty getPlaybackStateProperty() {
        return playbackStateProperty;
    }

    private MediaPlayer createMediaPlayer(Media song) {
        MediaPlayer mediaPlayer = new MediaPlayer(song);
        mediaPlayer.setVolume(volume);
        mediaPlayer.onEndOfMediaProperty().addListener((observableValue, runnable, t1) -> playNextSong());

        return mediaPlayer;
    }

    private void playNextSong() {
        currentSongIdx = (currentSongIdx + 1) % playlist.length;
        setActiveMediaPlayer(createMediaPlayer(playlist[currentSongIdx]));
        play();
    }

    private Media[] loadSongFiles() throws IOException {
        ArrayList<Media> songList = new ArrayList<>();

        File dir = new File(MUSIC_DIRECTORY_PATH);
        File[] files = dir.listFiles(File::isFile);

        if(files == null) {
            throw new IOException("MUSIC_DIRECTORY_PATH is not a directory!");
        }

        log("Adding following songs to playlist:");
        for (File f : files) {
            log(f.toString());
            Media m = new Media(f.toURI().toString());
            songList.add(m);
        }

        return songList.toArray(new Media[0]);
    }

    void log(String msg) {
        // Toggle console logging.
        boolean debugLogging = false;
        if(debugLogging) {
            System.out.println(msg);
        }
    }
}
