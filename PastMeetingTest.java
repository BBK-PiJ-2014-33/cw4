import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PastMeetingTest {

    private PastMeeting myPastMeetingClass;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetNotesNotNull() throws Exception {

        assertNotNull(myPastMeetingClass.getNotes());
    }

    /*@Test
    public void testGetNotes() throws Exception {
        //myPastMeetingClass

    }*/
}