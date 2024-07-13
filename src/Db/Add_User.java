package Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Add_User {
    public Add_User()
    {

    }
    public void add_user(String name, String email, String password, String role) {
        Connection con = null;
        try {
            con = new Connection_instance().get_connection();
            con.setAutoCommit(false); // start transaction

            PreparedStatement stmt = con.prepareStatement("insert into user (name , email , password , role) values(?,?,?,?)");
            stmt.setString(1,name);
            stmt.setString(2,email);
            stmt.setString(3,password);
            stmt.setString(4,role);

            stmt.executeUpdate();

            User user = new User(name,email,password,role);

            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch(SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}