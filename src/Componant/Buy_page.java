package Componant;

import Db.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Buy_page extends JFrame {

    Map<JSpinner, Integer> previousValues = new HashMap<>();

JFrame frame;
    JPanel panel;
    JScrollPane scrollPane;
    ArrayList<Product> addToCart;
    JLabel addressLabel = new JLabel("Address");
    JTextField addressField = new JTextField();
    JLabel contect_number = new JLabel("Contect Number");
    JTextField contect_numberField = new JTextField();
    JButton buyButton = new JButton("Buy");
Double total = 0.0;
    public Buy_page(String email, ArrayList<Product> addToCart) {
    frame = new JFrame();
        frame.setSize(1300, 900);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        this.addToCart = addToCart;
        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        for (int i = 0 ; i < addToCart.size(); i++) {
            Product product = addToCart.get(i);
total += product.getPrice();
            ProductCard productCard = new ProductCard(product);
            previousValues.put(productCard.spinner, 1);

            productCard.addToCartButton.setVisible(false);
            productCard.addToWishlistButton.setVisible(false);


            productCard.spinner.addChangeListener(e -> {
                int quantity = (int) productCard.spinner.getValue();
                int previousValue = previousValues.get(productCard.spinner);

                if (quantity > previousValue) {

                    total += product.getPrice() * (quantity - previousValue);

                } else if (quantity < previousValue) {

                    total -= product.getPrice() * (previousValue - quantity);

                }

                previousValues.put(productCard.spinner, quantity);

                productCard.priceLabel.setText(product.getPrice() * quantity + "₹");
                productCard.details.revalidate();
                productCard.details.repaint();
            });






            panel.add(productCard);
        }

        scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(1200, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane);
        buyButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(null, "Total Amount: " + total + "₹");
            frame.dispose();
        }
        );
        frame.add(buyButton);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new Buy_page("kr",new ArrayList<Product>());
    }

    public Buy_page() {

    }


}
