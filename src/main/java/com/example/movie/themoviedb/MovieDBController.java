package com.example.movie.themoviedb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RestController
@RequestMapping("/api/moviedb")
public class MovieDBController {

    public final String API_URL = "https://api.themoviedb.org/3";
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${MOVIEDB_API_KEY}")
    private String movieDbApiKey;

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("HELLO WORLD");
    }


    @GetMapping("/movie/search")
    public ResponseEntity<String> getMoviesByName(@RequestParam String name, @RequestParam(defaultValue = "1") String page) {
        String url = API_URL + "/search/movie" + "?query=" + name + "&page=" + page;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(movieDbApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(
                headers
        );

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                response.getBody()
        );
    }

    @GetMapping("/TV/search")
    public ResponseEntity<String> getTVByName(@RequestParam String name, @RequestParam(defaultValue = "1") String page) {
        String url = API_URL + "/search/TV" + "?query=" + name + "&page=" + page;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(movieDbApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(
                headers
        );

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                response.getBody()
        );
    }

}
