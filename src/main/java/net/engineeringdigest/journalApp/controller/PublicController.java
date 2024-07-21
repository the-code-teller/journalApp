package net.engineeringdigest.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.entity.Weather;
import net.engineeringdigest.journalApp.service.TextToSpeechService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PublicController {

    //    private static final Logger log = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private TextToSpeechService textToSpeechService;

    @GetMapping("health-check")
    public String healthCheck() {
        log.info("Health OK");
        log.error("Health OK");
        return "ok";
    }

    @CrossOrigin
    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User savedUser = null;
        try {
            savedUser = userService.saveNewUser(user);
        } catch (Exception e) {
//            log.info(e.getMessage());
//            log.error("Error occured with username: {}", user.getUsername(), e);
//            log.error("eeeeeeeeeeeeeee");
//            log.info("iiiiiiiiiiiiiii");
//            log.debug("dddddddddddddd");
//            log.warn("wwwwwwwwwwwwwwww");
//            log.trace("ttttttttttttttt");


            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("weather")
    public ResponseEntity<?> weather() {
        Weather.Current weather = weatherService.callWeatherAPI("NEW YORK").getCurrent();
        return new ResponseEntity<>(weather, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("tts")
    public ResponseEntity<?> textToSpeech() {
        String filePath = "D:\\learn\\journalApp\\speech.mpga";
        String text = "API that converts text into lifelike speech with best-in-class latency & uses the most advanced AI audio model ever";
        HttpStatus httpStatus = textToSpeechService.callTextToSpeechAPI(text, filePath);
        if (httpStatus.is2xxSuccessful()) {
            return new ResponseEntity<>(httpStatus);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
