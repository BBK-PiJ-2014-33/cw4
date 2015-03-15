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
        myPastMeetingClass = new PastMeetingImpl(myContacts, myDate,myContactManager.getGlobalMeetingID(),"");

    }

    @Test
    public void testGetNotesNotNull() throws Exception {

        assertNotNull(myPastMeetingClass.getNotes());
    }

    @Test
    public void testAddNotesGetNotes() throws Exception
    {
        String expected = "note1\r\n";
        expected = expected + "note2\r\n";
        expected = expected + "note3\r\n";

        myPastMeetingClass.addNotes("note1");
        myPastMeetingClass.addNotes("note2");
        myPastMeetingClass.addNotes("note3");
        String output = myPastMeetingClass.getNotes();

        assertEquals(myPastMeetingClass.getNotes(), expected);
    }
}