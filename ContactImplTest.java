import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

public class ContactImplTest {

    private ContactImpl myContactClass;


    @Before
    public void setUp() throws Exception {
        myContactClass = new ContactImpl();
    }

    @After
    public void cleanUp() throws Exception {
        myContactClass = new ContactImpl();
    }

    @Test
    public void testGetId() throws Exception {
        assertNotNull(myContactClass.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertNotNull(myContactClass.getName());
    }

    @Test
    public void testGetNotes() throws Exception {
        assertNotNull(myContactClass.getNotes());
    }

    @Test
    public void testAddNotes() throws Exception {

        String expected = "test";
        String output;
        myContactClass.addNotes("test");
        output = myContactClass.getNotes();
        assertEquals(output,expected);
    }

    @Test
    public void testIdUnique() throws Exception
    {

        int numberOfContacts = 10;
        int myId1, myId2;
        Contact [] myContacts = new ContactImpl[numberOfContacts];

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContacts[i] = new ContactImpl();
        }

        for (int i = 0; i < numberOfContacts; i++)
        {
            myId1 = myContacts[i].getId();
            for (int k = 0; k < numberOfContacts; k++)
            {
                if(i!=k)
                {
                    myId2 = myContacts[k].getId();
                    assertNotEquals(myId1,myId2);
                }

            }

        }

    }
}