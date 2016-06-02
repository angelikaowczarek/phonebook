package phonebook;

/**
 * Created by angelika on 05.04.16.
 */
public enum Title {
    BRAK("brak"), MGR("mgr"), MGR_INZ("mgr inż."), DR("dr"), PROF("prof.");

    private final String titleName;

    Title(String titleName) {
        this.titleName = titleName;
    }

    public String toString() {
        return titleName;
    }

    public Title getTitle(String title) {
        if (title.equals("brak")) return Title.BRAK;
        if (title.equals("mgr")) return Title.MGR;
        if (title.equals("mgr inż.")) return Title.MGR_INZ;
        if (title.equals("dr")) return Title.DR;
        if (title.equals("prof")) return Title.PROF;
        return null;
    }

}
