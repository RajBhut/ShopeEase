package Db;

import java.util.ArrayList;

public class User
{
    String name;
    String email;
    String password;
    String role;
    int id;
ArrayList<Integer> Wishlist = new ArrayList<>();
ArrayList<Integer> Cart = new ArrayList<>();
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    void Add_To_Wishlist(int id)
    {
        Wishlist.add(id);
    }
    void Remove_From_Wishlist(int id)
    {
        Wishlist.remove(id);
    }
    void Add_To_Cart(int id)
    {
        Cart.add(id);
    }
    void Remove_From_Cart(int id)
    {
        Cart.remove(id);
    }
}
