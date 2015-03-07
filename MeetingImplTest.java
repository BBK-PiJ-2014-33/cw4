import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class MeetingImplTest {

    private Meeting myMeetingClass;

    @Before
    public void setUp() throws Exception
    {
        myMeetingClass = new MeetingImpl();
    }

    @Test
    public void testGetId() throws Exception
    {
        assertNotNull(myMeetingClass.getId());
    }

    @Test
    public void testGetDate() throws Exception
    {
        assertNotNull(myMeetingClass.getDate());
    }

    @Test
    public void testGetContacts() throws Exception
    {
        assertNotNull(myMeetingClass.getContacts());
    }

    @Test
    public void testIdUnique() throws Exception
    {
        int numberOfMeetings = 10;
        int myId1, myId2;
        Meeting [] myMeetings = new MeetingImpl[numberOfMeetings];

        for (int i = 0; i < numberOfMeetings; i++)
        {
            myMeetings[i] = new MeetingImpl();
        }

        for (int i = 0; i < numberOfMeetings; i++)
        {
            myId1 = myMeetings[i].getId();
            for (int k = 0; k < numberOfMeetings; k++)
            {
                if(i!=k)
                {
                    myId2 = myMeetings[k].getId();
                    assertNotEquals(myId1,myId2);
                }

            }

        }

    }
}