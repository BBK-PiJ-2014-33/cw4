import java.util.*;

public class ContactManagerImpl implements ContactManager
{
    private Set<Contact> myContacts;
    private Set<Meeting> myMeetings;
    private static int GlobalMeetingID=1;

    public ContactManagerImpl (Set<Contact> contacts)
    {
        myContacts = new HashSet<Contact>();
        myMeetings = new HashSet<Meeting>();
        myContacts.addAll(contacts);
    }

    public int getGlobalMeetingID()
    {
        int myGlobalMeetingID = GlobalMeetingID;
        GlobalMeetingID++;
        return myGlobalMeetingID;
    }

    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        FutureMeeting myFutureMeeting = new FutureMeetingImpl(contacts, date, getGlobalMeetingID());
        if(date.before(Calendar.getInstance())){

            throw new IllegalArgumentException("Can not create future meeting in past!");
        }
        else if (!myContacts.containsAll(contacts))
        {
            throw new IllegalArgumentException("Contact list provided contains unknown / non-existent contact");
        }
        myMeetings.add(myFutureMeeting);
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
        PastMeeting myPastMeeting = new PastMeetingImpl(myContacts, Calendar.getInstance(), this.getGlobalMeetingID(),"");
        return myPastMeeting;
    }

    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text)
    {
        if(contacts.isEmpty())
        {
            throw new IllegalArgumentException("Contacts for this past meeting are empty");
        }
        else if(!myContacts.containsAll(contacts))
        {
            throw new IllegalArgumentException("Contact list provided contains non-existent contact");
        }
        else if((contacts==null)||date==null||text==null) {
            throw new NullPointerException("Parameters can not be null");
        }
            PastMeeting myPastMeeting = new PastMeetingImpl(contacts, date, this.getGlobalMeetingID(),text);
            myMeetings.add(myPastMeeting);
    }

    /**
     * Create a new contact with the specified name and notes.
     *
     * @param name the name of the contact.
     * @param notes notes to be added about the contact.
     * @throws NullPointerException if the name or the notes are null
     */
    public void addNewContact(String name, String notes)
    {

    }

    public Set<Contact> getContacts(String name)
    {
        if(name == null)
        {
            throw new NullPointerException("Parameters can not be null");
        }
        Set<Contact> myMatchingNameSet = new HashSet<Contact>();
        String ContactName;

        for (Contact c: myContacts)
        {
            ContactName = c.getName();
            if(ContactName.contains(name))
            {
                myMatchingNameSet.add(c);
            }
        }
        return myMatchingNameSet;
    }


}
