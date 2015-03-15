import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ContactManagerImpl implements ContactManager
{
    private Set<Contact> myContacts;

    public ContactManagerImpl (Set<Contact> contacts)
    {
        myContacts = new HashSet<Contact>();
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
}
