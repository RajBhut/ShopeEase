package Componant;

import Db.Connection_instance;
import Db.Product;
import Db.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Profile extends JFrame {
    String Email ;





    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel nameLabel = new JLabel("Name");
    JLabel emailLabel = new JLabel("Email");
JPanel WishlistPanel = new JPanel();
JPanel add_to_cartPanal = new JPanel();


    JButton updateButton = new JButton("Update");

    public Profile(User user) {
this.Email = user.getEmail();
setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        frame.setTitle("Profile");

        panel.setLayout(null);
nameLabel.setText("Name: "+user.getName());
        nameLabel.setBounds(10, 20, 150, 25);
        panel.add(nameLabel);


emailLabel.setText("Email: "+user.getEmail());
        emailLabel.setBounds(10, 50, 150, 25);
        panel.add(emailLabel);



        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        WishlistPanel.setLayout(new FlowLayout(FlowLayout.LEFT , 5, 5));
        ArrayList<Product> products = get_wishlist_items();
        for (int i = 0; i < products.size(); i++) {
            ProductCard productCard = new ProductCard(products.get(i));
            WishlistPanel.add(productCard);
        }  frame.add(panel);
        JLabel wishlistlable = new JLabel("Wishlist" , SwingConstants.CENTER);
        wishlistlable.setSize(800,50);
        frame.add(wishlistlable);
        frame.add(WishlistPanel);




        frame.setResizable(false);
        updateButton.setBounds(10, 110, 80, 25);
        panel.add(updateButton);
        getRootPane().setDefaultButton(updateButton);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Profile(new User("chamn", "chaman@123", "123456", "user"));
    }
    public ArrayList<Product> get_wishlist_items()
    {
        try
        {
            Connection con = new  Connection_instance().get_connection();
            PreparedStatement pst = con.prepareStatement("select id from user where email = ?");
            pst.setString(1,Email);
            int user_id = 0;
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                user_id = rs.getInt("id");
            }
            pst = con.prepareStatement("select * from wishlist where user_id = ?");
            pst.setInt(1,user_id);
            rs = pst.executeQuery();
            ArrayList <Integer> wishlist = new ArrayList<>();
            while (rs.next())
            {
                wishlist.add(rs.getInt("product_id"));
            }
            ArrayList<Product> products = new ArrayList<>();
            for (int i = 0; i < wishlist.size(); i++) {
                pst = con.prepareStatement("select * from product where id = ?");
                pst.setInt(1,wishlist.get(i));
                rs = pst.executeQuery();
                while (rs.next())
                {
                    PreparedStatement pst2 = con.prepareStatement("select * from producttag where product_id = ?");
                    pst2.setInt(1,rs.getInt("id"));
                    ResultSet rs2 = pst2.executeQuery();
                    ArrayList<String> tags = new ArrayList<>();
                    while (rs2.next()){
                        PreparedStatement pst3 = con.prepareStatement("select * from tag where id = ?");
                        pst3.setInt(1,rs2.getInt("tag_id"));
                        ResultSet rs3 = pst3.executeQuery();
                        while (rs3.next()){
                            tags.add(rs3.getString("tag_name"));
                        }
                    }
                    Product product = new Product(rs.getString("name"),rs.getInt("price"),rs.getInt("quantity"),tags,rs.getInt("discount"),rs.getString("imagePath"));
                    products.add(product);
                }
            }
            return products;
        }
        catch (Exception e)
        {e.printStackTrace();
        }
return  new ArrayList<Product>();

    }


}
