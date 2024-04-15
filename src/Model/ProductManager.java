package Model;

import Enums.ComputerComponents;
import Enums.SmartphoneComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManager {
    private int availableId = 0;
    private final List<Product> productList;
    private final HashMap<Integer,ComputerComponents> computerComponentsHashMap;
    private final HashMap<Integer,SmartphoneComponents> smartphoneComponentsHashMap;
    public ProductManager() {
        productList = new ArrayList<>();
        computerComponentsHashMap = new HashMap<>();
        smartphoneComponentsHashMap = new HashMap<>();
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
    public boolean addProduct(String name, float price, int amount,SmartphoneComponents component) {
        return this.addProduct(new Product(availableId,name,price,amount),component);
    }
    private boolean addProduct(Product product, SmartphoneComponents component) {
        if(this.addProduct(product)) {
            smartphoneComponentsHashMap.put(product.getId(),component);
            return true;
        }
        return false;
    }
    public boolean addProduct(Product product) {
        var productInList = productList.stream()
                .filter(item -> item.hashCode() == product.hashCode())
                .findFirst();
        if(productInList.isEmpty()) {
            product.setId(availableId);
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
    public int getAmountOfProduct(Product product) throws Exception {
        return this.getAmountOfProduct(product.getId());
    }
    public int getAmountOfProduct(int productId) throws Exception {
        var product = productList.stream()
                .filter(item -> item.getId() == productId)
                .findFirst();
        if(product.isEmpty()) {
            throw new Exception("Produkt nie istnieje");
        }
        return product.get().getAmount();
    }
    public boolean updateProduct(int id, String name, float price, int amount) {
        return this.updateProduct(new Product(id,name,price,amount));
    }
    public boolean updateProduct(int id, String name, float price, int amount,ComputerComponents component) {
        computerComponentsHashMap.put(id,component);
        return this.updateProduct(id,name,price,amount);
    }
    public boolean updateProduct(int id, String name, float price, int amount,SmartphoneComponents component) {
        smartphoneComponentsHashMap.put(id,component);
        return this.updateProduct(id,name,price,amount);
    }
    public boolean updateProduct(Product product) {
        productList.stream()
                .filter(item -> item.getId() == product.getId())
                .findFirst()
                .ifPresent(item -> {
                    item.setName(product.getName());
                    item.setPrice(product.getPrice());
                    item.setAmount(product.getAmount());
                });
        return true;
    }
    public void removeAmountOfProduct(Product product) {
        productList.stream()
                .filter(item -> item.getId() == product.getId())
                .findFirst()
                .ifPresent(item -> item.setAmount(item.getAmount() - product.getAmount()));
    }
    public void addAmountOfProduct(Product product) {
        productList.stream()
                .filter(item -> item.getId() == product.getId())
                .findFirst()
                .ifPresent(item -> item.setAmount(item.getAmount() + product.getAmount()));
    }
    public boolean updateProduct(Product product,ComputerComponents component) {
        computerComponentsHashMap.put(product.getId(), component);
        return this.updateProduct(product);
    }
    public boolean updateProduct(Product product,SmartphoneComponents component) {
        smartphoneComponentsHashMap.put(product.getId(), component);
        return this.updateProduct(product);
    }
    public boolean addProductComponents(int productId, ComputerComponents component) {
        var product = productList
                .stream()
                .filter(item -> item.getId() == productId)
                .findFirst();
        if(product.isEmpty()) {
            return false;
        }
        computerComponentsHashMap.put(productId,component);
        return true;
    }
    public boolean addProductComponents(int productId, SmartphoneComponents component) {
        var product = productList
                .stream()
                .filter(item -> item.getId() == productId)
                .findFirst();
        if(product.isEmpty()) {
            return false;
        }
        smartphoneComponentsHashMap.put(productId,component);
        return true;
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
    public List<Product> getProductList(SmartphoneComponents component) {
        List<Product> result = new ArrayList<>();
        for (Map.Entry<Integer, SmartphoneComponents> entry : smartphoneComponentsHashMap.entrySet()) {
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
