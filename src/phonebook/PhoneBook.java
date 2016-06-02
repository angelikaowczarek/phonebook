package phonebook;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;

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
    private JMenuBar menuBar;
    private JMenu saveLoad;
    private JMenu save;
    private JMenu load;
    private JMenuItem saveToFileItem;
    private JMenuItem saveSelectedToFileItem;
    private JMenuItem loadFromFileItem;
    private JMenuItem exportToTxtItem;
    private JMenuItem exportSelectedToTxtItem;
    private JMenuItem importFromTxtItem;
    private Icon iconSaveToFile;
    private Icon iconLoadFromFile;
    private Icon iconExportToTxt;
    private Icon iconImportFromTxt;
    private JFileChooser fileChooser;
    private String userhome;
    private FileFilter fileFilter;

    public PhoneBook() {
        super("PhoneBook");
        setContentPane(rootPanel);
        pack();
        setResizable(false);
        setLocation(150, 150);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createMenuBar();
        setWarningsHidden();
        addListeners();

        userhome = System.getProperty("user.home");
        indexInContactList = new ArrayList<>();
        isTempListDisplayed = false;
        contactList = new ContactList();
        tempContactList = new ContactList();
        filterList.setModel(contactList);
        filterList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        titleComboBox.setModel(new DefaultComboBoxModel<>(Title.values()));
        female.setSelected(true);
    }

    public void createMenuBar() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        saveLoad = new JMenu("Save/Load");
        save = new JMenu("Save");
        load = new JMenu("Load");
        createMenuIcons();
        createMenuItems();
        addMnemonicsToMenu();
        addElementsToMenu();
    }

    public void createMenuItems() {
        saveToFileItem = new JMenuItem("Save to file", iconSaveToFile);
        loadFromFileItem = new JMenuItem("Load from file", iconLoadFromFile);
        exportToTxtItem = new JMenuItem("Export to .txt", iconExportToTxt);
        importFromTxtItem = new JMenuItem("Import from .txt", iconImportFromTxt);
        saveSelectedToFileItem = new JMenuItem("Save selected contacts");
        exportSelectedToTxtItem = new JMenuItem("Save selected contacts to .txt");
    }

    public void addElementsToMenu() {
        menuBar.add(saveLoad);
        saveLoad.add(save);
        saveLoad.add(load);
        save.add(saveToFileItem);
        save.add(saveSelectedToFileItem);
        save.add(exportToTxtItem);
        save.add(exportSelectedToTxtItem);
        load.add(loadFromFileItem);
        load.add(importFromTxtItem);
    }

    public void addMnemonicsToMenu() {
        save.setMnemonic(KeyEvent.VK_S);
        load.setMnemonic(KeyEvent.VK_L);
        saveToFileItem.setMnemonic(KeyEvent.VK_A);
        saveSelectedToFileItem.setMnemonic(KeyEvent.VK_V);
        loadFromFileItem.setMnemonic(KeyEvent.VK_E);
        exportToTxtItem.setMnemonic(KeyEvent.VK_O);
        exportSelectedToTxtItem.setMnemonic(KeyEvent.VK_X);
        importFromTxtItem.setMnemonic(KeyEvent.VK_I);
    }

    public void createMenuIcons() {
        iconSaveToFile = UIManager.getIcon("FileView.floppyDriveIcon");
        iconLoadFromFile = UIManager.getIcon("Tree.leafIcon");
        iconExportToTxt = UIManager.getIcon("FileChooser.listViewIcon");
        iconImportFromTxt = UIManager.getIcon("FileChooser.detailsViewIcon");
    }

    public void loadFromFile(File file) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);
            ListSelectionListener[] listSelectionListeners = filterList.getListSelectionListeners();
            for (ListSelectionListener listSelectionListener :
                    listSelectionListeners) {
                filterList.removeListSelectionListener(listSelectionListener);
            }
            filterList.clearSelection();
            filterList.removeAll();
            contactList = (ContactList) objectInputStream.readObject();
            filterList.setModel(contactList);
            filterListListener();
            System.out.println("Loading: " + file.getName() + ".");

        } catch (FileNotFoundException e) {
            System.out.println(e);
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            //e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) objectInputStream.close();
            } catch (IOException e) {
            }
            try {
                if (fileInputStream != null) fileInputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public void saveToFile(File file) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(contactList);
            System.out.println("Saving: " + file.getName() + ".");

        } catch (FileNotFoundException e) {
            System.out.println(e);
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
            //e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) objectOutputStream.close();
            } catch (IOException e) {
            }
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
            }
        }

    }

    public void saveSelectedToFile(File file) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            tempContactList.cleanList();
            int[] selectedContactsIndices = filterList.getSelectedIndices();
            for (int contactIndex :
                    selectedContactsIndices) {
                tempContactList.add(contactList.getAt(contactIndex));
            }
            objectOutputStream.writeObject(tempContactList);
            System.out.println("Saving: " + file.getName() + ".");

        } catch (FileNotFoundException e) {
            System.out.println(e);
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
            //e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) objectOutputStream.close();
            } catch (IOException e) {
            }
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
            }
        }

    }

    public void exportToTxt(File file) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("name\ttelNumber\taddress\temail\tsex\ttitle\tcategory\n");
            for (Contact contact :
                    contactList.getContactList()) {
                bufferedWriter.write(contact.toString());
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportSelectedToTxt(File file) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("name\ttelNumber\taddress\temail\tsex\ttitle\tcategory\n");

            tempContactList.cleanList();
            int[] selectedContactsIndices = filterList.getSelectedIndices();
            for (int contactIndex :
                    selectedContactsIndices) {
                tempContactList.add(contactList.getAt(contactIndex));
                bufferedWriter.write(contactList.getAt(contactIndex).toString());
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importFromTxt(File file) {
        FileReader fileReader;
        try {
            contactList.cleanList();
            fileReader = new FileReader(file.getAbsoluteFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            line = bufferedReader.readLine();
            if (checkHeaders(line) != 1) {
                System.out.println("Wrong file content (headers)");
                return;
            }

            while ((line = bufferedReader.readLine()) != null) {
                Contact contact = new Contact();
                ArrayList<Category> categories = new ArrayList<>();
                String[] properties = line.split("\t");
                contact.setName(properties[0]);
                contact.setTelNumber(properties[1]);
                contact.setAddress(properties[2]);
                contact.setEmail(properties[3]);

                if (properties[4].equals(Sex.MALE.toString()))
                    contact.setSex(Sex.MALE);
                else
                    contact.setSex(Sex.FEMALE);

                contact.setTitle(Title.BRAK.getTitle(properties[5]));
//                if ( properties[5].equals("brak")) contact.setTitle(Title.BRAK);
//                if ( properties[5].equals("mgr")) contact.setTitle(Title.MGR);
//                if ( properties[5].equals("mgr inż.")) contact.setTitle(Title.MGR_INZ);
//                if ( properties[5].equals("dr")) contact.setTitle(Title.DR);
//                if ( properties[5].equals("prof")) contact.setTitle(Title.PROF);

                for (int i = 6; i < properties.length; i++) {
                    if (properties[i].equals("FAMILY")) {
                        categories.add(Category.FAMILY);
                    }
                    if (properties[i].equals("FRIENDS")) {
                        categories.add(Category.FRIENDS);
                    }
                    if (properties[i].equals("WORK")) {
                        categories.add(Category.WORK);
                    }
                }

                contact.setCategory(categories);

                contactList.add(contact);
            }
        } catch (IOException e) {
            System.out.println("Exception while reading the file.");
            //e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception while reading the file.");
        }
    }

    public int checkHeaders(String line) {
        String[] headers = line.split("\t");
        if (!headers[0].equals("name")) return -1;
        else if (!headers[1].equals("telNumber")) return -1;
        else if (!headers[2].equals("address")) return -1;
        else if (!headers[3].equals("email")) return -1;
        else if (!headers[4].equals("sex")) return -1;
        else if (!headers[5].equals("title")) return -1;
        else if (!headers[6].equals("category")) return -1;
        return 1;
    }

    public void addListeners() {
        filterListListener();
        filterFieldListener();

        addButtonListener();
        saveButtonListener();
        deleteButtonListener();

        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = emailField.getText();
                if (!text.matches("\\w+@[a-z0-9]{1,15}\\.[a-z]{2,5}")) {
                    warningEmail.setVisible(true);
                    addButton.setEnabled(false);
                    saveButton.setEnabled(false);
                } else {
                    warningEmail.setVisible(false);
                    if (areAnyWarnings()) return;
                    addButton.setEnabled(true);
                    saveButton.setEnabled(true);
                }
            }
        });
        addressField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = addressField.getText();
                if (!text.matches("[\\w \\-,.]+")) {                      //TODO
                    warningAddress.setVisible(true);
                    addButton.setEnabled(false);
                    saveButton.setEnabled(false);
                } else {
                    warningAddress.setVisible(false);
                    if (areAnyWarnings()) return;
                    addButton.setEnabled(true);
                    saveButton.setEnabled(true);
                }
            }
        });
        telNumberField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = telNumberField.getText();
                if (!text.matches("[0-9]{9}")) {
                    warningTelNumber.setVisible(true);
                    addButton.setEnabled(false);
                    saveButton.setEnabled(false);
                } else {
                    warningTelNumber.setVisible(false);
                    if (areAnyWarnings()) return;
                    addButton.setEnabled(true);
                    saveButton.setEnabled(true);
                }
            }
        });
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = nameField.getText();
                if (!text.matches("[a-zA-Z]{1,30} [a-zA-Z ]{1,30}")) {
                    warningName.setVisible(true);
                    addButton.setEnabled(false);
                    saveButton.setEnabled(false);
                } else {
                    warningName.setVisible(false);
                    if (areAnyWarnings()) return;
                    addButton.setEnabled(true);
                    saveButton.setEnabled(true);
                }
            }
        });

        saveToFileListener();
        saveSelectedToFileListener();
        loadFromFileListener();
        exportToTxtListener();
        exportSelectedToTxtListener();
        importFromTxtListener();
    }

    public void saveToFileListener() {
        saveToFileItem.addActionListener(e -> {
            fileFilter = new FileNameExtensionFilter("SER file", "ser");
            fileChooser = new JFileChooser(userhome + "/IdeaProjects/JavaLab5/data");
            fileChooser.addChoosableFileFilter(fileFilter);
            int returnVal = fileChooser.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                saveToFile(file);
            } else {
                System.out.println("Saving command cancelled by user.");
            }
        });
    }

    public void saveSelectedToFileListener() {
        saveSelectedToFileItem.addActionListener(e -> {
            fileFilter = new FileNameExtensionFilter("SER file", "ser");
            fileChooser = new JFileChooser(userhome + "/IdeaProjects/JavaLab5/data");
            fileChooser.addChoosableFileFilter(fileFilter);
            int returnVal = fileChooser.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                saveSelectedToFile(file);
            } else {
                System.out.println("Saving command cancelled by user.");
            }
        });
    }

    public void loadFromFileListener() {
        loadFromFileItem.addActionListener(e -> {
            fileFilter = new FileNameExtensionFilter("SER file", "ser");
            fileChooser = new JFileChooser(userhome + "/IdeaProjects/JavaLab5/data");
            fileChooser.addChoosableFileFilter(fileFilter);
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                loadFromFile(file);
            } else {
                System.out.println("Open command cancelled by user.");
            }
        });
    }

    public void exportToTxtListener() {
        exportToTxtItem.addActionListener(e -> {
            fileFilter = new FileNameExtensionFilter("TXT file", "txt");
            fileChooser = new JFileChooser(userhome + "/IdeaProjects/JavaLab5/data");
            fileChooser.addChoosableFileFilter(fileFilter);
            int returnVal = fileChooser.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                exportToTxt(file);
            } else {
                System.out.println("Export command cancelled by user.");
            }
        });
    }

    public void exportSelectedToTxtListener() {
        exportSelectedToTxtItem.addActionListener(e -> {
            fileFilter = new FileNameExtensionFilter("TXT file", "txt");
            fileChooser = new JFileChooser(userhome + "/IdeaProjects/JavaLab5/data");
            fileChooser.addChoosableFileFilter(fileFilter);
            int returnVal = fileChooser.showSaveDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                exportSelectedToTxt(file);
            } else {
                System.out.println("Export command cancelled by user.");
            }
        });
    }

    public void importFromTxtListener() {
        importFromTxtItem.addActionListener(e -> {
            fileFilter = new FileNameExtensionFilter("TXT file", "txt");
            fileChooser = new JFileChooser(userhome + "/IdeaProjects/JavaLab5/data");
            fileChooser.addChoosableFileFilter(fileFilter);
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                importFromTxt(file);
            } else {
                System.out.println("Import command cancelled by user.");
            }
        });
    }

    public void filterListListener() {
        filterList.addKeyListener(this);
        filterList.addListSelectionListener(e -> {
            setWarningsHidden();
            Contact contact;
            if (isTempListDisplayed == true) {
                //contact = contactList.getAt(indexInContactList.get(filterList.getSelectedIndex()));
                try {
                    contact = tempContactList.getAt(filterList.getSelectedIndex());
                    displayContact(contact);
                } catch (Exception a) {
                }
            } else {
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

        if (filterField.getText().length() > 0) {
            tempContactList.cleanList();
            indexInContactList.clear();
            isTempListDisplayed = true;
            String nameSubstring;
            int filterFieldLength;
            for (int i = 0; i < contactList.getSize(); i++) {
                filterFieldLength = filterField.getText().length();
                if (filterFieldLength > contactList.getAt(i).getName().length())
                    continue;
                nameSubstring = contactList.getAt(i).getName().substring(0, filterFieldLength);
                if (nameSubstring.equals(filterField.getText())) {
                    tempContactList.add(contactList.getAt(i));
                    indexInContactList.add(i);
                }
            }
            filterList.updateUI();
            filterList.setModel(tempContactList);
        } else {
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
            if (areAnyWarnings()) {
                return;
            }
            setWarningsHidden();
            createNewContactFromForm();
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
        if (filterList.getSelectedIndex() == -1)
            return;
        Contact contact;

        if (isTempListDisplayed == true) {
            contact = contactList.getAt(indexInContactList.get(filterList.getSelectedIndex()));
        } else {
            contact = contactList.getAt(filterList.getSelectedIndex());
        }

        ArrayList<Category> newCategories = new ArrayList<>();

        contact.setName(nameField.getText());
        contact.setTelNumber(telNumberField.getText());
        contact.setAddress(addressField.getText());
        contact.setEmail(emailField.getText());

        if (male.isSelected())
            contact.setSex(Sex.MALE);
        else
            contact.setSex(Sex.FEMALE);

        contact.setTitle((Title) titleComboBox.getSelectedItem());

        if (familyBox.isSelected())
            newCategories.add(Category.FAMILY);
        if (friendsBox.isSelected())
            newCategories.add(Category.FRIENDS);
        if (workBox.isSelected())
            newCategories.add(Category.WORK);

        contact.setCategory(newCategories);

        if (isTempListDisplayed == true) {
            contactList.set(indexInContactList.get(filterList.getSelectedIndex()), contact);
            tempContactList.set((filterList.getSelectedIndex()), contact);
        } else {
            contactList.set(filterList.getSelectedIndex(), contact);
        }
    }

    public void createNewContactFromForm() {
        Contact contact = new Contact();
        ArrayList<Category> categories = new ArrayList<>();

        contact.setName(nameField.getText());
        contact.setTelNumber(telNumberField.getText());
        contact.setAddress(addressField.getText());
        contact.setEmail(emailField.getText());

        if (male.isSelected())
            contact.setSex(Sex.MALE);
        else if (female.isSelected())
            contact.setSex(Sex.FEMALE);
        else
            warningSex.setVisible(true);

        contact.setTitle((Title) titleComboBox.getSelectedItem());

        if (familyBox.isSelected()) {
            categories.add(Category.FAMILY);
        }
        if (friendsBox.isSelected()) {
            categories.add(Category.FRIENDS);
        }
        if (workBox.isSelected()) {
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

        if (contact.categoryContains(Category.FAMILY))
            familyBox.setSelected(true);
        else
            familyBox.setSelected(false);
        if (contact.categoryContains(Category.FRIENDS))
            friendsBox.setSelected(true);
        else
            friendsBox.setSelected(false);
        if (contact.categoryContains(Category.WORK))
            workBox.setSelected(true);
        else
            workBox.setSelected(false);

        if (contact.getSex() == Sex.FEMALE)
            female.setSelected(true);
        else
            male.setSelected(true);

        titleComboBox.setSelectedItem(contact.getTitle());
    }

    public void clearFields() {
        for (int i = 0; i < fields.length; i++)
            fields[i].setText("");
        familyBox.setSelected(false);
        friendsBox.setSelected(false);
        workBox.setSelected(false);
        female.setSelected(true);
    }

    public void setWarningsHidden() {
        warningName.setVisible(false);
        warningTelNumber.setVisible(false);
        warningAddress.setVisible(false);
        warningEmail.setVisible(false);
        warningSex.setVisible(false);
        warningTitle.setVisible(false);
        warningCategory.setVisible(false);
    }

    public boolean areAnyWarnings() {
        if (warningName.isVisible()) return true;
        else if (warningTelNumber.isVisible()) return true;
        else if (warningAddress.isVisible()) return true;
        else if (warningEmail.isVisible()) return true;
        else if (warningSex.isVisible()) return true;
        else if (warningTitle.isVisible()) return true;
        else if (warningCategory.isVisible()) return true;
        else return false;
    }

    public void deleteFromList() {
        if (contactList.getSize() == 0) {
            JOptionPane.showMessageDialog(null, "Lista jest pusta");
        } else {
            int confirmRemoving = JOptionPane.showConfirmDialog(null, "Czy na pewno usunąć wpis?", "Pytanie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirmRemoving == 0) {
                clearFields();
                contactList.remove((filterList.getSelectedIndex()));
            }
        }
    }

    public ContactList getContactList() {
        return contactList;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 127) {
            setWarningsHidden();
            deleteFromList();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
}
