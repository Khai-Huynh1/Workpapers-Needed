package application;

public class Client {
    private String name;
    private int age;
    private String address;

    // Constructor
    public Client(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    // Setters (optional, if you need to modify clients later)
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // toString for easy display in TextFlow
    @Override
    public String toString() {
        return "Name: " + name + "\nAge: " + age + "\nAddress: " + address;
    }
}