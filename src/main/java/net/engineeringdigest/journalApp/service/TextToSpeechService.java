package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.TextToSpeechRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class TextToSpeechService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${elevenlabs.api.key}")
    private String API_KEY;

    public HttpStatus callTextToSpeechAPI(String text, String filePath) {
        String url = "https://api.elevenlabs.io/v1/text-to-speech/21m00Tcm4TlvDq8ikWAM";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("xi-api-key", API_KEY);

        String modelId = "eleven_multilingual_v2";
        TextToSpeechRequest textToSpeechRequest = TextToSpeechRequest
                .builder()
                .text(text)
                .model_id(modelId)
                .voice_settings(
                        TextToSpeechRequest
                                .VoiceSettings
                                .builder()
                                .stability(0.8)
                                .similarity_boost(0.9)
                                .build())
                .build();

        HttpEntity httpEntity = new HttpEntity(textToSpeechRequest, httpHeaders);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, byte[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            saveFile(response.getBody(), filePath);
            return response.getStatusCode();
        } else {
            throw new RuntimeException("Failed to download file: " + response.getStatusCode());
        }
    }

    public void saveFile(byte[] fileBytes, String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(fileBytes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file" + e.getLocalizedMessage());
        }
    }
}
