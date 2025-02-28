package Componant;

import Db.Product;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Product_Main extends JFrame {

    JScrollPane scrollPane;
    JPanel panel;
    ImagePanel imagePanel;
JPanel Imagecontainer ;
    Product_Main(Product product)
    {setSize(1200,800);
        setResizable(false);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(panel);

        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        imagePanel = new ImagePanel(product.getImagePath());
        Imagecontainer = new JPanel(new GridBagLayout());
        imagePanel.setPreferredSize(new Dimension(500, 500));
        Imagecontainer.setBackground(Color.cyan);
        imagePanel.setBorder(new CompoundBorder(new RoundedBorder(8),new EmptyBorder(5, 5, 5, 5)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;

        Imagecontainer.add(imagePanel, gbc);
        Imagecontainer.setPreferredSize(new Dimension(450, 500));
        Imagecontainer.setBorder(BorderFactory.createLineBorder(Color.black));
     panel.add(Imagecontainer);

     panel.add(new JLabel("Name: "+product.getName()));
        panel.add(new JLabel("Price: "+product.getPrice()));
        panel.add(new JLabel("Quantity: "+product.getQuantity()));
        panel.add(new JLabel("Discount: "+product.getDiscount()));
        panel.add(new JLabel("Tags: "+product.getTags()));

panel.setPreferredSize(new Dimension(1200, 800));
     scrollPane.getVerticalScrollBar().setUnitIncrement(10);
     scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);


    }

    public static void main(String[] args) {
        new Product_Main(new Product("T-Shirt", 100, 100, null, 10, "src/resorce/purple-t-shirt.jpg"));
    }
}
