import java.util.Calendar;
import java.util.Comparator;
import java.util.Set;

public class MeetingImpl implements Meeting {

    private int myMeetingId;
    private Calendar myDate;
    private Set<Contact> myContacts;


    public MeetingImpl(Set<Contact> Contacts, Calendar meetingDate, int myId)
    {
        myMeetingId = myId;
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

}

