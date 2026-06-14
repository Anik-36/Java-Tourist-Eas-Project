package frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import entity.Booking;
import entity.User;
import repository.BookingRepo;
import java.util.ArrayList;

public class IssueBook extends JFrame {
    private UIStyle.ModernTextField bIdT, cIdT, pIdT, dateT, amtT, modeT;
    private JTable table;
    private DefaultTableModel model;
    private BookingRepo repo = new BookingRepo();

    public IssueBook(User user) {
        super("TourEase - New Settlement Terminal");
        this.setSize(1000, 640);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);

        // Frame Layout
        this.getContentPane().setBackground(UIStyle.BACKGROUND);
        this.setLayout(new BorderLayout());

        // ================= Top Navigation Header =================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIStyle.CARD_BG);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIStyle.BORDER),
            BorderFactory.createEmptyBorder(16, 24, 16, 24)
        ));

        JLabel titleLabel = UIStyle.createLabel("Booking Settlement Terminal", UIStyle.FONT_TITLE, UIStyle.PRIMARY);
        titleLabel.setIcon(new UIStyle.BookingIcon(22, UIStyle.PRIMARY));
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
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        JLabel formTitle = UIStyle.createLabel("Transaction Settlement", UIStyle.FONT_SUBTITLE, UIStyle.TEXT_PRIMARY);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 4, 12, 4);
        formCard.add(formTitle, gbc);

        gbc.insets = new Insets(4, 4, 4, 4);

        // Booking ID
        JPanel pBid = new JPanel(new BorderLayout(0, 2));
        pBid.setOpaque(false);
        pBid.add(UIStyle.createFieldLabel("Booking ID"), BorderLayout.NORTH);
        bIdT = new UIStyle.ModernTextField("BK-" + System.currentTimeMillis() % 10000);
        pBid.add(bIdT, BorderLayout.CENTER);
        gbc.gridy = 1;
        formCard.add(pBid, gbc);

        // Client ID
        JPanel pCid = new JPanel(new BorderLayout(0, 2));
        pCid.setOpaque(false);
        pCid.add(UIStyle.createFieldLabel("Client ID"), BorderLayout.NORTH);
        cIdT = new UIStyle.ModernTextField();
        pCid.add(cIdT, BorderLayout.CENTER);
        gbc.gridy = 2;
        formCard.add(pCid, gbc);

        // Package ID
        JPanel pPid = new JPanel(new BorderLayout(0, 2));
        pPid.setOpaque(false);
        pPid.add(UIStyle.createFieldLabel("Package ID"), BorderLayout.NORTH);
        pIdT = new UIStyle.ModernTextField();
        pPid.add(pIdT, BorderLayout.CENTER);
        gbc.gridy = 3;
        formCard.add(pPid, gbc);

        // Date
        JPanel pDate = new JPanel(new BorderLayout(0, 2));
        pDate.setOpaque(false);
        pDate.add(UIStyle.createFieldLabel("Date (YYYY-MM-DD)"), BorderLayout.NORTH);
        dateT = new UIStyle.ModernTextField("2026-06-09");
        pDate.add(dateT, BorderLayout.CENTER);
        gbc.gridy = 4;
        formCard.add(pDate, gbc);

        // Pay Amount
        JPanel pAmt = new JPanel(new BorderLayout(0, 2));
        pAmt.setOpaque(false);
        pAmt.add(UIStyle.createFieldLabel("Payment Amount (BDT)"), BorderLayout.NORTH);
        amtT = new UIStyle.ModernTextField();
        pAmt.add(amtT, BorderLayout.CENTER);
        gbc.gridy = 5;
        formCard.add(pAmt, gbc);

        // Pay Method
        JPanel pMode = new JPanel(new BorderLayout(0, 2));
        pMode.setOpaque(false);
        pMode.add(UIStyle.createFieldLabel("Payment Method"), BorderLayout.NORTH);
        modeT = new UIStyle.ModernTextField("Cash");
        pMode.add(modeT, BorderLayout.CENTER);
        gbc.gridy = 6;
        formCard.add(pMode, gbc);

        // Buttons Panel (Save and Cancel)
        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        actionPanel.setOpaque(false);
        actionPanel.setPreferredSize(new Dimension(280, 100)); // Enlarge the button dimensions explicitly

        UIStyle.ModernButton confirm = new UIStyle.ModernButton("Execute Transaction");
        UIStyle.SecondaryButton cancel = new UIStyle.SecondaryButton("Cancel");
        actionPanel.add(confirm);
        actionPanel.add(cancel);

        gbc.gridy = 7;
        gbc.insets = new Insets(16, 4, 4, 4);
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        formCard.add(actionPanel, gbc);

        // Wrap Left Sidebar Form in JScrollPane to prevent disappearing on minimize/resize
        JScrollPane formScroll = new JScrollPane(formCard);
        formScroll.setBorder(null);
        formScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        formScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScroll.setOpaque(false);
        formScroll.getViewport().setOpaque(false);
        formScroll.setPreferredSize(new Dimension(340, 0));

        bodyPanel.add(formScroll, BorderLayout.WEST);

        // --- Right Workspace (Table Display) ---
        UIStyle.CardPanel tableCard = new UIStyle.CardPanel();
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        model = new DefaultTableModel(new String[] { "Booking ID", "Client Name", "Destination", "Date", "Amount (BDT)", "Status" }, 0);
        table = new JTable(model);
        UIStyle.styleTable(table);

        JScrollPane sp = new JScrollPane(table);
        UIStyle.styleScrollPane(sp);
        tableCard.add(sp, BorderLayout.CENTER);

        bodyPanel.add(tableCard, BorderLayout.CENTER);
        this.add(bodyPanel, BorderLayout.CENTER);

        // ================= Action Listeners =================
        confirm.addActionListener(e -> {
            String bId = bIdT.getText().trim();
            String cId = cIdT.getText().trim();
            String pId = pIdT.getText().trim();
            String date = dateT.getText().trim();
            String amtStr = amtT.getText().trim();
            String mode = modeT.getText().trim();

            if (bId.isEmpty() || cId.isEmpty() || pId.isEmpty() || date.isEmpty() || amtStr.isEmpty() || mode.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // DB Lookups to check if IDs exist
            boolean cExists = customerExists(cId);
            boolean pExists = packageExists(pId);

            if (!cExists && !pExists) {
                JOptionPane.showMessageDialog(this, "Customer ID and Package ID not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!cExists) {
                JOptionPane.showMessageDialog(this, "Customer not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pExists) {
                JOptionPane.showMessageDialog(this, "Package not found.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double amt;
            try {
                amt = Double.parseDouble(amtStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Payment Amount must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Booking b = new Booking(bId, cId, pId, date, "Confirmed");
            boolean success = repo.createBooking(b, amt, mode);
            if (success) {
                JOptionPane.showMessageDialog(this, "Receipt Generated Successfully", "Transaction Success", JOptionPane.INFORMATION_MESSAGE);
                new Home(user).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Transaction Aborted due to database error.", "Transaction Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        back.addActionListener(e -> {
            new Home(user).setVisible(true);
            this.dispose();
        });

        cancel.addActionListener(e -> {
            new Home(user).setVisible(true);
            this.dispose();
        });

        refresh();
    }

    private boolean customerExists(String customerId) {
        String sql = "SELECT 1 FROM customers WHERE customerId = ?";
        try (Connection conn = repository.DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, customerId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean packageExists(String packageId) {
        String sql = "SELECT 1 FROM packages WHERE packageId = ?";
        try (Connection conn = repository.DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, packageId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void refresh() {
        model.setRowCount(0);
        ArrayList<Object[]> report = repo.getBookingReport();
        for (Object[] row : report) {
            model.addRow(row);
        }
    }
}