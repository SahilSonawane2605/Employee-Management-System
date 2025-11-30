import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EmployeeManagementSystem extends JFrame {
    private ArrayList<Employee> employees = new ArrayList<>();
    private DefaultTableModel tableModel;

    private JTable employeeTable;
    private JTextField idField = new JTextField(5);
    private JTextField nameField = new JTextField(15);
    private JTextField deptField = new JTextField(10);
    private JTextField salaryField = new JTextField(10);

    public EmployeeManagementSystem() {
        setTitle("Employee Management System");
        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Table setup
        String[] columns = {"ID", "Name", "Department", "Salary"};
        tableModel = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Enter Employe ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Enter Employe Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Enter The Department:"));
        formPanel.add(deptField);
        formPanel.add(new JLabel("Enter Employ Salary:"));
        formPanel.add(salaryField);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);

        // Add panels to frame
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addBtn.addActionListener(e -> addEmployee());
        updateBtn.addActionListener(e -> updateEmployee());
        deleteBtn.addActionListener(e -> deleteEmployee());
        employeeTable.getSelectionModel().addListSelectionListener(e -> populateFields());

        setVisible(true);
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String dept = deptField.getText();
            double salary = Double.parseDouble(salaryField.getText());

            Employee emp = new Employee(id, name, dept, salary);
            employees.add(emp);
            tableModel.addRow(new Object[]{id, name, dept, salary});
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid data.");
        }
    }

    private void updateEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = nameField.getText();
            String dept = deptField.getText();
            double salary = Double.parseDouble(salaryField.getText());

            Employee emp = employees.get(selectedRow);
            emp.setName(name);
            emp.setDepartment(dept);
            emp.setSalary(salary);

            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(dept, selectedRow, 2);
            tableModel.setValueAt(salary, selectedRow, 3);
            clearFields();
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            employees.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            clearFields();
        }
    }

    private void populateFields() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            deptField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            salaryField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            idField.setEditable(false);
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
        salaryField.setText("");
        idField.setEditable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeManagementSystem::new);
    }
}