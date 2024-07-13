import javax.swing.*;

public class Login extends JFrame{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");

    Login()
    {
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

        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);
        frame.setResizable(false);
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);
        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String password = passwordField.getText();
            if(user.equals("admin") && password.equals("admin"))
            {
                JOptionPane.showMessageDialog(null, "Login Successful");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Login Failed");
            }
        });
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
