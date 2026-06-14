package frames;

import javax.swing.*;
import java.awt.*;
import entity.User;
import repository.UserRepo;

public class PasswordChange extends JFrame {
    public PasswordChange(User user) {
        super("Security Settings");
        this.setSize(500, 520);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Frame Layout
        this.getContentPane().setBackground(UIStyle.BACKGROUND);
        this.setLayout(new GridBagLayout());

        // Card Panel
        UIStyle.CardPanel card = new UIStyle.CardPanel();
        card.setPreferredSize(new Dimension(420, 420));
        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Title
        JLabel title = UIStyle.createLabel("Security Settings", UIStyle.FONT_TITLE, UIStyle.PRIMARY);
        title.setIcon(new UIStyle.LockIcon(22, UIStyle.PRIMARY));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 12, 16, 12);
        card.add(title, gbc);

        gbc.insets = new Insets(6, 12, 6, 12);

        // 1. Current Password Field
        JPanel pCurr = new JPanel(new BorderLayout(0, 2));
        pCurr.setOpaque(false);
        pCurr.add(UIStyle.createFieldLabel("Current Password"), BorderLayout.NORTH);
        
        JPanel pCurrInput = new JPanel(new BorderLayout(8, 0));
        pCurrInput.setOpaque(false);
        UIStyle.ModernPasswordField currPassF = new UIStyle.ModernPasswordField();
        JCheckBox chkCurr = new JCheckBox("Show");
        chkCurr.setFont(UIStyle.FONT_LABEL);
        chkCurr.setForeground(UIStyle.TEXT_MUTED);
        chkCurr.setOpaque(false);
        chkCurr.setFocusPainted(false);
        chkCurr.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkCurr.addActionListener(e -> currPassF.setEchoChar(chkCurr.isSelected() ? (char) 0 : '\u2022'));
        
        pCurrInput.add(currPassF, BorderLayout.CENTER);
        pCurrInput.add(chkCurr, BorderLayout.EAST);
        pCurr.add(pCurrInput, BorderLayout.CENTER);
        
        gbc.gridy = 1;
        card.add(pCurr, gbc);

        // 2. New Password Field
        JPanel pNew = new JPanel(new BorderLayout(0, 2));
        pNew.setOpaque(false);
        pNew.add(UIStyle.createFieldLabel("New Password"), BorderLayout.NORTH);
        
        JPanel pNewInput = new JPanel(new BorderLayout(8, 0));
        pNewInput.setOpaque(false);
        UIStyle.ModernPasswordField newPassF = new UIStyle.ModernPasswordField();
        JCheckBox chkNew = new JCheckBox("Show");
        chkNew.setFont(UIStyle.FONT_LABEL);
        chkNew.setForeground(UIStyle.TEXT_MUTED);
        chkNew.setOpaque(false);
        chkNew.setFocusPainted(false);
        chkNew.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkNew.addActionListener(e -> newPassF.setEchoChar(chkNew.isSelected() ? (char) 0 : '\u2022'));
        
        pNewInput.add(newPassF, BorderLayout.CENTER);
        pNewInput.add(chkNew, BorderLayout.EAST);
        pNew.add(pNewInput, BorderLayout.CENTER);
        
        gbc.gridy = 2;
        card.add(pNew, gbc);

        // 3. Confirm Password Field
        JPanel pConf = new JPanel(new BorderLayout(0, 2));
        pConf.setOpaque(false);
        pConf.add(UIStyle.createFieldLabel("Confirm Password"), BorderLayout.NORTH);
        
        JPanel pConfInput = new JPanel(new BorderLayout(8, 0));
        pConfInput.setOpaque(false);
        UIStyle.ModernPasswordField confPassF = new UIStyle.ModernPasswordField();
        JCheckBox chkConf = new JCheckBox("Show");
        chkConf.setFont(UIStyle.FONT_LABEL);
        chkConf.setForeground(UIStyle.TEXT_MUTED);
        chkConf.setOpaque(false);
        chkConf.setFocusPainted(false);
        chkConf.setCursor(new Cursor(Cursor.HAND_CURSOR));
        chkConf.addActionListener(e -> confPassF.setEchoChar(chkConf.isSelected() ? (char) 0 : '\u2022'));
        
        pConfInput.add(confPassF, BorderLayout.CENTER);
        pConfInput.add(chkConf, BorderLayout.EAST);
        pConf.add(pConfInput, BorderLayout.CENTER);
        
        gbc.gridy = 3;
        card.add(pConf, gbc);

        // Action Buttons Row
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        UIStyle.ModernButton save = new UIStyle.ModernButton("Update");
        UIStyle.SecondaryButton back = new UIStyle.SecondaryButton("Cancel");
        btnPanel.add(save);
        btnPanel.add(back);

        gbc.gridy = 4;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        card.add(btnPanel, gbc);

        // Add card to center of frame
        this.add(card);

        // Action Listeners
        save.addActionListener(e -> {
            String currPass = new String(currPassF.getPassword()).trim();
            String newPass = new String(newPassF.getPassword()).trim();
            String confPass = new String(confPassF.getPassword()).trim();
            
            if (currPass.isEmpty() || newPass.isEmpty() || confPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All password fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate current password (attempts log in query to verify)
            User u = new UserRepo().checkLogin(user.getUsername(), currPass);
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Incorrect Current Password.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate confirm password matches new password
            if (!newPass.equals(confPass)) {
                JOptionPane.showMessageDialog(this, "New Password and Confirm Password do not match.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Execute update
            if (new UserRepo().changePassword(user.getUsername(), newPass)) {
                JOptionPane.showMessageDialog(this, "Password Updated Successfully. You will now be logged out.", "Security Update", JOptionPane.INFORMATION_MESSAGE);
                new LoginFrame().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to Update Password", "Security Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        back.addActionListener(e -> {
            new Home(user).setVisible(true);
            this.dispose();
        });
    }
}