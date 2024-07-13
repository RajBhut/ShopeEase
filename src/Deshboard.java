import Db.User;

import javax.swing.*;
//want to add nav bar and add their some option like profile etc
public class Deshboard extends JFrame {
User user ;

    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel user_details = new JLabel();

    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");
    JButton profileButton = new JButton("Profile");
    JButton logoutButton = new JButton("Logout");
    JButton settingButton = new JButton("Setting");
    JButton helpButton = new JButton("Help");
    JButton aboutButton = new JButton("About");
    JButton contactButton = new JButton("Contact");
    JButton feedbackButton = new JButton("Feedback");
    JButton exitButton = new JButton("Exit");
    Deshboard(User current_user) {
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userField.setBounds(100, 20, 165, 25);
        panel.add(userField);

        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        user_details.setBounds(10, 0, 80, 25);
        user_details.setText(current_user.getName());
panel.add(user_details);


        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String password = passwordField.getText();
            if (user.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(null, "Login Successful");
            } else {
                JOptionPane.showMessageDialog(null, "Login Failed");
            }
        });
        profileButton.setBounds(100, 80, 80, 25);
        panel.add(profileButton);
        profileButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Profile");
        });
        logoutButton.setBounds(190, 80, 80, 25);
        panel.add(logoutButton);
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Logout");
        });
        settingButton.setBounds(10, 110, 80, 25);
        panel.add(settingButton);
        settingButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Setting");
        });
        helpButton.setBounds(100, 110, 80, 25);
        panel.add(helpButton
        );
        helpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Help");
        });

        aboutButton.setBounds(190, 110, 80, 25);
        panel.add(aboutButton);
        aboutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "About");
        });
        contactButton.setBounds(10, 140, 80, 25);
        panel.add(contactButton);
        contactButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Contact");
        });
        feedbackButton.setBounds(100, 140, 80, 25);
        panel.add(feedbackButton);
        feedbackButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Feedback");
        });
        exitButton.setBounds(190, 140, 80, 25);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Deshboard(new User("admin","admin","admin","admin"));
    }




}
