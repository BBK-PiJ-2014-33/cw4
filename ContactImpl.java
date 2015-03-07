
public class ContactImpl implements Contact {

    private static int GlobalContactID=1;
    private int myId;
    private String myName;
    private String myNotes ="";

    public ContactImpl(String Name)
    {
        myName = Name;
        myId = GlobalContactID;
        GlobalContactID++;
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
        StringBuilder st = new StringBuilder(myNotes);
        st.append(note);
        st.append(System.getProperty("line.separator"));
        myNotes = st.toString();
    }
}
