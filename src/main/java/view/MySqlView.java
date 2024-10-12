package view;

import controller.SyncManager;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MySqlConnection;

import model.Person;
import model.PostgressConnection;
import model.TabListener;

public class MySqlView extends JPanel implements TabListener {

    final private JTextField dpiField,
            firstNameField,
            secondNameField,
            firstSurnameField,
            secondSurnameField,
            homeAddressField,
            homePhoneField,
            mobilePhoneField,
            baseSalaryField,
            bonusField;
    final private DefaultTableModel tableModel;
    final private JButton addButton, updateButton, deleteButton, syncButton;
    List<Person> personList;
    private JTable personTable;

    private int selectedRow = -1;

    @Override
    public void onTabSelected() {
        try {
            updateTableData();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MySqlView() throws SQLException {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Configurar el espacio entre componentes
        gbc.insets = new Insets(5, 5, 5, 5);

        // Configurar los weights
        gbc.weightx = 0.05;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas y campos de texto
        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("DPI:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        dpiField = new JTextField();
        dpiField.setPreferredSize(new Dimension(150, 25));
        add(dpiField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nombre 1:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(150, 25));
        add(firstNameField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Nombre 2:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        secondNameField = new JTextField();
        secondNameField.setPreferredSize(new Dimension(150, 25));
        add(secondNameField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Apellido 1:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        firstSurnameField = new JTextField();
        firstSurnameField.setPreferredSize(new Dimension(150, 25));
        add(firstSurnameField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Apellido 2:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        secondSurnameField = new JTextField();
        secondSurnameField.setPreferredSize(new Dimension(150, 25));
        add(secondSurnameField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Dirección:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        homeAddressField = new JTextField();
        homeAddressField.setPreferredSize(new Dimension(150, 25));
        add(homeAddressField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Tel. Casa:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        homePhoneField = new JTextField();
        homePhoneField.setPreferredSize(new Dimension(150, 25));
        add(homePhoneField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Tel. Móvil:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        mobilePhoneField = new JTextField();
        mobilePhoneField.setPreferredSize(new Dimension(150, 25));
        add(mobilePhoneField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Salario Base:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        baseSalaryField = new JTextField();
        baseSalaryField.setPreferredSize(new Dimension(150, 25));
        baseSalaryField.setText("0");
        add(baseSalaryField, gbc);

        gbc.weightx = 0.05;
        gbc.gridx = 0;
        gbc.gridy = 9;
        add(new JLabel("Bonificación:"), gbc);
        gbc.weightx = 0.15;
        gbc.gridx = 1;
        bonusField = new JTextField();
        bonusField.setPreferredSize(new Dimension(150, 25));
        bonusField.setText("0");
        add(bonusField, gbc);

        // Botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Agregar botones
        addButton = new JButton("Agregar +");
        updateButton = new JButton("Actualizar ✏️");
        updateButton.setEnabled(false);
        deleteButton = new JButton("Eliminar 🗑️");
        deleteButton.setEnabled(false);

        addButton.setPreferredSize(new Dimension(150, 30));
        updateButton.setPreferredSize(new Dimension(150, 30));
        deleteButton.setPreferredSize(new Dimension(150, 30));

        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        add(buttonPanel, gbc);

        gbc.gridy++;
        gbc.weighty = 0.1;
        add(Box.createRigidArea(new Dimension(0, 10)), gbc);

        syncButton = new JButton("Sincronizar ⇆");
        gbc.weighty = 0;
        add(syncButton, gbc);

        gbc.weightx = 0.8;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 8;
        gbc.weighty = 0.9;
        gbc.gridheight = 11;
        gbc.fill = GridBagConstraints.BOTH;
        String[] columnNames = {"DPI", "Nombre 1", "Nombre 2", "Apellido 1", "Apellido 2", "Dirección", "Tel. Casa", "Tel. Móvil", "Salario Base", "Bonificación"};
        tableModel = new DefaultTableModel(columnNames, 0);
        personTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(personTable);
        add(scrollPane, gbc);

        // Listeners de los botones
        addButton.addActionListener((ActionEvent e) -> {
            try {
                addPerson();
            } catch (SQLException ex) {
                Logger.getLogger(MySqlView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        updateButton.addActionListener((ActionEvent e) -> {
            try {
                updatePerson();
            } catch (SQLException ex) {
                Logger.getLogger(MySqlView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            try {
                deletePerson();
            } catch (SQLException ex) {
                Logger.getLogger(MySqlView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        syncButton.addActionListener((ActionEvent e) -> {
            try {
                SyncManager.sync(MySqlConnection.execute(), PostgressConnection.execute());
                updateTableData();
            } catch (SQLException ex) {
                Logger.getLogger(MySqlView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        updateTableData();

        personTable.setDefaultEditor(Object.class, null);
        personTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = personTable.getSelectedRow();
                if (selectedRow >= 0) {
                    loadPersonData(selectedRow);
                    dpiField.setEditable(false);
                    addButton.setEnabled(false);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            }
        });

        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (!personTable.getBounds().contains(e.getPoint())) {

                    clearForm();
                }
            }
        });
    }

    private void updateTableData() throws SQLException {
        tableModel.setRowCount(0);

        personList = MySqlConnection.execute().getAllPersons();

        for (Person person : personList) {
            this.addPersonToTable(person);
        }
    }

    private void addPersonToTable(Person person) {
        tableModel.addRow(new Object[]{person.getDpi(), person.getFirstName(), person.getSecondName(), person.getFirstSurname(), person.getSecondSurname(), person.getHomeAddress(), person.getHomePhone(), person.getMobilePhone(), person.getBaseSalary(), person.getBonus()});
    }

    // Método para agregar personas
    private void addPerson() throws SQLException {
        Person person = createPersonFromForm();
        if (person != null) {
            personList.add(person);
            MySqlConnection.execute().createPerson(person);
            tableModel.addRow(new Object[]{person.getDpi(), person.getFirstName(), person.getSecondName(), person.getFirstSurname(), person.getSecondSurname(), person.getHomeAddress(), person.getHomePhone(), person.getMobilePhone(), person.getBaseSalary(), person.getBonus()});
            clearForm();
        }
    }
//
    // Método para actualizar personas

    private void updatePerson() throws SQLException {
        if (selectedRow >= 0) {
            Person person = createPersonFromForm();
            if (person != null) {

                MySqlConnection.execute().updatePerson(person);

                personList.set(selectedRow, person);

                tableModel.setValueAt(person.getDpi(), selectedRow, 0);
                tableModel.setValueAt(person.getFirstName(), selectedRow, 1);
                tableModel.setValueAt(person.getSecondName(), selectedRow, 2);
                tableModel.setValueAt(person.getFirstSurname(), selectedRow, 3);
                tableModel.setValueAt(person.getSecondSurname(), selectedRow, 4);
                tableModel.setValueAt(person.getHomeAddress(), selectedRow, 5);
                tableModel.setValueAt(person.getHomePhone(), selectedRow, 6);
                tableModel.setValueAt(person.getMobilePhone(), selectedRow, 7);
                tableModel.setValueAt(person.getBaseSalary(), selectedRow, 8);
                tableModel.setValueAt(person.getBonus(), selectedRow, 9);

                clearForm();
            }
        }
    }

    // Método para eliminar personas
    private void deletePerson() throws SQLException {
        if (selectedRow >= 0) {
            Person person = personList.get(selectedRow);
            personList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            MySqlConnection.execute().deletePerson(person.getDpi());
            clearForm();
            selectedRow = -1;
        }
    }

    // Método para cargar datos de la persona seleccionada en el formulario
    private void loadPersonData(int rowIndex) {
        Person person = personList.get(rowIndex);
        dpiField.setText(person.getDpi());
        firstNameField.setText(person.getFirstName());
        secondNameField.setText(person.getSecondName());
        firstSurnameField.setText(person.getFirstSurname());
        secondSurnameField.setText(person.getSecondSurname());
        homeAddressField.setText(person.getHomeAddress());
        homePhoneField.setText(person.getHomePhone());
        mobilePhoneField.setText(person.getMobilePhone());
        baseSalaryField.setText(String.valueOf(person.getBaseSalary()));
        bonusField.setText(String.valueOf(person.getBonus()));
    }

    // Método para crear un objeto Person a partir del formulario
    private Person createPersonFromForm() {
        try {
            String dpi = dpiField.getText();
            String firstName = firstNameField.getText();
            String secondName = secondNameField.getText();
            String firstSurname = firstSurnameField.getText();
            String secondSurname = secondSurnameField.getText();
            String homeAddress = homeAddressField.getText();
            String homePhone = homePhoneField.getText();
            String mobilePhone = mobilePhoneField.getText();
            float baseSalary = Float.parseFloat(baseSalaryField.getText());
            float bonus = Float.parseFloat(bonusField.getText());

            return new Person(dpi, firstName, secondName, firstSurname, secondSurname, homeAddress, homePhone, mobilePhone, baseSalary, bonus);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores válidos para Salario Base y Bonificación.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Método para limpiar el formulario
    private void clearForm() {
        dpiField.setText("");
        firstNameField.setText("");
        secondNameField.setText("");
        firstSurnameField.setText("");
        secondSurnameField.setText("");
        homeAddressField.setText("");
        homePhoneField.setText("");
        mobilePhoneField.setText("");
        baseSalaryField.setText("0");
        bonusField.setText("0");
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        dpiField.setEditable(true);
        selectedRow = -1;
        personTable.clearSelection();
    }
}
