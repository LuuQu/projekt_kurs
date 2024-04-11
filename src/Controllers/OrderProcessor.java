package Controllers;

import Model.Order;
import Model.Product;
import Model.ProductManager;

import java.util.Scanner;

public class OrderProcessor {
    public static void processOrder(Order order, ProductManager productManager) {
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
            generateInvoice(order);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public static void generateInvoice(Order order) {
        System.out.println("Zamówienie wykonane pomyślnie");
        System.out.println("Klient:");
        System.out.println(order.getPerson().toString());
        System.out.println("Zamówienie:");
        for(Product p : order.getProductList()) {
            System.out.println(p.toString());
        }
        System.out.println("Cena całkowita: ");
        System.out.println(order.getAmount());
        System.out.println();
        System.out.println("Naciśnij enter, aby kontynuować...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
