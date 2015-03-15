import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {

    private static int GlobalMeetingID=1;
    private int myMeetingId;
    private Calendar myDate;
    private Set<Contact> myContacts;
    protected String myNotes;

    public MeetingImpl(Set<Contact> Contacts, Calendar meetingDate)
    {
        myMeetingId = GlobalMeetingID;
        GlobalMeetingID++;
        myContacts = Contacts;
        myDate = meetingDate;
        myNotes = "";
    }

    public int getId()
    {
        return myMeetingId;
    }

    public Calendar getDate()
    {
        return myDate;
    }

    public Set<Contact> getContacts()
    {
        return myContacts;
    }

    public void addNotes(String note)
    {
        StringBuilder st = new StringBuilder(myNotes);
        st.append(note);
        st.append(System.getProperty("line.separator"));
        myNotes = st.toString();
    }
}

