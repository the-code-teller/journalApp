package net.engineeringdigest.journalApp.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TextToSpeechRequest {
    private String text;
    private String model_id;
    private VoiceSettings voice_settings;

    @Data
    @Builder
    public static class VoiceSettings{
        private double stability;
        private double similarity_boost;
    }
}
