import java.io.*;
import java.util.*;


public class ContactManagerImpl implements ContactManager, Serializable
{
    /**
     * Collection of contacts which have been added to each Contact Manager
     * Each contact has unique ID but may have same name as other contacts
     */
    private Set<Contact> myContacts;
    /**
     * Collection of all meetings which have been added to each Contact Manager
     * Each contact has unique ID but may have same meeting date and time as well as same list of contacts attending
     */
    private Set<Meeting> myMeetings;
    /**
     * GlobalMeetingID is used to ensures uniqueness of meeting IDs
     */
    private static int GlobalMeetingID;

    /**
     * Constructor for Contact Manager
     * @param contacts initial set of contacts
     */
    public ContactManagerImpl (Set<Contact> contacts)
    {
        myContacts = new HashSet<Contact>();
        myMeetings = new HashSet<Meeting>();
        GlobalMeetingID = 1;
        myContacts.addAll(contacts);
    }

    /**
     * Support method used to ensure that all meetings that belong to this ContactManager have unique IDs as specified
     * in requirements. It is assumed that all meetings belong to only one Contact Manager. Uniqueness of meeting ID is
     * ensured only relative to all meeting belonging to Contact Manager which generated each meeting and NOT relative to
     * meetings belonging to other Contact Managers.
     * @return the unique meeting ID which is then used in creation of new meeting
     */
    public int getGlobalMeetingID()
    {
        int myGlobalMeetingID = GlobalMeetingID;
        GlobalMeetingID++;
        return myGlobalMeetingID;
    }

    /**
     * Add a new meeting to be held in the future. *
     *
     * @param contacts a list of contacts that will participate in the meeting
     * @param date     the date on which the meeting will take place
     * @return the ID for the meeting
     * @throws IllegalArgumentException if the meeting is set for a time in the past,
     * or if any contact is unknown / non-existent
     */
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
    * Returns the list of meetings that are scheduled for, or that took
    * place on the specified date
    * If there are none, the returned list will be empty. Otherwise,
    * the list will be chronologically sorted and will not contain any duplicates.
    *
    * Guidance from forum by SERGIO GUTIERREZ-SANTOS  Post 3 in reply to 1  • 15 December 2014, 5:23 PM
    * The method must return also past meetings if it is called with a past date as parameter.
    * The name is confusing, a better name would have been getMeetingList(Calendar) or even getMeetingListOn(Calendar). We apologise for the confusion.
    * The meetings must be sorted by time (meetings take place on date and at a time, e.g. on 12-Dec-2013 @ 11h00).
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
            sortMeetingList(myList);
            return myList;
    }

    /**
     * This is support method that is used in number of methods to satisfy requirement to chronologically sort meetings
     * @param myList meeting list that needs to be chronologically sorted
     */
    private void sortMeetingList(List<Meeting> myList)
    {
        Collections.sort(myList, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting m1, Meeting m2) {
                return m1.getDate().compareTo(m2.getDate());
            }
        });
    }


    /*
     * Returns the list of future meetings scheduled with this contact.
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any duplicates.
     *
     * @param contact one of the user’s contacts
     * @return the list of future meeting(s) scheduled with this contact
     * @throws IllegalArgumentException if the contact does not exist */
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
        sortMeetingList(myList);
        return myList;
    }
    /**
     * Returns the list of past meetings in which this contact has participated.
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * If meeting was originally added as future meeting sub type but has migrated to past meeting category
     * due to passage of time it is explicitly converted to past meeting sub type
     *
     * @param contact one of the user’s contacts
     * @return the list of future meeting(s) scheduled with this contact (maybe empty).
     * @throws IllegalArgumentException if the contact does not exist*/
     public List<PastMeeting> getPastMeetingList(Contact contact)
     {
         List <PastMeeting> myPastMeetingList = new ArrayList<PastMeeting>();
         PastMeeting myPastMeeting;
         int myNumberOfMeetings = myMeetings.size();
         Meeting myMeeting;
         int [] myMeetingIds;
         if(!myContacts.contains(contact))
         {
             throw new IllegalArgumentException("This contact does not exist");
         }
         myMeetingIds = getMeetingIDs();
         for (int i = 0; i < myNumberOfMeetings; i++)
         {
             myMeeting = getMeeting(myMeetingIds[i]);
             if(myMeeting.getContacts().contains(contact))
             {
                 if(myMeeting.getDate().before(Calendar.getInstance()))
                 {
                     if(myMeeting.getClass().getName().equals("FutureMeetingImpl"))
                     {
                         addMeetingNotes(myMeetingIds[i],"");
                     }
                     myPastMeeting = getPastMeeting(myMeetingIds[i]);
                     myPastMeetingList.add(myPastMeeting);
                 }
             }
         }
         //sortMeetingList(myPastMeetingList);
         Collections.sort(myPastMeetingList, new Comparator<Meeting>() {
             @Override
             public int compare(Meeting m1, Meeting m2) {
                 return m1.getDate().compareTo(m2.getDate());
             }
         });
         return myPastMeetingList;
     }

    /**
     * Support method used to extract meeting IDs of all meetings belonging to Contact Manager from where it is called
     *
     * @return return array of integers representing all meeting IDs
     */
    private int [] getMeetingIDs()
    {
        int myMeetingSize = myMeetings.size();
        int item=0;
        int [] myMeetingIDs = new int [myMeetingSize];
        for (Meeting m : myMeetings)
    {
        myMeetingIDs[item] = m.getId();
        item++;
    }
    return myMeetingIDs;
}
    /*
    * Returns the FUTURE meeting with the requested ID, or null if there is none. *
    * @param id the ID for the meeting
    * @return the meeting with the requested ID, or null if it there is none.
    * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
    */
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

    /**
     * Returns the PAST meeting with the requested ID, or null if it there is none. *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     * @throws IllegalArgumentException if there is a meeting with that ID happening in the future
     */
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

    /*
     * Returns the meeting with the requested ID, or null if it there is none.
     * *
     * @param id the ID for the meeting
     * @return the meeting with the requested ID, or null if it there is none.
     */
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

    /*
     * Create a new record for a meeting that took place in the past*
     * @param contacts a list of participants
     * @param date the date on which the meeting took place
     * @param text messages to be added about the meeting.
     * @throws IllegalArgumentException if the list of contacts is
     * empty, or any of the contacts does not exist
     * @throws NullPointerException if any of the arguments is null **/
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
     * Create a new contact with the specified name and notes. *
     * @param name the name of the contact.
     * @param notes notes to be added about the contact.
     * @throws NullPointerException if the name or the notes are null
     */
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
                            break;
                    }
                }
            }
            if(!idFlag){
                throw new IllegalArgumentException("There is no meeting with this id");
            }
        }
    }
    /*
     * Returns a list containing the contacts that corresponds to the IDs*
     * @param ids an arbitrary number of contact IDs
     * @return a list containing the contacts that correspond to the IDs.
     * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact
     * */
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
    /**
     * Save all data to disk. *
     * This method must be executed when the program is
     * closed and when/if the user requests it. */

    public void flush() {
        try
        {
            FileOutputStream fileOut = new FileOutputStream("ContactManager.ser");
            ObjectOutputStream outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(this);
            outStream.close();
            fileOut.close();
        }catch(IOException i)

        {
            i.printStackTrace();
        }
    }
}
