package net.engineeringdigest.journalApp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Weather {

    private Current current;

    @Data
    public class Current {

        @JsonProperty("temp_c")
        private double temp;
        private int humidity;
        private int cloud;
        @JsonProperty("feelslike_c")
        private double feelslike;
    }

}

