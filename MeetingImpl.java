import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {

    private static int GlobalMeetingID=1;
    private int myMeetingId;
    private Calendar myDate;
    protected String myNotes;
    private Set<Contact> myContacts;

    public MeetingImpl(Set<Contact> Contacts, Calendar meetingDate)
    {
        myMeetingId = GlobalMeetingID;
        GlobalMeetingID++;
        myContacts = Contacts;
        myDate = meetingDate;
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

    public void addNotes (String notes)
    {

    }
}

