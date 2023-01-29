package Controller;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.net.URI;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class that reads information from the API
 */
public class APIReader
{
    /**
     * Method that converts the API information of a specific planet to a JSONObject and returns that
     * the JSONObject.
     * @param body the name of the planet which information is getting converted, in french.
     * @return a JSONObject for a specific planet
     */
    public JSONObject readBodyFromAPI(String body)
    {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject jObject = new JSONObject();
        try
        {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.le-systeme-solaire.net/rest/bodies/" + body))
                    .build();

            HttpResponse<String> reply = client.send(request, HttpResponse.BodyHandlers.ofString());

            String replyContent = reply.body();

            JSONParser parser = new JSONParser();

            jObject = (JSONObject) parser.parse(replyContent);

        }
        catch (Exception e)
        {
            System.out.println("ReadBodyFromAPI " + e.getMessage());
        }

        return jObject;
    }
}
