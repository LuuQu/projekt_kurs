package Model;

import Enums.ComputerComponents;
import Enums.SmartphoneComponents;

public class ProductEnum {
    int id;
    ComputerComponents computerComponent;
    SmartphoneComponents smartphoneComponent;

    public ProductEnum(int id, ComputerComponents computerComponent) {
        this.id = id;
        this.computerComponent = computerComponent;
    }
    public ProductEnum(int id, SmartphoneComponents smartphoneComponent) {
        this.id = id;
        this.smartphoneComponent = smartphoneComponent;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ComputerComponents getComputerComponent() {
        return computerComponent;
    }

    public void setComputerComponent(ComputerComponents computerComponent) {
        this.computerComponent = computerComponent;
    }

    public SmartphoneComponents getSmartphoneComponent() {
        return smartphoneComponent;
    }

    public void setSmartphoneComponent(SmartphoneComponents smartphoneComponent) {
        this.smartphoneComponent = smartphoneComponent;
    }
}
