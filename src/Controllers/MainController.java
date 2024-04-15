package Controllers;

import Enums.ComputerComponents;
import Enums.SmartphoneComponents;
import Enums.TypeOfPage;
import Model.*;

import java.awt.*;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Queue;
import java.util.*;

public class MainController {
    private Queue<String> methodsToInvoke;
    private List<Object> argsToInvoke;
    private TypeOfPage activePage;
    private List<Person> users;
    private Person loggedInPerson;
    private ProductManager productsInShop;
    private Cart cart;
    public MainController() {
        productsInShop = new ProductManager();
        cart = new Cart();
        users = DataController.getUsers();
        methodsToInvoke = new ArrayDeque<>();
        argsToInvoke = new ArrayList<>();
        for(Product p : DataController.getProducts()) {
            productsInShop.addProduct(p);
        }
//        addData();
//        DataController.saveProducts(productsInShop.getProductList());
    }
    // rozpoczęcie działania kontrolera
    public void main() {
        methodsToInvoke.add("loginMenu");
        while(!methodsToInvoke.isEmpty()) {
            invokeMethodByString(methodsToInvoke.poll(),argsToInvoke);
        }
    }

    ////////////     strony

    //ekran powitalny
    private void loginMenu() {
        clearConsole();
        List<String> options = new ArrayList<>();
        options.add("1. Zaloguj się");
        options.add("2. Zarejestruj się");
        options.add("3. Wyjście");
        printMenuAndOptions(options);
        HashMap<Character,String> methodOptions = new HashMap<>();
        methodOptions.put('1',"login");
        methodOptions.put('2',"register");
        methodOptions.put('3',"quit");
        scannerPicker(methodOptions, null);
    }
    //logowanie
    private void login() {
        clearConsole();
        printMenuAndOptions(new ArrayList<>());
        System.out.println("Nazwa użytkownika");
        String login = getScannerString();
        System.out.println("Hasło:");
        String password = getScannerString();
        var loggedUser = users.stream()
                .filter(item -> item.getLogin().equals(login) && item.getPassword().equals(password))
                .findFirst();
        if(loggedUser.isEmpty()) {
            System.out.println();
            System.out.println("Błędne dane logowania");
            System.out.println();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loginMenu();
            return;
        }
        loggedInPerson = loggedUser.get();
        methodsToInvoke.add("mainMenu");
        argsToInvoke = null;
    }
    //rejestracja
    private void register() {
        Person.PersonBuilder personBuilder = new Person.PersonBuilder();
        while (true) {
            System.out.println("Imię:");
            String name = getScannerString();
            if(name.isEmpty()) {
                System.out.println("Imie nie może być puste");
                continue;
            }
            personBuilder.name(name);
            break;
        }
        while (true) {
            System.out.println("Nazwisko:");
            String lastName = getScannerString();
            if(lastName.isEmpty()) {
                System.out.println("Nazwisko nie może być puste");
                continue;
            }
            personBuilder.lastName(lastName);
            break;
        }
        while(true) {
            System.out.println("Login:");
            String login = getScannerString();
            if(users.stream().filter(item -> item.getLogin().equals(login)).count() != 0) {
                System.out.println("Podany login jest już zajęty");
                continue;
            }
            if(login.isEmpty()) {
                System.out.println("Login nie może być pusty");
                continue;
            }
            personBuilder.login(login);
            break;
        }
        while (true) {
            System.out.println("Hasło:");
            String password = getScannerString();
            if(password.isEmpty()) {
                System.out.println("Hasło nie może być puste");
                continue;
            }
            personBuilder.lastName(password);
            break;
        }
        users.add(personBuilder.build());
        System.out.println();
        System.out.println("Rejestracja przebiegła pomyślnie.");
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DataController.saveUsers(users);
        methodsToInvoke.add("loginMenu");
        argsToInvoke = null;
    }
    //menu
    private void mainMenu() {
        clearConsole();
        List<String> options = new ArrayList<>();
        options.add("1. Przeglądaj produkty w sklepie");
        options.add("2. Przjedź do koszyka");
        options.add("3. Wyloguj się");
        options.add("4. Wyjście");
        printMenuAndOptions(options);
        HashMap<Character,String> methodOptions = new HashMap<>();
        methodOptions.put('1',"productList");
        methodOptions.put('2',"cardList");
        methodOptions.put('3',"logout");
        methodOptions.put('4',"quit");
        scannerPicker(methodOptions, null);
    }
    //ekran z produktami do kupienia
    private void productList() {
        activePage = TypeOfPage.SHOP;
        this.listWithProducts(1);
    }
    private void cardList() {
        activePage = TypeOfPage.CART;
        this.listWithProducts(1);
    }
    private void listWithProducts(Integer page) {
        clearConsole();
        printMenu();
        System.out.println("Produkty:");
        System.out.println();
        while (true) {
            try {
                if(activePage == TypeOfPage.CART) {
                    printProducts(page, cart.getProductList());
                }
                else {
                    printProducts(page, productsInShop.getProductList());
                }
                break;
            }
            catch (Exception e) {
                System.out.println(e);
                methodsToInvoke.add("listWithProducts");
                argsToInvoke = getListOfObjects(page - 1);
                return;
            }
        }
        System.out.println();
        List<String> options = new ArrayList<>();
        System.out.println("n - Następna strona");
        System.out.println("p - Poprzednia strona");
        System.out.println("c - cofnij do głównego menu");
        if(activePage == TypeOfPage.CART) {
            System.out.println("z - zamów produkty z koszyka");
        }
        System.out.println("0 - 9 podgląd przedmiotu");
        printOptions(options);
        HashMap<Character,String> methodOptions = new HashMap<>();
        HashMap<Character,List<Object>> methodArguments = new HashMap<>();
        methodOptions.put('n',"listWithProducts");
        methodOptions.put('p',"listWithProducts");
        methodOptions.put('c',"mainMenu");
        methodArguments.put('n',getListOfObjects(page + 1));
        if(page == 1) {
            methodArguments.put('p',getListOfObjects(page));
        }
        else {
            methodArguments.put('p',getListOfObjects(page - 1));
        }
        if(activePage == TypeOfPage.CART) {
            methodOptions.put('z',"processOrder");
        }
        for(int i = 0; i < 10; i++) {
            char c = (char)(i + '0');
            int index = i + (page - 1) * 10;
            if(activePage == TypeOfPage.CART) {
                if(cart.getProductList().size() <= index) {
                    methodOptions.put(c,"listWithProducts");
                    methodArguments.put(c,getListOfObjects(page));
                    continue;
                }
            }
            else {
                if(productsInShop.getProductList().size() <= index) {
                    methodOptions.put(c,"listWithProducts");
                    methodArguments.put(c,getListOfObjects(page));
                    continue;
                }
            }
            methodOptions.put(c,"singleProduct");
            methodArguments.put(c,getListOfObjects(index));
        }
        scannerPicker(methodOptions, methodArguments);
    }
    //pojedyńczy produkt
    private void singleProduct(Integer index) {
        clearConsole();
        Product product;
        List<Product> list;
        if(activePage == TypeOfPage.SHOP) {
            list = productsInShop.getProductList();
        }
        else {
            list = cart.getProductList();
        }
        try
        {
            product =  list.get(index);
        }
        catch (Exception e) {
            System.out.println("Produkt poza zasięgiem");
            methodsToInvoke.add("listWithProducts");
            argsToInvoke = getListOfObjects((list.size() - 1) % 10 + 1);
            return;
        }
        printMenu();
        System.out.println("Produkt:");
        System.out.println(product);
        if (product instanceof Computer) {
            Computer computer = (Computer) product;
            System.out.print("Obudowa -> ");
            System.out.println(computer.getComputerCase().printLabel());
            System.out.print("Płyta główna -> ");
            System.out.println(computer.getMotherboard().printLabel());
            System.out.print("Procesor -> ");
            System.out.println(computer.getProcessor().printLabel());
            System.out.print("Pamięć RAM -> ");
            System.out.println(computer.getRam().printLabel());
            System.out.print("Dysk twardy -> ");
            System.out.println(computer.getHardDrive().printLabel());
            System.out.print("Karta graficzna -> ");
            System.out.println(computer.getGraphicsCard().printLabel());
            System.out.print("Zasilacz -> ");
            System.out.println(computer.getCharger().printLabel());
        }
        if(product instanceof Smartphone) {
            Smartphone smartphone = (Smartphone) product;
            System.out.print("Kolor -> ");
            System.out.println(smartphone.printColor());
            System.out.print("Pojemność baterii -> ");
            System.out.println(smartphone.getBatteryCapacity());
            System.out.print("Etui -> " + smartphone.getPhoneCase().printLabel());
        }
        System.out.println();
        List<String> options = new ArrayList<>();
        HashMap<Character,String> methodOptions = new HashMap<>();
        HashMap<Character,List<Object>> methodArguments = new HashMap<>();
        if(activePage == TypeOfPage.CART) {
            //koszyk
            options.add("1. Zmień ilość produktu do zakupu");
            methodOptions.put('1',"setAmountOfProductToBuy"); //modyfikuj ilość - index i wczytanie nowej wartości
            methodArguments.put('1',getListOfObjects(index));
        }
        else {
            //sklep
            options.add("1. Kup produkt");
            //methodOptions.put('1',"login"); //dodawanie przedmiotu - index
            methodOptions.put('1',"setAmountOfProductToBuy");
            methodArguments.put('1',getListOfObjects(index));
        }
        if(activePage == TypeOfPage.CART) {
            if(product instanceof Computer) {
                options.add("2. Zmień obudowę");
                options.add("3. Zmień płytę główną");
                options.add("4. Zmień procesor");
                options.add("5. Zmień pamięć RAM");
                options.add("6. Zmień dysk twardy");
                options.add("7. Zmień kartę graficzną");
                options.add("8. Zmień zasilacz");
                methodOptions.put('2',"changeComponentOfComputer");
                methodArguments.put('2',getListOfObjects(index,ComputerComponents.COMPUTER_CASE, 1));
                methodOptions.put('3',"changeComponentOfComputer");
                methodArguments.put('3',getListOfObjects(index,ComputerComponents.MOTHERBOARD, 1));
                methodOptions.put('4',"changeComponentOfComputer");
                methodArguments.put('4',getListOfObjects(index,ComputerComponents.PROCESSOR, 1));
                methodOptions.put('5',"changeComponentOfComputer");
                methodArguments.put('5',getListOfObjects(index,ComputerComponents.RAM, 1));
                methodOptions.put('6',"changeComponentOfComputer");
                methodArguments.put('6',getListOfObjects(index,ComputerComponents.HARD_DRIVE, 1));
                methodOptions.put('7',"changeComponentOfComputer");
                methodArguments.put('7',getListOfObjects(index,ComputerComponents.GRAPHICS_CARD, 1));
                methodOptions.put('8',"changeComponentOfComputer");
                methodArguments.put('8',getListOfObjects(index,ComputerComponents.CHARGER, 1));
            }
            if(product instanceof Smartphone) {
                options.add("2. Zmień kolor Telefonu");
                options.add("3. Zmień pojemność baterii");
                options.add("4. Zmień etui");
                methodOptions.put('2',"changeComponentOfSmartPhone");
                methodArguments.put('2',getListOfObjects(index,SmartphoneComponents.COLOR, 1));
                methodOptions.put('3',"changeComponentOfSmartPhone");
                methodArguments.put('3',getListOfObjects(index,SmartphoneComponents.BATTERY_CAPACITY, 1));
                methodOptions.put('4',"changeComponentOfSmartPhone");
                methodArguments.put('4',getListOfObjects(index,SmartphoneComponents.SMARTPHONE_CASE, 1));
            }
            options.add("9. Usuń produkt z koszyka");
            methodOptions.put('9',"deleteProduct"); //usunięcie przedmiotu - enum
            methodArguments.put('9',getListOfObjects(index));
        }
        options.add("c - Cofnij");
        methodOptions.put('c',"listWithProducts");
        methodArguments.put('c',getListOfObjects(1));
        printOptions(options);
        scannerPicker(methodOptions, methodArguments);
    }
    //zmiana ilość produktu w koszyku
    private void setAmountOfProductToBuy(Integer index) {
        Product product;
        List<Product> list;
        if(activePage == TypeOfPage.SHOP) {
            list = productsInShop.getProductList();
        }
        else {
            list = cart.getProductList();
        }
        try
        {
            product =  list.get(index);
        }
        catch (Exception e) {
            System.out.println("Produkt poza zasięgiem");
            methodsToInvoke.add("listWithProducts");
            argsToInvoke = getListOfObjects((list.size() - 1) % 10 + 1);
            return;
        }
        int maxAmount;
        if(activePage == TypeOfPage.SHOP) {
            maxAmount = product.getAmount();
        }
        else {
            try {
                maxAmount = productsInShop.getAmountOfProduct(product.getId());
            }
            catch (Exception e) {
                System.out.println(e);
                methodsToInvoke.add("productList");
                argsToInvoke = null;
                return;
            }
        }
        System.out.println("Podaj ilość produktu do zakupienia:");
        System.out.println("Wybierz pomiędzy 1 - " + maxAmount);
        System.out.print("-> ");
        Scanner scanner = new Scanner(System.in);
        int newAmount;
        while(true) {
            try {
                newAmount = scanner.nextInt();
                if(newAmount > maxAmount || newAmount <= 0) {
                    throw new Exception();
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Wybierz liczbę pomiędzy 1 - " + maxAmount);
                System.out.print("-> ");
            }
            finally {
                scanner.nextLine();
            }
        }
        if(activePage == TypeOfPage.CART) {
            product.setAmount(newAmount);
        }
        else {
            Product product1 = product.copy();
            product1.setAmount(newAmount);
            cart.addProduct(product1);
        }
        if(activePage == TypeOfPage.CART) {
            methodsToInvoke.add("singleProduct");
            argsToInvoke = getListOfObjects(index);
        }
        else {
            methodsToInvoke.add("listWithProducts");
            argsToInvoke = getListOfObjects(1);
        }

    }
    private void deleteProduct(Integer index) {
        cart.removeProduct(index);
        methodsToInvoke.add("listWithProducts");
        argsToInvoke = getListOfObjects(1);
    }
    private void changeComponentOfComputer(Integer index, ComputerComponents computerComponents, Integer page) {
        Computer product;
        try
        {
            product = (Computer)cart.getProductList().get(index);
        }
        catch (Exception e) {
            System.out.println("Produkt poza zasięgiem");
            methodsToInvoke.add("listWithProducts");
            argsToInvoke = getListOfObjects((cart.getProductList().size() - 1) % 10 + 1);
            return;
        }
        Product componentToChange;
        try {
            switch (computerComponents) {
                case COMPUTER_CASE -> componentToChange = product.getComputerCase();
                case MOTHERBOARD -> componentToChange = product.getMotherboard();
                case PROCESSOR -> componentToChange = product.getProcessor();
                case RAM -> componentToChange = product.getRam();
                case HARD_DRIVE -> componentToChange = product.getHardDrive();
                case GRAPHICS_CARD -> componentToChange = product.getGraphicsCard();
                case CHARGER -> componentToChange = product.getCharger();
                default -> throw new Exception();
            }
        }
        catch (Exception e) {
            System.out.println(e);
            return;
        }
        List<Product> productList = productsInShop.getProductList(computerComponents)
                .stream()
                .filter(item -> item.getId() != product.getId())
                .toList();
        if(productList.isEmpty()) {
            System.out.println("Brak innych opcji w sklepie dla wybranej części komputera.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            methodsToInvoke.add("singleProduct");
            argsToInvoke = getListOfObjects(index);
            return;
        }
        clearConsole();
        printMenu();
        System.out.println("Wybierz nową część: ");
        List<String> options = new ArrayList<>();
        for(int i = 0; i < productList.size(); i++) {
            options.add((i+1) + ". " + productList.get(i).printLabel());
        }
        printOptions(options);
        Scanner scanner = new Scanner(System.in);
        int chosenOption;
        while (true) {
            try {
                System.out.println();
                System.out.print("-> ");
                chosenOption = scanner.nextInt();
                if(chosenOption < 1 || chosenOption > productList.size() + 1){
                    throw new Exception();
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Wybierz jedną z opcji 1 - " + (productList.size() + 1));
            }
        }
        Product newPart = productList.get(chosenOption - 1);
        float amountDiffrence = newPart.getPrice() - componentToChange.getPrice();
        switch (computerComponents) {
            case COMPUTER_CASE -> product.setComputerCase(newPart);
            case MOTHERBOARD -> product.setMotherboard(newPart);
            case PROCESSOR -> product.setProcessor(newPart);
            case RAM -> product.setRam(newPart);
            case HARD_DRIVE -> product.setHardDrive(newPart);
            case GRAPHICS_CARD -> product.setGraphicsCard(newPart);
            case CHARGER -> product.setCharger(newPart);
        }
        product.setPrice(product.getPrice() + amountDiffrence);
        methodsToInvoke.add("singleProduct");
        argsToInvoke = getListOfObjects(index);
    }
    private void changeComponentOfSmartPhone(Integer index, SmartphoneComponents smartphoneComponents, Integer page) {
        Smartphone product;
        try
        {
            product = (Smartphone)cart.getProductList().get(index);
        }
        catch (Exception e) {
            System.out.println("Produkt poza zasięgiem");
            methodsToInvoke.add("listWithProducts");
            argsToInvoke = getListOfObjects((cart.getProductList().size() - 1) % 10 + 1);
            return;
        }
        clearConsole();
        printMenu();
        if(smartphoneComponents == SmartphoneComponents.COLOR) {
            System.out.println("Podaj nasycenie koloru czerwonego (0 - 255)");
            int red = getScannerInt(0,255);
            System.out.println("Podaj nasycenie koloru zielonego (0 - 255)");
            int green = getScannerInt(0,255);
            System.out.println("Podaj nasycenie koloru niebieskiego (0 - 255)");
            int blue = getScannerInt(0,255);
            product.setColor(new Color(red, green, blue));
            methodsToInvoke.add("singleProduct");
            argsToInvoke = getListOfObjects(index);
            return;
        }
        if(smartphoneComponents == SmartphoneComponents.BATTERY_CAPACITY) {
            System.out.println("Podaj pojemność baterii (3000 - 10 000");
            product.setBatteryCapacity(getScannerInt(3000,10000));
            methodsToInvoke.add("singleProduct");
            argsToInvoke = getListOfObjects(index);
            return;
        }

        List<Product> productList = productsInShop.getProductList(smartphoneComponents)
                .stream()
                .filter(item -> item.getId() != product.getId())
                .toList();
        if(productList.isEmpty()) {
            System.out.println("Brak innych opcji w sklepie dla wybranej części komputera.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            methodsToInvoke.add("singleProduct");
            argsToInvoke = getListOfObjects(index);
            return;
        }
        Product componentToChange = product.getPhoneCase();
        System.out.println("Wybierz nową część: ");
        List<String> options = new ArrayList<>();
        for(int i = 0; i < productList.size(); i++) {
            options.add((i+1) + ". " + productList.get(i).printLabel());
        }
        printOptions(options);
        Scanner scanner = new Scanner(System.in);
        int chosenOption;
        while (true) {
            try {
                System.out.println();
                System.out.print("-> ");
                chosenOption = scanner.nextInt();
                if(chosenOption < 1 || chosenOption > productList.size() + 1){
                    throw new Exception();
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Wybierz jedną z opcji 1 - " + (productList.size() + 1));
            }
        }
        Product newPart = productList.get(chosenOption - 1);
        float amountDiffrence = newPart.getPrice() - componentToChange.getPrice();
        product.setPhoneCase(componentToChange);
        product.setPrice(product.getPrice() + amountDiffrence);
        methodsToInvoke.add("singleProduct");
        argsToInvoke = getListOfObjects(index);

    }
    private void processOrder() {
        OrderProcessor orderProcessor = new OrderProcessor(new Order(loggedInPerson.copySimplifiedPerson(),cart.getProductList(), LocalDateTime.now()),productsInShop);
        orderProcessor.start();
        cart = null;
        methodsToInvoke.add("mainMenu");
        argsToInvoke = null;
    }

    ////////////     funkcje pomocnicze

    //funkcja do wylogowania
    private void logout() {
        loggedInPerson = null;
        methodsToInvoke.add("loginMenu");
        argsToInvoke = null;
    }

    //drukuje przedmioty do wyświetlenia w sklepie/koszyku
    private void printProducts(int page, List<Product> list) throws Exception {
        int listSize = list.size();
        if(listSize < 10) {
            for(Product p : list) {
                System.out.println(p.printLabel());
            }
            return;
        }
        if(page*10 - listSize > 10) {
            throw new Exception("Przekroczono zakres");
        }
        List<Product> resultList;
        if(listSize < page * 10) {
            resultList = list.subList((page - 1) * 10, listSize);
            System.out.println("Strona " + page + ", produkty " + ((page - 1) * 10 + 1) + " - " + listSize);
        }
        else {
            resultList = list.subList((page - 1) * 10, (page) * 10);
            System.out.println("Strona " + page + ", produkty " + ((page - 1) * 10 + 1) + " - " + (page * 10));
        }
        for(int i = 0; i < resultList.size(); i++) {
            System.out.println(i + ". " + resultList.get(i).printLabel());
        }
    }
    //czyszczenie konsoli (przesunięcie jej o 30 linijek)
    public static void clearConsole() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
    //dodanie przykładowych danych
    private void addData() {
        productsInShop.addProduct("Wiatrak",100,36);
        productsInShop.addProduct("Obudowa1",100,42, ComputerComponents.COMPUTER_CASE);
        productsInShop.addProduct("Obudowa2",150,23, ComputerComponents.COMPUTER_CASE);
        productsInShop.addProduct("Płyta główna",600,12, ComputerComponents.MOTHERBOARD);
        productsInShop.addProduct("Płyta główna2",900,8, ComputerComponents.MOTHERBOARD);
        productsInShop.addProduct("Procesor",500,16, ComputerComponents.PROCESSOR);
        productsInShop.addProduct("Procesor2",800,15, ComputerComponents.PROCESSOR);
        productsInShop.addProduct("Ram",200,120, ComputerComponents.RAM);
        productsInShop.addProduct("Ram2",300,86, ComputerComponents.RAM);
        productsInShop.addProduct("DyskSSD",200,347, ComputerComponents.HARD_DRIVE);
        productsInShop.addProduct("DyshHDD",50,86, ComputerComponents.HARD_DRIVE);
        productsInShop.addProduct("Karta graficzna RTX4060",1500,15, ComputerComponents.GRAPHICS_CARD);
        productsInShop.addProduct("Karta graficzna Radeon RX660",1000,43, ComputerComponents.GRAPHICS_CARD);
        productsInShop.addProduct("zasilacz 400W",80,15, ComputerComponents.CHARGER);
        productsInShop.addProduct("zasilacz 600W",250,43, ComputerComponents.CHARGER);
        Product computerCase = productsInShop.getProductList(ComputerComponents.COMPUTER_CASE).get(0);
        Product motherboard = productsInShop.getProductList(ComputerComponents.MOTHERBOARD).get(0);
        Product processor = productsInShop.getProductList(ComputerComponents.PROCESSOR).get(0);
        Product ram = productsInShop.getProductList(ComputerComponents.RAM).get(0);
        Product hardDrive = productsInShop.getProductList(ComputerComponents.HARD_DRIVE).get(0);
        Product graphicsCard = productsInShop.getProductList(ComputerComponents.GRAPHICS_CARD).get(0);
        Product charger = productsInShop.getProductList(ComputerComponents.CHARGER).get(0);
        Computer computer = new Computer.ComputerBuilder()
                .id(16)
                .name("komputer")
                .amount(5)
                .price(3180)
                .computerCase(computerCase)
                .motherboard(motherboard)
                .processor(processor)
                .ram(ram)
                .hardDrive(hardDrive)
                .graphicsCard(graphicsCard)
                .charger(charger)
                .build();
        productsInShop.addProduct(computer);
        productsInShop.addProduct("Zielone etui",20,400,SmartphoneComponents.SMARTPHONE_CASE);
        productsInShop.addProduct("Niebieskie etui",30,500,SmartphoneComponents.SMARTPHONE_CASE);
        productsInShop.addProduct("Czerwone etui",40,600,SmartphoneComponents.SMARTPHONE_CASE);
        productsInShop.addProduct("Czarne etui",50,700,SmartphoneComponents.SMARTPHONE_CASE);
        productsInShop.addProduct("Białe etui",60,800,SmartphoneComponents.SMARTPHONE_CASE);
        Smartphone smartphone = new Smartphone(22,
                "Telefon",
                800,
                25,
                new Color(255,128,0),
                5000,
                productsInShop.getProductList(SmartphoneComponents.SMARTPHONE_CASE).get(0));
        productsInShop.addProduct(smartphone);
    }
    //wczytywanie pojedyńczego stringa
    private String getScannerString() {
        System.out.print("-> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    private int getScannerInt(int min, int max) {
        int result;
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                System.out.print("-> ");
                result = scanner.nextInt();
                if(result >= min && result <= max) {
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Podaj liczbę od " + min + " do " + max);
            }
            finally {
                scanner.nextLine();
            }
        }
        return result;
    }
    //drukowanie nagłówka każdego okienka
    private void printMenuAndOptions(List<String> options) {
        printMenu();
        printOptions(options);
    }
    private void printMenu() {
        System.out.println("==================================================");
        System.out.println("               Sklep internetowy");
        System.out.println("==================================================");
        System.out.println();
    }
    private void printOptions(List<String> options) {
        for(String s : options) {
            System.out.println(s);
        }
        System.out.println();
    }
    private List<Object> getListOfObjects(Object... args) {
        List<Object> list = new ArrayList<>();
        Collections.addAll(list, args);
        return list;
    }
    //wybór jednej z x opcji za pomocą skanera i char-ów
    private void scannerPicker(HashMap<Character,String> methodNames, HashMap<Character,List<Object>> methodArguments) {
        Scanner scanner = new Scanner(System.in);
        char chosenOption;
        while (true) {
            try {
                System.out.println();
                System.out.print("-> ");
                chosenOption = scanner.nextLine().charAt(0);
                if(!methodNames.containsKey(chosenOption)){
                    throw new Exception();
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Wybierz jedną z opcji");
            }
        }
        if(methodArguments == null) {
            argsToInvoke = null;
        }
        else {
            argsToInvoke = methodArguments.get(chosenOption);
        }
        methodsToInvoke.add(methodNames.get(chosenOption));
    }
    private void invokeMethodByString(String methodName, List<Object> args) {
        try {
            if(args == null) {
                Method method = this.getClass().getDeclaredMethod(methodName);
                method.invoke(this);
                return;
            }
            Class<?>[] typeOfArgs = new Class[args.size()];
            Object[] arguments = new Object[args.size()]; // Tablica do przechowywania argumentów
            for (int i = 0; i < args.size(); i++) {
                typeOfArgs[i] = args.get(i).getClass();
                arguments[i] = args.get(i); // Przypisanie argumentów do tablicy
            }
            Method method = this.getClass().getDeclaredMethod(methodName, typeOfArgs);
            method.invoke(this, arguments); // Przekazanie argumentów jako osobne argumenty
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //zakończenie działania programu
    private void quit() {

    }
}
