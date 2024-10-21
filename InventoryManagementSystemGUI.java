import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InventoryManagementSystemGUI extends JFrame {
    private ProductCRUD productCRUD;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField idField, nameField, quantityField, priceField;

    public InventoryManagementSystemGUI() {
        productCRUD = new ProductCRUD();
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        createTable();
        createForm();
        createButtons();

        // Refresh table data
        refreshTable();

        setVisible(true);
    }

    private void createTable() {
        String[] columns = {"ID", "Name", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createForm() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        idField = new JTextField(10);
        nameField = new JTextField(10);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);

        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);

        add(formPanel, BorderLayout.NORTH);
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Product> products = productCRUD.getAllProducts();
        for (Product product : products) {
            Object[] row = {product.getId(), product.getName(), product.getQuantity(), product.getPrice()};
            tableModel.addRow(row);
        }
    }

    private void addProduct() {
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        Product product = new Product(0, name, quantity, price);
        productCRUD.addProduct(product);
        refreshTable();
        clearFields();
    }

    private void updateProduct() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        Product product = new Product(id, name, quantity, price);
        productCRUD.updateProduct(product);
        refreshTable();
        clearFields();
    }

    private void deleteProduct() {
        int id = Integer.parseInt(idField.getText());
        productCRUD.deleteProduct(id);
        refreshTable();
        clearFields();
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InventoryManagementSystemGUI();
            }
        });
    }
}