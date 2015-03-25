

<<<<<<< HEAD
import java.util.*;
=======
import org.omg.CORBA.*;

import java.lang.Object;
import java.util.*;
import java.util.Collections;
>>>>>>> origin/master

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
    /*
    * Returns the list of meetings that are scheduled for, or that took
    * place on, the specified date
    * If there are none, the returned list will be empty. Otherwise,
    * the list will be chronologically sorted and will not contain any duplicates.
    *
    * @param date the date
    * @return the list of meetings
     */

    public List<Meeting> getFutureMeetingList(Calendar date)
    {
        List <Meeting> myList = new ArrayList();
        for (Meeting m : myMeetings)
        {
            if(m.getDate().get(Calendar.YEAR)==date.get(Calendar.YEAR) && m.getDate().get(Calendar.MONTH)==date.get(Calendar.MONTH)&& m.getDate().get(Calendar.DAY_OF_MONTH)==date.get(Calendar.DAY_OF_MONTH))
            {
                myList.add(m);
            }
        }
        Collections.sort(myList, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });
            return myList;
    }

    public List<Meeting> getFutureMeetingList(Contact contact)
    {
        List <Meeting> myList = new ArrayList();
        if(!myContacts.contains(contact))
        {
            throw new IllegalArgumentException("This contact does not exist");
        }
        for (Meeting m : myMeetings)
        {
           if(m.getContacts().contains(contact)&& m.getDate().after(Calendar.getInstance()))
           {
               myList.add(m);
           }
        }

        Collections.sort(myList, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });

        return myList;
    }
<<<<<<< HEAD
     /**
     * Returns the list of past meetings in which this contact has participated. *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param contact one of the user’s contacts
     * @return the list of future meeting(s) scheduled with this contact (maybe empty).
     * @throws IllegalArgumentException if the contact does not exist*/

     public List<PastMeeting> getPastMeetingList(Contact contact)
     {
         List <PastMeeting> myPastMeetingList = new ArrayList();
         return myPastMeetingList;
     }
=======
>>>>>>> origin/master

    public FutureMeeting getFutureMeeting(int id)
    {
        Meeting myMeeting = getMeeting(id);
        if (myMeeting == null)
        {
            return null;
        }
        else if(myMeeting.getDate().before(Calendar.getInstance()))
        {
            throw new IllegalArgumentException("This meeting happened in the past!");
        }
        return (FutureMeeting) myMeeting;
    }

    public PastMeeting getPastMeeting(int id)
    {
        if (myMeetings.isEmpty())
        {
            return null;
        }
        else {
            for (Meeting m : myMeetings) {
                if (m.getId() == id && m.getDate().before(Calendar.getInstance())) {
                    return (PastMeeting) m;
                }
                else if(m.getDate().after(Calendar.getInstance()))
                {
                    throw new IllegalArgumentException("This meeting happening in the future!");
                }
            }
        }
        return null;
    }

    public Meeting getMeeting(int id)
    {
        if (myMeetings.isEmpty())
        {
            return null;
        }
        else {
            for (Meeting m : myMeetings) {
                if (m.getId() == id) {
                    return m;
                }
            }
        }
        return null;
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
     * Add notes to a meeting.
     * This method is used when a future meeting takes place, and is
     * then converted to a past meeting (with notes). *
     * It can be also used to add notes to a past meeting at a later date. *
     * @param id the ID of the meeting
     * @param text messages to be added about the meeting.
     * @throws IllegalArgumentException if the meeting does not exist
     * @throws IllegalStateException if the meeting is set for a date in the future
     * @throws NullPointerException if the notes are null */

    public void addMeetingNotes(int id, String text) {
        Boolean idFlag = false;
        if (text.equals(null))
        {
            throw new NullPointerException("Notes can not be null");
        }else{
            for (Meeting m : myMeetings){
                if (m.getId() == id){
                    if (m.getDate().after(Calendar.getInstance())){
                        throw new IllegalStateException("Can not add notes to meeting in future");
                    }else{
                            PastMeeting myPastMeeting = new PastMeetingImpl(m.getContacts(),m.getDate(),m.getId(),text);
                            myMeetings.remove(m);
                            myMeetings.add(myPastMeeting);
                            idFlag = true;
                    }
                }

            }
            if(!idFlag){
                throw new IllegalArgumentException("There is no meeting with this id");
            }

        }
    }
    public Set<Contact> getContacts(int... ids){

        Set<Contact> myMatchingContacts = new HashSet<Contact>();
        Set<Integer> myContactManagerIds = new HashSet<Integer>();
        Set<Integer> myQueryIds = new HashSet<Integer>();
        int ContactId;
        for (int i = 0; i < ids.length; i++) {
            myQueryIds.add(ids[i]);
        }
        for (Contact c: myContacts)
        {
            ContactId = c.getId();
            myContactManagerIds.add(ContactId);
            for (int i = 0; i < ids.length; i++) {
                if (ContactId == ids[i]) {
                    myMatchingContacts.add(c);
                }
            }
        }
        if(!myContactManagerIds.containsAll(myQueryIds))
        {
            throw new IllegalArgumentException("Contact ids provided contains non-existent contact id");
        }
        return myMatchingContacts;
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
