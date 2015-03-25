import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PastMeetingTest {

    private PastMeetingImpl myPastMeetingClass;
    private Set<Contact> myContacts;
    Calendar myDate;
    ContactManagerImpl myContactManager;

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
        myContactManager = new ContactManagerImpl(myContacts);
        myPastMeetingClass = new PastMeetingImpl(myContacts, myDate,myContactManager.getGlobalMeetingID(),"meeting went well");

    }

    @Test
    public void testGetNotesNotNull() throws Exception {

        assertNotNull(myPastMeetingClass.getNotes());
    }

    @Test
    public void testGetNotes() throws Exception
    {
        String expected = "meeting went well";
        assertEquals(myPastMeetingClass.getNotes(), expected);
    }
}