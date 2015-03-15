import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String myNotes;

    public PastMeetingImpl (Set<Contact> myContacts, Calendar myMeetingDate, String notes)
    {
        super (myContacts, myMeetingDate);
        myNotes = notes;
    }

    public void addNotes(String note)
    {
        StringBuilder st = new StringBuilder(myNotes);
        st.append(note);
        st.append(System.getProperty("line.separator"));
        myNotes = st.toString();
    }

    public String getNotes()
    {
        return myNotes;
    }

}
