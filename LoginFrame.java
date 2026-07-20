import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class LoginFrame extends JFrame {

    private JTextField username;
    private JPasswordField password;
    private JCheckBox showPassword;
    private JCheckBox rememberMe;
    private JButton loginBtn;
    private JButton cancelBtn;

    private final Color BG_TOP       = new Color(13, 17, 38);
    private final Color BG_BOTTOM    = new Color(25, 10, 50);
    private final Color CARD_BG      = new Color(255, 255, 255, 18);
    private final Color CARD_BORDER  = new Color(255, 255, 255, 40);
    private final Color ACCENT       = new Color(120, 80, 255);
    private final Color ACCENT_HOVER = new Color(150, 110, 255);
    private final Color TEXT_PRIMARY = new Color(240, 240, 255);
    private final Color TEXT_MUTED   = new Color(160, 155, 190);
    private final Color FIELD_BG     = new Color(255, 255, 255, 12);
    private final Color FIELD_BORDER = new Color(255, 255, 255, 30);

    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 580);
        setLocationRelativeTo(null);
        setUndecorated(true);

        initComponents();
        addListeners();
        setVisible(true);
    }

    private void initComponents() {
        JLayeredPane layered = new JLayeredPane();
        layered.setPreferredSize(new Dimension(460, 580));

        // Background
        GradientPanel bgPanel = new GradientPanel();
        bgPanel.setBounds(0, 0, 460, 580);
        layered.add(bgPanel, Integer.valueOf(0));

        // Glass card
        GlassCard card = new GlassCard();
        card.setBounds(40, 70, 380, 450);
        card.setLayout(null);
        layered.add(card, Integer.valueOf(1));

        // ── TOP BAR with ✕ button ──────────────────────────────────
        // Dark top bar panel
        JPanel topBar = new JPanel(null);
        topBar.setBackground(new Color(8, 10, 25));
        topBar.setBounds(0, 0, 460, 40);
        layered.add(topBar, Integer.valueOf(3));

        // Title label in top bar
        JLabel barTitle = new JLabel("  🔒  Secure Login");
        barTitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        barTitle.setForeground(TEXT_MUTED);
        barTitle.setBounds(10, 0, 300, 40);
        topBar.add(barTitle);

        // ✕ Close button — red, top-right corner
        JButton closeBtn = new CloseButton();
        closeBtn.setBounds(420, 5, 30, 30);
        closeBtn.addActionListener(e -> System.exit(0));
        topBar.add(closeBtn);

        // ── Card content ──────────────────────────────────────────

        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("Georgia", Font.BOLD, 26));
        title.setForeground(TEXT_PRIMARY);
        title.setBounds(0, 28, 380, 36);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(title);

        JLabel subtitle = new JLabel("Sign in to continue");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setBounds(0, 66, 380, 20);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(subtitle);

        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        userLabel.setForeground(TEXT_MUTED);
        userLabel.setBounds(36, 106, 200, 16);
        card.add(userLabel);

        username = new StyledTextField();
        username.setBounds(36, 124, 308, 42);
        card.add(username);

        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        passLabel.setForeground(TEXT_MUTED);
        passLabel.setBounds(36, 178, 200, 16);
        card.add(passLabel);

        password = new StyledPasswordField();
        password.setBounds(36, 196, 308, 42);
        card.add(password);

        showPassword = new StyledCheckBox("Show Password");
        showPassword.setBounds(36, 250, 160, 26);
        card.add(showPassword);

        rememberMe = new StyledCheckBox("Remember Me");
        rememberMe.setBounds(200, 250, 150, 26);
        card.add(rememberMe);

        loginBtn = new AccentButton("Sign In");
        loginBtn.setBounds(36, 292, 308, 46);
        card.add(loginBtn);

        cancelBtn = new GhostButton("Clear Fields");
        cancelBtn.setBounds(36, 350, 308, 36);
        card.add(cancelBtn);

        // Drag to move window (from top bar)
        Point[] dragStart = {null};
        topBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { dragStart[0] = e.getPoint(); }
        });
        topBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (dragStart[0] != null) {
                    Point loc = getLocation();
                    setLocation(loc.x + e.getX() - dragStart[0].x,
                                loc.y + e.getY() - dragStart[0].y);
                }
            }
        });

        setContentPane(layered);
    }

    private void addListeners() {
        showPassword.addActionListener(e ->
            password.setEchoChar(showPassword.isSelected() ? (char) 0 : '●'));
        loginBtn.addActionListener(e -> handleLogin());
        cancelBtn.addActionListener(e -> handleCancel());
    }

    private void handleLogin() {
        String user = username.getText().trim();
        String pass = new String(password.getPassword());
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields.", "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println("Username: " + user);
        System.out.println("Remember Me: " + rememberMe.isSelected());
        JOptionPane.showMessageDialog(this,
            "Welcome back, " + user + "!", "Login Successful",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleCancel() {
        username.setText("");
        password.setText("");
        showPassword.setSelected(false);
        rememberMe.setSelected(false);
        password.setEchoChar('●');
    }

    // ── Custom Components ─────────────────────────────────────────────

    // Red ✕ close button drawn manually
    class CloseButton extends JButton {
        private boolean hovered = false;

        CloseButton() {
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
            });
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Red circle background on hover
            if (hovered) {
                g2.setColor(new Color(200, 50, 50));
                g2.fillOval(0, 0, getWidth(), getHeight());
            }

            // Draw ✕ cross manually with two lines
            g2.setColor(hovered ? Color.WHITE : new Color(180, 100, 100));
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int pad = 9;
            g2.drawLine(pad, pad, getWidth() - pad, getHeight() - pad); // \ line
            g2.drawLine(getWidth() - pad, pad, pad, getHeight() - pad); // / line

            g2.dispose();
        }
    }

    class GradientPanel extends JPanel {
        GradientPanel() { setOpaque(true); }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(0, 0, BG_TOP, getWidth(), getHeight(), BG_BOTTOM));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
            g2.setColor(ACCENT);
            g2.fillOval(-60, -60, 200, 200);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.10f));
            g2.setColor(new Color(80, 200, 255));
            g2.fillOval(300, 380, 220, 220);
        }
    }

    class GlassCard extends JPanel {
        GlassCard() { setOpaque(false); }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(CARD_BG);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 24, 24));
            g2.setColor(CARD_BORDER);
            g2.setStroke(new BasicStroke(1.2f));
            g2.draw(new RoundRectangle2D.Double(0.6, 0.6, getWidth()-1.2, getHeight()-1.2, 24, 24));
            g2.dispose();
        }
    }

    class StyledTextField extends JTextField {
        StyledTextField() {
            setOpaque(false);
            setForeground(TEXT_PRIMARY);
            setCaretColor(ACCENT);
            setFont(new Font("SansSerif", Font.PLAIN, 14));
            setBorder(new EmptyBorder(10, 14, 10, 14));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(FIELD_BG);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
            g2.setColor(isFocusOwner() ? ACCENT : FIELD_BORDER);
            g2.setStroke(new BasicStroke(isFocusOwner() ? 1.5f : 1f));
            g2.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth()-1, getHeight()-1, 10, 10));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class StyledPasswordField extends JPasswordField {
        StyledPasswordField() {
            setOpaque(false);
            setForeground(TEXT_PRIMARY);
            setCaretColor(ACCENT);
            setFont(new Font("SansSerif", Font.PLAIN, 14));
            setEchoChar('●');
            setBorder(new EmptyBorder(10, 14, 10, 14));
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(FIELD_BG);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
            g2.setColor(isFocusOwner() ? ACCENT : FIELD_BORDER);
            g2.setStroke(new BasicStroke(isFocusOwner() ? 1.5f : 1f));
            g2.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth()-1, getHeight()-1, 10, 10));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class StyledCheckBox extends JCheckBox {
        StyledCheckBox(String text) {
            super(text);
            setOpaque(false);
            setForeground(TEXT_MUTED);
            setFont(new Font("SansSerif", Font.PLAIN, 12));
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    class AccentButton extends JButton {
        private boolean hovered = false;

        AccentButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
            });
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color c1 = hovered ? ACCENT_HOVER : ACCENT;
            Color c2 = hovered ? new Color(160, 80, 255) : new Color(80, 40, 200);
            g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    class GhostButton extends JButton {
        private boolean hovered = false;

        GhostButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(TEXT_MUTED);
            setFont(new Font("SansSerif", Font.PLAIN, 13));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
            });
        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (hovered) {
                g2.setColor(new Color(255, 255, 255, 15));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                setForeground(TEXT_PRIMARY);
            } else {
                setForeground(TEXT_MUTED);
            }
            g2.setColor(FIELD_BORDER);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(new RoundRectangle2D.Double(0.5, 0.5, getWidth()-1, getHeight()-1, 10, 10));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
