package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Smartphone extends Electronics{
    private Color color;
    private int batteryCapacity;
    ArrayList<Product> accessories;

    public Smartphone(int id, String name, float price, int amount, Color color, int batteryCapacity) {
        this(id, name, price, amount);
        this.color = color;
        this.batteryCapacity = batteryCapacity;
    }
    public Smartphone(int id, String name, float price, int amount) {
        super(id, name, price, amount);
        accessories = new ArrayList<Product>();

    }
    public void addAccessory(Product product) {
        accessories.add(product);
    }
    public void removeAccessory(Product product) {
        accessories.remove(product);
    }
    public void removeAccessory(int id) {
        Optional<Product> product = accessories.stream().filter(item -> item.getId() == id).findFirst();
        product.ifPresent(this::removeAccessory);
    }
    public void removeAccessory(String name) {
        Optional<Product> product = accessories.stream().filter(item -> Objects.equals(item.getName(), name)).findFirst();
        product.ifPresent(this::removeAccessory);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
}
