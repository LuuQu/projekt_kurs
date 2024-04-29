package controllers;

import jsonAdapters.ColorAdapter;
import jsonAdapters.ComputerAdapter;
import jsonAdapters.ProductAdapter;
import jsonAdapters.SmartphoneAdapter;
import model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class DataController {
    private static final ReentrantLock userDataLock = new ReentrantLock();
    private static final ReentrantLock userDataAddSingleLock = new ReentrantLock();
    private static final String userDataPATH = "src/data/users.json";
    private static final ReentrantLock productsLock = new ReentrantLock();
    private static final ReentrantLock productsAddSingleLock = new ReentrantLock();
    private static final String productsPATH = "src/data/products.json";
    private static final ReentrantLock ordersLock = new ReentrantLock();
    private static final ReentrantLock ordersAddSingleLock = new ReentrantLock();
    private static final String ordersPATH = "src/data/orders.json";
    private static final ReentrantLock componentsLock = new ReentrantLock();
    private static final ReentrantLock componentsAddSingleLock = new ReentrantLock();
    private static final String componentsPATH = "src/data/components.json";

    public DataController() {
        createFileIfDontExist(userDataPATH);
        createFileIfDontExist(productsPATH);
        createFileIfDontExist(ordersPATH);
        createFileIfDontExist(componentsPATH);
    }

    //Użytkownicy
    public static void addNewUser(Person person) {
        userDataAddSingleLock.lock();
        List<Person> users = getUsers();
        users.add(person);
        saveUsers(users);
        userDataAddSingleLock.unlock();
    }

    public static List<Person> getUsers() {
        List<Person> users = new ArrayList<>();
        Gson gson = new Gson();
        createFileIfDontExist(userDataPATH);
        userDataLock.lock();
        try (FileReader reader = new FileReader(userDataPATH)) {
            Type listType = new TypeToken<List<Person>>() {
            }.getType();
            List<Person> usersFromJson = gson.fromJson(reader, listType);
            if (usersFromJson == null) {
                userDataLock.unlock();
                return users;
            }
            users.addAll(usersFromJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDataLock.unlock();
        return users;
    }

    public static void saveUsers(List<Person> persons) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        userDataLock.lock();
        try (FileWriter writer = new FileWriter(userDataPATH)) {
            gson.toJson(persons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userDataLock.unlock();
    }

    //Przedmioty w sklepie
    public static void addNewProductToShop(Product product) {
        productsAddSingleLock.lock();
        List<Product> products = getProducts();
        products.add(product);
        saveProducts(products);
        productsAddSingleLock.unlock();
    }

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Smartphone.class, new SmartphoneAdapter())
                .registerTypeAdapter(Computer.class, new ComputerAdapter())
                .registerTypeAdapter(Product.class, new ProductAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .create();
        createFileIfDontExist(productsPATH);

        productsLock.lock();
        try (FileReader reader = new FileReader(productsPATH)) {
            Type listType = new TypeToken<List<Product>>() {
            }.getType();
            List<Product> productsFromJson = gson.fromJson(reader, listType);
            if (productsFromJson == null) {
                productsLock.unlock();
                return products;
            }
            products.addAll(productsFromJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        productsLock.unlock();
        return products;
    }

    public static void saveProducts(List<Product> persons) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Smartphone.class, new SmartphoneAdapter())
                .registerTypeAdapter(Computer.class, new ComputerAdapter())
                .registerTypeAdapter(Product.class, new ProductAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .setPrettyPrinting()
                .create();
        productsLock.lock();
        try (FileWriter writer = new FileWriter(productsPATH)) {
            gson.toJson(persons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productsLock.unlock();
    }

    //Zamówienia
    public static void saveNewOrder(Order order) {
        ordersAddSingleLock.lock();
        List<Order> orders = getOrders();
        orders.add(order);
        saveAllOrders(orders);
        ordersAddSingleLock.unlock();
    }

    public static List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Smartphone.class, new SmartphoneAdapter())
                .registerTypeAdapter(Computer.class, new ComputerAdapter())
                .registerTypeAdapter(Product.class, new ProductAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .create();
        createFileIfDontExist(ordersPATH);
        ordersLock.lock();
        try (FileReader reader = new FileReader(ordersPATH)) {
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            List<Order> ordersInJson = gson.fromJson(reader, listType);
            if (ordersInJson == null) {
                ordersLock.unlock();
                return orders;
            }
            orders.addAll(ordersInJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ordersLock.unlock();
        return orders;
    }

    public static void saveAllOrders(List<Order> persons) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Smartphone.class, new SmartphoneAdapter())
                .registerTypeAdapter(Computer.class, new ComputerAdapter())
                .registerTypeAdapter(Product.class, new ProductAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .setPrettyPrinting()
                .create();
        ordersLock.lock();
        try (FileWriter writer = new FileWriter(ordersPATH)) {
            gson.toJson(persons, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ordersLock.unlock();
    }

    //Części smartfona/komputera
    public static void saveNewComponent(ProductEnum component) {
        componentsAddSingleLock.lock();
        List<ProductEnum> items = getComponents();
        items.add(component);
        saveComponents(items);
        componentsAddSingleLock.unlock();
    }

    public static List<ProductEnum> getComponents() {
        List<ProductEnum> items = new ArrayList<>();
        Gson gson = new Gson();
        createFileIfDontExist(componentsPATH);
        componentsLock.lock();
        try (FileReader reader = new FileReader(componentsPATH)) {
            Type listType = new TypeToken<List<ProductEnum>>() {
            }.getType();
            List<ProductEnum> itemsInJson = gson.fromJson(reader, listType);
            if (itemsInJson == null) {
                componentsLock.unlock();
                return items;
            }
            items.addAll(itemsInJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        componentsLock.unlock();
        return items;
    }

    public static void saveComponents(List<ProductEnum> components) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        componentsLock.lock();
        try (FileWriter writer = new FileWriter(componentsPATH)) {
            gson.toJson(components, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        componentsLock.unlock();
    }

    //Pomocnicze
    private static void createFileIfDontExist(String path) {
        switch (path) {
            case userDataPATH -> userDataLock.lock();
            case productsPATH -> productsLock.lock();
            case ordersPATH -> ordersLock.lock();
            case componentsPATH -> componentsLock.lock();
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        switch (path) {
            case userDataPATH -> userDataLock.unlock();
            case productsPATH -> productsLock.unlock();
            case ordersPATH -> ordersLock.unlock();
            case componentsPATH -> componentsLock.unlock();
        }
    }
}
