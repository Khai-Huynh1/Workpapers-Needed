package application;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClientGenerator {
    private final Random random = new Random();
    private final List<String> firstNames = Arrays.asList(
    	    "John", "Jane", "Michael", "Sarah", "David", "Emily", "James", "Laura", "Robert", "Anna",
    	    "Khai", "Kenny", "Ryan", "Lebron", "Thomas", "Olivia", "William", "Sophia", "Charles",
    	    "Emma", "Daniel", "Ava", "Matthew", "Isabella", "Joseph", "Mia", "Billy", "Wojak", "Soyjak", "Chad"
    );
    private final List<String> lastNames = Arrays.asList(
    	    "Doe", "Smith", "Johnson", "Brown", "Taylor", "Wilson", "Davis", "Clark", "Harris", "Lewis",
    	    "Huynh", "Nguyen", "Masterton", "James", "Thompson", "Garcia", "Martinez", "Robinson",
    	    "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Hill", "Bob", "Wojakerson", "Sojakerson", "Chaderson"
    );
    private final List<Integer> ages = Arrays.asList(
    	    25, 28, 30, 32, 35, 38, 40, 42, 45, 50, 22, 27, 33, 36, 39, 43, 47, 52, 55, 60, 65, 70
    );
    private final List<String> addresses = Arrays.asList(
    	    "123 Main St", "456 Oak Ave", "789 Pine Rd", "101 Maple Dr", "202 Birch Ln",
    	    "303 Cedar Ct", "404 Elm St", "505 Walnut Ave", "606 Spruce Rd", "707 Chestnut Dr",
    	    "808 Willow Way", "909 Sycamore St", "111 Laurel Ln", "222 Hazel Dr", "333 Magnolia Ct",
    	    "444 Poplar Ave", "555 Ash Rd", "666 Linden St", "777 Hickory Ln", "888 Olive Dr",
    	    "999 Cedarwood Ct", "1010 Evergreen Ave"
    );

    public Client generateClient() {
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));
        String fullName = firstName + " " + lastName;
        int age = ages.get(random.nextInt(ages.size()));
        String address = addresses.get(random.nextInt(addresses.size()));
        return new Client(fullName, age, address);
    }

    public Identification generateMismatchedId(Client client) {
        // 30% chance for each field to be incorrect
        String name = client.getName();
        int age = client.getAge();
        String address = client.getAddress();

        if (random.nextDouble() < 0.3) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            name = firstName + " " + lastName;
            // Ensure the mismatched name is different
            while (name.equals(client.getName())) {
                firstName = firstNames.get(random.nextInt(firstNames.size()));
                lastName = lastNames.get(random.nextInt(lastNames.size()));
                name = firstName + " " + lastName;
            }
        }

        if (random.nextDouble() < 0.3) {
            age = ages.get(random.nextInt(ages.size()));
            // Ensure the mismatched age is different
            while (age == client.getAge()) {
                age = ages.get(random.nextInt(ages.size()));
            }
        }

        if (random.nextDouble() < 0.3) {
            address = addresses.get(random.nextInt(addresses.size()));
            // Ensure the mismatched address is different
            while (address.equals(client.getAddress())) {
                address = addresses.get(random.nextInt(addresses.size()));
            }
        }

        return new Identification(name, age, address);
    }
}