import Componant.Deshboard;
import Db.Connection_instance;
import Db.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
        getRootPane().setDefaultButton(loginButton);
        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String password = passwordField.getText();

            Connection con = new Connection_instance().get_connection();

            try {
                PreparedStatement stmt = con.prepareStatement("select * from user where name = ? and password = ?");
                stmt.setString(1,user);
                stmt.setString(2,password);
                ResultSet rs =  stmt.executeQuery();
                if(!rs.next())
                {
                    JOptionPane.showMessageDialog(this, "Login Failed");
                    return;
                }
                else {
                    JOptionPane.showMessageDialog(this, "Login Successful");
                new Deshboard(new User(rs.getString("name"),rs.getString("email"),rs.getString("password"),rs.getString("role")));
frame.setVisible(false);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Login Failed");
            }

        });
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }



}
