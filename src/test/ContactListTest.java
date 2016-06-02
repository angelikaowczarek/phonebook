package test;

import org.junit.Test;
import phonebook.Contact;
import phonebook.ContactList;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;  // main one

public class ContactListTest {

    @Test
    public void shouldReturnZeroWhenThereAreNoElements() throws Exception {
        // given
        ContactList emptyList = new ContactList();

        // when
        int listSize = emptyList.getSize();

        // then
        assertEquals(0, listSize);
    }

    @Test
    public void shouldReturnOneWhenThereAnElement() throws Exception {
        // given
        ContactList oneElementList = new ContactList();
        oneElementList.add(new Contact());

        // when
        int listSize = oneElementList.getSize();

        // then
        assertThat(listSize).isNotEqualTo(0);
    }

    @Test
    public void shouldReturnThreeWhenThereAreThreeElements() throws Exception {
        // given
        ContactList threeElementList = new ContactList();
        threeElementList.add(new Contact());
        threeElementList.add(new Contact());
        threeElementList.add(new Contact());

        // when
        int listSize = threeElementList.getSize();

        // then
        assertThat(listSize).isSameAs(3);
    }

    @Test
    public void shouldReturnContact1WhenAskedForIndex1() throws Exception {
        // given
        ContactList contactList = new ContactList();
        Contact contact0 = new Contact();
        Contact contact1 = new Contact();
        contactList.add(contact0);
        contactList.add(contact1);

        // when
        Contact returnedContact = contactList.getAt(1);

        // then
        assertEquals(contact1, returnedContact);
    }

    @Test
    public void shouldReturnContact1WhenAskedForIndex0AfterSetting() throws Exception {
        // given
        ContactList contactList = new ContactList();
        Contact contact0 = new Contact();
        Contact contact1 = new Contact();
        contactList.add(contact0);
        contactList.set(0, contact1);

        // when
        Contact returnedContact = contactList.getAt(0);

        // then
        assertThat(returnedContact).isEqualTo(contact1);
    }

    @Test
    public void shouldReturnContact1WhenAskedForIndex0AfterRemoving() throws Exception {
        // given
        ContactList contactList = new ContactList();
        Contact contact0 = new Contact();
        Contact contact1 = new Contact();
        contactList.add(contact0);
        contactList.add(contact1);

        // when
        contactList.remove(0);
        Contact returnedContact = contactList.getAt(0);

        // then
        assertThat(returnedContact).isSameAs(contact1);
    }

    @Test
    public void shouldReturnNullWhenAskedForNotExistingIndex() throws Exception {
        // given
        ContactList contactList = new ContactList();

        // when
        Contact returnedContact = contactList.getAt(1);

        // then
        assertNull(returnedContact);
    }

    @Test
    public void gettingContactsShouldReturnExpectedList() throws Exception {
        // given
        ContactList contactList = new ContactList();
        ArrayList<Contact> contacts = new ArrayList<>();
        contactList.setContactList(contacts);

        // when
        ArrayList<Contact> returnedContacts = contactList.getContactList();

        // then
        assertEquals(contacts, returnedContacts);
    }

    @Test
    public void gettingContactsArrayListWithNoElementsFromContactListShouldReturnAnEmptyList() throws Exception {
        // given
        ContactList contactList = new ContactList();

        // when
        ArrayList<Contact> returnedContacts = contactList.getContactList();

        // then
        assertThat(returnedContacts).isEmpty();
    }

}