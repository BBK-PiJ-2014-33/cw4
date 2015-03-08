import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    public PastMeetingImpl (Set<Contact> myContacts, Calendar myMeetingDate)
    {
        super (myContacts, myMeetingDate);
        myNotes = "";
    }
    public String getNotes()
    {
        return myNotes;
    }

}
