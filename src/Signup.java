import javax.swing.*;

public class Signup extends JFrame{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JLabel confirmPasswordLabel = new JLabel("Confirm Password");
    JPasswordField confirmPasswordField = new JPasswordField();
    JLabel EmailLabel = new JLabel("Email");
    JTextField EmailField = new JTextField();
    JButton signupButton = new JButton("Signup");
    Signup()
    {
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userField.setBounds(100, 20, 165, 25);
        panel.add(userField);

        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        confirmPasswordLabel.setBounds(10, 80, 80, 25);
        panel.add(confirmPasswordLabel);

        confirmPasswordField.setBounds(100, 80, 165, 25);
        panel.add(confirmPasswordField);

        EmailLabel.setBounds(10, 110, 80, 25);
        panel.add(EmailLabel);

        EmailField.setBounds(100, 110, 165, 25);
        panel.add(EmailField);

        frame.setResizable(false);
        signupButton.setBounds(10, 140, 80, 25);
        panel.add(signupButton);
        signupButton.addActionListener(e -> {
            String user = userField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String email = EmailField.getText();
            if(user.equals("") || password.equals("") || confirmPassword.equals("") || email.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            }
            else if(!password.equals(confirmPassword))
            {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Signup Successful");
            }
        });
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Signup();
    }
}
