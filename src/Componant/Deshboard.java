package Componant;

import Db.Connection_instance;
import Db.Product;
import Db.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Deshboard extends JFrame {
    User user;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel user_details = new JLabel();
    JButton profileButton = new JButton("Profile");
    JButton logoutButton = new JButton("Logout");
    JButton settingButton = new JButton("Setting");
    JButton helpButton = new JButton("Help");
    JButton aboutButton = new JButton("About");
    JButton contactButton = new JButton("Contact");
    JButton feedbackButton = new JButton("Feedback");
    JButton exitButton = new JButton("Exit");

    public Deshboard(User current_user) {
        frame.setSize(800, 800);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));

        // Add buttons to menuPanel
        menuPanel.add(profileButton);
        menuPanel.add(logoutButton);
        menuPanel.add(settingButton);
        menuPanel.add(helpButton);
        menuPanel.add(aboutButton);
        menuPanel.add(contactButton);
        menuPanel.add(feedbackButton);
        menuPanel.add(exitButton);

        frame.add(menuPanel, BorderLayout.SOUTH);

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3)); // Change 3 to the number of columns you want

        ArrayList<Product> products = getProducts();
        for (int i = 0 ; i<products.size();i++) {
            ProductCard productCard = new ProductCard(products.get(i));
            productCard.setBorder(new EmptyBorder(10, 10, 10, 10));


            productPanel.add(productCard);
        }

        frame.add(productPanel, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // This is a placeholder for your actual method that retrieves products
    private ArrayList<Product> getProducts()  {
try{
    Connection con = new  Connection_instance().get_connection();
    PreparedStatement pst = con.prepareStatement("select * from product");
    ResultSet rs1 = pst.executeQuery();

    ArrayList<Product> products = new ArrayList<>();
    while (rs1.next()){
        PreparedStatement pst2 = con.prepareStatement("select * from producttag where product_id = ?");
        pst2.setInt(1,rs1.getInt("id"));
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
        Product product = new Product(rs1.getString("name"),rs1.getInt("price"),rs1.getInt("quantity"),rs1.getInt("id"),tags,rs1.getInt("discount"),rs1.getString("imagePath"));

        products.add(product);
    }
    return products;
}
catch (Exception e){
    e.printStackTrace();
}
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        new Deshboard(new User("admin","admin","admin","admin"));
    }
}