package Controllers;

import Model.Order;
import Model.Person;
import Model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataController {
    private static final String userDataPATH = "src/Data/users.json";
    private static final String productsPATH = "src/Data/products.json";
    private static final String ordersPATH = "src/Data/orders.json";
    public DataController() {
        createFileIfDontExist(userDataPATH);
        createFileIfDontExist(productsPATH);
    }
    //Użytkownicy
    public static void addNewUser(Person person) {
        List<Person> users = getUsers();
        users.add(person);
        saveUsers(users);
    }
    public static List<Person> getUsers() {
        List<Person> users = new ArrayList<>();
        Gson gson = new Gson();
        createFileIfDontExist(userDataPATH);

        try (FileReader reader = new FileReader(userDataPATH)) {
            Type listType = new TypeToken<List<Person>>() {}.getType();
            List<Person> usersFromJson = gson.fromJson(reader, listType);
            if(usersFromJson == null) {
                return users;
            }
            users.addAll(usersFromJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public static void saveUsers(List<Person> persons) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(userDataPATH)) {
            gson.toJson(persons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Przedmioty w sklepie
    public static void addNewProductToShop(Product product) {
        List<Product> products = getProducts();
        products.add(product);
        saveProducts(products);
    }
    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Gson gson = new Gson();
        createFileIfDontExist(productsPATH);

        try (FileReader reader = new FileReader(productsPATH)) {
            Type listType = new TypeToken<List<Product>>() {}.getType();
            List<Product> productsFromJson = gson.fromJson(reader, listType);
            if(productsFromJson == null) {
                return products;
            }
            products.addAll(productsFromJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    public static void saveProducts(List<Product> persons) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(productsPATH)) {
            gson.toJson(persons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Zamówienia
    public static void saveNewOrder(Order order) {
        List<Order> orders = getOrders();
        orders.add(order);
        saveAllOrders(orders);
    }
    public static List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        Gson gson = new Gson();
        createFileIfDontExist(ordersPATH);

        try (FileReader reader = new FileReader(ordersPATH)) {
            Type listType = new TypeToken<List<Order>>() {}.getType();
            List<Order> ordersInJson = gson.fromJson(reader, listType);
            if(ordersInJson == null) {
                return orders;
            }
            orders.addAll(ordersInJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
    public static void saveAllOrders(List<Order> persons) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(ordersPATH)) {
            gson.toJson(persons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Pomocnicze
    private static void createFileIfDontExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
