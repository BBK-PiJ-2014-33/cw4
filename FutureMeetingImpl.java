import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting
{
    public FutureMeetingImpl (Set<Contact> myContacts, Calendar myMeetingDate)
    {
        super (myContacts, myMeetingDate);
    }
}
