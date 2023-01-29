package Model;

/**
 @author Albin Ahlbeck
  * Contains the artist, the name of the song and filepath
 */
public class Song
{
    private String artist;
    private String songName;
    private String path;

    /**
     @author Albin Ahlbeck
      * Constructs the song
     @param artist The artist of the song
     @param songName Name of the song
     @param path The file path to the song
     */
    public Song(String artist, String songName, String path)
    {
        this.artist = artist;
        this.songName = songName;
        this.path = path;
    }
    /**
     @author Albin Ahlbeck
     @return returns a string of the path
     */
    public String getPath() {
        return path;
    }

    /**
     @author Albin Ahlbeck
     @return returns a string that contains the artist and the songname
     */
    public String toString()
    {
        String res = (artist + " - " + songName);
        return res;
    }
}
