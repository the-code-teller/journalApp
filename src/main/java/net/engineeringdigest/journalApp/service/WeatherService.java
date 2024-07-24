package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constant.Placeholder;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
//    private final String url = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY&aqi=no";

    @Value("${weather.api.key}")
    private String API_KEY;
//    private String country = "LONDON";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

//    private final String url = appCache.APP_CACHE.get("weather_api");

    public Weather callWeatherAPI(String city) {
//        String finalUrl = url.replace("<apiKey>", API_KEY).replace("<city>", city);
//        String finalUrl = appCache.APP_CACHE.get(AppCache.keys.WEATHER_API.toString()).replace("<apiKey>", API_KEY).replace("<city>", city);
        String finalUrl = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholder.API_KEY, API_KEY).replace(Placeholder.CITY, city);
//        User user = User.builder().username("r").password("r").build();

        // For POST call
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("key", "value");
//        HttpEntity<User> userHttpEntity = new HttpEntity<>(user, httpHeaders);
//        ResponseEntity<Weather> response = restTemplate.exchange(finalUrl, HttpMethod.POST, userHttpEntity, Weather.class);

//        For GET calls

        Weather weather = redisService.get("weather_of" + city, Weather.class);
        if (weather != null) {
            return weather;
        }

        ResponseEntity<Weather> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, Weather.class);
        HttpStatus statusCode = response.getStatusCode();
        Weather responseBody = response.getBody();
        if (responseBody != null) {
            redisService.set("weather_of" + city, responseBody, 300l);  // saving weather response for 5 minutes
        }
        return responseBody;
    }
}
