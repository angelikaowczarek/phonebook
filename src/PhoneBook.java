import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by angelika on 04.04.16.
 */
public class PhoneBook extends JFrame implements FocusListener, KeyListener {
    private JPanel rootPanel;
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;

    private JTextField filterField;
    private JLabel filter;
    private JList filterList;

    private JPanel infoPanel;
    private JPanel fieldsPanel;
    private JPanel fieldsNamesPanel;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField telNumberField;
    private JTextField nameField;
    private JComboBox titleComboBox;
    private JRadioButton male;
    private JRadioButton female;
    private JCheckBox familyBox;
    private JCheckBox friendsBox;
    private JCheckBox workBox;

    private JLabel name;
    private JLabel telNumber;
    private JLabel address;
    private JLabel email;
    private JLabel title;
    private JLabel sex;
    private JLabel category;

    private JPanel innerPanel;
    private JPanel listAndFilterPanel;
    private JPanel dataPanel;
    private JPanel listPanel;
    private JPanel filterPanel;
    private JPanel buttonsPanel;

    private JLabel warningName;
    private JLabel warningTelNumber;
    private JLabel warningAddress;
    private JLabel warningEmail;
    private JLabel warningSex;
    private JLabel warningTitle;
    private JLabel warningCategory;
    private JPanel warningPanel;

    private Component currentComponent;
    private JTextField fields[] = {nameField, telNumberField, addressField, emailField};
    private ContactList contactList;
    private ContactList tempContactList;
    private boolean isTempListDisplayed;
    private ArrayList<Integer> indexInContactList;

    public PhoneBook() {
        super("PhoneBook");
        setContentPane(rootPanel);
        pack();
        setResizable(false);
        setLocation(150,150);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setWarningsHidden();
        addListeners();

        indexInContactList = new ArrayList<>();
        isTempListDisplayed = false;
        contactList = new ContactList();
        tempContactList = new ContactList();
        filterList.setModel(contactList);
        filterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        titleComboBox.setModel(new DefaultComboBoxModel<>(Title.values()));
        female.setSelected(true);
    }

    public void addListeners() {
        filterListListener();
        addButtonListener();
        saveButtonListener();
        deleteButtonListener();
        filterFieldListener();
        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                String text = emailField.getText();
                if(!text.matches("\\w+@[a-z0-9]{1,15}\\.[a-z]{2,5}")) {
                    warningEmail.setVisible(true);
                    addButton.setEnabled(false);
                    saveButton.setEnabled(false);
                }
                else {
                    warningEmail.setVisible(false);
                    addButton.setEnabled(true);
                    saveButton.setEnabled(true);
                }
            }
        });
        addressField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                String text = addressField.getText();
                if(!text.matches("[\\w \\-,]+") ) {                      //TODO
                    warningAddress.setVisible(true);
                    addButton.setEnabled(false);
                    saveButton.setEnabled(false);
                }
                else {
                    warningAddress.setVisible(false);
                    addButton.setEnabled(true);
                    saveButton.setEnabled(true);
                }
            }
        });
        telNumberField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                String text = telNumberField.getText();
                if(!text.matches("[0-9]{9}")) {
                    warningTelNumber.setVisible(true);
                    addButton.setEnabled(false);
                    saveButton.setEnabled(false);
                }
                else {
                    warningTelNumber.setVisible(false);
                    addButton.setEnabled(true);
                    saveButton.setEnabled(true);
                }
            }
        });
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                String text = nameField.getText();
                if(!text.matches("[a-zA-Z]{1,30} [a-zA-Z ]{1,30}")) {
                    warningName.setVisible(true);
                    addButton.setEnabled(false);
                    saveButton.setEnabled(false);
                }
                else {
                    warningName.setVisible(false);
                    addButton.setEnabled(true);
                    saveButton.setEnabled(true);
                }
            }
        });
    }

    public void filterListListener() {
        filterList.addKeyListener(this);
        filterList.addListSelectionListener(e -> {
            setWarningsHidden();
            Contact contact;
            if ( isTempListDisplayed == true ) {
                //contact = contactList.getAt(indexInContactList.get(filterList.getSelectedIndex()));
                try {
                    contact = tempContactList.getAt(filterList.getSelectedIndex());
                    displayContact(contact);
                }
                catch(Exception a) {}
            }
            else {
                contact = contactList.getAt(filterList.getSelectedIndex());
                displayContact(contact);
            }
        });
    }

    public void filterFieldListener() {
        filterField.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
    }

    public void newFilter() {
        //isTempListDisplayed = false;

        if(filterField.getText().length()>0) {
            tempContactList.cleanList();
            indexInContactList.clear();
            isTempListDisplayed = true;
            for(int i = 0; i < contactList.getSize();i++) {
                if (contactList.getAt(i).getName().substring(0, filterField.getText().length()).equals(filterField.getText())) {
                        tempContactList.add(contactList.getAt(i));
                        indexInContactList.add(i);
                }
            }
            filterList.setModel(tempContactList);
        }
        else {
            indexInContactList.clear();
            filterList.setModel(contactList);
            isTempListDisplayed = false;
        }
    }

    public void deleteButtonListener() {
        deleteButton.addActionListener(e -> {
            setWarningsHidden();
            deleteFromList();
        });
    }

    public void addButtonListener() {
        addButton.addActionListener(e -> {
            if (areAnyWarnings()) return;
            setWarningsHidden();
            createNewContact();
            clearFields();
        });
    }

    public void saveButtonListener() {
        saveButton.addActionListener(e -> {
            if (areAnyWarnings()) return;
            setWarningsHidden();
            updateContact();
            clearFields();
        });
    }

    public void updateContact() {
        Contact contact = (Contact)contactList.getAt(filterList.getSelectedIndex());
        ArrayList<Category> categories = contact.getCategory();
        ArrayList<Category> newCategories = new ArrayList<>();

        contact.setName(nameField.getText());
        contact.setTelNumber(telNumberField.getText());
        contact.setAddress(addressField.getText());
        contact.setEmail(emailField.getText());

        if ( male.isSelected() )
            contact.setSex(Sex.MALE);
        else
            contact.setSex(Sex.FEMALE);

        contact.setTitle((Title) titleComboBox.getSelectedItem());

        if(familyBox.isSelected())
            newCategories.add(Category.FAMILY);
        if(friendsBox.isSelected())
            newCategories.add(Category.FRIENDS);
        if(workBox.isSelected())
            newCategories.add(Category.WORK);

        contact.setCategory(newCategories);

        //dodać pomocniczą tabelę, która mówi, z jakiego indeksu pochodzi dany obiekt
        contactList.set((filterList.getSelectedIndex()), contact);
    }

    public void createNewContact() {
        Contact contact = new Contact();
        ArrayList<Category> categories = new ArrayList<>();

        contact.setName(nameField.getText());
        contact.setTelNumber(telNumberField.getText());
        contact.setAddress(addressField.getText());
        contact.setEmail(emailField.getText());

        if ( male.isSelected() )
            contact.setSex(Sex.MALE);
        else if ( female.isSelected() )
            contact.setSex(Sex.FEMALE);
        else
            warningSex.setVisible(true);

        contact.setTitle((Title) titleComboBox.getSelectedItem());

        if(familyBox.isSelected()) {
            categories.add(Category.FAMILY);
        }
        if(friendsBox.isSelected()) {
            categories.add(Category.FRIENDS);
        }
        if(workBox.isSelected()) {
            categories.add(Category.WORK);
        }

        contact.setCategory(categories);

        contactList.add(contact);
    }

    public void displayContact(Contact contact) {
        addressField.setText(contact.getAddress());
        nameField.setText(contact.getName());
        emailField.setText(contact.getEmail());
        telNumberField.setText(contact.getTelNumber());

        if ( contact.categoryContains(Category.FAMILY) )
            familyBox.setSelected(true);
        else
            familyBox.setSelected(false);
        if ( contact.categoryContains(Category.FRIENDS) )
            friendsBox.setSelected(true);
        else
            friendsBox.setSelected(false);
        if ( contact.categoryContains(Category.WORK) )
            workBox.setSelected(true);
        else
            workBox.setSelected(false);

        if ( contact.getSex() == Sex.FEMALE )
            female.setSelected(true);
        else
            male.setSelected(true);

        titleComboBox.setSelectedItem(contact.getTitle());
    }

    public void clearFields() {
        for(int i = 0 ; i < fields.length; i++)
            fields[i].setText("");
        familyBox.setSelected(false);
        friendsBox.setSelected(false);
        workBox.setSelected(false);
        female.setSelected(true);
    }

    public void setWarningsHidden () {
        warningName.setVisible(false);
        warningTelNumber.setVisible(false);
        warningAddress.setVisible(false);
        warningEmail.setVisible(false);
        warningSex.setVisible(false);
        warningTitle.setVisible(false);
        warningCategory.setVisible(false);
    }

    public boolean areAnyWarnings () {
        if ( warningName.isVisible() ) return true;
        else if ( warningTelNumber.isVisible() ) return true;
        else if ( warningAddress.isVisible() ) return true;
        else if ( warningEmail.isVisible() ) return true;
        else if ( warningSex.isVisible() ) return true;
        else if ( warningTitle.isVisible() ) return true;
        else if ( warningCategory.isVisible() ) return true;
        else return false;
    }

    public void deleteFromList() {
        if( contactList.getSize() == 0 )
        {
            JOptionPane.showMessageDialog(null, "Lista jest pusta");
        }
        else
        {
            int confirmRemoving =  JOptionPane.showConfirmDialog(null, "Czy na pewno usunąć wpis?", "Pytanie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if ( confirmRemoving == 0 ) {
                clearFields();
                contactList.remove((filterList.getSelectedIndex()));
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if ( e.getKeyCode() == 127 ) {
            setWarningsHidden();
            deleteFromList();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {}
}
