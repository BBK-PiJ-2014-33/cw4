import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {

    private static int GlobalMeetingID=1;
    private int myMeetingId;
    private Calendar myDate;
    private Set<Contact> myContacts;


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
}
