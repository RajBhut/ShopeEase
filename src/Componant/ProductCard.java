
package Componant;

import Db.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        imageLabel.setPreferredSize(new Dimension(128, 128)); // Set preferred size

        ImageIcon imageIcon = new ImageIcon(product.getImagePath());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(imageLabel.getPreferredSize().width, -1, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));

        JPanel detailsPanel = new JPanel(new BorderLayout());

        // Create title label with product name
        titleLabel = new JLabel(product.getName());

        // Create price label with formatted price
        priceLabel = new JLabel(  product.getPrice() +"₹");

        // Add to cart button
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("Arial", Font.BOLD, 10));

        // Add wishlist button
        addToWishlistButton = new JButton("Add to Wishlist");

        addToWishlistButton.setFont(new Font("Arial", Font.BOLD, 10 )); // Set font name to Arial, style to BOLD, and size to 10
        addToWishlistButton.setBorder(new EmptyBorder(5, 1, 5, 1));
        addToCartButton.setBorder(new EmptyBorder(5, 1, 5, 1));
        // Add components to details panel
        detailsPanel.add(titleLabel, BorderLayout.NORTH);
        detailsPanel.add(priceLabel, BorderLayout.SOUTH);
        detailsPanel.add(addToCartButton, BorderLayout.WEST);
        detailsPanel.add(Box.createRigidArea(new Dimension(10, 5)), BorderLayout.CENTER);

        detailsPanel.add(addToWishlistButton, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.gridy = 0;
        add(imageLabel, gbc);
        gbc.gridy = 1;
        add(detailsPanel, gbc);
    }
}