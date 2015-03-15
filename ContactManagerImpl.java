import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ContactManagerImpl implements ContactManager
{
    private Set<Contact> myContacts;
    private Set<Meeting> myMeetings;

    public ContactManagerImpl (Set<Contact> contacts)
    {
        myContacts = new HashSet<Contact>();
        myMeetings = new HashSet<Meeting>();
        myContacts.addAll(contacts);
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        FutureMeeting myFutureMeeting = new FutureMeetingImpl(contacts, date);
        if(date.before(Calendar.getInstance())){

            throw new IllegalArgumentException("Can not create future meeting in past!");
        }
        else if (!myContacts.containsAll(contacts))
        {
            throw new IllegalArgumentException("Contact list provided contains unknown / non-existent contact");
        }
        return myFutureMeeting.getId();
    }

    /**
     * Returns the PAST meeting with the requested ID, or null if it there is none. *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
     */
    public PastMeeting getPastMeeting(int id)
    {
        PastMeeting myPastMeeting = new PastMeetingImpl(myContacts, Calendar.getInstance());
        return myPastMeeting;
    }

    /**
     * Create a new record for a meeting that took place in the past*
     * @param contacts a list of participants
     * @param date the date on which the meeting took place
     * @param text messages to be added about the meeting.
     * @throws IllegalArgumentException if the list of contacts is
     * empty, or any of the contacts does not exist
     * @throws NullPointerException if any of the arguments is null **/

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text)
    {

    }

}
