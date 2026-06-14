package frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import entity.Customer;
import entity.User;
import repository.CustomerRepo;
import java.util.ArrayList;

public class ManageCustomer extends JFrame {
    private UIStyle.ModernTextField idT, nameT, phT, emT, nidT, sTxt;
    private JTable table;
    private DefaultTableModel model;
    private CustomerRepo repo = new CustomerRepo();
    private User currUser;

    public ManageCustomer(User user) {
        super("TourEase - Client Registry");
        this.setSize(1000, 640);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.currUser = user;

        // Main Layout
        this.getContentPane().setBackground(UIStyle.BACKGROUND);
        this.setLayout(new BorderLayout());

        // ================= Top Navigation Header =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIStyle.CARD_BG);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyle.BORDER),
            BorderFactory.createEmptyBorder(16, 24, 16, 24)
        ));

        JLabel titleLabel = UIStyle.createLabel("Client Registry", UIStyle.FONT_TITLE, UIStyle.TEXT_PRIMARY);
        titleLabel.setIcon(new UIStyle.CustomerIcon(22, UIStyle.PRIMARY));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        UIStyle.SecondaryButton back = new UIStyle.SecondaryButton("Back to Dashboard", new UIStyle.BackIcon(16, UIStyle.TEXT_PRIMARY));
        headerPanel.add(back, BorderLayout.EAST);
        this.add(headerPanel, BorderLayout.NORTH);

        // ================= Main Body Panel =================
        JPanel bodyPanel = new JPanel(new BorderLayout(24, 0));
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // --- Left Sidebar Form ---
        UIStyle.CardPanel formCard = new UIStyle.CardPanel();
        formCard.setPreferredSize(new Dimension(320, 0));
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        JLabel formTitle = UIStyle.createLabel("Customer Profile Details", UIStyle.FONT_SUBTITLE, UIStyle.TEXT_PRIMARY);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 4, 16, 4);
        formCard.add(formTitle, gbc);

        gbc.insets = new Insets(6, 4, 6, 4);

        // Client ID Field
        JPanel pId = new JPanel(new BorderLayout(0, 4));
        pId.setOpaque(false);
        pId.add(UIStyle.createFieldLabel("Client ID"), BorderLayout.NORTH);
        idT = new UIStyle.ModernTextField();
        pId.add(idT, BorderLayout.CENTER);
        gbc.gridy = 1;
        formCard.add(pId, gbc);

        // Name Field
        JPanel pName = new JPanel(new BorderLayout(0, 4));
        pName.setOpaque(false);
        pName.add(UIStyle.createFieldLabel("Full Name"), BorderLayout.NORTH);
        nameT = new UIStyle.ModernTextField();
        pName.add(nameT, BorderLayout.CENTER);
        gbc.gridy = 2;
        formCard.add(pName, gbc);

        // Phone Field
        JPanel pPh = new JPanel(new BorderLayout(0, 4));
        pPh.setOpaque(false);
        pPh.add(UIStyle.createFieldLabel("Phone Number"), BorderLayout.NORTH);
        phT = new UIStyle.ModernTextField();
        pPh.add(phT, BorderLayout.CENTER);
        gbc.gridy = 3;
        formCard.add(pPh, gbc);

        // Email Field
        JPanel pEm = new JPanel(new BorderLayout(0, 4));
        pEm.setOpaque(false);
        pEm.add(UIStyle.createFieldLabel("Email Address"), BorderLayout.NORTH);
        emT = new UIStyle.ModernTextField();
        pEm.add(emT, BorderLayout.CENTER);
        gbc.gridy = 4;
        formCard.add(pEm, gbc);

        // NID Field
        JPanel pNid = new JPanel(new BorderLayout(0, 4));
        pNid.setOpaque(false);
        pNid.add(UIStyle.createFieldLabel("NID/Passport"), BorderLayout.NORTH);
        nidT = new UIStyle.ModernTextField();
        pNid.add(nidT, BorderLayout.CENTER);
        gbc.gridy = 5;
        formCard.add(pNid, gbc);

        // Numeric Inputs Real-Time Filtering
        KeyAdapter digitOnlyAdapter = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        };
        idT.addKeyListener(digitOnlyAdapter);
        phT.addKeyListener(digitOnlyAdapter);
        nidT.addKeyListener(digitOnlyAdapter);

        // Buttons Panel (Save, Update, Delete)
        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0));
        row1.setOpaque(false);
        UIStyle.ModernButton addB = new UIStyle.ModernButton("Save");
        UIStyle.ModernButton upB = new UIStyle.ModernButton("Update");
        row1.add(addB);
        row1.add(upB);

        UIStyle.SecondaryButton delB = new UIStyle.SecondaryButton("Delete Profile");
        delB.setForeground(UIStyle.DANGER);
        btnPanel.add(row1);
        btnPanel.add(delB);

        gbc.gridy = 6;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        formCard.add(btnPanel, gbc);

        bodyPanel.add(formCard, BorderLayout.WEST);

        // --- Right Workspace ---
        JPanel rightPanel = new JPanel(new BorderLayout(0, 16));
        rightPanel.setOpaque(false);

        // Search Bar Card
        UIStyle.CardPanel searchCard = new UIStyle.CardPanel();
        searchCard.setLayout(new BorderLayout(16, 0));
        searchCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIStyle.BORDER, 1, true),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));

        JLabel sLabel = UIStyle.createLabel("Search registry:", UIStyle.FONT_LABEL, UIStyle.TEXT_PRIMARY);
        searchCard.add(sLabel, BorderLayout.WEST);

        sTxt = new UIStyle.ModernTextField();
        searchCard.add(sTxt, BorderLayout.CENTER);

        UIStyle.ModernButton sBtn = new UIStyle.ModernButton("Find", new UIStyle.SearchIcon(14, Color.WHITE));
        searchCard.add(sBtn, BorderLayout.EAST);

        rightPanel.add(searchCard, BorderLayout.NORTH);

        // Table Panel Card
        UIStyle.CardPanel tableCard = new UIStyle.CardPanel();
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        model = new DefaultTableModel(new String[] { "Client ID", "Full Name", "Phone", "Email Address", "NID/Passport" }, 0);
        table = new JTable(model);
        UIStyle.styleTable(table);

        JScrollPane sp = new JScrollPane(table);
        UIStyle.styleScrollPane(sp);
        tableCard.add(sp, BorderLayout.CENTER);

        rightPanel.add(tableCard, BorderLayout.CENTER);
        bodyPanel.add(rightPanel, BorderLayout.CENTER);
        this.add(bodyPanel, BorderLayout.CENTER);

        // ================= Action Listeners =================
        addB.addActionListener(e -> {
            String id = idT.getText().trim();
            String name = nameT.getText().trim();
            String ph = phT.getText().trim();
            String em = emT.getText().trim();
            String nid = nidT.getText().trim();
            
            if (id.isEmpty() || name.isEmpty() || ph.isEmpty() || em.isEmpty() || nid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(id)) {
                JOptionPane.showMessageDialog(this, "Customer ID must contain only numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(ph)) {
                JOptionPane.showMessageDialog(this, "Phone Number must contain only numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(nid)) {
                JOptionPane.showMessageDialog(this, "NID/Passport must contain only numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean success = repo.addCustomer(new Customer(id, name, ph, em, nid));
            if (success) {
                JOptionPane.showMessageDialog(this, "Customer profile saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refresh("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save profile. ID might already exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        upB.addActionListener(e -> {
            String id = idT.getText().trim();
            String name = nameT.getText().trim();
            String ph = phT.getText().trim();
            String em = emT.getText().trim();
            String nid = nidT.getText().trim();
            
            if (id.isEmpty() || name.isEmpty() || ph.isEmpty() || em.isEmpty() || nid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(id)) {
                JOptionPane.showMessageDialog(this, "Customer ID must contain only numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(ph)) {
                JOptionPane.showMessageDialog(this, "Phone Number must contain only numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(nid)) {
                JOptionPane.showMessageDialog(this, "NID/Passport must contain only numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean success = repo.updateCustomer(new Customer(id, name, ph, em, nid));
            if (success) {
                JOptionPane.showMessageDialog(this, "Customer profile updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refresh("");
            } else {
                JOptionPane.showMessageDialog(this, "Customer ID not found. Update aborted.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        delB.addActionListener(e -> {
            String id = idT.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Customer ID is required to delete.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isNumeric(id)) {
                JOptionPane.showMessageDialog(this, "Customer ID must contain only numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this profile?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = repo.deleteCustomer(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Customer profile deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refresh("");
                } else {
                    JOptionPane.showMessageDialog(this, "Customer ID not found.", "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sBtn.addActionListener(e -> refresh(sTxt.getText()));
        back.addActionListener(e -> {
            new Home(currUser).setVisible(true);
            this.dispose();
        });

        table.getSelectionModel().addListSelectionListener(x -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                idT.setText(model.getValueAt(r, 0).toString());
                nameT.setText(model.getValueAt(r, 1).toString());
                phT.setText(model.getValueAt(r, 2).toString());
                emT.setText(model.getValueAt(r, 3).toString());
                nidT.setText(model.getValueAt(r, 4).toString());
            }
        });
        refresh("");
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    private void refresh(String k) {
        model.setRowCount(0);
        ArrayList<Customer> res = repo.searchCustomer(k);
        for (Customer c : res)
            model.addRow(
                    new Object[] { c.getCustomerId(), c.getName(), c.getPhone(), c.getEmail(), c.getNidOrPassport() });
    }
}