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
    public Person(PersonBuilder personBuilder) {
        this(personBuilder.name, personBuilder.lastName, personBuilder.login, personBuilder.password);
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
    public static class PersonBuilder {
        private final boolean[] assigned = new boolean[4];
        private String name;
        private String lastName;
        private String login;
        private String password;
        public PersonBuilder name(String name) {
            this.name = name;
            assigned[0] = true;
            return this;
        }
        public PersonBuilder lastName(String lastName) {
            this.lastName = lastName;
            assigned[1] = true;
            return this;
        }
        public PersonBuilder login(String login) {
            this.login = login;
            assigned[2] = true;
            return this;
        }
        public PersonBuilder password(String password) {
            this.password = password;
            assigned[3] = true;
            return this;
        }
        public Person build() {
            for(boolean bool : assigned) {
                if(!bool) {
                    throw new RuntimeException("Nie przypisano wszystkich pól");
                }
            }
            return new Person(this);
        }
    }
}
