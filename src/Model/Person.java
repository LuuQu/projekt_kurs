package Model;

public class Person {
    private String name;
    private String lastName;
    private String login;
    private String password;
    public Person(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }
    public Person(String name, String lastName, String login, String password) {
        this(name,lastName);
        this.login = login;
        this.password = password;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name + " " + lastName;
    }
}
