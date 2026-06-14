package frames;

import javax.swing.*;
import java.awt.*;
import entity.User;
import repository.DatabaseConnection;
import java.sql.*;

public class Home extends JFrame {
    private JLabel cCount, bCount, rCount;

    public Home(User user) {
        super("TourEase - Management Dashboard");
        this.setSize(920, 620);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // ================= Sidebar Panel (Left) =================
        JPanel sidebar = new JPanel();
        sidebar.setBackground(UIStyle.SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(260, 620));
        sidebar.setLayout(new BorderLayout());

        // Sidebar Header (Branding & User Info)
        JPanel brandPanel = new JPanel(new GridBagLayout());
        brandPanel.setOpaque(false);
        brandPanel.setBorder(BorderFactory.createEmptyBorder(24, 16, 24, 16));
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.gridx = 0;
        gbcB.fill = GridBagConstraints.HORIZONTAL;
        gbcB.weightx = 1.0;

        // Brand Title with Icon
        JLabel brandTitle = UIStyle.createLabel(" TourEase", UIStyle.FONT_TITLE, Color.WHITE);
        brandTitle.setIcon(new UIStyle.HomeIcon(22, Color.WHITE));
        gbcB.gridy = 0;
        brandPanel.add(brandTitle, gbcB);

        // Separator
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setForeground(UIStyle.SIDEBAR_HOVER);
        sep.setBackground(UIStyle.SIDEBAR_HOVER);
        gbcB.gridy = 1;
        gbcB.insets = new Insets(12, 0, 12, 0);
        brandPanel.add(sep, gbcB);

        // User Info Card
        JLabel userWelcome = UIStyle.createLabel("Hello, " + user.getUsername(), UIStyle.FONT_SUBTITLE, Color.WHITE);
        JLabel userRole = UIStyle.createLabel(user.getRole().toUpperCase(), UIStyle.FONT_LABEL, UIStyle.TEXT_MUTED);
        gbcB.gridy = 2;
        gbcB.insets = new Insets(0, 0, 2, 0);
        brandPanel.add(userWelcome, gbcB);
        gbcB.gridy = 3;
        gbcB.insets = new Insets(0, 0, 0, 0);
        brandPanel.add(userRole, gbcB);

        sidebar.add(brandPanel, BorderLayout.NORTH);

        // Sidebar Menu Panel (Center)
        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        SidebarButton cBtn = new SidebarButton("Customer Management", new UIStyle.CustomerIcon(18, Color.LIGHT_GRAY));
        SidebarButton pBtn = new SidebarButton("Tour Package Setup", new UIStyle.PackageIcon(18, Color.LIGHT_GRAY));
        SidebarButton bBtn = new SidebarButton("New Booking Engine", new UIStyle.BookingIcon(18, Color.LIGHT_GRAY));
        SidebarButton rBtn = new SidebarButton("Operations Report", new UIStyle.LedgerIcon(18, Color.LIGHT_GRAY));
        SidebarButton passBtn = new SidebarButton("Change Password", new UIStyle.LockIcon(18, Color.LIGHT_GRAY));
        SidebarButton outBtn = new SidebarButton("Logout", new UIStyle.LogoutIcon(18, Color.LIGHT_GRAY));

        menuPanel.add(cBtn);
        menuPanel.add(Box.createVerticalStrut(6));
        menuPanel.add(pBtn);
        menuPanel.add(Box.createVerticalStrut(6));
        menuPanel.add(bBtn);
        menuPanel.add(Box.createVerticalStrut(6));
        menuPanel.add(rBtn);
        menuPanel.add(Box.createVerticalStrut(6));
        menuPanel.add(passBtn);
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(outBtn);

        sidebar.add(menuPanel, BorderLayout.CENTER);
        this.add(sidebar, BorderLayout.WEST);

        // ================= Content Panel (Right) =================
        JPanel contentDesk = new JPanel(new BorderLayout());
        contentDesk.setBackground(UIStyle.BACKGROUND);
        contentDesk.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        // Content Header
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        headerPanel.setOpaque(false);
        JLabel pageTitle = UIStyle.createLabel("Dashboard Overview", UIStyle.FONT_TITLE, UIStyle.TEXT_PRIMARY);
        JLabel pageSub = UIStyle.createLabel("Welcome to the TourEase Operations Desk", UIStyle.FONT_LABEL, UIStyle.TEXT_MUTED);
        headerPanel.add(pageTitle);
        headerPanel.add(pageSub);
        contentDesk.add(headerPanel, BorderLayout.NORTH);

        // Dashboard Metrics Container
        JPanel bodyPanel = new JPanel(new BorderLayout(0, 24));
        bodyPanel.setOpaque(false);
        bodyPanel.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0));

        // Metric KPI Cards grid (3 columns)
        JPanel kpiGrid = new JPanel(new GridLayout(1, 3, 20, 0));
        kpiGrid.setOpaque(false);

        cCount = new JLabel("0");
        bCount = new JLabel("0");
        rCount = new JLabel("0.00 BDT");

        kpiGrid.add(createMetricCard("TOTAL CLIENTS", cCount, new UIStyle.CustomerIcon(24, UIStyle.PRIMARY)));
        kpiGrid.add(createMetricCard("BOOKINGS", bCount, new UIStyle.BookingIcon(24, UIStyle.PRIMARY)));
        kpiGrid.add(createMetricCard("TOTAL REVENUE", rCount, new UIStyle.LedgerIcon(24, UIStyle.PRIMARY)));

        bodyPanel.add(kpiGrid, BorderLayout.NORTH);

        // Welcome / Illustration Card at the bottom
        UIStyle.CardPanel welcomeCard = new UIStyle.CardPanel();
        welcomeCard.setLayout(new GridBagLayout());
        GridBagConstraints gbcWC = new GridBagConstraints();
        gbcWC.gridx = 0;
        gbcWC.insets = new Insets(4, 4, 4, 4);

        JLabel welcomeTitle = UIStyle.createLabel("TourEase Management System", UIStyle.FONT_SUBTITLE, UIStyle.TEXT_PRIMARY);
        gbcWC.gridy = 0;
        welcomeCard.add(welcomeTitle, gbcWC);

        JLabel welcomeDesc = UIStyle.createLabel("Select an option from the sidebar to manage customers, tour packages, booking requests, or view sales ledger reports.", UIStyle.FONT_REGULAR, UIStyle.TEXT_MUTED);
        welcomeDesc.setHorizontalAlignment(SwingConstants.CENTER);
        gbcWC.gridy = 1;
        gbcWC.insets = new Insets(8, 20, 8, 20);
        welcomeCard.add(welcomeDesc, gbcWC);

        bodyPanel.add(welcomeCard, BorderLayout.CENTER);
        contentDesk.add(bodyPanel, BorderLayout.CENTER);
        this.add(contentDesk, BorderLayout.CENTER);

        // ================= Action Listeners =================
        cBtn.addActionListener(e -> {
            new ManageCustomer(user).setVisible(true);
            this.dispose();
        });
        pBtn.addActionListener(e -> {
            new ManagePackage(user).setVisible(true);
            this.dispose();
        });
        bBtn.addActionListener(e -> {
            new IssueBook(user).setVisible(true);
            this.dispose();
        });
        rBtn.addActionListener(e -> {
            new BookList(user).setVisible(true);
            this.dispose();
        });
        passBtn.addActionListener(e -> {
            new PasswordChange(user).setVisible(true);
            this.dispose();
        });
        outBtn.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        calculateMetrics();
    }

    private JPanel createMetricCard(String title, JLabel countLabel, Icon icon) {
        UIStyle.CardPanel card = new UIStyle.CardPanel();
        card.setLayout(new BorderLayout(16, 8));

        // Icon Circle Panel
        JPanel iconPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(238, 242, 255)); // Indigo 50 background
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(50, 50));
        
        JLabel iconHolder = new JLabel(icon);
        iconHolder.setHorizontalAlignment(SwingConstants.CENTER);
        iconPanel.add(iconHolder);

        card.add(iconPanel, BorderLayout.WEST);

        // Details Panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        infoPanel.setOpaque(false);

        JLabel titleLabel = UIStyle.createLabel(title, UIStyle.FONT_LABEL, UIStyle.TEXT_MUTED);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        countLabel.setForeground(UIStyle.TEXT_PRIMARY);

        infoPanel.add(titleLabel);
        infoPanel.add(countLabel);

        card.add(infoPanel, BorderLayout.CENTER);
        return card;
    }

    private void calculateMetrics() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) FROM customers");
            if (r.next())
                cCount.setText(r.getString(1));
            r = s.executeQuery("SELECT COUNT(*) FROM bookings");
            if (r.next())
                bCount.setText(r.getString(1));
            r = s.executeQuery("SELECT SUM(amountPaid) FROM payments");
            if (r.next())
                rCount.setText(r.getDouble(1) + " BDT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sidebar navigation button subclass helper
    private static class SidebarButton extends JButton {
        public SidebarButton(String text, Icon icon) {
            super(text, icon);
            setFont(UIStyle.FONT_BUTTON);
            setForeground(new Color(203, 213, 225)); // Slate 300
            setHorizontalAlignment(SwingConstants.LEFT);
            setIconTextGap(12);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setRolloverEnabled(true);
            
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    setForeground(Color.WHITE);
                    repaint();
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    setForeground(new Color(203, 213, 225));
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isRollover()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIStyle.SIDEBAR_HOVER);
                g2.fillRoundRect(4, 2, getWidth() - 8, getHeight() - 4, 8, 8);
                g2.dispose();
            }
            super.paintComponent(g);
        }
    }
}