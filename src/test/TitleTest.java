package test;

import org.junit.Test;
import phonebook.Title;
import static org.junit.Assert.*;

public class TitleTest {

    @Test
    public void toStringShouldReturnStringBrakForTheEnumBRAK() throws Exception {
        // given
        Title title = Title.BRAK;

        // when
        String titleConvertedToSting = title.toString();

        // then
        assertEquals("brak", titleConvertedToSting);
    }

    @Test
    public void forGivenMgrStringShouldReturnMGREnum() throws Exception {
        // given
        String stringValueOfMGR = "mgr";
        Title title = Title.MGR;

        // when
        Title returnedTitle = title.getTitle(stringValueOfMGR);

        // then
        assertEquals(title, returnedTitle);
    }

    @Test
    public void forNotExistingTitleShouldReturnNull() throws Exception {
        // given
        String stringNotExisting = "This string does not exist in enum";

        // when
        Title returnedTitle = Title.BRAK.getTitle(stringNotExisting);

        // then
        assertNull(returnedTitle);
    }
}