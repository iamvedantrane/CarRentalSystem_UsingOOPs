package com.company;

import java.util.*;

public class CarRentalSystem {

    private final List<Car> cars = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Rental> rentals = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CarRentalSystem system = new CarRentalSystem();
        system.seedCars();   
        system.runConsole();   
    }

    private void seedCars() {
        cars.add(new Car("C001", "Toyota", "Fortuner", 3500));
        cars.add(new Car("C002", "Hyundai", "Creta", 2200));
        cars.add(new Car("C003", "Mahindra", "XUV700", 2800));
    }

    private void runConsole() {
        while (true) {
        	System.out.println("*****************************************");
            System.out.println("*           CAR RENTAL MENU             *");
            System.out.println("*****************************************");
            System.out.println("* 1. Show Available Cars                *");
            System.out.println("* 2. Rent a Car                         *");
            System.out.println("* 3. Return a Car                       *");
            System.out.println("* 4. Exit                               *");
            System.out.println("*****************************************");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showAvailableCars();
                case "2" -> rentCar();
                case "3" -> returnCar();
                case "4" -> {
                    System.out.println("Thank you for using Car Rental System!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showAvailableCars() {
        System.out.println("\nAvailable Cars:");
        boolean anyAvailable = false;
        for (Car car : cars) {
            if (car.isAvailable()) {
                System.out.println(car);
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("No cars are currently available.");
        }
    }

    private void rentCar() {
        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();
        String customerId = "CUST" + (customers.size() + 1);
        Customer customer = new Customer(customerId, name);
        customers.add(customer);

        showAvailableCars();

        System.out.print("\nEnter Car ID to rent: ");
        String carId = scanner.nextLine();

        Car selectedCar = findCarById(carId, true);
        if (selectedCar == null) {
            System.out.println("Invalid or unavailable car ID.");
            return;
        }

        System.out.print("Enter number of rental days: ");
        int days;
        try {
            days = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Rental cancelled.");
            return;
        }

        double total = selectedCar.calculatePrice(days);
        System.out.printf("Total Price: ₹%.2f\n", total);
        System.out.print("Confirm rental (Y/N): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            selectedCar.rent();
            rentals.add(new Rental(selectedCar, customer, days));
            System.out.println("Car rented successfully!");
        } else {
            System.out.println("Rental cancelled.");
        }
    }

    private void returnCar() {
        System.out.print("\nEnter Car ID to return: ");
        String carId = scanner.nextLine();

        Car car = findCarById(carId, false);
        if (car == null) {
            System.out.println("Invalid or not rented car ID.");
            return;
        }

        Rental rental = findRentalByCar(car);
        if (rental == null) {
            System.out.println("Rental not found.");
            return;
        }

        car.returnCar();
        rentals.remove(rental);
        System.out.println("Car returned successfully by " + rental.getCustomer().getName());
    }

    private Car findCarById(String id, boolean mustBeAvailable) {
        for (Car car : cars) {
            if (car.getCarId().equalsIgnoreCase(id)) {
                if (mustBeAvailable && !car.isAvailable()) return null;
                if (!mustBeAvailable && car.isAvailable()) return null;
                return car;
            }
        }
        return null;
    }

    private Rental findRentalByCar(Car car) {
        for (Rental rental : rentals) {
            if (rental.getCar().equals(car)) return rental;
        }
        return null;
    }
}
