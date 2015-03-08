import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PastMeetingTest {

    private PastMeeting myPastMeetingClass;
    private Set<Contact> myContacts;
    Calendar myDate;

    @Before
    public void setUp() throws Exception {
        int numberOfContacts = 5;
        myDate = Calendar.getInstance();
        myContacts = new HashSet<Contact>();
        String [] Names = {"Anna Jones", "David Crampton", "Maria Garcia", "Nick Jones", "Scott Goldstone"};
        for (int i = 0; i < numberOfContacts; i++)
        {
            myContacts.add(new ContactImpl(Names[i]));
        }
        myPastMeetingClass = new PastMeetingImpl(myContacts, myDate);

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