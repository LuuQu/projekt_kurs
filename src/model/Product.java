package model;

import java.util.List;
import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private float price;
    private int amount;

    public Product(int id, String name, float price, int amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Product> addProductToList(List<Product> list) {
        var itemInList = list
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst();
        if (itemInList.isEmpty()) {
            list.add(this.copy());
            return list;
        }
        Product p = itemInList.get();
        int newAmount = p.getAmount() + amount;
        p.setAmount(newAmount);
        return list;
    }

    public Product copy() {
        return new Product(this.id, this.name, this.price, this.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(price, product.price) == 0 && amount == product.amount && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Nazwa produktu: " + name + "\n"
                + "Cena: " + price + "\n"
                + "Ilość: " + amount + "\n"
                + "Suma: " + price * amount;
    }

    public String printLabel() {
        return name + ", cena: " + price;
    }
}
