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

    @Before
    public void setUp() throws Exception
    {
        int numberOfContacts = 5;
        myDate = Calendar.getInstance();
        myContacts = new HashSet<Contact>();
        String [] Names = {"Anna Jones", "David Crampton", "Maria Garcia", "Nick White", "Scott Goldstone"};
        for (int i = 0; i < numberOfContacts; i++)
        {
            myContacts.add(new ContactImpl(Names[i]));
        }
        myContactManagerClass = new ContactManagerImpl(myContacts);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFutureMeetingInPast()
    {
        Calendar myPastDate = Calendar.getInstance();
        myPastDate.set(1900, Calendar.JANUARY, 30);
        myContactManagerClass.addFutureMeeting(myContacts,myDate);

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
        assertNotNull(myContactManagerClass.addFutureMeeting(myContacts,myDate));
    }

    @Test
    public void testGetPastMeeting() throws Exception {

        Calendar myPastDate = Calendar.getInstance();
        int myPastMeetingID;
        PastMeeting expected;
        myPastDate.set(1900, Calendar.JANUARY, 30);
        PastMeeting myPastMeeting = new PastMeetingImpl(myContacts, myPastDate);
        expected = myPastMeeting;
        myPastMeetingID = myPastMeeting.getId();
        assertEquals(myContactManagerClass.getPastMeeting(myPastMeetingID), expected);
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
        Set<Contact> myPastMeetingContacts;
        Calendar myPastDate = Calendar.getInstance();
        myPastDate.set(1900, Calendar.JANUARY, 30);
        myPastMeetingContacts = new HashSet<Contact>();

        int numberOfContacts = 5;
        String [] Names = {"Anna Jones", "David Crampton", "Maria Garcia", "Nick White", "Scott Goldstone"};
        for (int i = 0; i < numberOfContacts; i++)
        {
            myPastMeetingContacts.add(new ContactImpl(Names[i]));
        }
        myPastMeetingContacts.add(new ContactImpl("Emily Storey"));
        myContactManagerClass.addNewPastMeeting(myPastMeetingContacts, myPastDate, "meeting took place");

    }

    @Test(expected = NullPointerException.class)
    public void testAddNewPastMeetingNullArgument()
    {
        Set<Contact> myPastMeetingContacts;
        myPastMeetingContacts = new HashSet<Contact>();
        Calendar myPastDate = Calendar.getInstance();
        myPastDate.set(1900, Calendar.JANUARY, 30);
        myContactManagerClass.addNewPastMeeting(myPastMeetingContacts, myPastDate, "meeting took place");
    }


/*
    @Test
    public void testGetFutureMeeting() throws Exception {

    }

    @Test
    public void testGetMeeting() throws Exception {

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

    @Test
    public void testAddNewContact() throws Exception {

    }

    @Test
    public void testGetContacts() throws Exception {

    }

    @Test
    public void testGetContacts1() throws Exception {

    }

    @Test
    public void testFlush() throws Exception {

    }
*/}