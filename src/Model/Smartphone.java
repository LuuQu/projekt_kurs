package Model;

import java.awt.*;
import java.util.List;

public class Smartphone extends Electronics{
    private Color color;
    private int batteryCapacity;
    private Product phoneCase;

    public Smartphone(int id, String name, float price, int amount, Color color, int batteryCapacity, Product phoneCase) {
        this(id, name, price, amount);
        this.color = color;
        this.batteryCapacity = batteryCapacity;
        this.phoneCase = phoneCase;
    }
    public Smartphone(int id, String name, float price, int amount) {
        super(id, name, price, amount);
    }

    public Product getPhoneCase() {
        return phoneCase;
    }

    public void setPhoneCase(Product phoneCase) {
        this.phoneCase = phoneCase;
    }

    public Color getColor() {
        return color;
    }
    public String printColor() {
        return "Czerwony -> " + color.getRed()
                + ", Zielony -> " + color.getGreen()
                + ", Niebieski -> " + color.getBlue();
    }
    @Override
    public Smartphone copy() {
        return new Smartphone(this.getId(),
                this.getName(),
                this.getPrice(),
                this.getAmount(),
                this.color,
                this.batteryCapacity,
                this.phoneCase);
    }
    @Override
    public java.util.List<Product> addProductToList(List<Product> list) {
        phoneCase.setAmount(1);
        super.addProductToList(list);
        phoneCase.addProductToList(list);
        return list;
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
