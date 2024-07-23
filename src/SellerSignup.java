import Componant.Deshboard;
import Db.Add_User;
import Db.Connection_instance;
import Db.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SellerSignup extends JFrame {
    String role;
    JFrame frame = new JFrame();
    JPanel panel;
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JLabel confirmPasswordLabel = new JLabel("Confirm Password");
    JPasswordField confirmPasswordField = new JPasswordField();
    JLabel EmailLabel = new JLabel("Email");
    JTextField EmailField = new JTextField();
    JButton signupButton = new JButton("Signup");
    JLabel ContectInfo = new JLabel("Contect Info.");
    JTextField Contextfield = new JTextField();
    ImageIcon icon = new ImageIcon("src/resorce/network-mesh-wire-digital-technology-background/17973908.jpg");

    JLabel background = new JLabel(icon);

    SellerSignup() {

    }

    SellerSignup(String role) {

        this.role = role;
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ImageIcon icon = new ImageIcon("src/resorce/network-mesh-wire-digital-technology-background/17973908.jpg");
        ImagePanel panel = new ImagePanel(icon.getImage());
        panel.setLayout(null);
        userLabel.setBounds(100, 120, 100, 25);
        panel.add(userLabel);

        userField.setBounds(220, 120, 165, 25);
        panel.add(userField);

        EmailLabel.setBounds(100, 150, 100, 25);
        panel.add(EmailLabel);

        EmailField.setBounds(220, 150, 165, 25);
        panel.add(EmailField);

        passwordLabel.setBounds(100, 180, 100, 25);
        panel.add(passwordLabel);

        passwordField.setBounds(220, 180, 165, 25);
        panel.add(passwordField);

        confirmPasswordLabel.setBounds(100, 210, 100, 25);
        panel.add(confirmPasswordLabel);

        confirmPasswordField.setBounds(220, 210, 165, 25);
        panel.add(confirmPasswordField);

        Contextfield.setBounds(220, 240, 165, 25);
        ContectInfo.setBounds(100, 240, 100, 25);
        panel.add(ContectInfo);
        panel.add(Contextfield);

        frame.setResizable(false);
        signupButton.setBounds(200, 290, 100, 25);
        panel.add(signupButton);

        frame.add(panel);
        signupButton.addActionListener(e -> {
            String user = userField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String email = EmailField.getText();
            String contect = Contextfield.getText();
            if (user.equals("") || password.equals("") || confirmPassword.equals("") || email.equals("") || contect.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match");
            } else {
                if (!checkEmail(email)) {

                    JOptionPane.showMessageDialog(null, "Signup Successful");
                    if (Add_seller(user, email, password, contect)) {
                        new Add_User().add_user(user, email, password, role);
                        new Deshboard(new User(user, email, password, role));
                    }
                } else {
                    userField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                    EmailField.setText("");
                }
            }
        });
        frame.setVisible(true);
    }

    boolean checkEmail(String email) {
        try (Connection con = new Connection_instance().get_connection()) {
            PreparedStatement pst = con.prepareStatement("select * from user where email = ?");
            pst.setString(1, email);
            if (pst.executeQuery().next()) {
                JOptionPane.showMessageDialog(null, "Email already exists");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    boolean Add_seller(String name, String email, String password, String contect) {
        Connection con = null;
        try {
            con = new Connection_instance().get_connection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement("insert into seller (name , contact_info  , password) values(?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, contect);
            stmt.setString(3, password);


            stmt.executeUpdate();


            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            UIManager.put( "TextComponent.arc", 10 );
            UIManager.put( "Button.arc", 600  );
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        new SellerSignup("seller");
    }

}

class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}