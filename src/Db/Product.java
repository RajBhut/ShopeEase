package Db;

import java.util.ArrayList;

public class Product {
    int discount;
    String name;
    int price;
    int quantity;
    int id;
ArrayList<String> tags = new ArrayList<>();
void addTag(String tag)
{
    tags.add(tag);
}
String removeTag(String tag)
{
    tags.remove(tag);
    return tag;
}
    public Product(String name, int price, int quantity, int id, ArrayList<String> tags) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
        this.tags = tags;
}
    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
}
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", id=" + id +
                '}';
    }

    public Product(String name, int price, int quantity, int id) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.id = id;
    }




}
