package frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import entity.TourPackage;
import entity.User;
import repository.PackageRepo;
import java.util.ArrayList;

public class ManagePackage extends JFrame {
    private UIStyle.ModernTextField idT, destT, prcT, durT, hIdT, tIdT;
    private JComboBox<String> catBox;
    private JTable table;
    private DefaultTableModel model;
    private PackageRepo repo = new PackageRepo();
    private User currUser;

    public ManagePackage(User user) {
        super("TourEase - Product Management");
        this.setSize(1000, 640);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true); // Ensure JFrame has standard minimize/maximize buttons
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

        JLabel titleLabel = UIStyle.createLabel("Tour Package Setup", UIStyle.FONT_TITLE, UIStyle.TEXT_PRIMARY);
        titleLabel.setIcon(new UIStyle.PackageIcon(22, UIStyle.PRIMARY));
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
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        JLabel formTitle = UIStyle.createLabel("Package Details", UIStyle.FONT_SUBTITLE, UIStyle.TEXT_PRIMARY);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 4, 12, 4);
        formCard.add(formTitle, gbc);

        gbc.insets = new Insets(4, 4, 4, 4);

        // Package ID
        JPanel pId = new JPanel(new BorderLayout(0, 2));
        pId.setOpaque(false);
        pId.add(UIStyle.createFieldLabel("Package ID"), BorderLayout.NORTH);
        idT = new UIStyle.ModernTextField();
        pId.add(idT, BorderLayout.CENTER);
        gbc.gridy = 1;
        formCard.add(pId, gbc);

        // Destination
        JPanel pDest = new JPanel(new BorderLayout(0, 2));
        pDest.setOpaque(false);
        pDest.add(UIStyle.createFieldLabel("Destination"), BorderLayout.NORTH);
        destT = new UIStyle.ModernTextField();
        pDest.add(destT, BorderLayout.CENTER);
        gbc.gridy = 2;
        formCard.add(pDest, gbc);

        // Category
        JPanel pCat = new JPanel(new BorderLayout(0, 2));
        pCat.setOpaque(false);
        pCat.add(UIStyle.createFieldLabel("Category"), BorderLayout.NORTH);
        catBox = new JComboBox<>(new String[] { "Domestic", "International" });
        catBox.setFont(UIStyle.FONT_REGULAR);
        catBox.setBackground(Color.WHITE);
        catBox.setBorder(BorderFactory.createLineBorder(UIStyle.BORDER, 1));
        pCat.add(catBox, BorderLayout.CENTER);
        gbc.gridy = 3;
        formCard.add(pCat, gbc);

        // Price
        JPanel pPrc = new JPanel(new BorderLayout(0, 2));
        pPrc.setOpaque(false);
        pPrc.add(UIStyle.createFieldLabel("Price (BDT)"), BorderLayout.NORTH);
        prcT = new UIStyle.ModernTextField();
        pPrc.add(prcT, BorderLayout.CENTER);
        gbc.gridy = 4;
        formCard.add(pPrc, gbc);

        // Duration
        JPanel pDur = new JPanel(new BorderLayout(0, 2));
        pDur.setOpaque(false);
        pDur.add(UIStyle.createFieldLabel("Duration (Days)"), BorderLayout.NORTH);
        durT = new UIStyle.ModernTextField();
        pDur.add(durT, BorderLayout.CENTER);
        gbc.gridy = 5;
        formCard.add(pDur, gbc);

        // Hotel ID
        JPanel pHid = new JPanel(new BorderLayout(0, 2));
        pHid.setOpaque(false);
        pHid.add(UIStyle.createFieldLabel("Hotel ID"), BorderLayout.NORTH);
        hIdT = new UIStyle.ModernTextField();
        pHid.add(hIdT, BorderLayout.CENTER);
        gbc.gridy = 6;
        formCard.add(pHid, gbc);

        // Trans ID
        JPanel pTid = new JPanel(new BorderLayout(0, 2));
        pTid.setOpaque(false);
        pTid.add(UIStyle.createFieldLabel("Transport ID"), BorderLayout.NORTH);
        tIdT = new UIStyle.ModernTextField();
        pTid.add(tIdT, BorderLayout.CENTER);
        gbc.gridy = 7;
        formCard.add(pTid, gbc);

        // Save Button
        UIStyle.ModernButton addB = new UIStyle.ModernButton("Add Package");
        gbc.gridy = 8;
        gbc.insets = new Insets(16, 4, 4, 4);
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        formCard.add(addB, gbc);

        bodyPanel.add(formCard, BorderLayout.WEST);

        // --- Right Workspace ---
        UIStyle.CardPanel tableCard = new UIStyle.CardPanel();
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        model = new DefaultTableModel(new String[] { "Package ID", "Destination", "Category", "Price (BDT)", "Duration" }, 0);
        table = new JTable(model);
        UIStyle.styleTable(table);

        JScrollPane sp = new JScrollPane(table);
        UIStyle.styleScrollPane(sp);
        tableCard.add(sp, BorderLayout.CENTER);

        bodyPanel.add(tableCard, BorderLayout.CENTER);
        this.add(bodyPanel, BorderLayout.CENTER);

        // ================= Action Listeners =================
        addB.addActionListener(e -> {
            String id = idT.getText().trim();
            String dest = destT.getText().trim();
            String priceStr = prcT.getText().trim();
            String dur = durT.getText().trim();
            String hotelId = hIdT.getText().trim();
            String transId = tIdT.getText().trim();
            
            if (id.isEmpty() || dest.isEmpty() || priceStr.isEmpty() || dur.isEmpty() || hotelId.isEmpty() || transId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean success = repo.addPackage(new TourPackage(id, dest, catBox.getSelectedItem().toString(),
                    price, dur, hotelId, transId));
            if (success) {
                JOptionPane.showMessageDialog(this, "Tour package added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                refresh();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add package. ID might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        back.addActionListener(e -> {
            new Home(currUser).setVisible(true);
            this.dispose();
        });
        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        ArrayList<TourPackage> res = repo.getAllPackages();
        for (TourPackage p : res)
            model.addRow(new Object[] { p.getPackageId(), p.getDestination(), p.getCategory(), p.getPrice(),
                    p.getDuration() });
    }
}