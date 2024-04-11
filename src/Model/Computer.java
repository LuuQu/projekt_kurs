package Model;

public class Computer extends Electronics {
    private Product computerCase;
    private Product motherboard;
    private Product processor;
    private Product ram;
    private Product hardDrive;
    private Product graphicsCard;
    private Product charger;

    public Computer(int id,
                    String name,
                    float price,
                    int amount,
                    Product computerCase,
                    Product motherboard,
                    Product processor,
                    Product ram,
                    Product hardDrive,
                    Product graphicsCard,
                    Product charger) {
        this(id, name, price, amount);
        this.computerCase = computerCase;
        this.motherboard = motherboard;
        this.processor = processor;
        this.ram = ram;
        this.hardDrive = hardDrive;
        this.graphicsCard = graphicsCard;
        this.charger = charger;
    }
    public Computer(ComputerBuilder computerBuilder) {
        this(computerBuilder.id,
                computerBuilder.name,
                computerBuilder.price,
                computerBuilder.amount,
                computerBuilder.computerCase,
                computerBuilder.motherboard,
                computerBuilder.processor,
                computerBuilder.ram,
                computerBuilder.hardDrive,
                computerBuilder.graphicsCard,
                computerBuilder.charger);
    }
    public Computer(int id, String name, float price, int amount) {
        super(id, name, price, amount);
    }

    public Product getComputerCase() {
        return computerCase;
    }

    public void setComputerCase(Product computerCase) {
        this.computerCase = computerCase;
    }

    public Product getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(Product motherboard) {
        this.motherboard = motherboard;
    }

    public Product getProcessor() {
        return processor;
    }

    public void setProcessor(Product processor) {
        this.processor = processor;
    }

    public Product getRam() {
        return ram;
    }

    public void setRam(Product ram) {
        this.ram = ram;
    }

    public Product getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(Product hardDrive) {
        this.hardDrive = hardDrive;
    }

    public Product getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(Product graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public Product getCharger() {
        return charger;
    }

    public void setCharger(Product charger) {
        this.charger = charger;
    }
    @Override
    public Computer copy() {
        return new Computer(this.getId(),
                this.getName(),
                this.getPrice(),
                this.getAmount(),
                this.computerCase,
                this.motherboard,
                this.processor,
                this.ram,
                this.hardDrive,
                this.graphicsCard,
                this.charger);
    }
    public static class ComputerBuilder {
        private final boolean[] basicAssigned = new boolean[4];
        private int id;
        private String name;
        private float price;
        private int amount;
        private Product computerCase;
        private Product motherboard;
        private Product processor;
        private Product ram;
        private Product hardDrive;
        private Product graphicsCard;
        private Product charger;
        public ComputerBuilder id(int id) {
            this.id = id;
            basicAssigned[0] = true;
            return this;
        }
        public ComputerBuilder name(String name) {
            this.name = name;
            basicAssigned[1] = true;
            return this;
        }
        public ComputerBuilder price(float price) {
            this.price = price;
            basicAssigned[2] = true;
            return this;
        }
        public ComputerBuilder amount(int amount) {
            this.amount = amount;
            basicAssigned[3] = true;
            return this;
        }
        public ComputerBuilder computerCase(Product computerCase) {
            this.computerCase = computerCase;
            return this;
        }
        public ComputerBuilder motherboard(Product motherboard) {
            this.motherboard = motherboard;
            return this;
        }
        public ComputerBuilder processor(Product processor) {
            this.processor = processor;
            return this;
        }
        public ComputerBuilder ram(Product ram) {
            this.ram = ram;
            return this;
        }
        public ComputerBuilder hardDrive(Product hardDrive) {
            this.hardDrive = hardDrive;
            return this;
        }
        public ComputerBuilder graphicsCard(Product graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }
        public ComputerBuilder charger(Product charger) {
            this.charger = charger;
            return this;
        }
        public Computer build() {
            for(boolean bool : basicAssigned) {
                if(!bool) {
                    throw new RuntimeException("Nie przypisano wszystkich podstawowych pól");
                }
            }
            return new Computer(this);
        }
    }
}
