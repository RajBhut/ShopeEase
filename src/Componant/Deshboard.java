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
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        user_details.setText(current_user.getName());
        panel.add(user_details);

        panel.add(profileButton);
        profileButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Profile"));

        panel.add(logoutButton);
        logoutButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Logout"));

        panel.add(settingButton);
        settingButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Setting"));

        panel.add(helpButton);
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Help"));

        panel.add(aboutButton);
        aboutButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "About"));

        panel.add(contactButton);
        contactButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Contact"));

        panel.add(feedbackButton);
        feedbackButton.addActionListener(e -> JOptionPane.showMessageDialog(null, "Feedback"));

        panel.add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        // Assuming you have a method that returns a list of products
        ArrayList<Product> products = getProducts();
        for (int i = 0 ; i<products.size();i++) {
            ProductCard productCard = new ProductCard( products.get(i));
            panel.add(productCard);
        }

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
        System.out.println(product);
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