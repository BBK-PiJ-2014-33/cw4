import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ContactManagerTest {
    ContactManager myContactManagerClass;
    Calendar myDate;
    private Set<Contact> myContacts;
    private Set<Contact> myPastMeetingContacts;
    private int myPastMeetingId;

    @Before
    public void setUp() throws Exception
    {
        int numberOfContacts = 5;
        myDate = Calendar.getInstance();
        myContacts = new HashSet<Contact>();
        myPastMeetingContacts  = new HashSet<Contact>();
        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContacts.add(new ContactImpl(Names[i]));
            myPastMeetingContacts.add(new ContactImpl(Names[i]));
        }
        myContactManagerClass = new ContactManagerImpl(myContacts);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFutureMeetingInPast()
    {
        Calendar myPastDate = Calendar.getInstance();
        myPastDate.set(1900, Calendar.JANUARY, 30);
        myContactManagerClass.addFutureMeeting(myContacts,myPastDate);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFutureMeetingIllegalContact()
    {
        Set<Contact> myFutureMeetingContacts;
        myFutureMeetingContacts = new HashSet<Contact>();
        int numberOfContacts = 5;
        String [] Names = {"Anna Jones", "David Crampton", "Maria Garcia", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myFutureMeetingContacts.add(new ContactImpl(Names[i]));
        }
        myFutureMeetingContacts.add(new ContactImpl("Emily Storey"));
        myContactManagerClass.addFutureMeeting(myFutureMeetingContacts,myDate);
    }

    @Test
    public void testAddFutureMeetingNotNull() throws Exception
    {
        Calendar myFutureDate = Calendar.getInstance();
        myFutureDate.set(2016,Calendar.JANUARY, 30);
        assertNotNull(myContactManagerClass.addFutureMeeting(myContacts,myFutureDate));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddMeetingNotesMeetingDoesNotExist() throws Exception {
        myContactManagerClass.addMeetingNotes(0,"adding meeting notes");
    }
    @Test(expected = IllegalStateException.class)
    public void testAddMeetingNotesMeetingSetForDateInFuture() throws Exception {
        int myMeetingId;
        Calendar myFutureDate = Calendar.getInstance();
        myFutureDate.set(2016,Calendar.JANUARY, 30);
        myMeetingId = myContactManagerClass.addFutureMeeting(myContacts,myFutureDate);
        myContactManagerClass.addMeetingNotes(myMeetingId,"adding meeting notes");
    }
    @Test(expected = NullPointerException.class)
    public void testAddMeetingNotesNullNotes() throws Exception {
        int myMeetingId;
        String myNotes = null;
        myMeetingId = myContactManagerClass.addFutureMeeting(myContacts,Calendar.getInstance());
        TimeUnit.SECONDS.sleep(2);
        myContactManagerClass.addMeetingNotes(myMeetingId,myNotes);
    }
    @Test
    public void testAddMeetingNotes() throws Exception {
        int myMeetingId;
        String expected = "adding meeting notes";
        PastMeeting myPastMeeting;
        myMeetingId = myContactManagerClass.addFutureMeeting(myContacts,Calendar.getInstance());
        TimeUnit.SECONDS.sleep(2);
        myContactManagerClass.addMeetingNotes(myMeetingId,expected);
        myPastMeeting = myContactManagerClass.getPastMeeting(myMeetingId);
        assertEquals(myPastMeeting.getNotes(),expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPastMeetingFutureMeetingID() throws Exception {
        int myFutureMeetingID;
        Calendar myFutureDate = Calendar.getInstance();
        myFutureDate.set(2016,Calendar.JANUARY, 30);
        myFutureMeetingID = myContactManagerClass.addFutureMeeting(myContacts,myFutureDate);
        myContactManagerClass.getPastMeeting(myFutureMeetingID);
    }

    @Test
    public void testGetPastMeeting() throws Exception {
        ContactManager myLocalContactManager = new ContactManagerImpl(myContacts);
        int myMeetingId;
        myMeetingId =  myLocalContactManager.addFutureMeeting(myContacts,Calendar.getInstance());
        TimeUnit.SECONDS.sleep(2);
        myLocalContactManager.getPastMeeting(myMeetingId);
    }

    @Test(expected = IllegalArgumentException.class)
      public void testAddNewPastMeetingEmptyContact() {
        Set<Contact> myPastMeetingContacts;
        Calendar myPastDate = Calendar.getInstance();
        myPastDate.set(1900, Calendar.JANUARY, 30);
        myPastMeetingContacts = new HashSet<Contact>();
        myContactManagerClass.addNewPastMeeting(myPastMeetingContacts, myPastDate, "meeting took place");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingContactDoesNotExist() {

        Calendar myPastDate = Calendar.getInstance();
        myPastDate.set(1900, Calendar.JANUARY, 30);
        myPastMeetingContacts.add(new ContactImpl("Emily Storey"));
        myContactManagerClass.addNewPastMeeting(myPastMeetingContacts, myPastDate, "meeting took place");

    }

    @Test(expected = NullPointerException.class)
    public void testAddNewPastMeetingNullArgument()
    {
        Calendar myPastDate =Calendar.getInstance();
        String myNotes = null;
        myPastDate.set(1900, Calendar.JANUARY, 30);
        myContactManagerClass.addNewPastMeeting(myContacts, myPastDate, myNotes);
    }


    @Test
    public void testGetFutureMeetingDoesNotExist() throws Exception {
        assertNull(myContactManagerClass.getFutureMeeting(0));
    }


    @Test
    public void testGetFutureMeeting() throws Exception {
        int expected, output;
        FutureMeeting myFutureMeeting;
        Calendar myFutureDate = Calendar.getInstance();
        myFutureDate.set(2016,Calendar.JANUARY, 30);
        expected = myContactManagerClass.addFutureMeeting(myContacts,myFutureDate);
        myFutureMeeting = myContactManagerClass.getFutureMeeting(expected);
        output = myFutureMeeting.getId();
        assertEquals(output,expected);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testGetFutureMeetingHappeningInPast() throws Exception {
        int myMeetingId;
        FutureMeeting myFutureMeeting;
        myMeetingId = myContactManagerClass.addFutureMeeting(myContacts,Calendar.getInstance());
        TimeUnit.SECONDS.sleep(2);
        myFutureMeeting = myContactManagerClass.getFutureMeeting(myMeetingId);
    }

    @Test
    public void testGetMeetingDoesNotExist() throws Exception {
        assertNull(myContactManagerClass.getMeeting(0));
    }

    @Test
    public void testGetMeetingContactManagerWithNoMeetings() throws Exception {
        ContactManager myLocalContactManagerClass;
        myLocalContactManagerClass = new ContactManagerImpl(myContacts);
        assertNull(myLocalContactManagerClass.getMeeting(0));
    }

    @Test
    public void testGetMeeting() throws Exception {
        int myFutureMeetingID;
        int expected, output;
        Meeting myTestMeeting;
        Calendar myFutureDate = Calendar.getInstance();
        myFutureDate.set(2016,Calendar.JANUARY, 30);
        myFutureMeetingID = myContactManagerClass.addFutureMeeting(myContacts,myFutureDate);
        expected = myFutureMeetingID;
        myTestMeeting = myContactManagerClass.getMeeting(myFutureMeetingID);
        output = myTestMeeting.getId();
        assertEquals(output,expected);
    }

    @Test
    public void testGetFutureMeetingList() throws Exception {

    }

    @Test
    public void testGetFutureMeetingList1() throws Exception {

    }

    @Test
    public void testGetPastMeetingList() throws Exception {

    }



    @Test(expected = NullPointerException.class)
    public void testAddNewContactNameNull() throws Exception {
        myContactManagerClass.addNewContact("Anna Kent", null);
    }
    @Test(expected = NullPointerException.class)
    public void testAddNewContactNotesNull() throws Exception {
        myContactManagerClass.addNewContact(null, "test notes");
    }
    @Test(expected = NullPointerException.class)
    public void testAddNewContactNameAndNotesNull() throws Exception {
        myContactManagerClass.addNewContact(null, null);
    }
    @Test
    public void testAddNewContact() throws Exception {

        Set<Contact> myMatchingContacts = new HashSet<Contact>();
        myContactManagerClass.addNewContact("Anna Kent", "test notes");
        myMatchingContacts = myContactManagerClass.getContacts("Anna Kent");
        assertFalse(myMatchingContacts.isEmpty());
    }

    @Test
    public void testGetContactsString() throws Exception {
        ContactManager myLocalContactManagerClass;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> expected = new HashSet<Contact>();
        int numberOfContacts = 2;

        String [] Names = {"Anna Jones", "Maria Jones"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            Contact myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            expected.add(myContact);
        }

        myLocalContacts.add(new ContactImpl("David Crampton"));
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        assertEquals(myLocalContactManagerClass.getContacts("Jones"), expected);
    }

    @Test(expected = NullPointerException.class)
    public void testGetContactsStringEmpty() throws Exception {

        myContactManagerClass.getContacts("");
    }

    @Test(expected = NullPointerException.class)
    public void testGetContactsStringNull() throws Exception {

        String myTestString = null;
        myContactManagerClass.getContacts(myTestString);
    }

    @Test
    public void testGetContactsArbitraryIds() throws Exception {
        ContactManager myLocalContactManagerClass;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> expected = new HashSet<Contact>();
        int numberOfContacts = 3;
        int [] myContactIDs = new int [3];

        String [] Names = {"Anna Jones", "Maria Jones", "David Crampton"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            Contact myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            expected.add(myContact);
            myContactIDs[i] = myContact.getId();
        }

        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        myLocalContactManagerClass.addNewContact("Tom Walters", "producer");
        assertEquals(myLocalContactManagerClass.getContacts(myContactIDs[0],myContactIDs[1],myContactIDs[2] ), expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetContactsNotRealContactID() throws Exception {
        ContactManager myLocalContactManagerClass;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> expected = new HashSet<Contact>();
        int numberOfContacts = 3;
        int [] myContactIDs = new int [3];

        String [] Names = {"Anna Jones", "Maria Jones", "David Crampton"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            Contact myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            expected.add(myContact);
            myContactIDs[i] = myContact.getId();
        }

        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        expected = myLocalContactManagerClass.getContacts(myContactIDs[0],myContactIDs[1],0 );
    }

    @Test
    public void testFlush() throws Exception {

    }
}