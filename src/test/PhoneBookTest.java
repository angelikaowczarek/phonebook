package test;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import phonebook.PhoneBook;

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
        Assert.assertEquals(1, returningValue);
    }

    @Test
    public void testShouldReturnMinusOneWhenIncorrectHeaderIsGiven() throws Exception {
        int returningValue =
                pb.checkHeaders(
                        "incorrect header");
        Assert.assertEquals(-1, returningValue);
    }

    @Test
    public void testShouldReturnFalseWhenWarningAreHidden() throws Exception {
        // given
        pb.setWarningsHidden();

        // when
        boolean areAnyWarnings = pb.areAnyWarnings();

        // then
        Assert.assertEquals(false, areAnyWarnings);
    }
}