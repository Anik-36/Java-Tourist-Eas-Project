package frames;

import javax.swing.*;
import java.awt.*;
import entity.User;
import repository.UserRepo;

public class PasswordChange extends JFrame {
    public PasswordChange(User user) {
        super("Security Settings");
        this.setSize(440, 340);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Frame Layout
        this.getContentPane().setBackground(UIStyle.BACKGROUND);
        this.setLayout(new GridBagLayout());

        // Card Panel
        UIStyle.CardPanel card = new UIStyle.CardPanel();
        card.setPreferredSize(new Dimension(360, 240));
        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Title
        JLabel title = UIStyle.createLabel("Security Settings", UIStyle.FONT_TITLE, UIStyle.PRIMARY);
        title.setIcon(new UIStyle.LockIcon(22, UIStyle.PRIMARY));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 8, 12, 8);
        card.add(title, gbc);

        gbc.insets = new Insets(8, 8, 8, 8);

        // Password Panel
        JPanel passPanel = new JPanel(new BorderLayout(0, 4));
        passPanel.setOpaque(false);
        passPanel.add(UIStyle.createFieldLabel("New Password"), BorderLayout.NORTH);
        UIStyle.ModernPasswordField passF = new UIStyle.ModernPasswordField();
        passPanel.add(passF, BorderLayout.CENTER);
        gbc.gridy = 1;
        card.add(passPanel, gbc);

        // Action Buttons Row
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        UIStyle.ModernButton save = new UIStyle.ModernButton("Update");
        UIStyle.SecondaryButton back = new UIStyle.SecondaryButton("Cancel");
        btnPanel.add(save);
        btnPanel.add(back);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        card.add(btnPanel, gbc);

        // Add card to center of frame
        this.add(card);

        // Action Listeners
        save.addActionListener(e -> {
            String newPassword = new String(passF.getPassword());
            if (newPassword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "New Password cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (new UserRepo().changePassword(user.getUsername(), newPassword)) {
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