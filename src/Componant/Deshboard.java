package Componant;

import Db.Connection_instance;
import Db.Product;
import Db.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
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

    JButton profileButton = new JButton("🙍‍♂ \n  Profile️");
    JButton logoutButton = new JButton("Logout");
    JButton settingButton = new JButton("⚙️ \n settings");
    JButton helpButton = new JButton("Help");
    JButton aboutButton = new JButton("About");
    JButton contactButton = new JButton("📞 \n contect Us");
    JButton feedbackButton = new JButton("Feedback");
    JButton exitButton = new JButton("🚪 \n Exit");
    JPanel menuPanel;
    JPanel searchPanel;
    private JPanel productPanel;
    public Deshboard(User current_user) {

        this.user = current_user;
        frame.setSize(800, 800);
        productPanel = new JPanel();
        productPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
         menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
profileButton.addActionListener(e->{
    new Profile(user);

        }) ;
        JPanel outerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        menuPanel.add(profileButton);
        menuPanel.add(logoutButton);
        menuPanel.add(settingButton);
        menuPanel.add(helpButton);
        menuPanel.add(aboutButton);
        menuPanel.add(contactButton);
        menuPanel.add(feedbackButton);
        menuPanel.add(exitButton);
        menuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        menuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
outerPanel.add(menuPanel);
outerPanel.setBackground(Color.white);

        //frame.add(menuPanel, BorderLayout.CENTER);
         searchPanel = new JPanel(new BorderLayout());
searchPanel.setPreferredSize(new Dimension(800,40));
        JTextField searchBar = new JTextField();

searchBar.setBorder(  new CompoundBorder(new RoundedBorder(8),new EmptyBorder(10,10,10,10)));

        searchPanel.add(searchBar, BorderLayout.CENTER);
        searchBar.setPreferredSize(new Dimension(500, 20));
        JButton searchButton = new JButton("Search");
        searchButton.setBorder(new CompoundBorder(new RoundedBorder(8),new EmptyBorder(10,10,10,10)));

        searchPanel.add(searchButton, BorderLayout.EAST);
        JPanel productPanel = new ImagePanel("src/resorce/shopping-paper-bags-icon-isolated/JEMA GER 1722-04.jpg");


        ArrayList<Product> products = getProducts();
        for (int i = 0 ; i<products.size();i++) {
            ProductCard productCard = new ProductCard(products.get(i));
            productCard.setBorder(new EmptyBorder(10, 5, 10, 5));

            int finalI = i;
            productCard.addToWishlistButton.addActionListener(e -> {
                new Product_add(false).Add_to_Wishlist(user.getName(), products.get(finalI).getName());


            });
            productCard.addToCartButton.addActionListener(e -> {
                new Product_add(false).Add_to_Cart(user.getName(), products.get(finalI).getName());

            });
            Border combination = new CompoundBorder(new RoundedBorder(8),new ShadowBorder(3));

            productCard.setBorder(combination);

            productPanel.add(productCard);
        }



frame.add(searchPanel,BorderLayout.NORTH);
        frame.add(outerPanel,BorderLayout.SOUTH);
        frame.add(productPanel,BorderLayout.CENTER);
        searchButton.addActionListener(e->{
            updateProductPanel(getProducts(searchBar.getText()));
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void updateProductPanel(ArrayList<Product> products) {

        frame.getContentPane().removeAll();

        frame.setLayout(new BorderLayout());
        productPanel = new ImagePanel("src/resorce/shopping-paper-bags-icon-isolated/JEMA GER 1722-04.jpg");

        for (Product product : products) {
            ProductCard productCard = new ProductCard(product);
            productCard.addToWishlistButton.addActionListener(e -> {
                new Product_add(false).Add_to_Wishlist(user.getName(), product.getName());


            });
            productCard.addToCartButton.addActionListener(e->{
                new Product_add(false).Add_to_Cart(user.getName(), product.getName());
            });

            productPanel.add(productCard);
        }




        frame.add(menuPanel, BorderLayout.SOUTH);
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(productPanel, BorderLayout.CENTER);


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


class TestPanel extends JPanel {
    private Color[] colors;

    TestPanel(Color... colors) {
        this.colors = colors;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();

        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            fractions[i] = (float) i / (colors.length - 1);
        }

        LinearGradientPaint gp = new LinearGradientPaint(0, 0, w, h, fractions, colors);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 50);
    }
}

class ImagePanel extends JPanel {

    private Image backgroundImage;

    public ImagePanel(String fileName) {
        backgroundImage = new ImageIcon(fileName).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        double aspectRatio = (double) backgroundImage.getWidth(this) / backgroundImage.getHeight(this);
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        int imageWidth;
        int imageHeight;

        if (panelWidth / aspectRatio < panelHeight) {
            imageWidth = panelWidth;
            imageHeight = (int) (panelWidth / aspectRatio);
        } else {
            imageWidth = (int) (panelHeight * aspectRatio);
            imageHeight = panelHeight;
        }

        // Draw the image at the calculated width and height
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
