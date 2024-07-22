import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JFrame {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
            UIManager.put( "Button.arc", 999 );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
           JOptionPane.showMessageDialog(null,"Please Try again later"  );
        }
        JOptionPane role = new JOptionPane();
       int val =  role.showOptionDialog(role, "choose role" , "Role" , JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null , new String[]{"Seller","customer"},"customer");
       if(val == 0)
       {
           JOptionPane pan = new JOptionPane();
           int selected_value = pan.showOptionDialog(null, "Choose an option", "Option Dialog Box", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Login", "SignUp"}, "Login");

           if(selected_value == 1)
           {
               new Signup("customer");
           }
           else if(selected_value == 0)
           {
               new Login();
           }
           else
           {
               System.exit(0);
           }
       }else {

           JOptionPane pan = new JOptionPane();
           int selected_value = pan.showOptionDialog(null, "Choose an option", "Option Dialog Box", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Login", "SignUp"}, "Login");

           if(selected_value == 1)
           {
               new Signup("seller");
           }
           else if(selected_value == 0)
           {
               new Login();
           }
           else
           {
               System.exit(0);
           }
       }

    }
}
