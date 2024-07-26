package Componant;

import Db.Connection_instance;
import Db.Product;
import Db.User;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

public class Profile extends JFrame {
    String Email ;




JButton UpdateButton = new JButton("Update");
    JFrame frame = new JFrame();
    Color color1 = Color.decode("#dad8db");
    Color color2 = Color.decode("#e0d2d7");
    Color color3 = Color.decode("#e6cec8");
    Color color4 = Color.decode("#dfcdb7");
    Color color5 = Color.decode("#c7d1b1");
    JPanel panel = new JPanel();
    JLabel nameLabel = new JLabel("Name");
    JLabel emailLabel = new JLabel("Email");
JPanel WishlistPanel = new JPanel();
JPanel add_to_cartPanal = new JPanel();
JPanel My_productPanel = new JPanel();
JPanel My_orderPanel = new JPanel();
JScrollPane wishlistScroll = new JScrollPane(WishlistPanel);
JScrollPane add_to_cartScroll = new JScrollPane(add_to_cartPanal);
JScrollPane My_productScroll = new JScrollPane(My_productPanel);
JButton buy = new JButton("Buy");



    public Profile(User user) {
this.Email = user.getEmail();

frame.setLayout(new FlowLayout());
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            UIManager.put("Button.arc", 999);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }


        TestPanel testPanel = new TestPanel(color1, color2, color3, color4, color5);
        testPanel.setLayout(new FlowLayout());


        frame.setContentPane(testPanel);


        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);



nameLabel.setText("Name: "+user.getName());
        nameLabel.setSize( 800, 25);
        panel.add(nameLabel);


emailLabel.setText("Email: "+user.getEmail());
        emailLabel.setSize(150, 25);
        panel.add(emailLabel);
        UpdateButton.addActionListener(e->{
            update_profile();
        });

        panel.setBackground(Color.white);

        frame.setSize(1300, 900);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        WishlistPanel.setLayout(new FlowLayout(FlowLayout.LEFT , 5, 5));
        add_to_cartPanal.setLayout(new FlowLayout(FlowLayout.LEFT , 5, 5));
panel.setPreferredSize(new Dimension(1250, 100));



ArrayList<Product> products = get_wishlist_items();
ArrayList<Product> add_to_cart = get_add_to_cart();
frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
Dimension newSize = new Dimension(1250 , products.size()*90 + 50);
Dimension cartsize = new Dimension(1250 , add_to_cart.size()*90 + 50);
WishlistPanel.setPreferredSize(newSize);
add_to_cartPanal.setPreferredSize(cartsize);

                WishlistPanel.revalidate();
                add_to_cartPanal.revalidate();
            }
        });



        for (int i = add_to_cart.size()-1  ; i >= 0 ; i--) {
            Product current = add_to_cart.get(i);

            ProductCard productCard = new ProductCard(add_to_cart.get(i));
productCard.spinner.setVisible(false);
            productCard.addToWishlistButton.setVisible(false);
            productCard.addToCartButton.setText("Remove from cart");

            productCard.addToCartButton.addActionListener(e->{
                try {
                    Connection con = new Connection_instance().get_connection();
                    PreparedStatement pst = con.prepareStatement("select id from user where email = ?");
                    pst.setString(1,Email);
                    int user_id = 0;
                    ResultSet rs = pst.executeQuery();
                    while (rs.next())
                    {
                        user_id = rs.getInt("id");
                    }
                    pst = con.prepareStatement("select id from product where name = ?");
                    pst.setString(1,current.getName());
                    rs = pst.executeQuery();
                    int product_id = 0;
                    while (rs.next())
                    {
                        product_id = rs.getInt("id");
                    }

 pst = con.prepareStatement("delete from addcart where product_id = ? and user_id = ?");
                            pst.setInt(1, product_id);
                            pst.setInt(2,user_id);

                            pst.executeUpdate();
                            add_to_cartPanal.remove(productCard);

                            add_to_cart.remove(current);

                            add_to_cartPanal.revalidate();
                            add_to_cartPanal.repaint();


                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                    add_to_cartPanal.revalidate();
                    add_to_cartPanal.repaint();
                }
            });
            add_to_cartPanal.add(productCard);
        }

        for (int i = products.size()-1; i >=0; i--)
        {  Product current = products.get(i);
            ProductCard productCard = new ProductCard(products.get(i));
            productCard.spinner.setVisible(false);
            productCard.addToCartButton.setVisible(false);
            productCard.addToWishlistButton.setText("Remove from Wishlist");

            productCard.addToWishlistButton.addActionListener(e->{
                try {
                    Connection con = new Connection_instance().get_connection();
                    PreparedStatement pst = con.prepareStatement("select id from user where email = ?");
                    pst.setString(1,Email);
                    int user_id = 0;
                    ResultSet rs = pst.executeQuery();
                    while (rs.next())
                    {
                        user_id = rs.getInt("id");
                    }
                    pst = con.prepareStatement("select id from product where name = ?");
                    pst.setString(1,current.getName());
                    rs = pst.executeQuery();
                    int product_id = 0;
                    while (rs.next())
                    {
                        product_id = rs.getInt("id");
                    }

                            pst = con.prepareStatement("delete from wishlist where product_id = ? and user_id = ?");
                           pst.setInt(1, product_id);
                            pst.setInt(2,user_id);
                            pst.executeUpdate();
                            WishlistPanel.remove(productCard);

                            products.remove(current);

                            WishlistPanel.revalidate();
                            WishlistPanel.repaint();


                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                    WishlistPanel.revalidate();
                    WishlistPanel.repaint();
                }
            });
            WishlistPanel.add(productCard);
        }
        panel.add(UpdateButton);


        JLabel wishlistlable = new JLabel("Wishlist" , SwingConstants.CENTER);
        setDefaultFont(wishlistlable,new Font("Poppins", Font.BOLD, 20));
        wishlistlable.setPreferredSize(new Dimension(1250,30));
        frame.add(panel);
frame.add(wishlistlable);


        wishlistScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        wishlistScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        wishlistScroll.getVerticalScrollBar().setUnitIncrement(10);
        wishlistScroll.setPreferredSize(new Dimension( 1250, 250));
        wishlistScroll.setViewportView(WishlistPanel);
        wishlistScroll.setBorder(new CompoundBorder(new RoundedBorder(18),new EmptyBorder(5, 5, 5, 5)));
        frame.add(wishlistScroll);

        JLabel addtocartlable = new JLabel("Add to carted Items" , SwingConstants.CENTER);
        setDefaultFont(addtocartlable,new Font("Poppins", Font.BOLD, 20));
        addtocartlable.setPreferredSize(new Dimension(1250,30));
        frame.add(addtocartlable);
        add_to_cartScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
          add_to_cartScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add_to_cartScroll.getVerticalScrollBar().setUnitIncrement(10);
            add_to_cartScroll.setPreferredSize( new Dimension(1250, 250));
            add_to_cartScroll.setBackground(Color.white);
        add_to_cartScroll.setViewportView(add_to_cartPanal);
        add_to_cartScroll.setBorder(new CompoundBorder(new RoundedBorder(18),new EmptyBorder(5, 5, 5, 5)));
        frame.add(add_to_cartScroll);
buy.addActionListener(e->{
    new Buy_page(Email , add_to_cart);

});
UIManager.put("Button.arc", 999);
        buy.setBackground(new Color(250, 249, 246));
        buy.setBorder(new CompoundBorder(new RoundedBorder(18),new EmptyBorder(20, 0, 20, 0) ));
buy.setPreferredSize(new Dimension(100,50));
        frame.add(buy);

        frame.setResizable(false);



        frame.setVisible(true);
    }
//-----------------------------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());

        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        new Profile(new User("seler1", "sel1", "123", "seller"));
    }



    public ArrayList<Product> get_add_to_cart()
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
            pst = con.prepareStatement("select * from addcart where user_id = ?");
            pst.setInt(1,user_id);
            rs = pst.executeQuery();
            ArrayList <Integer> addcart = new ArrayList<>();
            while (rs.next())
            {
                addcart.add(rs.getInt("product_id"));
            }
            ArrayList<Product> products = new ArrayList<>();
            for (int i = 0; i < addcart.size(); i++) {
                pst = con.prepareStatement("select * from product where id = ?");
                pst.setInt(1,addcart.get(i));
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


    public void update_profile()
    {
     JFrame popupMenu = new JFrame();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        emailField.setSize(200,50);
        nameField.setSize(200,50);
        JButton updateButton = new JButton("Update");
        JPanel panel = new JPanel();
        popupMenu.setSize(400, 400);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT , 5, 5));
        panel.add(new JLabel("Name"));

        panel.add(nameField);
        panel.add(new JLabel("Email"));
        panel.add(emailField);
        panel.add(updateButton);


popupMenu.setDefaultCloseOperation(HIDE_ON_CLOSE);
        popupMenu.add(panel);
      popupMenu.setVisible(true);


        updateButton.addActionListener(e->{
            if(nameField.getText().equals("") || emailField.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this,"Please fill all the fields");
                return;
            }
            try {
                Connection con = new Connection_instance().get_connection();
                PreparedStatement pst = con.prepareStatement("select * from user where email = ?");
                pst.setString(1,emailField.getText());
                ResultSet rs = pst.executeQuery();
                if (rs.next())
                {
                    JOptionPane.showMessageDialog(this,"Email already exist");
                    return;
                }



                PreparedStatement pst1 = con.prepareStatement("update user set name = ? , email = ?  where email = ?");
                pst1.setString(1,nameField.getText());
                pst1.setString(2,emailField.getText());

                pst1.setString(3,Email);
                pst1.executeUpdate();
                JOptionPane.showMessageDialog(this,"Profile Updated");
            }
            catch (Exception e1)
            {
                e1.printStackTrace();
            }
        });




    }

    public void setDefaultFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setDefaultFont(child, font);
            }
        }
    }
}
