package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

//    @Disabled
//    @Test
    @ParameterizedTest
    @ValueSource(strings = {
            "rahul",
            "amit",
            "priya"
    })
    public void testFindByUsername(String username) {
//        assertTrue(5 > 3);
//        assertEquals(4, 2+1);
        assertNotNull(userService.findByUsername(username));
    }

    @Disabled
    @Test
    public void confirmJournalEntriesPresent() {
        assertTrue(userService.findByUsername("rahul").getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,3,5",
            "1,2,4"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }
}
