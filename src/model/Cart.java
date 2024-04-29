package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> productList;

    public Cart() {
        productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        var productInList = productList.stream()
                .filter(item -> item.getId() == product.getId())
                .findFirst();
        if (productInList.isEmpty()) {
            productList.add(product);
            return;
        }
        productInList.get().setAmount(productInList.get().getAmount() + product.getAmount());
    }

    private void addNumOfProducts(Product product, int amount) {
        productList.stream()
                .filter(item -> item.getId() == product.getId())
                .findFirst()
                .ifPresent(item -> item.setAmount(item.getAmount() + amount));
    }

    public boolean removeProduct(Product product) {
        return productList.remove(product);
    }

    public boolean removeProduct(int productId) {
        return productList.remove(productList.get(productId));
    }

    public boolean removeProduct(Product product, int amount) {
        var productInList = productList.stream().filter(item -> item.getId() == product.getId()).findFirst();
        if (productInList.isEmpty()) {
            return false;
        }
        if (productInList.get().getAmount() > amount) {
            return false;
        }
        productInList.get().setAmount(productInList.get().getAmount() - amount);
        return true;
    }

    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Zawartość koszyka:");
        stringBuilder.append("\n");
        for (Product p : productList) {
            stringBuilder.append(p.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
