import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by angelika on 05.04.16.
 */
public class Contact implements Serializable {
    private String name;
    private String telNumber;
    private String address;
    private String email;
    private Sex sex;
    private Title title;
    private ArrayList<Category> category = new ArrayList<>();

    public Contact(String name, String telNumber, String address, String email, Sex sex, Title title, ArrayList<Category> category) {
        this.name = name;
        this.telNumber = telNumber;
        this.address = address;
        this.email = email;
        this.sex = sex;
        this.title = title;
        this.category = category;
    }

    public Contact() {
        this.name = "";
        this.telNumber = "";
        this.address = "";
        this.email = "";
        this.sex = null;
        this.title = Title.BRAK;
        this.category = new ArrayList<>();
    }

    public String toString() {
        String contact = name+"\t"+telNumber+"\t"+address+"\t"+email+"\t"+sex+"\t"+title;
        for (Category c :
                category) {
            contact += "\t" + c.toString();
        }
        contact += "\n";
        return contact;
    }

    public boolean categoryContains(Category c) {
        return category.contains(c);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
