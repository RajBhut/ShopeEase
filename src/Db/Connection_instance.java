package Db;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection_instance {
    public Connection_instance()
    {

    }

    public Connection get_connection()
    {

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/labchecker","root","root");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error" );
        }
        return  null;
    }


}
