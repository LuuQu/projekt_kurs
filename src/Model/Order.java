package Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class Order {
    private Person person;
    private List<Product> productList;
    private float amount;
    private Date date;
    private String gmt;
    public Order(Person person, List<Product> productList, LocalDateTime dateTime) {
        this.person = person;
        this.productList = productList;
        this.date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(dateTime, zoneId);
        this.gmt = zonedDateTime.getOffset().toString();
        calculateAmount();
    }
    private void calculateAmount() {
        amount = 0;
        for(Product p : productList) {
            amount += p.getAmount() * p.getPrice();
        }
    }

    public String getGmt() {
        return gmt;
    }

    public void setGmt(String gmt) {
        this.gmt = gmt;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }
}
