import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerForm extends JFrame {
    JTextField txtId;
    JTextField txtName;
    JTextField txtAddress;
    JTextField txtSalary;
    JButton btnSave;
    JButton btnUpdate;
    JButton btnDelete;
    DefaultTableModel tableMoedel;
    JTable customertable;

    CustomerForm() {
        setTitle("Customer Form");
        setSize(300, 400);
        setLayout(new BorderLayout());
        addInputs();
        addTable();

        setVisible(true);
    }

    private void addInputs() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // vertical layout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID"));
        txtId = new JTextField(10);
        panel.add(txtId);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // space

        txtId.addActionListener(e ->{
            filterAndSetCustomerDetails();
        });

        panel.add(new JLabel("Name"));
        txtName = new JTextField(10);
        panel.add(txtName);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // space

        panel.add(new JLabel("Address"));
        txtAddress = new JTextField(10);
        panel.add(txtAddress);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); //

        panel.add(new JLabel("Salary"));
        txtSalary = new JTextField(10);

        add(txtSalary);
        panel.add(txtSalary);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); //

        btnSave = new JButton("Save Customer");
        panel.add(btnSave);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); //

        btnSave.addActionListener(e -> {
            saveCustomer();
        });

        btnUpdate = new JButton("Update Customer");
        panel.add(btnUpdate);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); //

        btnUpdate.addActionListener(e -> {
            updateCustomer();
        });

        btnDelete = new JButton("Delete Customer");
        panel.add(btnDelete);
        panel.add(Box.createRigidArea(new Dimension(0,10)));

        btnDelete.addActionListener(e ->{
            deleteCustomer();
        });

        add(panel, BorderLayout.NORTH);
    }

    private void addTable(){
        String[] columns = {"ID", "Name", "Address", "Salary"};
        tableMoedel = new DefaultTableModel(columns, 0);
        customertable = new JTable(tableMoedel);
        JScrollPane scrollPane = new JScrollPane(customertable);
        add(scrollPane, BorderLayout.CENTER);
    }



    private void deleteCustomer(){
        for (Customer tempCustomer : Database.customerTable){
            if (txtId.getText().equals(tempCustomer.getId())){
                Database.customerTable.remove(tempCustomer);
                JOptionPane.showMessageDialog(this, "Customer deleted successfully");
                clear();
                return;
            }
        }
    }

    private void filterAndSetCustomerDetails() {
        String customerId = txtId.getText();
        clear();
        for (Customer customer: Database.customerTable){
            if (customer.getId().equals(customerId)){
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtAddress.setText(customer.getAddress());
                txtSalary.setText(String.valueOf(customer.getSalary()));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Customer Not Found!");
    }

    private void saveCustomer() {
        String customerId = txtId.getText();
        String customerName = txtName.getText();
        String customerAddress = txtAddress.getText();
        double customerSalary = Double.parseDouble(txtSalary.getText());

        Customer customer = new Customer(
                customerId,
                customerName,
                customerAddress,
                customerSalary
        );
        Database.customerTable.add(customer);
        JOptionPane.showMessageDialog(this, "Customer Saved");
        clear();
    }


    private void updateCustomer() {
        for (Customer tempCustomer : Database.customerTable) {
            if (txtId.getText().equals(tempCustomer.getId())) {
                tempCustomer.setName(txtName.getText());
                tempCustomer.setAddress(txtAddress.getText());
                tempCustomer.setSalary(Double.parseDouble(txtSalary.getText()));
                JOptionPane.showMessageDialog(this, "Customer Updated!");
                clear();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Customer Not Found");
    }

    private void clear(){
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtSalary.setText("");
    }
}