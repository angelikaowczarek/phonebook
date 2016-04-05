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
}
