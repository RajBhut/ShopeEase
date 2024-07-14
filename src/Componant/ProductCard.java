
package Componant;

import Db.Product;

import javax.swing.*;
import java.awt.*;


public class ProductCard extends JPanel {

    private JLabel imageLabel;
    private JLabel titleLabel;
    private JLabel priceLabel;
    private JButton addToCartButton;
    private JButton addToWishlistButton;

    public ProductCard(Product product) {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        imageLabel = new JLabel();


        ImageIcon imageIcon = new ImageIcon(product.getImagePath());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(100, 50 , Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));


        JPanel detailsPanel = new JPanel(new BorderLayout());

        // Create title label with product name
        titleLabel = new JLabel(product.getName());

        // Create price label with formatted price
        priceLabel = new JLabel("$" + product.getPrice());

        // Add to cart button
        addToCartButton = new JButton("Add to Cart");

        // Add wishlist button
        addToWishlistButton = new JButton("Add to Wishlist");

        // Add components to details panel
        detailsPanel.add(titleLabel, BorderLayout.NORTH);
        detailsPanel.add(priceLabel, BorderLayout.CENTER);
        detailsPanel.add(addToCartButton, BorderLayout.WEST);
        detailsPanel.add(addToWishlistButton, BorderLayout.EAST);


        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.gridy = 0;
        add(imageLabel, gbc);
        gbc.gridy = 1;
        add(detailsPanel, gbc);
    }


}