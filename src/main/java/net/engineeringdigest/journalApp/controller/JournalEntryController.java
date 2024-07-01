package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
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

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/{username}")
    public ResponseEntity<?> getALlJournalEntriesByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if (journalEntries == null || journalEntries.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{username}")
    public ResponseEntity<?> addEntryByUsername(@RequestBody JournalEntry journalEntry, @PathVariable String username) {
        try {
            User user = journalEntryService.saveEntry(journalEntry, username);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        if (journalEntry.isPresent())
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @DeleteMapping("{username}/{id}")
    public ResponseEntity<?> deleteJournalEntryByUsernameAndId(@PathVariable ObjectId id, @PathVariable String username) {
        try {
            journalEntryService.deleteById(id, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
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
