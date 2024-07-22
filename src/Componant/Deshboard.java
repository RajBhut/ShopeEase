package Componant;

import Db.Connection_instance;
import Db.Product;
import Db.User;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class Deshboard extends JFrame {
    User user;
    JFrame frame = new JFrame();
    JScrollPane productkeeper;
    JButton profileButton = new JButton(" 🙍‍Profile️ ");
    JPanel logo = new TestPanel(new Color(118, 226, 229, 255), new Color(114, 197, 191, 255), new Color(114, 197, 191));
    JButton logoutButton = new JButton("  Logout  ");
    JButton settingButton = new JButton("⚙️settings");
    JButton helpButton = new JButton("   Help   ");
    JButton aboutButton = new JButton("  About  ");
    JButton exitButton = new JButton(" 🚪  Exit ");
    JPanel menuPanel;
    JPanel searchPanel;
    JButton MyProductButton = new JButton(" 🛒 My Products");
    private JPanel productPanel;

    public Deshboard(User current_user) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("Button.arc", 999);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        this.user = current_user;
        frame.setSize(800, 800);
        productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));







        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        profileButton.addActionListener(e -> {
            new Profile(user);

        });


        menuPanel.add(profileButton);
        menuPanel.add(logoutButton);
        menuPanel.add(settingButton);
        menuPanel.add(helpButton);
        menuPanel.add(aboutButton);

        menuPanel.add(exitButton);
if(user.getRole().equals("seller")){
    menuPanel.add(MyProductButton);
    MyProductButton.addActionListener(e -> {
        new Profile(user);
    });
}

        menuPanel.setBackground(new Color(66, 64, 64, 255));
        menuPanel.setPreferredSize(new Dimension(800, 60));

        logo.setPreferredSize(new Dimension(800, 50));
        logo.setOpaque(true);
        logo.setBackground(Color.cyan);
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(logo, BorderLayout.NORTH);
        searchPanel.setPreferredSize(new Dimension(800, 90));
        JTextField searchBar = new IconTextField(new ImageIcon("src/resorce/searchlogo.jpg"));

        searchBar.setBorder(new CompoundBorder(new EmptyBorder(2, 10, 2, 10), new RoundedBorder(8)));

        searchPanel.add(searchBar, BorderLayout.CENTER);
        searchBar.setPreferredSize(new Dimension(400, 40));
        searchBar.setMargin(new Insets(10, 10, 10, 10));
        settingButton.setMargin(new Insets(10, 10, 10, 10));
        exitButton.setMargin(new Insets(10, 10, 10, 10));
        logoutButton.setMargin(new Insets(10, 10, 10, 10));
        profileButton.setMargin(new Insets(10, 10, 10, 10));
        helpButton.setMargin(new Insets(10, 10, 10, 10));
        exitButton.setMargin(new Insets(10, 10, 10, 10));
        aboutButton.setMargin(new Insets(10, 10, 10, 10));
MyProductButton.setMargin(new Insets(10, 10, 10, 10));
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.setBorder(new CompoundBorder(new EmptyBorder(5, 10, 5, 10), new RoundedBorder(8)));

        searchPanel.add(searchButton, BorderLayout.EAST);
        JPanel productPanel = new ImagePanel("src/resorce/shopping-paper-bags-icon-isolated/JEMA GER 1722-04.jpg");


        ArrayList<Product> products = getProducts();


        for (int i = 0; i < products.size(); i++) {
            ProductCard productCard = new ProductCard(products.get(i));
            productCard.setBorder(new EmptyBorder(10, 5, 10, 5));

            int finalI = i;
            productCard.addToWishlistButton.addActionListener(e -> {
                new Product_add(false).Add_to_Wishlist(user.getName(), products.get(finalI).getName());


            });
            productCard.addToCartButton.addActionListener(e -> {
                new Product_add(false).Add_to_Cart(user.getName(), products.get(finalI).getName());

            });
            Border combination = new CompoundBorder(new RoundedBorder(8), new ShadowBorder(3));

            productCard.setBorder(combination);
            productCard.addMouseListener(new java.awt.event.MouseAdapter() {
                                             public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                 productCard.setBackground(Color.lightGray);

                                             }

                                             @Override
                                             public void mouseClicked(MouseEvent e) {
                                                 super.mouseClicked(e);
                                                 System.out.println("clicked");
                                             }

                                             public void mouseExited(java.awt.event.MouseEvent evt) {
                                                 productCard.setBackground(Color.white);
                                             }
                                         }
            );

            productPanel.add(productCard);




        }
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {


                Dimension newSize = new Dimension(800 , products.size()*90);


                productPanel.setPreferredSize(newSize);


                productPanel.revalidate();
            }
        });
productkeeper = new JScrollPane(productPanel);

        productkeeper.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        productkeeper.getVerticalScrollBar().setUnitIncrement(10);


        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(menuPanel, BorderLayout.SOUTH);
        frame.add(productkeeper, BorderLayout.CENTER);
        searchButton.addActionListener(e -> {
            updateProductPanel(getProducts(searchBar.getText()));
        });

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void updateProductPanel(ArrayList<Product> products) {

        productkeeper.getViewport().removeAll();


        productPanel = new ImagePanel("src/resorce/shopping-paper-bags-icon-isolated/JEMA GER 1722-04.jpg");
        productPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));


        for (Product product : products) {
            ProductCard productCard = new ProductCard(product);
            productCard.setBorder(new EmptyBorder(10, 5, 10, 5));

            productCard.addToWishlistButton.addActionListener(e -> {
                new Product_add(false).Add_to_Wishlist(user.getName(), product.getName());
            });

            productCard.addToCartButton.addActionListener(e -> {
                new Product_add(false).Add_to_Cart(user.getName(), product.getName());
            });

            Border combination = new CompoundBorder(new RoundedBorder(8), new ShadowBorder(3));
            productCard.setBorder(combination);

            productCard.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    productCard.setBackground(Color.lightGray);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    productCard.setBackground(Color.white);
                }
            });

            productPanel.add(productCard);
        }


        Dimension newSize = new Dimension(800 , products.size()*90);
        productPanel.setPreferredSize(newSize);


        productkeeper.setViewportView(productPanel);


        productkeeper.revalidate();
        productkeeper.repaint();
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


    private ArrayList<Product> getProducts() {
        try {
            Connection con = new Connection_instance().get_connection();
            PreparedStatement pst = con.prepareStatement("select * from product");
            ResultSet rs1 = pst.executeQuery();

            ArrayList<Product> products = new ArrayList<>();
            while (rs1.next()) {
                PreparedStatement pst2 = con.prepareStatement("select * from producttag where product_id = ?");
                pst2.setInt(1, rs1.getInt("id"));
                ResultSet rs2 = pst2.executeQuery();
                ArrayList<String> tags = new ArrayList<>();
                while (rs2.next()) {
                    PreparedStatement pst3 = con.prepareStatement("select * from tag where id = ?");
                    pst3.setInt(1, rs2.getInt("tag_id"));
                    ResultSet rs3 = pst3.executeQuery();
                    while (rs3.next()) {
                        tags.add(rs3.getString("tag_name"));
                    }
                }
                Product product = new Product(rs1.getString("name"), rs1.getInt("price"), rs1.getInt("quantity"), tags, rs1.getInt("discount"), rs1.getString("imagePath"));

                products.add(product);
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        new Deshboard(new User("admin", "admin", "admin", "admin"));
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

class IconTextField extends JTextField {
    private Icon icon;

    public IconTextField(Icon icon) {
        this.icon = icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Insets insets = getInsets();
        int height = getHeight() - insets.top - insets.bottom;
        icon.paintIcon(this, g, getWidth() - insets.right - height, insets.top);
    }
}

