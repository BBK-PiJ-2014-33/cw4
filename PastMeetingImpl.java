import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting , Serializable {

    private String myNotes;

    public PastMeetingImpl (Set<Contact> myContacts, Calendar myMeetingDate, int myId, String notes)
    {
        super (myContacts, myMeetingDate, myId);
        myNotes = notes;
    }

    public String getNotes()
    {
        return myNotes;
    }

}
