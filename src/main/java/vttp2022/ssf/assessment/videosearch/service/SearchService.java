package vttp2022.ssf.assessment.videosearch.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.assessment.videosearch.models.Game;

@Service
public class SearchService {
    private static final String LINK = "https://api.rawg.io/api/games";

    @Value("${rawg.api.key}")
    private String apiKey;

    // private boolean hasKey;

    // System.out.println(">>> API keyset: "+ apiKey);
    
    // @PostConstruct
    // private void init() {
    //     // hasKey = null != apiKey;
    //     System.out.println(">>> API keyset: "+ apiKey);
    // }

    // GET https://api.rawg.io/api/games?key=7b7f46b156c4488da35dafefc24fc249&search=PlayStation&page_size=2
    public List<Game> search(String searchString, Integer count) {
        
        String gameUrl = UriComponentsBuilder.fromUriString(LINK)
            .queryParam("key", apiKey)
            .queryParam("search", searchString)
            .queryParam("page_size", count)
            .toUriString();

        List<Game> games = new ArrayList<>();
        RestTemplate temp = new RestTemplate();
        ResponseEntity<String> res = null;

        try {
            res = temp.getForEntity(gameUrl, String.class);
            InputStream is = new ByteArrayInputStream(res.getBody().getBytes());
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            JsonArray GameArr = o.getJsonArray("results");
            System.out.println(">>> results: "+ o);

            // GAME class: 
            // name "name", 
            // backgroundImage "image_background", 
            // rating "rating" FLOAT
            for(int i=0; i<GameArr.size(); i++) {
                JsonObject obj = GameArr.getJsonObject(i);
                Game game = new Game();
                game.setName(obj.getString("name"));
			    game.setBackgroundImage(obj.getString("background_image"));
			    game.setRating(Float.parseFloat(obj.get("rating").toString()));
                games.add(game);
            }

        } catch (Exception ex) {
            //TO DO: handle exception
            System.err.printf(ex.getMessage());
            ex.printStackTrace();
        }

        return games;
    }
}
