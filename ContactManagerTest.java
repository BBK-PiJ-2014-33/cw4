import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ContactManagerTest {
    ContactManager myContactManagerClass;
    Calendar myDate;
    private Set<Contact> myContacts;
    private Set<Contact> myPastMeetingContacts;

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


    /**
     * Returns the PAST meeting with the requested ID, or null if it there is none. *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
     *//*

    @Test
    public void testGetPastMeetingFutureMeetingID() throws Exception {
        int myFutureMeetingID;
        Calendar myFutureDate = Calendar.getInstance();
        myFutureDate.set(2016,Calendar.JANUARY, 30);
        myFutureMeetingID = myContactManagerClass.addFutureMeeting(myContacts,myFutureDate);
        assertNull(myContactManagerClass.getPastMeeting(myFutureMeetingID));
    }
    @Test
    public void testGetPastMeeting() throws Exception {

        ContactManager myLocalContactManager = new ContactManagerImpl(myContacts);
        Calendar myPastDate = Calendar.getInstance();
        myPastDate.set(1900, Calendar.JANUARY, 30);
        myLocalContactManager.addNewPastMeeting(myContacts, myPastDate,"new past meeting created in testGetPastMeeting");
    }*/

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
    public void testGetFutureMeeting() throws Exception {

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


    @Test
    public void testAddMeetingNotes() throws Exception {

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
        ContactManagerImpl myLocalContactManagerClass;
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

        myContactManagerClass.getContacts(null);
    }

    @Test
    public void testGetContacts1() throws Exception {

    }

    @Test
    public void testFlush() throws Exception {

    }
}