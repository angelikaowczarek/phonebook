package test;

import org.junit.Before;
import org.junit.Test;
import phonebook.ContactList;
import phonebook.PhoneBook;
import static org.assertj.core.api.Assertions.assertThat;  // main one
import java.io.File;
import static junit.framework.Assert.*;

public class PhoneBookTest {
    private PhoneBook pb;

    @Before
    public void setUp() throws Exception {
        pb = new PhoneBook();
    }

    @Test
    public void testShouldReturnOneWhenCorrectHeaderIsGiven() throws Exception {
        int returningValue =
                pb.checkHeaders(
                        "name\ttelNumber\taddress\temail\tsex\ttitle\tcategory");
        assertEquals(1, returningValue);
    }

    @Test
    public void testShouldReturnMinusOneWhenIncorrectHeaderIsGiven() throws Exception {
        int returningValue =
                pb.checkHeaders(
                        "incorrect header");
        assertEquals(-1, returningValue);
    }

    @Test
    public void testShouldReturnFalseWhenWarningAreHidden() throws Exception {
        // given
        pb.setWarningsHidden();

        // when
        boolean areAnyWarnings = pb.areAnyWarnings();

        // then
        assertFalse(areAnyWarnings);
    }

    @Test
    public void checkIfImportGivesWhatWasExportedBefore() throws Exception {
        // given
        ContactList tempContactList = pb.getContactList();
        pb.exportToTxt(new File("./test.txt"));

        // when
        pb.importFromTxt(new File("./test.txt"));

        // then
        assertThat(pb.getContactList()).isSameAs(tempContactList);
    }

    @Test
    public void checkIfImportGivesWhatWasExportedSelectivelyBefore() throws Exception {
        // given
        ContactList tempContactList = pb.getContactList();
        pb.exportSelectedToTxt(new File("./test.txt"));

        // when
        pb.importFromTxt(new File("./test.txt"));

        // then
        assertThat(pb.getContactList()).isSameAs(tempContactList);
    }

}