
package Componant;

import Db.Product;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;


public class ProductCard extends JPanel {

     JLabel imageLabel;
    private JLabel titleLabel;
    private JLabel priceLabel;
    JButton addToCartButton;
     JButton addToWishlistButton;

    public ProductCard(Product product) {
        UIManager.put( "Button.arc", 999 );
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(128, 128));

        ImageIcon imageIcon = new ImageIcon(product.getImagePath());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(imageLabel.getPreferredSize().width, -1, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));

        JPanel detailsPanel = new JPanel(new BorderLayout());


        titleLabel = new JLabel(product.getName());

        priceLabel = new JLabel(  product.getPrice() +"₹");


        addToCartButton = new JButton(" Add to Cart ");
        addToCartButton.setFont(new Font("Arial", Font.BOLD, 13));
        Border combination = new CompoundBorder(new RoundedBorder(8),new EmptyBorder(5, 5, 5, 5
        ) );

setBorder(combination);
        setBackground(Color.white);


        addToWishlistButton = new JButton(" Add to Wishlist ");

        addToWishlistButton.setFont(new Font("Arial", Font.BOLD, 13 ));
        addToWishlistButton.setBorder(new EmptyBorder(5, 1, 5, 1));
        addToCartButton.setBorder(new EmptyBorder(5, 1, 5, 1));


        addToCartButton.setBorder(combination);
        addToCartButton.setMargin(new Insets(15, 15, 15, 15));
addToWishlistButton.setBorder(combination);
        addToWishlistButton.setMargin(new Insets(15, 15, 15, 15));
addToWishlistButton.setSize(80,50);
addToCartButton.setSize(80,50);

        detailsPanel.add(titleLabel, BorderLayout.NORTH);
        detailsPanel.add(priceLabel, BorderLayout.SOUTH);
        detailsPanel.add(addToCartButton, BorderLayout.WEST);
        detailsPanel.add(Box.createRigidArea(new Dimension(10, 5)), BorderLayout.CENTER);

        detailsPanel.add(addToWishlistButton, BorderLayout.EAST);
detailsPanel.setBorder(new EmptyBorder(5,5,5,5));
        gbc.gridx = 0;
        gbc.weightx = 2;
        gbc.gridy = 0;
        add(imageLabel, gbc);
        gbc.gridy = 1;
        add(detailsPanel, gbc);
    }
}
class RoundedBorder extends AbstractBorder {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
class ShadowBorder extends AbstractBorder {
    private int shadowSize;

    ShadowBorder(int shadowSize) {
        this.shadowSize = shadowSize;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a gradient that goes from transparent to gray
        GradientPaint paint = new GradientPaint(width - shadowSize, y, new Color(0, 0, 0, 0), width, y, Color.GRAY);
        g2d.setPaint(paint);
        g2d.fillRect(width - shadowSize, y, shadowSize, height);

        paint = new GradientPaint(x, height - shadowSize, new Color(0, 0, 0, 0), x, height, Color.GRAY);
        g2d.setPaint(paint);
        g2d.fillRect(x, height - shadowSize, width, shadowSize);

        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, shadowSize, shadowSize);
    }
}