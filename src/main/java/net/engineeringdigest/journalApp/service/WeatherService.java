package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final String url = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=COUNTRY&aqi=no";
    private final String API_KEY = "cf3346330a5d4f65a3e201046241607";
//    private String country = "LONDON";

    @Autowired
    private RestTemplate restTemplate;

    public Weather callWeatherAPI(String country) {
        String finalUrl = url.replace("API_KEY", API_KEY).replace("COUNTRY", country);
        User user = User.builder().username("r").password("r").build();

        // For POST call
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("key", "value");
        HttpEntity<User> userHttpEntity = new HttpEntity<>(user, httpHeaders);
//        ResponseEntity<Weather> response = restTemplate.exchange(finalUrl, HttpMethod.POST, userHttpEntity, Weather.class);

//        For GET calls
        ResponseEntity<Weather> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, Weather.class);
        HttpStatus statusCode = response.getStatusCode();
        Weather responseBody = response.getBody();
        return responseBody;
    }
}
