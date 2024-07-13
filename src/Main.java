import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JFrame {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
           JOptionPane.showMessageDialog(null,"Please Try again later"  );
        }
        JOptionPane pan = new JOptionPane();
int selected_value = pan.showOptionDialog(null, "Choose an option", "Option Dialog Box", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Login", "SignUp"}, "Login");

        if(selected_value == 1)
        {
              new Signup();
        }
        else
        {
            new Login();
        }

    }
}
