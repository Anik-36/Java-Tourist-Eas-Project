package frames;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIStyle {
    // Theme Colors
    public static final Color PRIMARY = new Color(79, 70, 229);        // Indigo 600
    public static final Color PRIMARY_HOVER = new Color(67, 56, 202);  // Indigo 700
    public static final Color PRIMARY_CLICK = new Color(55, 48, 163);  // Indigo 800
    public static final Color BACKGROUND = new Color(248, 250, 252);   // Slate 50
    public static final Color CARD_BG = Color.WHITE;
    public static final Color SIDEBAR_BG = new Color(15, 23, 42);      // Slate 900
    public static final Color SIDEBAR_HOVER = new Color(30, 41, 59);   // Slate 800
    
    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);    // Slate 900
    public static final Color TEXT_MUTED = new Color(100, 116, 139);   // Slate 500
    public static final Color TEXT_ON_DARK = new Color(241, 245, 249); // Slate 100
    
    public static final Color BORDER = new Color(226, 232, 240);       // Slate 200
    public static final Color BORDER_FOCUS = new Color(79, 70, 229);   // Indigo 600
    
    public static final Color SUCCESS = new Color(16, 185, 129);       // Emerald 500
    public static final Color DANGER = new Color(239, 68, 68);         // Red 500

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    // Apply look and feel settings
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    // Modern Primary Rounded Button
    public static class ModernButton extends JButton {
        public ModernButton(String text) {
            super(text);
            setFont(FONT_BUTTON);
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setRolloverEnabled(true);
            
            // Add MouseListener to force repaint on hover
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { repaint(); }
                @Override
                public void mouseExited(MouseEvent e) { repaint(); }
            });
        }

        public ModernButton(String text, Icon icon) {
            this(text);
            setIcon(icon);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (!isEnabled()) {
                g2.setColor(new Color(203, 213, 225)); // Disabled slate color
            } else if (getModel().isPressed()) {
                g2.setColor(PRIMARY_CLICK);
            } else if (getModel().isRollover()) {
                g2.setColor(PRIMARY_HOVER);
            } else {
                g2.setColor(PRIMARY);
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Modern Outline/Secondary Rounded Button
    public static class SecondaryButton extends JButton {
        public SecondaryButton(String text) {
            super(text);
            setFont(FONT_BUTTON);
            setForeground(TEXT_PRIMARY);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setRolloverEnabled(true);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { repaint(); }
                @Override
                public void mouseExited(MouseEvent e) { repaint(); }
            });
        }

        public SecondaryButton(String text, Icon icon) {
            this(text);
            setIcon(icon);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) {
                g2.setColor(new Color(226, 232, 240));
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(241, 245, 249));
            } else {
                g2.setColor(Color.WHITE);
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            
            g2.setColor(BORDER);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Custom Rounded Border Helper
    public static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        public RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(x + 1, y + 1, width - 2, height - 2, radius, radius);
            g2.dispose();
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 12, 8, 12);
        }
    }

    // Modern Rounded Text Field
    public static class ModernTextField extends JTextField {
        public ModernTextField() {
            this("");
        }

        public ModernTextField(String text) {
            super(text);
            setFont(FONT_REGULAR);
            setBackground(Color.WHITE);
            setForeground(TEXT_PRIMARY);
            setCaretColor(TEXT_PRIMARY);
            setOpaque(true);
            setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(BORDER, 8),
                BorderFactory.createEmptyBorder(0, 4, 0, 4)
            ));
            
            addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(BORDER_FOCUS, 8),
                        BorderFactory.createEmptyBorder(0, 4, 0, 4)
                    ));
                }
                @Override
                public void focusLost(FocusEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(BORDER, 8),
                        BorderFactory.createEmptyBorder(0, 4, 0, 4)
                    ));
                }
            });
        }
    }

    // Modern Rounded Password Field
    public static class ModernPasswordField extends JPasswordField {
        public ModernPasswordField() {
            super();
            setFont(FONT_REGULAR);
            setBackground(Color.WHITE);
            setForeground(TEXT_PRIMARY);
            setCaretColor(TEXT_PRIMARY);
            setOpaque(true);
            setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(BORDER, 8),
                BorderFactory.createEmptyBorder(0, 4, 0, 4)
            ));
            
            addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(BORDER_FOCUS, 8),
                        BorderFactory.createEmptyBorder(0, 4, 0, 4)
                    ));
                }
                @Override
                public void focusLost(FocusEvent e) {
                    setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(BORDER, 8),
                        BorderFactory.createEmptyBorder(0, 4, 0, 4)
                    ));
                }
            });
        }
    }

    // Card Panel Component
    public static class CardPanel extends JPanel {
        public CardPanel() {
            setLayout(new BorderLayout());
            setBackground(CARD_BG);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
            ));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.dispose();
        }
    }

    // Clean Custom Label Helper
    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public static JLabel createFieldLabel(String text) {
        return createLabel(text, FONT_LABEL, TEXT_MUTED);
    }

    // JTable styling helper
    public static void styleTable(JTable table) {
        table.setFont(FONT_REGULAR);
        table.setRowHeight(32);
        table.setGridColor(BORDER);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(238, 242, 255)); // Indigo 50
        table.setSelectionForeground(PRIMARY);
        
        // Header Customization
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_LABEL);
        header.setBackground(new Color(241, 245, 249)); // Slate 100
        header.setForeground(TEXT_PRIMARY);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
        
        // Use custom renderer to force background and foreground colors on all platforms
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSel, boolean hasF, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(t, value, isSel, hasF, row, col);
                label.setBackground(new Color(241, 245, 249)); // Light slate gray background
                label.setForeground(TEXT_PRIMARY); // Dark text
                label.setFont(FONT_LABEL);
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 1, BORDER),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
                ));
                label.setHorizontalAlignment(SwingConstants.LEFT);
                return label;
            }
        });
        
        // Alternating row styling
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (isSelected) {
                    c.setBackground(t.getSelectionBackground());
                    c.setForeground(t.getSelectionForeground());
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                    c.setForeground(TEXT_PRIMARY);
                }
                // Border/Padding inside cell
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        });
    }

    // ScrollPane Customization
    public static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        scrollPane.getViewport().setBackground(Color.WHITE);
    }

    // Vector Icon Classes

    public static class HomeIcon implements Icon {
        private final int size;
        private final Color color;
        public HomeIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Roof
            g2.drawLine(x + size / 2, y + 2, x + 2, y + size / 2 + 1);
            g2.drawLine(x + size / 2, y + 2, x + size - 2, y + size / 2 + 1);
            // Walls
            g2.drawRect(x + 4, y + size / 2, size - 8, size / 2 - 2);
            // Door
            g2.fillRect(x + size / 2 - 2, y + size - 5, 4, 5);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    public static class CustomerIcon implements Icon {
        private final int size;
        private final Color color;
        public CustomerIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Head
            g2.drawOval(x + size / 2 - 4, y + 2, 8, 8);
            // Body Arc
            g2.drawArc(x + 2, y + size / 2 + 2, size - 4, size / 2, 0, 180);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    public static class PackageIcon implements Icon {
        private final int size;
        private final Color color;
        public PackageIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Luggage bag body
            g2.drawRoundRect(x + 3, y + 5, size - 6, size - 8, 4, 4);
            // Handle
            g2.drawArc(x + size / 2 - 3, y + 1, 6, 6, 0, 180);
            // Wheels
            g2.fillOval(x + 5, y + size - 3, 3, 3);
            g2.fillOval(x + size - 8, y + size - 3, 3, 3);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    public static class BookingIcon implements Icon {
        private final int size;
        private final Color color;
        public BookingIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Ticket rectangle
            g2.drawRoundRect(x + 2, y + 4, size - 4, size - 8, 4, 4);
            // Ticket notches
            g2.setColor(c.getParent() != null ? c.getParent().getBackground() : BACKGROUND);
            g2.fillOval(x - 3, y + size / 2 - 3, 6, 6);
            g2.fillOval(x + size - 3, y + size / 2 - 3, 6, 6);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    public static class LedgerIcon implements Icon {
        private final int size;
        private final Color color;
        public LedgerIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Bar graph outline
            g2.drawRect(x + 1, y + 1, size - 2, size - 2);
            // Graph bars
            g2.fillRect(x + 4, y + size - 6, 3, 4);
            g2.fillRect(x + 8, y + size - 10, 3, 8);
            g2.fillRect(x + 12, y + size - 8, 3, 6);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    public static class LockIcon implements Icon {
        private final int size;
        private final Color color;
        public LockIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Lock body
            g2.drawRoundRect(x + 3, y + size / 2, size - 6, size / 2 - 1, 3, 3);
            // Shackle
            g2.drawArc(x + 5, y + 2, size - 10, size / 2, 0, 180);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    public static class LogoutIcon implements Icon {
        private final int size;
        private final Color color;
        public LogoutIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Door outline (bracket)
            g2.drawLine(x + size / 2, y + 2, x + 2, y + 2);
            g2.drawLine(x + 2, y + 2, x + 2, y + size - 2);
            g2.drawLine(x + 2, y + size - 2, x + size / 2, y + size - 2);
            // Arrow pointing out
            g2.drawLine(x + size / 3, y + size / 2, x + size - 2, y + size / 2);
            g2.drawLine(x + size - 5, y + size / 2 - 3, x + size - 2, y + size / 2);
            g2.drawLine(x + size - 5, y + size / 2 + 3, x + size - 2, y + size / 2);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    public static class SearchIcon implements Icon {
        private final int size;
        private final Color color;
        public SearchIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Glass circle
            g2.drawOval(x + 2, y + 2, size - 8, size - 8);
            // Handle
            g2.drawLine(x + size - 6, y + size - 6, x + size - 1, y + size - 1);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    public static class BackIcon implements Icon {
        private final int size;
        private final Color color;
        public BackIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            // Arrow pointing left
            g2.drawLine(x + 2, y + size / 2, x + size - 2, y + size / 2);
            g2.drawLine(x + 2, y + size / 2, x + 6, y + size / 2 - 4);
            g2.drawLine(x + 2, y + size / 2, x + 6, y + size / 2 + 4);
            g2.dispose();
        }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }
}
