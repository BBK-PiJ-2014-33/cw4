import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;


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


    @Test
    public void testGetPastMeetingDoesNotExist() throws Exception {
        assertNull(myContactManagerClass.getFutureMeeting(0));
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
        Calendar myPastDate = Calendar.getInstance();
        String output, expected;
        int myMeetingId;
        PastMeeting myFinalPastMeeting;
        expected = "my test past meeting";
        myPastDate.set(2014,Calendar.JANUARY, 30);
        myLocalContactManager.addNewPastMeeting(myContacts,myPastDate,"my test past meeting");
        List <Meeting> myMeetingList = myLocalContactManager.getFutureMeetingList(myPastDate);
        Meeting myPastMeeting = myMeetingList.get(0);
        myMeetingId = myPastMeeting.getId();
        myFinalPastMeeting = myLocalContactManager.getPastMeeting(myMeetingId);
        output = myFinalPastMeeting.getNotes();
        assertEquals(output,expected);
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
    public void testGetFutureMeetingListNoMeetingsOnThisDate() throws Exception {
        List myMeetingList;
        Calendar myLocalDate = Calendar.getInstance();
        myLocalDate.set(2020,Calendar.JANUARY, 30);
        myContactManagerClass.addFutureMeeting(myContacts,Calendar.getInstance());
        TimeUnit.SECONDS.sleep(2);
        myContactManagerClass.addFutureMeeting(myContacts,Calendar.getInstance());
        TimeUnit.SECONDS.sleep(2);
        myContactManagerClass.addFutureMeeting(myContacts,Calendar.getInstance());
        TimeUnit.SECONDS.sleep(2);
        myMeetingList = myContactManagerClass.getFutureMeetingList(myLocalDate);
        assertTrue(myMeetingList.isEmpty());
    }

    @Test
    public void testGetFutureMeetingListNoDuplicates() throws Exception {
        List myMeetingList;
        Calendar myLocalDate = Calendar.getInstance();
        myLocalDate.set(2020,Calendar.JANUARY,30);
        myContactManagerClass.addFutureMeeting(myContacts,myLocalDate);
        myContactManagerClass.addFutureMeeting(myContacts,myLocalDate);
        myContactManagerClass.addFutureMeeting(myContacts, myLocalDate);
        myMeetingList = myContactManagerClass.getFutureMeetingList(myLocalDate);
        for (int i = 0; i < myMeetingList.size()-1; i++)
        {
            assertNotSame(myMeetingList.get(i), myMeetingList.get(i+1));
        }
    }
    @Test
    public void testGetFutureMeetingListSorted() throws Exception {
        List <Meeting> myMeetingList;
        Calendar myLocalDate = Calendar.getInstance();
        Calendar myDate1, myDate2, myDate3,  myDate4;
        myDate3 = Calendar.getInstance();
        TimeUnit.SECONDS.sleep(2);
        myDate2 = Calendar.getInstance();
        TimeUnit.SECONDS.sleep(2);
        myDate1 = Calendar.getInstance();
        myDate4 = Calendar.getInstance();
        myDate4.set(2016,Calendar.JANUARY, 30);
        myContactManagerClass.addNewPastMeeting(myContacts, myDate1,"");
        myContactManagerClass.addNewPastMeeting(myContacts, myDate2,"");
        myContactManagerClass.addNewPastMeeting(myContacts, myDate3,"");
        myContactManagerClass.addFutureMeeting(myContacts, myDate4);
        myMeetingList = myContactManagerClass.getFutureMeetingList(myLocalDate);
        for (int i = 0; i < myMeetingList.size()-1; i++)
        {
            assertTrue(myMeetingList.get(i).getDate().before(myMeetingList.get(i+1).getDate()));
        }
    }

    @Test
    public void testGetFutureMeetingListContactNoMeetingsWithThisContact() throws Exception {
        List myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> myMeetingContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int numberOfContacts = 5;

        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            myMeetingContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContacts.add(myContact);
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalDate.set(2020,Calendar.JANUARY, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myMeetingList =  myLocalContactManagerClass.getFutureMeetingList(myContact);
        assertTrue(myMeetingList.isEmpty());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetFutureMeetingListContactIllegalContact() throws Exception {
        List myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int numberOfContacts = 5;

        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalDate.set(2020,Calendar.JANUARY, 30);
        myLocalContactManagerClass.addFutureMeeting(myLocalContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myLocalContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myLocalContacts,myLocalDate);
        myMeetingList =  myLocalContactManagerClass.getFutureMeetingList(myContact);
    }
    @Test
    public void testGetFutureMeetingListContactNoDuplicates() throws Exception {
        List <Meeting> myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> myMeetingContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int numberOfContacts = 5;

        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            myMeetingContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContacts.add(myContact);
        myMeetingContacts.add(myContact);
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalDate.set(2020,Calendar.MARCH, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalDate.set(2020,Calendar.FEBRUARY, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalDate.set(2020,Calendar.JANUARY, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myMeetingList =  myLocalContactManagerClass.getFutureMeetingList(myContact);

        for (int i = 0; i < myMeetingList.size()-1; i++)
        {
            assertNotSame(myMeetingList.get(i), myMeetingList.get(i+1));
        }

    }
    /*@Test
    public void testGetFutureMeetingListContactSorted() throws Exception {
        List <Meeting> myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> myMeetingContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int numberOfContacts = 5;

        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            myMeetingContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContacts.add(myContact);
        myMeetingContacts.add(myContact);
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalDate.set(2020,Calendar.MARCH, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalDate.set(2020,Calendar.FEBRUARY, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalDate.set(2020,Calendar.JANUARY, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myMeetingList =  myLocalContactManagerClass.getFutureMeetingList(myContact);
        for (int i = 0; i < myMeetingList.size()-1; i++)
        {
            assertTrue(myMeetingList.get(i).getDate().before(myMeetingList.get(i+1).getDate()));
        }
    }*/

    @Test
    public void testGetFutureMeetingListContact() throws Exception
    {
        List <Meeting> myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> myMeetingContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int expected, output;
        int numberOfContacts = 5;
        expected = 3;

        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            myMeetingContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContacts.add(myContact);
        myMeetingContacts.add(myContact);
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalDate.set(2020,Calendar.MARCH, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalDate.set(2020,Calendar.FEBRUARY, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalDate.set(2020,Calendar.JANUARY, 30);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalDate = Calendar.getInstance();
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        TimeUnit.SECONDS.sleep(2);
        myMeetingList =  myLocalContactManagerClass.getFutureMeetingList(myContact);
        output = myMeetingList.size();
        assertEquals(output,expected);
    }
    @Test
    public void testGetPastMeetingListNoMeetingsWithThisContact() throws Exception {
        List myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> myMeetingContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int numberOfContacts = 5;

        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            myMeetingContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContacts.add(myContact);
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myMeetingList =  myLocalContactManagerClass.getPastMeetingList(myContact);
        assertTrue(myMeetingList.isEmpty());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGetPastMeetingListIllegalContact() throws Exception {
        List myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int numberOfContacts = 5;

        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalContactManagerClass.addFutureMeeting(myLocalContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myLocalContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myLocalContacts,myLocalDate);
        myMeetingList =  myLocalContactManagerClass.getPastMeetingList(myContact);
    }

    @Test
    public void testGetPastMeetingListNoDuplicates() throws Exception {
        List <PastMeeting> myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> myMeetingContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int numberOfContacts = 5;
        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};
        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            myMeetingContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContacts.add(myContact);
        myMeetingContacts.add(myContact);
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myMeetingList =  myLocalContactManagerClass.getPastMeetingList(myContact);

        for (int i = 0; i < myMeetingList.size()-1; i++)
        {
            assertNotSame(myMeetingList.get(i), myMeetingList.get(i+1));
        }
    }

    /*@Test

    public void testGetPastMeetingListSorted() throws Exception {
        List <Meeting> myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> myMeetingContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int numberOfContacts = 5;
        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};
        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            myMeetingContacts.add(myContact);
        }

        myMeetingContacts.add(myContact);
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myMeetingList =  myLocalContactManagerClass.getPastMeetingList(myContact);
        for (int i = 0; i < myMeetingList.size()-1; i++)
        {
            assertTrue(myMeetingList.get(i).getDate().before(myMeetingList.get(i+1).getDate()));
        }
    }*/

    @Test
    public void testGetPastMeetingList() throws Exception
    {
        List <PastMeeting> myMeetingList;
        Set<Contact> myLocalContacts = new HashSet<Contact>();
        Set<Contact> myMeetingContacts = new HashSet<Contact>();
        Contact myContact;
        ContactManager myLocalContactManagerClass;
        int expected, output;
        int numberOfContacts = 5;
        expected = 3;
        String [] Names = {"Anna Jones", "David Crampton", "Maria Jones", "Nick White", "Scott Goldstone"};
        for (int i = 0; i < numberOfContacts; i++)
        {
            myContact = new ContactImpl(Names[i]);
            myLocalContacts.add(myContact);
            myMeetingContacts.add(myContact);
        }
        myContact = new ContactImpl("Brenda Howard");
        myLocalContacts.add(myContact);
        myMeetingContacts.add(myContact);
        myLocalContactManagerClass = new ContactManagerImpl(myLocalContacts);
        Calendar myLocalDate = Calendar.getInstance();
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        myLocalContactManagerClass.addFutureMeeting(myMeetingContacts,myLocalDate);
        TimeUnit.SECONDS.sleep(2);
        myMeetingList =  myLocalContactManagerClass.getPastMeetingList(myContact);
        output = myMeetingList.size();
        assertEquals(output,expected);
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