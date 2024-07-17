package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<Weather> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, Weather.class);
        HttpStatus statusCode = response.getStatusCode();
        Weather responseBody = response.getBody();
        return responseBody;
    }
}
