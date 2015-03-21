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

    /*
    Guidance from forum by SERGIO GUTIERREZ-SANTOSPost 3 in reply to 1  • 15 December 2014, 5:23 PM

    The method must return also past meetings if it is called with a past date as parameter.

    The name is confusing, a better name would have been getMeetingList(Calendar) or even getMeetingListOn(Calendar). We apologise for the confusion.

    The meetings must be sorted by time (meetings take place on date and at a time, e.g. on 12-Dec-2013 @ 11h00).

    ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * Returns the list of meetings that are scheduled for, or that took
     * place on, the specified date
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any duplicates.
     *
     * @param date the date
     * @return the list of meetings

     List<Meeting> getFutureMeetingList(Calendar date);
     */

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

    /*
     * Returns the meeting with the requested ID, or null if it there is none.
     * *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     */
    public Meeting getMeeting(int id)
    {
        Meeting myMeeting = new MeetingImpl(myContacts,Calendar.getInstance(), this.getGlobalMeetingID());
        return myMeeting;
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

    public void addNewContact(String name, String notes)
    {
        if(name==null || notes == null )
        {
            throw new NullPointerException("Parameters can not be null");
        }
        Contact myNewContact = new ContactImpl(name);
        myNewContact.addNotes(notes);
        myContacts.add(myNewContact);
    }


    /*
    Guidance from forum by KEITH MANNOCK  Post 4 in reply to 3  • 23 February 2015, 2:02 PM

    The null argument throws an exception and so should the empty string.
     */
    public Set<Contact> getContacts(String name)
    {
        if(name == null || name.isEmpty())
        {
            throw new NullPointerException("Parameters can not be null or empty");
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
