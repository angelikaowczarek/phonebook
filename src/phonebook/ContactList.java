package phonebook;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by angelika on 05.04.16.
 */
public class ContactList extends AbstractListModel implements Serializable {

    private ArrayList<Contact> contactList = new ArrayList<>();

    @Override
    public int getSize() {
        return contactList.size();
    }

    @Override
    public Object getElementAt(int index) {
        return (contactList.get(index).getName());
    }

    public Contact getAt(int index) {
        try {
            return (contactList.get(index));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    public void add(Contact c) {
        contactList.add(c);
        fireContentsChanged(this, 0, getSize());
    }

    public void set(int index, Contact c) {
        contactList.set(index, c);
        fireContentsChanged(this, 0, getSize());
    }

    public void remove(int index) {
        contactList.remove(index);
        fireContentsChanged(this, 0, getSize());
    }

    public void cleanList() {
        contactList.clear();
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<Contact> contactList) {
        this.contactList = contactList;
    }
}
