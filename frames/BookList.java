package frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import entity.User;
import repository.BookingRepo;
import java.util.ArrayList;

public class BookList extends JFrame {
    public BookList(User user) {
        super("TourEase - Consolidated Operations Ledger");
        this.setSize(920, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

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

        JLabel titleLabel = UIStyle.createLabel("Consolidated Operations Ledger", UIStyle.FONT_TITLE, UIStyle.TEXT_PRIMARY);
        titleLabel.setIcon(new UIStyle.LedgerIcon(22, UIStyle.PRIMARY));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        UIStyle.SecondaryButton back = new UIStyle.SecondaryButton("Return to Dashboard", new UIStyle.BackIcon(16, UIStyle.TEXT_PRIMARY));
        headerPanel.add(back, BorderLayout.EAST);
        this.add(headerPanel, BorderLayout.NORTH);

        // ================= Main Body Panel =================
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        UIStyle.CardPanel tableCard = new UIStyle.CardPanel();
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        DefaultTableModel model = new DefaultTableModel(
                new String[] { "Booking ID", "Client Name", "Destination", "Date", "Collected (BDT)", "Status" }, 0);
        JTable table = new JTable(model);
        UIStyle.styleTable(table);

        JScrollPane sp = new JScrollPane(table);
        UIStyle.styleScrollPane(sp);
        tableCard.add(sp, BorderLayout.CENTER);

        bodyPanel.add(tableCard, BorderLayout.CENTER);
        this.add(bodyPanel, BorderLayout.CENTER);

        // Action Listener
        back.addActionListener(e -> {
            new Home(user).setVisible(true);
            this.dispose();
        });

        // Load Data
        ArrayList<Object[]> report = new BookingRepo().getBookingReport();
        for (Object[] row : report)
            model.addRow(row);
    }
}