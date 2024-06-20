package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> journalEntries = journalEntryService.getAll();
        if (journalEntries == null || journalEntries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            JournalEntry journalEntry1 = journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        if (journalEntry.isPresent())
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {
        try {
            journalEntryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntryUpdates) {
        JournalEntry journalEntry = journalEntryService.getEntryById(id).orElse(null);
        if (journalEntry == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(journalEntryUpdates.getTitle() != null && !journalEntryUpdates.getTitle().isEmpty())
            journalEntry.setTitle(journalEntryUpdates.getTitle());
        if(journalEntryUpdates.getContent() != null && !journalEntryUpdates.getContent().isEmpty())
            journalEntry.setContent(journalEntryUpdates.getContent());

        JournalEntry updatedJournalEntry = journalEntryService.saveEntry(journalEntry);
        return new ResponseEntity<>(updatedJournalEntry, HttpStatus.OK);
    }
}
