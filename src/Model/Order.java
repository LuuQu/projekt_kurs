package Model;

import java.util.List;

public class Order {
    private Person person;
    private List<Product> productList;
    private float amount;
    public Order(Person person, List<Product> productList) {
        this.person = person;
        this.productList = productList;
        calculateAmount();
    }
    private void calculateAmount() {
        amount = 0;
        for(Product p : productList) {
            amount += p.getAmount() * p.getPrice();
        }
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        calculateAmount();
    }
    public float getAmount() {
        return amount;
    }
}
