package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    @Transactional
    public User saveEntry(JournalEntry journalEntry, String username) {
        User user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(savedEntry);
        return userService.saveUser(user);
    }

    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username) {
        User user = userService.findByUsername(username);
        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if(removed) {
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
