package Db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Add_Product
{
    public Add_Product()
    {

    }
    public void add_product(String name, int price, int quantity, int id, ArrayList<String> tags , int discount, String imagePath) throws SQLException {
        Connection con = null;
        try {
            con = new Connection_instance().get_connection();
            con.setAutoCommit(false); // start transaction

            PreparedStatement stmt = con.prepareStatement("insert into product (name , price , quantity , discount ,imagePath) values(?,?,?,?,?,?,?)");

            stmt.setString(1,name);
            stmt.setInt(2,price);
            stmt.setInt(3,quantity);
            stmt.setInt(4,discount);

            stmt.setString(5,imagePath);

            stmt.executeUpdate();

            Product product = new Product(name,price,quantity,tags,discount,imagePath);

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
