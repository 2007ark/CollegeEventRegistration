package com.marian;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;

public class EventRegistrationFrame extends JFrame {
    
    private JTextField nameField;
    private JTextField emailField;
    private JTextField classField;
    private JTextField deptField;
    private JComboBox<String> eventCombo;
    private JLabel countLabel;
    private JLabel statusDotLabel;
    private JButton themeToggleButton;
    private JPanel mainPanel;
    private JPanel bannerPanel;
    private JPanel formCard;
    private JLabel headerTitle;
    private JLabel headerSub;
    private JLabel nameLabel, emailLabel, classLabel, deptLabel, eventLabel;

    private boolean isDarkMode = false;

    // Theme Color Palettes
    private final Color LIGHT_BG = new Color(248, 249, 250);
    private final Color LIGHT_CARD = Color.WHITE;
    private final Color LIGHT_TEXT = new Color(52, 58, 64);
    private final Color LIGHT_BORDER = new Color(218, 224, 233);

    private final Color DARK_BG = new Color(30, 33, 36);
    private final Color DARK_CARD = new Color(40, 43, 48);
    private final Color DARK_TEXT = new Color(240, 242, 245);
    private final Color DARK_BORDER = new Color(60, 64, 67);

    public EventRegistrationFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        setTitle("Marian Engineering College - Event Registration Portal");
        setSize(520, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Container Panel
        mainPanel = new JPanel(null);
        mainPanel.setBackground(LIGHT_BG);
        add(mainPanel);

        // Top Banner Panel
        bannerPanel = new JPanel(null);
        bannerPanel.setBackground(new Color(24, 69, 101));
        bannerPanel.setBounds(0, 0, 520, 80);
        mainPanel.add(bannerPanel);

        headerTitle = new JLabel("Marian Engineering College");
        headerTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setBounds(25, 16, 300, 22);
        bannerPanel.add(headerTitle);

        headerSub = new JLabel("Event Registration & Management Portal");
        headerSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        headerSub.setForeground(new Color(174, 214, 241));
        headerSub.setBounds(25, 40, 300, 18);
        bannerPanel.add(headerSub);

        // Theme Toggle Button in Banner
        themeToggleButton = new JButton("Dark Mode");
        styleThemeToggleButton(themeToggleButton);
        themeToggleButton.setBounds(370, 24, 125, 32);
        bannerPanel.add(themeToggleButton);

        // Form Fields Container (Card Style)
        formCard = new JPanel(null);
        formCard.setBackground(LIGHT_CARD);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT_BORDER, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        formCard.setBounds(25, 100, 455, 390);
        mainPanel.add(formCard);

        int yStart = 20;
        int rowHeight = 52;

        // Student Name
        nameLabel = new JLabel("Student Name *");
        styleLabel(nameLabel);
        nameLabel.setBounds(20, yStart, 130, 25);
        formCard.add(nameLabel);

        nameField = new JTextField();
        styleTextField(nameField);
        nameField.setBounds(155, yStart, 270, 32);
        formCard.add(nameField);

        // Email Address
        yStart += rowHeight;
        emailLabel = new JLabel("Email Address *");
        styleLabel(emailLabel);
        emailLabel.setBounds(20, yStart, 130, 25);
        formCard.add(emailLabel);

        emailField = new JTextField();
        styleTextField(emailField);
        emailField.setBounds(155, yStart, 270, 32);
        formCard.add(emailField);

        // Class / Sem
        yStart += rowHeight;
        classLabel = new JLabel("Class / Sem *");
        styleLabel(classLabel);
        classLabel.setBounds(20, yStart, 130, 25);
        formCard.add(classLabel);

        classField = new JTextField();
        styleTextField(classField);
        classField.setBounds(155, yStart, 270, 32);
        formCard.add(classField);

        // Department
        yStart += rowHeight;
        deptLabel = new JLabel("Department *");
        styleLabel(deptLabel);
        deptLabel.setBounds(20, yStart, 130, 25);
        formCard.add(deptLabel);

        deptField = new JTextField();
        styleTextField(deptField);
        deptField.setBounds(155, yStart, 270, 32);
        formCard.add(deptField);

        // Event Selection
        yStart += rowHeight;
        eventLabel = new JLabel("Select Event *");
        styleLabel(eventLabel);
        eventLabel.setBounds(20, yStart, 130, 25);
        formCard.add(eventLabel);

        String[] events = {
            "Gaming Tournament", 
            "Coding Hackathon", 
            "Robotics Workshop", 
            "Ansys Workshop", 
            "Cultural Fest",
            "Other (Type Custom Event)..."
        };
        eventCombo = new JComboBox<>(events);
        eventCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        eventCombo.setBackground(LIGHT_CARD);
        eventCombo.setForeground(LIGHT_TEXT);
        eventCombo.setBounds(155, yStart, 270, 32);
        formCard.add(eventCombo);

        // Action Buttons
        JButton submitButton = new JButton("Register");
        styleButton(submitButton, new Color(40, 167, 69), new Color(33, 136, 56));
        submitButton.setBounds(155, yStart + 55, 130, 36);
        formCard.add(submitButton);

        JButton viewButton = new JButton("Records");
        styleButton(viewButton, new Color(0, 123, 255), new Color(0, 105, 217));
        viewButton.setBounds(295, yStart + 55, 130, 36);
        formCard.add(viewButton);

        // Bottom Status & Counter Bar
        countLabel = new JLabel("Total Registrations: -");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(new Color(108, 117, 125));
        countLabel.setBounds(25, 510, 330, 25);
        mainPanel.add(countLabel);

        statusDotLabel = new JLabel("Database Online");
        statusDotLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusDotLabel.setForeground(new Color(40, 167, 69));
        statusDotLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        statusDotLabel.setBounds(330, 510, 150, 25);
        mainPanel.add(statusDotLabel);

        updateRegistrationCount();

        // Theme Toggle Action Listener
        themeToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isDarkMode = !isDarkMode;
                applyTheme();
            }
        });

        // Submit Action Listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = nameField.getText().trim();
                String email = emailField.getText().trim();
                String studentClass = classField.getText().trim();
                String department = deptField.getText().trim();
                String eventName = (String) eventCombo.getSelectedItem();

                if (studentName.isEmpty()) {
                    showStyledMessage(null, "Student Name is mandatory!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    nameField.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    showStyledMessage(null, "Email address is mandatory!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    emailField.requestFocus();
                    return;
                }

                if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    showStyledMessage(null, "Please enter a valid email address format!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    emailField.requestFocus();
                    return;
                }

                if (studentClass.isEmpty()) {
                    showStyledMessage(null, "Class / Sem is mandatory!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    classField.requestFocus();
                    return;
                }

                if (department.isEmpty()) {
                    showStyledMessage(null, "Department is mandatory!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    deptField.requestFocus();
                    return;
                }

                if ("Other (Type Custom Event)...".equals(eventName)) {
                    eventName = JOptionPane.showInputDialog(null, "Enter custom event name:", "Custom Event", JOptionPane.QUESTION_MESSAGE);
                    if (eventName == null || eventName.trim().isEmpty()) {
                        return;
                    }
                    eventName = eventName.trim();
                }

                String url = "jdbc:mysql://localhost:3306/LMCST?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
                String user = "root";
                String password = "root123";

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, password);
                    
                    String query = "INSERT INTO registrations (student_name, email, class_name, department, event_name) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, studentName);
                    pstmt.setString(2, email);
                    pstmt.setString(3, studentClass);
                    pstmt.setString(4, department);
                    pstmt.setString(5, eventName);
                    
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();

                    showStyledMessage(null, "Successfully registered " + studentName + " for " + eventName + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    nameField.setText("");
                    emailField.setText("");
                    classField.setText("");
                    deptField.setText("");
                    eventCombo.setSelectedIndex(0);
                    updateRegistrationCount();

                } catch (Exception ex) {
                    showStyledMessage(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // View Records with Admin Auth Gate
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (authenticateAdmin()) {
                    openViewWindow();
                }
            }
        });
    }

    private void applyTheme() {
        Color bg = isDarkMode ? DARK_BG : LIGHT_BG;
        Color cardBg = isDarkMode ? DARK_CARD : LIGHT_CARD;
        Color textColor = isDarkMode ? DARK_TEXT : LIGHT_TEXT;
        Color borderColor = isDarkMode ? DARK_BORDER : LIGHT_BORDER;

        mainPanel.setBackground(bg);
        formCard.setBackground(cardBg);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        nameLabel.setForeground(textColor);
        emailLabel.setForeground(textColor);
        classLabel.setForeground(textColor);
        deptLabel.setForeground(textColor);
        eventLabel.setForeground(textColor);

        styleTextField(nameField);
        styleTextField(emailField);
        styleTextField(classField);
        styleTextField(deptField);

        eventCombo.setBackground(cardBg);
        eventCombo.setForeground(textColor);

        countLabel.setForeground(isDarkMode ? new Color(170, 175, 180) : new Color(108, 117, 125));

        if (isDarkMode) {
            themeToggleButton.setText("Light Mode");
            themeToggleButton.setBackground(new Color(60, 64, 67));
        } else {
            themeToggleButton.setText("Dark Mode");
            themeToggleButton.setBackground(new Color(40, 50, 70));
        }

        mainPanel.repaint();
    }

    private boolean authenticateAdmin() {
        JDialog loginDialog = new JDialog(this, "Admin Login Authentication", true);
        loginDialog.setSize(360, 220);
        loginDialog.setLocationRelativeTo(this);
        loginDialog.setLayout(null);
        loginDialog.setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, 360, 220);
        panel.setBackground(isDarkMode ? DARK_CARD : Color.WHITE);
        loginDialog.add(panel);

        JLabel userLabel = new JLabel("Username:");
        styleLabel(userLabel);
        if (isDarkMode) userLabel.setForeground(DARK_TEXT);
        userLabel.setBounds(20, 25, 100, 25);
        panel.add(userLabel);

        JTextField userField = new JTextField();
        styleTextField(userField);
        userField.setBounds(130, 25, 190, 30);
        panel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        styleLabel(passLabel);
        if (isDarkMode) passLabel.setForeground(DARK_TEXT);
        passLabel.setBounds(20, 70, 100, 25);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        styleTextField(passField);
        passField.setBounds(130, 70, 190, 30);
        panel.add(passField);

        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn, new Color(24, 69, 101), new Color(15, 45, 68));
        loginBtn.setBounds(130, 120, 190, 35);
        panel.add(loginBtn);

        final boolean[] isAuthenticated = {false};

        ActionListener performLogin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword());

                if ("admin".equals(username) && "admin123".equals(password)) {
                    isAuthenticated[0] = true;
                    loginDialog.dispose();
                } else {
                    showStyledMessage(loginDialog, "Invalid Username or Password!", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
                    passField.setText("");
                    passField.requestFocus();
                }
            }
        };

        loginBtn.addActionListener(performLogin);
        passField.addActionListener(performLogin);

        loginDialog.setVisible(true);
        return isAuthenticated[0];
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(isDarkMode ? DARK_TEXT : new Color(52, 58, 64));
    }

    private void styleTextField(JTextField tf) {
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setBackground(isDarkMode ? new Color(50, 54, 58) : Color.WHITE);
        tf.setForeground(isDarkMode ? DARK_TEXT : Color.BLACK);
        tf.setCaretColor(isDarkMode ? Color.WHITE : Color.BLACK);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(isDarkMode ? DARK_BORDER : new Color(206, 212, 218)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    private void styleButton(JButton button, Color normalColor, Color hoverColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(normalColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }

    private void styleThemeToggleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setBackground(new Color(40, 50, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(60, 75, 100));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(isDarkMode ? new Color(60, 64, 67) : new Color(40, 50, 70));
            }
        });
    }

    private void showStyledMessage(Component parent, String message, String title, int messageType) {
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 12));
        JOptionPane.showMessageDialog(parent, message, title, messageType);
    }

    private void updateRegistrationCount() {
        String url = "jdbc:mysql://localhost:3306/LMCST?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, "root", "root123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM registrations");
            if (rs.next()) {
                int total = rs.getInt(1);
                countLabel.setText("Total Registrations: " + total);
                statusDotLabel.setText("Database Online");
                statusDotLabel.setForeground(new Color(40, 167, 69));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            countLabel.setText("Total Registrations: (Offline)");
            statusDotLabel.setText("Database Offline");
            statusDotLabel.setForeground(new Color(220, 53, 69));
        }
    }

    private void openViewWindow() {
        JFrame viewFrame = new JFrame("Manage Registrations - Marian College");
        viewFrame.setSize(880, 520);
        viewFrame.setLocationRelativeTo(null);

        String[] columns = {"Student Name", "Email", "Class / Sem", "Department", "Event Name"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    if (isDarkMode) {
                        c.setBackground(row % 2 == 0 ? new Color(40, 43, 48) : new Color(48, 52, 57));
                        c.setForeground(DARK_TEXT);
                    } else {
                        c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        };

        table.setRowHeight(28);
        table.setSelectionBackground(new Color(222, 235, 247));
        table.setSelectionForeground(new Color(33, 37, 41));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(isDarkMode ? new Color(50, 54, 58) : new Color(235, 240, 245));
        table.getTableHeader().setForeground(isDarkMode ? DARK_TEXT : Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setGridColor(isDarkMode ? DARK_BORDER : new Color(230, 235, 240));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 12));
        searchPanel.setBackground(isDarkMode ? DARK_BG : LIGHT_BG);
        JLabel searchLabel = new JLabel("Search Records:");
        styleLabel(searchLabel);
        if (isDarkMode) searchLabel.setForeground(DARK_TEXT);
        JTextField searchField = new JTextField(24);
        styleTextField(searchField);
        searchField.setPreferredSize(new Dimension(250, 32));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        bottomPanel.setBackground(isDarkMode ? DARK_BG : LIGHT_BG);
        
        JButton editButton = new JButton("Edit Selected");
        styleButton(editButton, new Color(23, 162, 184), new Color(13, 138, 158));
        editButton.setPreferredSize(new Dimension(135, 34));

        JButton deleteButton = new JButton("Delete Selected");
        styleButton(deleteButton, new Color(220, 53, 69), new Color(200, 35, 51));
        deleteButton.setPreferredSize(new Dimension(145, 34));

        JButton exportButton = new JButton("Export to CSV");
        styleButton(exportButton, new Color(108, 117, 125), new Color(90, 98, 104));
        exportButton.setPreferredSize(new Dimension(140, 34));
        
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(exportButton);

        String url = "jdbc:mysql://localhost:3306/LMCST?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String password = "root123";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT student_name, email, class_name, department, event_name FROM registrations");

            while (rs.next()) {
                String name = rs.getString("student_name");
                String email = rs.getString("email");
                String studentClass = rs.getString("class_name");
                String dept = rs.getString("department");
                String event = rs.getString("event_name");
                model.addRow(new Object[]{name, email, studentClass, dept, event});
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception ex) {
            showStyledMessage(viewFrame, "Error loading data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    showStyledMessage(viewFrame, "Please select a row to edit.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int modelRow = table.convertRowIndexToModel(selectedRow);
                String oldName = (String) model.getValueAt(modelRow, 0);
                String oldEmail = (String) model.getValueAt(modelRow, 1);
                String oldClass = (String) model.getValueAt(modelRow, 2);
                String oldDept = (String) model.getValueAt(modelRow, 3);
                String oldEvent = (String) model.getValueAt(modelRow, 4);

                JDialog editDialog = new JDialog(viewFrame, "Edit Student Registration", true);
                editDialog.setSize(400, 380);
                editDialog.setLocationRelativeTo(viewFrame);
                editDialog.setLayout(null);

                JPanel editPanel = new JPanel(null);
                editPanel.setBounds(0, 0, 400, 380);
                editPanel.setBackground(isDarkMode ? DARK_CARD : Color.WHITE);
                editDialog.add(editPanel);

                JLabel lblName = new JLabel("Student Name:");
                styleLabel(lblName);
                if (isDarkMode) lblName.setForeground(DARK_TEXT);
                lblName.setBounds(25, 20, 110, 25);
                editPanel.add(lblName);

                JTextField txtName = new JTextField(oldName);
                styleTextField(txtName);
                txtName.setBounds(140, 20, 210, 30);
                editPanel.add(txtName);

                JLabel lblEmail = new JLabel("Email Address:");
                styleLabel(lblEmail);
                if (isDarkMode) lblEmail.setForeground(DARK_TEXT);
                lblEmail.setBounds(25, 65, 110, 25);
                editPanel.add(lblEmail);

                JTextField txtEmail = new JTextField(oldEmail);
                styleTextField(txtEmail);
                txtEmail.setBounds(140, 65, 210, 30);
                editPanel.add(txtEmail);

                JLabel lblClass = new JLabel("Class / Sem:");
                styleLabel(lblClass);
                if (isDarkMode) lblClass.setForeground(DARK_TEXT);
                lblClass.setBounds(25, 110, 110, 25);
                editPanel.add(lblClass);

                JTextField txtClass = new JTextField(oldClass);
                styleTextField(txtClass);
                txtClass.setBounds(140, 110, 210, 30);
                editPanel.add(txtClass);

                JLabel lblDept = new JLabel("Department:");
                styleLabel(lblDept);
                if (isDarkMode) lblDept.setForeground(DARK_TEXT);
                lblDept.setBounds(25, 155, 110, 25);
                editPanel.add(lblDept);

                JTextField txtDept = new JTextField(oldDept);
                styleTextField(txtDept);
                txtDept.setBounds(140, 155, 210, 30);
                editPanel.add(txtDept);

                JLabel lblEvent = new JLabel("Event Name:");
                styleLabel(lblEvent);
                if (isDarkMode) lblEvent.setForeground(DARK_TEXT);
                lblEvent.setBounds(25, 200, 110, 25);
                editPanel.add(lblEvent);

                JTextField txtEvent = new JTextField(oldEvent);
                styleTextField(txtEvent);
                txtEvent.setBounds(140, 200, 210, 30);
                editPanel.add(txtEvent);

                JButton saveEditBtn = new JButton("Save Changes");
                styleButton(saveEditBtn, new Color(40, 167, 69), new Color(33, 136, 56));
                saveEditBtn.setBounds(140, 260, 210, 35);
                editPanel.add(saveEditBtn);

                saveEditBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        String newName = txtName.getText().trim();
                        String newEmail = txtEmail.getText().trim();
                        String newClass = txtClass.getText().trim();
                        String newDept = txtDept.getText().trim();
                        String newEvent = txtEvent.getText().trim();

                        if (newName.isEmpty() || newEmail.isEmpty() || newClass.isEmpty() || newDept.isEmpty() || newEvent.isEmpty()) {
                            showStyledMessage(editDialog, "All fields are required!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        try {
                            Connection conn = DriverManager.getConnection(url, user, password);
                            String updateQuery = "UPDATE registrations SET student_name = ?, email = ?, class_name = ?, department = ?, event_name = ? WHERE student_name = ? AND email = ? AND class_name = ? AND department = ? AND event_name = ? LIMIT 1";
                            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                            pstmt.setString(1, newName);
                            pstmt.setString(2, newEmail);
                            pstmt.setString(3, newClass);
                            pstmt.setString(4, newDept);
                            pstmt.setString(5, newEvent);
                            pstmt.setString(6, oldName);
                            pstmt.setString(7, oldEmail);
                            pstmt.setString(8, oldClass);
                            pstmt.setString(9, oldDept);
                            pstmt.setString(10, oldEvent);

                            pstmt.executeUpdate();
                            pstmt.close();
                            conn.close();

                            model.setValueAt(newName, modelRow, 0);
                            model.setValueAt(newEmail, modelRow, 1);
                            model.setValueAt(newClass, modelRow, 2);
                            model.setValueAt(newDept, modelRow, 3);
                            model.setValueAt(newEvent, modelRow, 4);

                            showStyledMessage(editDialog, "Record updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            editDialog.dispose();

                        } catch (Exception ex) {
                            showStyledMessage(editDialog, "Error updating record: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                editDialog.setVisible(true);
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Registrations as CSV");
                int userSelection = fileChooser.showSaveDialog(viewFrame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
                    }

                    try (FileWriter writer = new FileWriter(fileToSave)) {
                        writer.write("Student Name,Email,Class/Sem,Department,Event Name\n");
                        for (int i = 0; i < model.getRowCount(); i++) {
                            writer.write(model.getValueAt(i, 0) + "," + 
                                         model.getValueAt(i, 1) + "," + 
                                         model.getValueAt(i, 2) + "," + 
                                         model.getValueAt(i, 3) + "," + 
                                         model.getValueAt(i, 4) + "\n");
                        }
                        showStyledMessage(viewFrame, "Successfully exported to CSV!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        showStyledMessage(viewFrame, "Export failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    int confirm = JOptionPane.showConfirmDialog(
                        viewFrame, 
                        "Are you sure you want to delete this registration?", 
                        "Confirm Delete", 
                        JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            Connection conn = DriverManager.getConnection(url, user, password);
                            String deleteQuery = "DELETE FROM registrations WHERE student_name = ? AND email = ? AND class_name = ? AND department = ? AND event_name = ? LIMIT 1";
                            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);

                            pstmt.setString(1, (String) model.getValueAt(modelRow, 0));
                            pstmt.setString(2, (String) model.getValueAt(modelRow, 1));
                            pstmt.setString(3, (String) model.getValueAt(modelRow, 2));
                            pstmt.setString(4, (String) model.getValueAt(modelRow, 3));
                            pstmt.setString(5, (String) model.getValueAt(modelRow, 4));
                            pstmt.executeUpdate();

                            pstmt.close();
                            conn.close();

                            model.removeRow(modelRow);
                            updateRegistrationCount();
                            showStyledMessage(viewFrame, "Registration deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception ex) {
                            showStyledMessage(viewFrame, "Error deleting record: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    showStyledMessage(viewFrame, "Please select a row to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        viewFrame.add(searchPanel, BorderLayout.NORTH);
        viewFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        viewFrame.add(bottomPanel, BorderLayout.SOUTH);
        viewFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EventRegistrationFrame().setVisible(true);
            }
        });
    }
}