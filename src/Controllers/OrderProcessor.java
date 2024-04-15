package Controllers;

import Model.Order;
import Model.Product;
import Model.ProductManager;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class OrderProcessor extends Thread {
    private Order order;
    private ProductManager productManager;
    public OrderProcessor(Order order, ProductManager productManager) {
        this.order = order;
        this.productManager = productManager;
    }
    public void processOrder() {
        try {
            for(Product p : order.getProductList()) {
                int avaiableAmount = productManager.getAmountOfProduct(p.getId());
                if(p.getAmount() > avaiableAmount) {
                    StringBuilder builder = new StringBuilder();
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
            for(Product p : order.getProductList()) {
                productManager.removeAmountOfProduct(p);
            }
            generateInvoice();
        }
        catch (Exception ex) {
            System.out.println(ex);
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
