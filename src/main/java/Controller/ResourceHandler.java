package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Responsible for loading resources from the file system.
 * @author Joel Eriksson Sinclair
 */
public class ResourceHandler {

    /**
     * Returns a list of all found .jpg and .png files in a given directory.
     * @param directoryPath Path to the directory to search.
     * @return A list of URLs, in String format, of the found images. Returns null if the given path is not a directory.
     */
    private static URL[] getImageURLsFromDirectory(String directoryPath) {
        ArrayList<URL> imageList = new ArrayList<>();

        try {
            File directoy = new File(directoryPath);
            File[] imgFiles = directoy.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
            if(imgFiles == null) {
                System.err.println("getImagesFromDirectory: No images found in directory: " + directoryPath);
                return null;
            }
            if(imgFiles.length > 0) {
                for (File imgFile : imgFiles) {
                    String filePath = imgFile.getAbsolutePath();
                    imageList.add(new URL("file:" + filePath));
                }
            }
            return imageList.toArray(new URL[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param fileName Name of a planet. Suffix with _moons to get moon info for specified planet.
     * @return
     */
    private static String[] fetchInfoList(String fileName) {
        if(fileName == null) return null;

        String filePath = "src/main/resources/Info/" + fileName.toLowerCase() + ".txt";
        ArrayList<String> infoList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while (br.ready()) {
                String line = br.readLine();
                // check if line is ok
                if(line.length() > 10 && line.length() < 200) infoList.add(line);
            }

            return infoList.toArray(new String[0]);
        } catch (IOException e) {
            return null;
        }
    }

    public static String[] getPlanetInfo(String planetName) {
        return fetchInfoList(planetName);
    }

    public static String[] getPlanetsMoonInfo(String planetName) {
        return fetchInfoList(planetName + "_moons");
    }

    public static URL[] getPlanetImageURLs(String planetName) {
        if(planetName == null || planetName.equals("")) return null;
        return getImageURLsFromDirectory("src/main/resources/Images/planets/" + planetName);
    }

    public static URL[] getPlanetsMoonImageURLs(String planetName) {
        if(planetName == null || planetName.equals("")) return null;
        return getImageURLsFromDirectory("src/main/resources/Images/planets/" + planetName + "/moons");
    }
}
