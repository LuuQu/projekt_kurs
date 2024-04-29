# Dokumentacja aplikacji
Projekt jest systemem sklepu internetowego, który jest obsługiwany za pomocą konsoli

<h2>Kontrolery</h2>
<h3>DataController</h3> 
Jest on odpowiedzialny za połączenie do bazy danych w postaci plików JSON. 
<table>
  <tr>
    <th>Nazwa funkcji</th>
    <th>Opis</th>
  </tr>
  <tr>
    <td>addNewUser(Person person)</td>
    <td>dodaje nowego użytkownika do bazy danych (pliku JSON)</td>
  </tr>
  <tr>
    <td>getUsers()</td>
    <td>zwraca wszystkich zapisanych użytkowników w pliku JSON</td>
  </tr>
  <tr>
    <td>saveUsers(List<Person> persons)</td>
    <td>nadpisuje zapisanych uzytkowników listą osób do pliku JSON</td>
  </tr>
      
  <tr>
    <td>addNewProductToShop(Product product)</td>
    <td>dodaje nowy produkt do bazy danych (pliku JSON)</td>
  </tr>
  <tr>
    <td>getProducts()</td>
    <td>zwraca wszystkie zapisane produkty w pliku JSON</td>
  </tr>
  <tr>
    <td>saveProducts(List<Product> persons)</td>
    <td>nadpisuje zapisane produkty w pliku JSON listą produktów podaną w parametrze</td>
  </tr>
      
  <tr>
    <td>saveNewOrder(Order order)</td>
    <td>dodaje nowe zamówienie do bazy danych (pliku JSON)</td>
  </tr>
  <tr>
    <td>getOrders()</td>
    <td>zwraca wszystkie zapisane zamówienia w pliku JSON</td>
  </tr>
  <tr>
    <td>saveAllOrders(List<Order> persons)</td>
    <td>nadpisuje zapisane zamówienia w pliku JSON listą zamówień podaną w parametrze</td>
  </tr>
      
  <tr>
    <td>saveNewComponent(ProductEnum component)</td>
    <td>zapisuje pojedyńcze połączenie Produkt - Komponent komputera/telefonu do bazy danych (pliku JSON)</td>
  </tr>
  <tr>
    <td>getComponents()</td>
    <td>zwraca wszystkie zapisane połączenia Produkt - Komponent komputera/telefonu w pliku JSON</td>
  </tr>
  <tr>
    <td>saveComponents(List<ProductEnum> components)</td>
    <td>nadpisuje zapisane dane w pliku JSON listą połączeń Produkt - Komponent komputera/telefonu podaną w parametrze</td>
  </tr>
  <tr>
    <td>createFileIfDontExist(String path)</td>
    <td>tworzy plik JSON w przypadku, gdy nie został jeszcze stworzony</td>
  </tr>
</table>
<h3> MainController </h3>
<p>Główny kontroler aplikacji. Na tym pliku jest oparty interfejs, który steruje całą aplikacją</p>
<table>
  <tr>
    <th>Nazwa funkcji</th>
    <th>Opis</th>
  </tr>
  <tr>
    <td>main()</td>
    <td>Główna funkcja, która uruchamia poszczególne funkcje w trakcie działania programu</td>
  </tr>
  <tr>
    <td>loginMenu()</td>
    <td>Widok na wejściu do aplikacji. Z tego miejsca użytkownik loguje lub rejestruje się przed wejściem do okna głównego</td>
  </tr>
  <tr>
    <td>login()</td>
    <td>Funkcja odpowiedzialna za zalogowanie użytkownika</td>
  </tr>
  <tr>
    <td>register()</td>
    <td>Funkcja odpowiedzialna za rejestrację uzytkownika</td>
  </tr>
  <tr>
    <td>mainMenu()</td>
    <td>Widok menu właściwego. Z tego miejsca uzytkownik ma możliwość rozpoczecia przeglądania przedmiotów w sklepie, przejścia do koszyka, wylogowania lub wyjścia z aplikacji.</td>
  </tr>
  <tr>
    <td>productList()</td>
    <td>Ustawia tryb sklepu aplikacji oraz przełącza na funkcję <i>listWithProducts()</i></td>
  </tr>
  <tr>
    <td>cardList()</td>
    <td>Ustawia tryb koszyka aplikacji oraz przełącza na funkcję <i>listWithProducts()</i></td>
  </tr>
  <tr>
    <td>listWithProducts(Integer page)</td>
    <td>Wypisuje do 10 przedmiotów w sklepie/koszyku w zależności od aktualnego trybu aplikacji. Widok odpowiedzialny za wyświetlanie koszyka oraz przedmiotów w sklepie</td>
  </tr>
  <tr>
    <td>singleProduct(Integer index)</td>
    <td>Widok opisujący szegóły przedmiotu. W tym widoku następuje kupno z sklepu, edycja oraz usuwanie z koszyka przedmiotów</td>
  </tr>
  <tr>
    <td>setAmountOfProductToBuy(Integer index)</td>
    <td>Widok następujący po wybraniu opcji zakupu przedmiotu. Ustala ilość sztuk zakupowanego przedmiotu</td>
  </tr>
  <tr>
    <td>deleteProduct(Integer index)</td>
    <td>Funkcja usuwa produkt z koszyka</td>
  </tr>
  <tr>
    <td>changeComponentOfComputer(Integer index, ComputerComponents, Integer page)</td>
    <td>Funkcja odpowiedzialna za zamianę konkretnie podanej części komputera na inną dostępną w sklepie.</td>
  </tr>
  <tr>
    <td>changeComponentOfSmartPhone(Integer index, ComputerComponents, Integer page)</td>
    <td>Funkcja odpowiedzialna za zamianę konkretnie podanej części smartfonu (aktualnie jedynie etui telefonu) na inną dostępną w sklepie.</td>
  </tr>
  <tr>
    <td>processOrder()</td>
    <td>Tworzy wątek <i>OrderProcessor</i>, który obsługuje zamówienie, a następnie wraca do głównego menu <i>(mainMenu())</i></td>
  </tr>
  <tr>
    <td>logout()</td>
    <td>Wylogowuje użytkownika i wraca do ekranu powitalnego <i>(loginMenu())</i></td>
  </tr>
  <tr>
    <td>printProducts(int page, List<Product> list)</td>
    <td>Strona mająca za zadanie wypisać produkty na konkretnej stronie sklepu/koszyka (np. produkty <i>1-10</i>,<i>11-20</i> itp.)</td>
  </tr>
  <tr>
    <td>clearConsole()</td>
    <td>"Wyczyszczenie" konsoli (przesunięcie kursora o 30 linijek)</td>
  </tr>
  <tr>
    <td>addData()</td>
    <td>Funkcja mająca na celu dodanie przykładowych danych do koszyka. Używana jedynie w przypaku potrzeby resetu przedmiotów w pliku</td>
  </tr>
  <tr>
    <td>getScannerString()</td>
    <td>Zwraca pojedyńczą linijkę z konsoli</td>
  </tr>
  <tr>
    <td>getScannerInt(int min, int max)</td>
    <td>Zwraca liczbę z konsoli w zakresie <i>min - max</i></td>
  </tr>
  <tr>
    <td>printMenuAndOptions(List<String> options</td>
    <td>Wypisuje nagłowek oraz opcje dostępne w aktualnym ekranie</td>
  </tr>
  <tr>
    <td>printMenu()</td>
    <td>Wypisuje nagłówek w konsoli</td>
  </tr>
  <tr>
    <td>printOptions(List<String> options)</td>
    <td>Wypisuje aktualne opcje w oknie</td>
  </tr>
  <tr>
    <td>getListOfObjects(Object... args)</td>
    <td>Zwraca obiekty podane w argumencie w formacie listy obiektów. Funkcja używana do utworzenia listy argumentów funkcji do wywołania</td>
  </tr>
  <tr>
    <td>scannerPicker(HashMap<Character,String> methodNames, HashMap<Character,List<Object>> methodArguments)</td>
    <td>Funkcja zczytująca wybraną opcję w aktualnym oknie oraz przekierowywująca na kolejną funkcję. Przyjmuje od 1 do n znaków do wczytania oraz funkcje im odpowiadające</td>
  </tr>
  <tr>
    <td>invokeMethodByString(String methodName, List<Object> args)</td>
    <td>Miejsce wywołania kolejnej funkcji</td>
  </tr>
  <tr>
    <td>quit()</td>
    <td>Pusta funkcja umożliwiająca zakończenie działania programu</td>
  </tr>
</table>
<h3>OrderProcessor</h3>
Wątek odpowiedzialny za wykonanie złożonego zamówienia
<table>
  <tr>
    <th>Nazwa funkcji</th>
    <th>Opis</th>
  </tr>
  <tr>
    <td>addProductToList(Product product, List<Product> list)</td>
    <td>Funkcja pomocnicza, któa w trakcie tworzenia listy produktów do kupienia tworzy lub dodaje ilość sztuk do przedmiotu w liście</td>
  </tr>
  <tr>
    <td>setAmountOfProduct(Product p, int amount)</td>
    <td>Zmienia ilość sztuk w produkcie</td>
  </tr>
  <tr>
    <td>processOrder()</td>
    <td>Właściwa funkcja wątku. Tworzy pełną listę produktów, następnie sprawdza, czy może wykonać zmówienie, przetwarza je oraz wywołuje generowanie faktury</td>
  </tr>
  <tr>
    <td>generateInvoice()</td>
    <td>Tworzy fakturę, wypisuje ją na ekran oraz zapisuje ją do pliku</td>
  </tr>
  <tr>
    <td>run()</td>
    <td>Funkcja odpowiedzialna za uruchomienie wątku</td>
  </tr>
</table>
<h2>Data</h2>
Folder zawiera pliki JSON aplikacji<br><br>
<table>
  <tr>
    <th>plik</th>
    <th>opis</th>
  </tr>
  <tr>
    <td>components.json</td>
    <td>zbór id oraz przypisana konkretna część komputera lub telefonu</td>
  </tr>
  <tr>
    <td>orders.json</td>
    <td>zbiór wszystkich zamówień dokonanych w aplikacji</td>
  </tr>
  <tr>
    <td>products.json</td>
    <td>zbiór wszystkich przedmiotów dostępnych do kupienia w aplikacji</td>
  </tr>
  <tr>
    <td>users.json</td>
    <td>zbiór użytkowników w bazie aplikacji</td>
  </tr>
</table>
<h2>Enums</h2>
Folder zawiera wszystkie enumy wykorzystane w aplikacji<br><br>
<table>
  <tr>
    <th>plik</th>
    <th>opis</th>
  </tr>
  <tr>
    <td>ComputerComponents</td>
    <td>wszystkie możliwe części komputera do zmiany</td>
  </tr>
  <tr>
    <td>SmartphoneComponents</td>
    <td>wszystkie możliwe części smartfona do zmiany</td>
  </tr>
  <tr>
    <td>TypeOfPage</td>
    <td>informuje o aktualnym trybie działania aplikacji</td>
  </tr>
</table>
<h2>JsonAdapters</h2>
Folder zawierający adaptery pomiędzy głównymi obiektami aplikacji, a plikami JSON<br><br>
<table>
  <tr>
    <th>plik</th>
    <th>opis</th>
  </tr>
  <tr>
    <td>ColorAdapter</td>
    <td>Serializacja i deserializacja obiektu Color</td>
  </tr>
  <tr>
    <td>ComputerAdapter</td>
    <td>Serializacja i deserializacja obiektu Computer</td>
  </tr>
  <tr>
    <td>ProductAdapter</td>
    <td>Serializacja i deserializacja obiektu Product</td>
  </tr>
  <tr>
    <td>SmartphoneAdapter</td>
    <td>Serializacja i deserializacja obiektu Smartphone</td>
  </tr>
</table>
<h2>Models</h2>
Folder zawierający wszystkie modele użyte w aplikacji
<h3>Cart</h3>
model zawierający zbiór produktów do kupna
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>List&lt;Product&gt;</td>
    <td>productList</td>
  </tr>
</table>
<h3>Computer</h3>
model opisujący specjalny produkt zawierający 7 różnych części zamiennych.<br> 
Ze względu na obszerną ilość zmiennych, do modelu został zaimplementowany builder
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>int</td>
    <td>id</td>
  </tr>
  <tr>
    <td>String</td>
    <td>name</td>
  </tr>
  <tr>
    <td>float</td>
    <td>price</td>
  </tr>
  <tr>
    <td>int</td>
    <td>amount</td>
  </tr>
  <tr>
    <td>Product</td>
    <td>computerCase</td>
  </tr>
  <tr>
    <td>Product</td>
    <td>motherboard</td>
  </tr>
  <tr>
    <td>Product</td>
    <td>processor</td>
  </tr>
  <tr>
    <td>Product</td>
    <td>ram</td>
  </tr>
  <tr>
    <td>Product</td>
    <td>hardDrive</td>
  </tr>
  <tr>
    <td>Product</td>
    <td>graphicsCard</td>
  </tr>
  <tr>
    <td>Product</td>
    <td>charger</td>
  </tr>
</table>
<h3>Electronics</h3>
model dziedziczący po produkcie nie posiadający dodatkowych funkcji
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>int</td>
    <td>id</td>
  </tr>
  <tr>
    <td>String</td>
    <td>name</td>
  </tr>
  <tr>
    <td>float
    <td>price</td>
  </tr>
  <tr>
    <td>int</td>
    <td>amount</td>
  </tr>
</table>
<h3>Order</h3>
model służący do przechowywania wszystkich informacji o zamówieniu.
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>Person</td>
    <td>person</td>
  </tr>
  <tr>
    <td>List&lt;Product&gt;</td>
    <td>productList</td>
  </tr>
  <tr>
    <td>float</td>
    <td>amount</td>
  </tr>
  <tr>
    <td>Date</td>
    <td>date</td>
  </tr>
  <tr>
    <td>String</td>
    <td>gmt</td>
  </tr>
</table>
<h3>Person</h3>
model zawierający informacje o konkretnym użytkowniku
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>String</td>
    <td>name</td>=
  </tr>
  <tr>
    <td>String</td>
    <td>lastName</td>
  </tr>
  <tr>
    <td>String</td>
    <td>login</td>
  </tr>
  <tr>
    <td>String</td>
    <td>password</td>
  </tr>
</table>
<h3>Procuct</h3>
model zawierający informacje o podstawowym produkcie dostępnym w sklepie
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>int</td>
    <td>id</td>
  </tr>
  <tr>
    <td>String</td>
    <td>name</td>
  </tr>
  <tr>
    <td>float</td>
    <td>price</td>
  </tr>
  <tr>
    <td>int</td>
    <td>amount</td>
  </tr>
</table>
<h3>ProductEnum</h3>
model zawierający identyfikatory konkretnych części Komputera lub Smartfona
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>int</td>
    <td>id</td>
  </tr>
  <tr>
    <td>ComputerComponents</td>
    <td>computerComponent</td>
  </tr>
  <tr>
    <td>SmartphoneComponents</td>
    <td>smartphoneComponent</td>
  </tr>
</table>
<h3>ProductManager</h3>
model zawierający wszystkie przedmioty umieszczone w sklepie.<br>
Zawarto w nim CRUD do listy produktów. <br>
Zawiera 2 hashMap-y przechowujące informacje o indeksach zamiennych części do komputerów oraz telefonów
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>List&lt;Product&gt</td>
    <td>availableId</td>
  </tr>
  <tr>
    <td>HashMap&lt;Integer,ComputerComponents&gt</td>
    <td>computerComponentsHashMap</td>
  </tr>
  <tr>
    <td>HashMap&lt;Integer,SmartphoneComponents&gt</td>
    <td>smartphoneComponentsHashMap</td>
  </tr>
</table>
<h3>Smartphone</h3>
model opisujący specjalny produkt zawierający 3 zmienne części (Kolor, pojemność baterii oraz Etui)
<table>
  <tr>
    <th>Typ pola</th>
    <th>Nazwa</th>
  </tr>
  <tr>
    <td>Color</td>
    <td>color</td>
  </tr>
  <tr>
    <td>int</td>
    <td>batteryCapacity</td>
  </tr>
  <tr>
    <td>Product</td>
    <td>phoneCase</td>
  </tr>
</table>
