package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class JournalEntry {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private  String title;

    private String content;

    private LocalDateTime date;

}
