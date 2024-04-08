package Model;

import Enums.ComputerComponents;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManager {
    private int availableId = 0;
    private final List<Product> productList;
    private final HashMap<Integer,ComputerComponents> computerComponentsHashMap;
    public ProductManager() {
        productList = new ArrayList<>();
        computerComponentsHashMap = new HashMap<>();
    }
    public boolean addProduct(String name, float price, int amount) {
        return this.addProduct(new Product(availableId,name,price,amount));
    }
    public boolean addProduct(String name, float price, int amount,ComputerComponents component) {
        return this.addProduct(new Product(availableId,name,price,amount),component);
    }
    private boolean addProduct(Product product, ComputerComponents component) {
        if(this.addProduct(product)) {
            computerComponentsHashMap.put(product.getId(),component);
            return true;
        }
        return false;
    }
    private boolean addProduct(Product product) {
        var productInList = productList.stream()
                .filter(item -> item.hashCode() == product.hashCode())
                .findFirst();
        if(productInList.isEmpty()) {
            productList.add(product);
            availableId++;
            return true;
        }
        return false;
    }
    public boolean deleteProduct(int id) {
        if(id < 0) {
            return false;
        }
        if(id >= productList.size()) {
            return false;
        }
        productList.remove(id);
        computerComponentsHashMap.remove(id);
        return true;
    }
    public boolean deleteProduct(Product product) {
        if(productList.remove(product)) {
            computerComponentsHashMap.remove(product.getId());
            return true;
        }
        return false;
    }
    public boolean updateProduct(int id, String name, float price, int amount) {
        return this.updateProduct(new Product(id,name,price,amount));
    }
    public boolean updateProduct(int id, String name, float price, int amount,ComputerComponents component) {
        computerComponentsHashMap.put(id,component);
        return this.updateProduct(id,name,price,amount);
    }
    public boolean updateProduct(Product product) {
        return true;
    }
    public boolean updateProduct(Product product,ComputerComponents component) {
        computerComponentsHashMap.put(product.getId(), component);
        return this.updateProduct(product);
    }
    public List<Product> getProductList() {
        return productList;
    }
    public List<Product> getProductList(ComputerComponents component) {
        List<Product> result = new ArrayList<>();
        for (Map.Entry<Integer, ComputerComponents> entry : computerComponentsHashMap.entrySet()) {
            if(entry.getValue() == component) {
                productList.stream()
                        .filter(product -> product.getId() == entry.getKey())
                        .findFirst()
                        .ifPresent(result::add);
            }
        }
        return result;
    }
}
