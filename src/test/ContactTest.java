package test;
import org.junit.Before;
import org.junit.Test;
import phonebook.*;

import java.util.ArrayList;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;  // main one
import static org.assertj.core.api.Assertions.atIndex; // for List assertions
import static org.assertj.core.api.Assertions.tuple; // when extracting several properties at once
import static org.assertj.core.api.Assertions.fail; // use when writing exception tests
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown; // idem
import static org.assertj.core.api.Assertions.filter; // for Iterable/Array assertions
import static org.assertj.core.api.Assertions.offset; // for floating number assertions
import static org.assertj.core.api.Assertions.anyOf; // use with Condition
import static org.assertj.core.api.Assertions.contentOf; // use with File assertions

public class ContactTest {
    Contact contact;
    String name, telNumber, address, email;
    Sex sex;
    Title title;
    ArrayList<Category> category;

    @Before
    public void setUp() throws Exception {
        name = "Anna Nowak";
        telNumber = "554554664";
        address = "Gdynia";
        email = "anna@nowakzgdyni.pl";
        title = Title.DR;
        category = new ArrayList<>();
        category.add(Category.FAMILY);
        category.add(Category.FRIENDS);

        contact = new Contact(name, telNumber, address, email, sex, title, category);
    }

    @Test
    public void checkIfReturnsProperString() throws Exception {
        // given
        String expectedString =
                "Anna Nowak\t554554664\tGdynia\tanna@nowakzgdyni.pl\t"
                        + sex + "\t"
                        + title + "\t"
                        + Category.FAMILY.toString()  + "\t"
                        + Category.FRIENDS.toString() + "\n";

        // when
        String returnedString = contact.toString();

        // then
        assertThat(returnedString).isEqualTo(expectedString);
    }

//    @Test
//    public void telNumberShouldContainOnlyDigits() throws Exception {
//        assertThat(telNumber).containsOnlyDigits();
//    }

    @Test
    public void gotNameShouldBeTheSameAsGivenName() throws Exception {
        // when
        String resultName = contact.getName();

        // then
        assertThat(resultName).isSameAs(name);
    }

    @Test
    public void gotTelShouldBeTheSameAsGivenName() throws Exception {
        // when
        String resultTelNumber = contact.getTelNumber();

        // then
        assertThat(resultTelNumber).isSameAs(telNumber);
    }

//    @Test
//    public void emailShouldContainAt() throws Exception {
//        // given
//        String expectedChar = "@";
//
//        // when
//        String resultEmail = contact.getEmail();
//
//        // then
//        assertThat(resultEmail).contains(expectedChar);
//    }
//
//    @Test
//    public void telNumberShouldBeNineDigitsLong() throws Exception {
//        // given
//        int expectedNumberOfDigits = 9;
//
//        // when
//        String resultTelNumber = contact.getTelNumber();
//
//        // then
//        assertThat(resultTelNumber).hasSize(expectedNumberOfDigits);
//    }
}
