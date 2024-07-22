package Componant;

import Db.Connection_instance;
import Db.Product;
import Db.User;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Product_add extends JFrame {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel quantitiLable = new JLabel("Quantity");
    JTextField quantitifield = new JTextField();
    JLabel nameLabel = new JLabel("Name");
    JLabel priceLabel = new JLabel("Price");
    JButton imageButton = new JButton("Choose Image");
    JTextField nameField = new JTextField();
    JTextField priceField = new JTextField();

    JButton addButton = new JButton("Add Product");
    JLabel tagLabel = new JLabel("tag (tag1,tag2...)");
    JTextField tagfield = new JTextField();
    String imagePath = "";


    Product_add(boolean is_admin)
    {

    }

    Product_add()
    {


        frame.setSize(400, 400); // Increase the height of the frame to fit all components
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        nameField.setBounds(100, 20, 165, 25);
        panel.add(nameField);

        priceLabel.setBounds(10, 50, 80, 25);
        panel.add(priceLabel);

        priceField.setBounds(100, 50, 165, 25);
        panel.add(priceField);




        tagLabel.setBounds(10, 110, 80, 25);
        panel.add(tagLabel);

        tagfield.setBounds(100, 110, 165, 25);
        panel.add(tagfield);

        frame.setResizable(false);
quantitiLable.setBounds(10,140,80,25);
quantitifield.setBounds(100,140,165,25);
        addButton.setBounds(10, 180, 120, 25);
        panel.add(addButton);
        panel.add(quantitifield);
        panel.add(quantitiLable);



        imageButton.setBounds(100, 80, 165, 25);
        panel.add(imageButton);

        imageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\Admin\\OneDrive\\Desktop\\javarepo\\Blinknotes\\gui\\src\\resorce"));
            fileChooser.showOpenDialog(null);
            imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            while (!imagePath.matches(".*\\.(jpg|png|jpeg)")) {
                JOptionPane.showMessageDialog(null, "Image must be a jpg, jpeg or png file");
                fileChooser.showOpenDialog(null);
                imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String price = priceField.getText();
            String tag = tagfield.getText();
            String quantity = quantitifield.getText() ;
            if(name.equals("") || price.equals("") || tag.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            }
            else
            {



                while (!price.matches("\\d+$"))
                {
                    JOptionPane.showMessageDialog(null,"Price must be positive intiger");
                    priceField.setText("");
                }
                while (!quantity.matches("\\d+$"))
                {
                    JOptionPane.showMessageDialog(null,"quantiti must be positive intiger");
                    quantitifield.setText("");
                }
                add_product(name,Integer.parseInt(price),Integer.parseInt(quantity) ,  0 , imagePath);
                priceField.setText("");
                nameField.setText("");
                tagfield.setText("");
                quantitifield.setText("");



            }
        });
        frame.setVisible(true);
    }
    public boolean add_product(String name, int price, int quantitiy, int discount ,String imagepath ) {
        Connection con = null;
        try {
            con = new Connection_instance().get_connection();
            con.setAutoCommit(false);
            PreparedStatement pstm2 = con.prepareStatement("select * from product where name = ?");
            pstm2.setString(1,name);
            ResultSet ans = pstm2.executeQuery();
            if(ans.next())
            {
                JOptionPane.showMessageDialog(null,"Product already exists");
                pstm2.close();
                return false;
            }

            PreparedStatement stmt = con.prepareStatement("insert into product (name , price , quantity , discount,imagepath) values(?,?,?,?,?)");
     stmt.setString(1 ,name);
     stmt.setInt(2,price);
     stmt.setInt(3,quantitiy);
     stmt.setInt(4,discount);
     stmt.setString(5,imagepath);

            stmt.executeUpdate();



            con.commit();
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Product Added");
add_tag(tagfield.getText().split(",") , name);
        return  true;
    }
boolean add_tag(String[] tags ,String product_name)
{
    Connection con = null;
    try {
        con = new Connection_instance().get_connection();
        con.setAutoCommit(false);
        PreparedStatement pst4 = con.prepareStatement("select id from product where name = ?");
        pst4.setString(1,product_name);
        ResultSet rs = pst4.executeQuery();
        int product_id = 0;
        if(rs.next())
        {

            product_id = rs.getInt("id");
            System.out.println(product_id);
        }
        for (String tag : tags) {
            PreparedStatement pstm2 = con.prepareStatement("select * from tag where tag_name = ?");
            pstm2.setString(1,tag);
            if(!pstm2.executeQuery().next())
            {
                PreparedStatement stmt = con.prepareStatement("insert into tag (tag_name) values(?)");
                stmt.setString(1,tag);
                stmt.executeUpdate();
            }
        }
        for (String tag : tags) {
            PreparedStatement pstm3 = con.prepareStatement("select * from tag where tag_name = ?");
            pstm3.setString(1,tag);
            int tag_id = 0;
            ResultSet rs1 = pstm3.executeQuery();
            if(rs1.next())
            {
                tag_id = rs1.getInt("id");
            }
//            PreparedStatement stmt2 = con.prepareStatement("insert into producttag (product_id , tag_id) values(?,?)");
//            stmt2.setInt(1,product_id);
//            stmt2.setInt(2,tag_id);
//            stmt2.executeUpdate();
            if(product_id != 0) {
                PreparedStatement stmt2 = con.prepareStatement("insert into producttag (product_id , tag_id) values(?,?)");
                stmt2.setInt(1,product_id);
                stmt2.setInt(2,tag_id);
                stmt2.executeUpdate();
            } else {
                System.err.println("Product name does not exist in the product table");
            }
        }




        con.commit();
    } catch (SQLException e) {

        e.printStackTrace();

    } finally {
        if (con != null) {
            try {
                con.setAutoCommit(true);

                } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return  true;
}

    public void Add_to_Wishlist(String username , String product_name) {
        try {
            Connection con = new Connection_instance().get_connection();

            // Get user_id
            PreparedStatement ps = con.prepareStatement("select id from user where name = ?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            int user_id = 0;
            if (rs.next()) {
                user_id = rs.getInt("id");
            }
            ps.close();


            PreparedStatement ps1 = con.prepareStatement("select id from product where name = ?");
            ps1.setString(1,product_name);
            ResultSet rs1 = ps1.executeQuery();
            int product_id = 0;
            if (rs1.next()) {
                product_id = rs1.getInt("id");
            }
            ps1.close();


            PreparedStatement psCheck = con.prepareStatement("select * from wishlist where user_id = ? and product_id = ?");
            psCheck.setInt(1, user_id);
            psCheck.setInt(2, product_id);
            ResultSet rsCheck = psCheck.executeQuery();
            if (!rsCheck.next()) {

                PreparedStatement ps2 = con.prepareStatement("insert into wishlist (user_id ,product_id ) values (? , ?);");
                ps2.setInt(1,user_id);
                ps2.setInt(2,product_id);
                ps2.execute();
                ps2.close();
                JOptionPane.showMessageDialog(null , "Product added to wishlist ");
            } else {
                JOptionPane.showMessageDialog(null , "Product already in wishlist ");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void Add_to_Cart(String username , String product_name) {
        try {
            Connection con = new Connection_instance().get_connection();

            PreparedStatement ps = con.prepareStatement("select id from user where name = ?");
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            int user_id = 0;
            if (rs.next()) {
                user_id = rs.getInt("id");
            }
            ps.close();


            PreparedStatement ps1 = con.prepareStatement("select id from product where name = ?");
            ps1.setString(1,product_name);
            ResultSet rs1 = ps1.executeQuery();
            int product_id = 0;
            if (rs1.next()) {
                product_id = rs1.getInt("id");
            }
            ps1.close();


            PreparedStatement psCheck = con.prepareStatement("select * from addcart where user_id = ? and product_id = ?");
            psCheck.setInt(1, user_id);
            psCheck.setInt(2, product_id);
            ResultSet rsCheck = psCheck.executeQuery();
            if (!rsCheck.next()) {

                PreparedStatement ps2 = con.prepareStatement("insert into addcart (user_id ,product_id ) values (? , ?);");
                ps2.setInt(1,user_id);
                ps2.setInt(2,product_id);
                ps2.execute();
                ps2.close();
                JOptionPane.showMessageDialog(null , "Product added to cart ");
            } else {
                JOptionPane.showMessageDialog(null , "Product already in cart ");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Product_add();
    }
}
