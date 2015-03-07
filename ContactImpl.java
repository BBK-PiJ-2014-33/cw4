
public class ContactImpl implements Contact {

    private static int GlobalID=1;
    private int myId;
    private String myName;
    private String myNotes ="";

    public ContactImpl(String Name)
    {
        myName = Name;
        myId = GlobalID;
        GlobalID++;
    }

    public int getId()
    {
        return myId;
    }
    public String getName()
    {
        return myName;
    }
    public String getNotes()
    {
        return myNotes;
    }
    public void addNotes(String note)
    {
        myNotes = myNotes + note;
    }
}
