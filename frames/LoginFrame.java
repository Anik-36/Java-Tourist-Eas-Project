package frames;

import javax.swing.*;
import java.awt.*;
import entity.User;
import repository.UserRepo;

public class LoginFrame extends JFrame {
    private UIStyle.ModernTextField uTxt;
    private UIStyle.ModernPasswordField pTxt;

    public LoginFrame() {
        super("TourEase - Portal Login");
        this.setSize(420, 460);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Frame Background
        this.getContentPane().setBackground(UIStyle.BACKGROUND);
        this.setLayout(new GridBagLayout());
        
        // Login Card
        UIStyle.CardPanel card = new UIStyle.CardPanel();
        card.setBackground(UIStyle.SIDEBAR_BG); // Dark theme card
        card.setPreferredSize(new Dimension(340, 360));
        card.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        // App Title
        JLabel title = UIStyle.createLabel("TourEase", UIStyle.FONT_TITLE, Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        card.add(title, gbc);
        
        // Subtitle
        JLabel subtitle = UIStyle.createLabel("Sign in to your account", UIStyle.FONT_LABEL, new Color(203, 213, 225));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        card.add(subtitle, gbc);
        
        // User Label & Field
        JPanel userPanel = new JPanel(new BorderLayout(0, 4));
        userPanel.setOpaque(false);
        JLabel userLbl = UIStyle.createFieldLabel("Username");
        userLbl.setForeground(new Color(203, 213, 225)); // Light gray label
        userPanel.add(userLbl, BorderLayout.NORTH);
        uTxt = new UIStyle.ModernTextField();
        userPanel.add(uTxt, BorderLayout.CENTER);
        gbc.gridy = 2;
        gbc.insets = new Insets(16, 8, 4, 8); // more spacing before user field
        card.add(userPanel, gbc);
        
        // Password Label & Field
        JPanel passPanel = new JPanel(new BorderLayout(0, 4));
        passPanel.setOpaque(false);
        JLabel passLbl = UIStyle.createFieldLabel("Password");
        passLbl.setForeground(new Color(203, 213, 225)); // Light gray label
        passPanel.add(passLbl, BorderLayout.NORTH);
        pTxt = new UIStyle.ModernPasswordField();
        passPanel.add(pTxt, BorderLayout.CENTER);
        gbc.gridy = 3;
        gbc.insets = new Insets(8, 8, 6, 8); // standard spacing after password field
        card.add(passPanel, gbc);
        
        // Show Password Checkbox
        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.setFont(UIStyle.FONT_LABEL);
        showPass.setForeground(Color.WHITE); // High contrast text color
        showPass.setOpaque(false);
        showPass.setFocusPainted(false);
        showPass.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 8, 12, 8);
        card.add(showPass, gbc);
        
        showPass.addActionListener(e -> {
            pTxt.setEchoChar(showPass.isSelected() ? (char) 0 : '\u2022');
        });
        
        // Login Button
        UIStyle.ModernButton btn = new UIStyle.ModernButton("Sign In");
        gbc.gridy = 5;
        gbc.insets = new Insets(8, 8, 8, 8);
        card.add(btn, gbc);
        
        // Set Default Button on root pane to capture Enter key
        this.getRootPane().setDefaultButton(btn);
        
        // Action Listener
        btn.addActionListener(ae -> {
            String username = uTxt.getText().trim();
            String password = new String(pTxt.getPassword());
            
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Username", "Login Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Password", "Login Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            User u = new UserRepo().checkLogin(username, password);
            if (u != null) {
                new Home(u).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username or Password Incorrect", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add card to frame
        this.add(card);
    }
}