import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactImplTest {

    private ContactImpl myContactClass;

    @Before
    public void setUp() throws Exception {
        myContactClass = new ContactImpl("Anna");
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

        myContactClass.addNotes("test");
        assertNotNull(myContactClass.getNotes());
    }

    @Test
    public void testAddNotes() throws Exception {

        String expected;
        String output;

        expected = "note1\r\n";
        expected = expected + "note2\r\n";
        expected = expected + "note3\r\n";

        myContactClass.addNotes("note1");
        myContactClass.addNotes("note2");
        myContactClass.addNotes("note3");
        output = myContactClass.getNotes();
        assertEquals(output,expected);
    }

    @Test
    public void testIdUnique() throws Exception
    {

        int numberOfContacts = 10;
        int myId1, myId2;
        Contact [] myContacts = new ContactImpl[numberOfContacts];
        String [] Names = {"Anna", "David", "Maria", "Nick", "Scott", "Andy", "Jack", "John", "Alan", "Julian"};

        for (int i = 0; i < numberOfContacts; i++)
        {
            myContacts[i] = new ContactImpl(Names[i]);
        }

        for (int i = 0; i < numberOfContacts; i++)
        {
            myId1 = myContacts[i].getId();
            for (int k = 0; k < numberOfContacts; k++)
            {
                if(i!=k)
                {
                    myId2 = myContacts[k].getId();
                    assertFalse(myId1==myId2);
                    //assertNotEquals(myId1,myId2);
                }

            }

        }

    }
}