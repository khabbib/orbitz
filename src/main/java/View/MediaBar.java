package View;

import Model.Song;
import Model.Theme;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

import java.io.File;


/**
 @author Albin Ahlbeck
 *
 * A Media Bar that contains the ability to play music, pause it and change the volume
 * Also features a Combo Box for selection of tracks.
 */
public class MediaBar extends HBox
    {
        private Slider volumeSlider = new Slider(); // Slider for volume
        private Button btnPlay = new Button("||"); // For pausing the player
        private Label lblVolume = new Label("Volume: ");

        private MediaPlayer player;

        private ComboBox<Song> songComboBox;

        private Media song;

        private Song[] songs;

        /**
         @author Albin Ahlbeck
          * Constructs the MediaBar by seting sizes and adding componenets
         @param theme The theme to use to get the colors
         */
        public MediaBar(Theme theme)
        {
            songs = initSongs();
            songComboBox = new ComboBox<Song>(FXCollections.observableArrayList(songs));
            songComboBox.getSelectionModel().select(0);
            String path = songs[0].getPath();
            song = new Media(new File(path).toURI().toString());
            player = new MediaPlayer(song);
            player.play();
            setAlignment(Pos.CENTER);
            setPadding(new Insets(5, 10, 5, 10));
            setSpacing(8);
            // Set the preference for volume bar
            volumeSlider.setPrefWidth(70);
            volumeSlider.setMinWidth(30);
            volumeSlider.setValue(100);
            btnPlay.setPrefWidth(30);
            addTheme(theme);

            getChildren().add(btnPlay);
            getChildren().add(lblVolume);
            getChildren().add(volumeSlider);
            getChildren().add(songComboBox);
            setBackground(Background.EMPTY);

            EventHandler<ActionEvent> event =
                    new EventHandler<ActionEvent>()
                    {
                        /**
                         @author Albin Ahlbeck
                          * Controls the play button
                         @param e ActionEvent
                         */
                        public void handle(ActionEvent e)
                        {
                            System.out.println("Pausing");
                            if (player.getStatus() == Status.PLAYING)
                            {
                                player.pause();
                            }
                            btnPlay.setText("||");
                            song = new Media(new File(songComboBox.getSelectionModel().
                                    getSelectedItem().getPath()).toURI().toString());
                            player = new MediaPlayer(song);
                            player.play();
                        }
                    };

            songComboBox.setOnAction(event);

            btnPlay.setOnAction(new EventHandler<ActionEvent>()
            {
                /**
                 @author Albin Ahlbeck
                  * Checks the status on the player and acts acordingly
                 @param e ActionEvent
                 */
                public void handle(ActionEvent e)
                {
                    Status status = player.getStatus(); // To get the status of Player
                    if (status == status.PLAYING)
                    {

                        // If the status is Video playing
                        if (player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration()))
                        {

                            // If the player is at the end of video
                            player.seek(player.getStartTime()); // Restart the video
                            player.play();
                        }
                        else
                        {
                            // Pausing the player
                            player.pause();

                            btnPlay.setText(">");
                        }

                    }
                    // If the video is stopped, halted or paused
                    if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED)
                    {
                        player.play(); // Start the video
                       btnPlay.setText("||");
                    }
                }
            });

            // volume control
            volumeSlider.valueProperty().addListener(new InvalidationListener()
            {
                /**
                 @author Albin Ahlbeck
                  * Allows the user to press the volume slider to change the value (without dragging)
                 @param ov The object that is being observed
                 */
                public void invalidated(Observable ov)
                {
                    if (volumeSlider.isPressed()) {
                        player.setVolume(volumeSlider.getValue() / 100);

                    }
                }
            });
        }
        /**
         @author Albin Ahlbeck
          * Creates new songs and adds them to an array
         @return Song[] The list of songs can was created
         */
        private Song[] initSongs()
        {
            int songs = 4;
            Song[] tempSongs = new Song[songs];
            tempSongs[0] = new Song("Emil Rottmayer","Descend", "sound/Emil Rottmayer - Descend.mp3");
            tempSongs[1] = new Song("Mike Noise","Low Earth Orbit", "sound/Mike Noise Low Earth Orbit.mp3");
            tempSongs[2] = new Song("Daniel Rosenfeld","Stranger Things  Theme Songx","sound/Stranger Things Theme Songx.mp3");
            tempSongs[3] = new Song("John Williams", "Star Wars Main Theme", "sound/starwars.mp3");
            return tempSongs;
        }

        /**
         @author Albin Ahlbeck
          * Changes the colour on components
         @param theme The theme to use to color the components
         */
        public void addTheme(Theme theme)
        {
            btnPlay.setTextFill(theme.getMainPaint());
            btnPlay.setBackground(new Background(new BackgroundFill(theme.getSecondaryPaint(),
                    CornerRadii.EMPTY, Insets.EMPTY)));

            songComboBox.setBackground(new Background(new BackgroundFill(theme.getSecondaryPaint(),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            lblVolume.setTextFill(theme.getSecondaryPaint());
        }
        /**
         @author Albin Ahlbeck
          * Changes the song without the use of the combobox
         @param index The position of the song that is going to be played
         */
        public void changeSong(int index)
        {
            songComboBox.getSelectionModel().select(index);
        }

    }