package com.company;

public class Car {
    private final String carId;
    private final String brand;
    private final String model;
    private final double pricePerDay;
    private boolean available;

    public Car(String carId, String brand, String model, double pricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.available = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void rent() {
        this.available = false;
    }

    public void returnCar() {
        this.available = true;
    }

    public double calculatePrice(int days) {
        return pricePerDay * days;
    }

    @Override
    public String toString() {
        return String.format("%s - %s %s - ₹%.2f/day", carId, brand, model, pricePerDay);
    }
}
