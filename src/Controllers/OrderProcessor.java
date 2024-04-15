package Controllers;

import Model.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderProcessor extends Thread {
    private Order order;
    private ProductManager productManager;
    public OrderProcessor(Order order, ProductManager productManager) {
        this.order = order;
        this.productManager = productManager;
    }
    public List<Product> addProductToList(Product product, List<Product> list) {
        var itemInList = list
                .stream()
                .filter(item -> item.getId() == product.getId())
                .findFirst();
        if(itemInList.isEmpty()) {
            list.add(product.copy());
            return list;
        }
        setAmountOfProduct(itemInList.get(),itemInList.get().getAmount() + product.getAmount());
        return list;
    }
    public Product setAmountOfProduct(Product p, int amount) {
        p.setAmount(amount);
        return p;
    }
    public void processOrder() {
        List<Product> listOfItemsToBuy = new ArrayList<>();
        for(Product p : order.getProductList()) {
            if(p instanceof Computer computer) {
                addProductToList(setAmountOfProduct(computer.getComputerCase(),1),listOfItemsToBuy);
                addProductToList(setAmountOfProduct(computer.getMotherboard(),1),listOfItemsToBuy);
                addProductToList(setAmountOfProduct(computer.getProcessor(),1),listOfItemsToBuy);
                addProductToList(setAmountOfProduct(computer.getRam(),1),listOfItemsToBuy);
                addProductToList(setAmountOfProduct(computer.getHardDrive(),1),listOfItemsToBuy);
                addProductToList(setAmountOfProduct(computer.getGraphicsCard(),1),listOfItemsToBuy);
                addProductToList(setAmountOfProduct(computer.getCharger(),1),listOfItemsToBuy);
            }
            if(p instanceof Smartphone smartphone) {
                addProductToList(setAmountOfProduct(smartphone.getPhoneCase(),1),listOfItemsToBuy);
            }
            addProductToList(p,listOfItemsToBuy);
        }
        try {
            for(Product p : listOfItemsToBuy) {
                int avaiableAmount = productManager.getAmountOfProduct(p.getId());
                if(p.getAmount() > avaiableAmount) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("Zamówienie nieudane");
                    builder.append("\n");
                    builder.append("Brak wymagającej ilości produktu w magazynie");
                    builder.append("\n");
                    builder.append("Produkt w zamówieniu:");
                    builder.append("\n");
                    builder.append(p.toString());
                    builder.append("\n");
                    builder.append("Ilość w magazynie:");
                    builder.append("\n");
                    builder.append(avaiableAmount);
                    throw new Exception(builder.toString());
                }
            }
            for(Product p : listOfItemsToBuy) {
                productManager.removeAmountOfProduct(p);
            }
            generateInvoice();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void generateInvoice() {
        DataController.saveNewOrder(order);
        DataController.saveProducts(productManager.getProductList());
        LocalDateTime localDateTime = order.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        // Uzyskaj strefę czasową dla aktualnej lokalizacji
        ZoneId zoneId = ZoneId.systemDefault();
        // Tworzenie obiektu ZonedDateTime z LocalDateTime i ZoneId
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);

        // Pobierz przesunięcie czasowe dla strefy czasowej
        String offset = zonedDateTime.getOffset().toString();
        System.out.println("Zamówienie wykonane pomyślnie");
        System.out.println("Godzina zamówienia: ");
        System.out.println(localDateTime.getDayOfMonth() + "-"
                + localDateTime.getMonthValue() + "-"
                + localDateTime.getYear()+ " "
                + localDateTime.getHour() + ":"
                + localDateTime.getMinute()+ ":"
                + localDateTime.getSecond() + " "
                + "UTC " + offset);
        System.out.println("Klient:");
        System.out.println(order.getPerson().toString());
        System.out.println("Zamówienie:");
        for(Product p : order.getProductList()) {
            System.out.println(p.toString());
        }
        System.out.println("Cena całkowita: ");
        System.out.println(order.getAmount());
        System.out.println();
    }
    @Override
    public void run() {
        processOrder();
    }
}
