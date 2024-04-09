package Controllers;

import Enums.*;
import Model.*;

import java.lang.reflect.Method;
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
        users = new ArrayList<>();
        methodsToInvoke = new ArrayDeque<>();
        argsToInvoke = new ArrayList<>();
        addData();
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
        System.out.println("Imię:");
        personBuilder.name(getScannerString());
        System.out.println("Nazwisko:");
        personBuilder.lastName(getScannerString());
        while(true) {
            System.out.println("Login:");
            String login = getScannerString();
            if(users.stream().filter(item -> item.getLogin().equals(login)).count() != 0) {
                System.out.println("Podany login jest już zajęty");
                continue;
            }
            personBuilder.login(login);
            break;
        }
        System.out.println("Hasło:");
        personBuilder.password(getScannerString());
        users.add(personBuilder.build());
        System.out.println();
        System.out.println("Rejestracja przebiegła pomyślnie.");
        System.out.println();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        this.listWithProducts(1,TypeOfPage.SHOP);
    }
    private void cardList() {
        activePage = TypeOfPage.CART;
        this.listWithProducts(1,TypeOfPage.CART);
    }
    private void listWithProducts(Integer page, TypeOfPage typeOfPage) {
        clearConsole();
        printMenu();
        System.out.println("Produkty:");
        System.out.println();
        while (true) {
            try {
                if(typeOfPage == TypeOfPage.CART) {
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
                argsToInvoke = getListOfObjects(page - 1, typeOfPage);
                return;
            }
        }
        System.out.println();
        List<String> options = new ArrayList<>();
        System.out.println("n - Następna strona");
        System.out.println("p - Poprzednia strona");
        System.out.println("c - cofnij do głównego menu");
        System.out.println("0 - 9 podgląd przedmiotu");
        printOptions(options);
        HashMap<Character,String> methodOptions = new HashMap<>();
        HashMap<Character,List<Object>> methodArguments = new HashMap<>();
        methodOptions.put('n',"listWithProducts");
        methodOptions.put('p',"listWithProducts");
        methodOptions.put('c',"mainMenu");
        methodArguments.put('n',getListOfObjects(page + 1, typeOfPage));
        if(page == 1) {
            methodArguments.put('p',getListOfObjects(page, typeOfPage));
        }
        else {
            methodArguments.put('p',getListOfObjects(page - 1, typeOfPage));
        }
        for(int i = 0; i < 10; i++) {
            char c = (char)(i + '0');
            int index = i + (page - 1) * 10;
            if(typeOfPage == TypeOfPage.CART) {
                if(cart.getProductList().size() <= index) {
                    methodOptions.put(c,"listWithProducts");
                    methodArguments.put(c,getListOfObjects(page, typeOfPage));
                    continue;
                }
            }
            else {
                if(productsInShop.getProductList().size() <= index) {
                    methodOptions.put(c,"listWithProducts");
                    methodArguments.put(c,getListOfObjects(page, typeOfPage));
                    continue;
                }
            }
            methodOptions.put(c,"singleProduct");
            methodArguments.put(c,getListOfObjects(index, typeOfPage));
        }
        scannerPicker(methodOptions, methodArguments);
    }
    //pojedyńczy produkt
    private void singleProduct(Integer index, TypeOfPage typeOfPage) {
        clearConsole();
        Product product;
        try
        {
            product =  productsInShop.getProductList().get(index);
        }
        catch (Exception e) {
            System.out.println("Produkt poza zasięgiem");
            methodsToInvoke.add("listWithProducts");
            argsToInvoke = getListOfObjects((productsInShop.getProductList().size() - 1) % 10 + 1, activePage);
            return;
        }
        printMenu();
        System.out.println("Produkt:");
        System.out.println(product);
        List<String> options = new ArrayList<>();
        HashMap<Character,String> methodOptions = new HashMap<>();
        HashMap<Character,List<Object>> methodArguments = new HashMap<>();
        if(typeOfPage == TypeOfPage.CART) {
            //koszyk
            options.add("1. Zmień ilość produktu do zakupu");
            options.add("2. Usuń produkt z koszyka");
            methodOptions.put('1',"setAmountOfProductToBuy"); //modyfikuj ilość - index i wczytanie nowej wartości
            methodArguments.put('1',getListOfObjects(index));
            methodOptions.put('2',"deleteProduct"); //usunięcie przedmiotu - enum
            methodArguments.put('2',getListOfObjects(index));
//            if(product instanceof Computer || product instanceof Smartphone) {
//                options.add("3. Modyfikuj produkt");
//                methodOptions.put('3',"login"); //modyfikacja przedmiotu
//            }
        }
        else {
            //sklep
            options.add("1. Kup produkt");
            options.add("2. Cofnij");
            //methodOptions.put('1',"login"); //dodawanie przedmiotu - index
            methodOptions.put('1',"addProductToCart");
            methodArguments.put('1',getListOfObjects(index));
            methodOptions.put('2',"listWithProducts");
            methodArguments.put('2',getListOfObjects(1,typeOfPage));
        }
        printOptions(options);
        scannerPicker(methodOptions, methodArguments);
    }
    //zmiana ilość produktu w koszyku
    private void setAmountOfProductToBuy(Integer index) {
        Product product;
        try
        {
            product =  productsInShop.getProductList().get(index);
        }
        catch (Exception e) {
            System.out.println("Produkt poza zasięgiem");
            methodsToInvoke.add("listWithProducts");
            argsToInvoke = getListOfObjects((productsInShop.getProductList().size() - 1) % 10 + 1, activePage);
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int newAmount;
        while(true) {
            try {
                newAmount = scanner.nextInt();
                if(newAmount > product.getAmount() || newAmount <= 0) {
                    throw new Exception();
                }
                break;
            }
            catch (Exception e) {
                System.out.println("Wybierz liczbę pomiędzy 1 - " + product.getAmount());
            }
            finally {
                scanner.nextLine();
            }
        }
        if(activePage == TypeOfPage.CART) {
            int finalNewAmount = newAmount;
            cart.getProductList()
                    .stream()
                    .filter(item -> item.getId() == product.getId())
                    .findFirst()
                    .ifPresent(item -> {
                        item.setAmount(finalNewAmount);
                    });
        }
        else {
            cart.addProduct(product.copy());
        }
        methodsToInvoke.add("singleProduct");
        argsToInvoke = getListOfObjects(index, activePage);

    }
    private void deleteProduct(Integer index) {
        cart.removeProduct(index);
        methodsToInvoke.add("listWithProducts");
        argsToInvoke = getListOfObjects(1, activePage);
    }
    private void addProductToCart(Integer index) {
        try {
            var product = productsInShop.getProductList()
                    .stream()
                    .filter(item -> item.getId() == index)
                    .findFirst();
            if(product.isEmpty()) {
                throw new Exception("Brak przedmiotu w sklepie o podanym indeksie");
            }
            cart.addProduct(product.get());
        }
        catch (Exception e) {
            System.out.println(e);
        }
        methodsToInvoke.add("setAmountOfProductToBuy");
        argsToInvoke = getListOfObjects(index);
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
    }
    //wczytywanie pojedyńczego stringa
    private String getScannerString() {
        System.out.print("-> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
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
