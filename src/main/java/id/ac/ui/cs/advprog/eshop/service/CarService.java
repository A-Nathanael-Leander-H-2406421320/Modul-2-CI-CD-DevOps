package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.List;

public interface CarService extends ReadService<Car>, WriteService<Car> {
    public Car create(Car car);

    public List<Car> findAll();

    Car findById(String carId);

    public void update(String carId, Car car);

    public void delete(String carId);
}
