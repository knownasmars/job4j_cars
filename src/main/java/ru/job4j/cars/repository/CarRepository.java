package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarRepository {

    private final CrudRepository repository;

    public Car add(Car car) {
        repository.run(session -> session.persist(car));
        return car;
    }

    public void update(Car car) {
        repository.run(session -> session.merge(car));
    }

    public void delete(int id) {
        repository.run(
                "delete from Car where id = :fId",
                Map.of("fId", id)
        );
    }

    public Optional<Car> findById(int id) {
        return repository.optional(
                "from Car where id = :fId", Car.class,
                Map.of("fId", id)
        );
    }
}
