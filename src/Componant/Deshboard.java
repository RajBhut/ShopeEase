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
import java.util.Arrays;

public class Deshboard extends JFrame {
    User user;
    JFrame frame = new JFrame();

    JButton profileButton = new JButton("Profile");
    JButton logoutButton = new JButton("Logout");
    JButton settingButton = new JButton("Setting");
    JButton helpButton = new JButton("Help");
    JButton aboutButton = new JButton("About");
    JButton contactButton = new JButton("Contact");
    JButton feedbackButton = new JButton("Feedback");
    JButton exitButton = new JButton("Exit");
    JPanel menuPanel;
    JPanel searchPanel;
    private JPanel productPanel;
    public Deshboard(User current_user) {
        frame.setSize(800, 800);
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
         menuPanel = new JPanel();
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
         searchPanel = new JPanel(new BorderLayout());
        JTextField searchBar = new JTextField();
        searchBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        searchPanel.add(searchBar, BorderLayout.CENTER);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchButton, BorderLayout.EAST);
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3));

        ArrayList<Product> products = getProducts();
        for (int i = 0 ; i<products.size();i++) {
            ProductCard productCard = new ProductCard(products.get(i));
            productCard.setBorder(new EmptyBorder(10, 10, 10, 10));


            productPanel.add(productCard);
        }

frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(productPanel, BorderLayout.CENTER);
        searchButton.addActionListener(e->{
            updateProductPanel(getProducts(searchBar.getText()));
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void updateProductPanel(ArrayList<Product> products) {
        // Remove all components from frame
        frame.getContentPane().removeAll();

        // Create new productPanel
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 3));
        for (Product product : products) {
            ProductCard productCard = new ProductCard(product);
            productCard.setBorder(new EmptyBorder(10, 10, 10, 10));
            productPanel.add(productCard);
        }

        // Add menuPanel, searchPanel, and productPanel back to frame
        frame.add(menuPanel, BorderLayout.SOUTH);
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(productPanel, BorderLayout.CENTER);

        // Revalidate and repaint frame to reflect changes
        frame.revalidate();
        frame.repaint();
    }
    private ArrayList<Product> getProducts(String search) {
        try {
            Connection con = new Connection_instance().get_connection();
            String sql = "SELECT p.*, GROUP_CONCAT(t.tag_name) AS tags " +
                    "FROM Product p " +
                    "INNER JOIN ProductTag pt ON p.id = pt.product_id " +
                    "INNER JOIN Tag t ON pt.tag_id = t.id " +
                    "WHERE (t.tag_name LIKE ? OR p.name LIKE ?) " +
                    "GROUP BY p.id;";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + search + "%");
            pst.setString(2, "%" + search + "%");
            ResultSet rs = pst.executeQuery();

            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                String tagsString = rs.getString("tags");
                ArrayList<String> tags = new ArrayList<>();
                if (tagsString != null) {
                    tags.addAll(Arrays.asList(tagsString.split(",")));
                }
                Product product = new Product(rs.getString("name"), rs.getInt("price"), rs.getInt("quantity"),
                         tags, rs.getInt("discount"), rs.getString("imagePath"));
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

//    private ArrayList<Product> getProducts(String search)  {
//        try{
//            Connection con = new  Connection_instance().get_connection();
//            PreparedStatement pst = con.prepareStatement("SELECT p.*\n" +
//                    "FROM Product p\n" +
//                    "INNER JOIN ProductTag pt ON p.id = pt.product_id\n" +
//                    "INNER JOIN Tag t ON pt.tag_id = t.id\n" +
//                    "WHERE t.tag_name LIKE ? OR p.name LIKE ?;");
//            pst.setString(1,"%"+search+"%");
//            pst.setString(2,"%"+search+"%");
//            ResultSet rs1 = pst.executeQuery();
//
//            ArrayList<Product> products = new ArrayList<>();
//while (rs1.next()){
//    PreparedStatement pst2 = con.prepareStatement("select * from producttag where product_id = ?");
//    pst2.setInt(1,rs1.getInt("id"));
//    ResultSet rs2 = pst2.executeQuery();
//    ArrayList<String> tags = new ArrayList<>();
//    while (rs2.next()){
//        PreparedStatement pst3 = con.prepareStatement("select * from tag where id = ?");
//        pst3.setInt(1,rs2.getInt("tag_id"));
//        ResultSet rs3 = pst3.executeQuery();
//        while (rs3.next()){
//            tags.add(rs3.getString("tag_name"));
//        }
//    }
//Product product = new Product(rs1.getString("name"),rs1.getInt("price"),rs1.getInt("quantity"),rs1.getInt("id"),tags,rs1.getInt("discount"),rs1.getString("imagePath"));
//products.add(product);
//    System.out.println(product);
//
//}
//
//            return products;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }


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
                Product product = new Product(rs1.getString("name"),rs1.getInt("price"),rs1.getInt("quantity"),tags,rs1.getInt("discount"),rs1.getString("imagePath"));

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