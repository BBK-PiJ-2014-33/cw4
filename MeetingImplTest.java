import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MeetingImplTest {

    private Meeting myMeetingClass;
    private Set<Contact> myContacts;
    private Calendar myDate;
    private ContactManagerImpl myContactManager;

    @Before
    public void setUp() throws Exception
    {
        int numberOfContacts = 5;
        myDate = Calendar.getInstance();
        myContacts = new HashSet<Contact>();
        String [] Names = {"Anna Jones", "David Crampton", "Maria Garcia", "Nick Jones", "Scott Goldstone"};
        for (int i = 0; i < numberOfContacts; i++)
        {
            myContacts.add(new ContactImpl(Names[i]));
        }
        myContactManager = new ContactManagerImpl(myContacts);
        myMeetingClass = new MeetingImpl(myContacts, myDate,myContactManager.getGlobalMeetingID());
    }

    @Test
    public void testGetIdNotNull() throws Exception
    {
        assertNotNull(myMeetingClass.getId());
    }

    @Test
    public void testGetDateNotNull() throws Exception
    {
        assertNotNull(myMeetingClass.getDate());
    }

    @Test
    public void testGetDate() throws Exception
    {
        Calendar expected = myDate;
        assertEquals(myMeetingClass.getDate(), myDate);
    }

    @Test
    public void testGetContactsNotNull() throws Exception
    {
        assertNotNull(myMeetingClass.getContacts());
    }

    @Test
    public void testGetContacts() throws Exception
    {
        Set<Contact> expected = myContacts;
        assertEquals(myMeetingClass.getContacts(), expected);
    }


    @Test
    public void testIdUnique() throws Exception
    {
        int numberOfMeetings = 5;
        int myId1, myId2;
        Meeting [] myMeetings = new MeetingImpl[numberOfMeetings];

        for (int i = 0; i < numberOfMeetings; i++)
        {
            myMeetings[i] = new MeetingImpl(myContacts,myDate, myContactManager.getGlobalMeetingID());
        }

        for (int i = 0; i < numberOfMeetings; i++)
        {
            myId1 = myMeetings[i].getId();
            for (int k = 0; k < numberOfMeetings; k++)
            {
                if(i!=k)
                {
                    myId2 = myMeetings[k].getId();
                    assertFalse(myId1==myId2);
                }

            }

        }

    }

}