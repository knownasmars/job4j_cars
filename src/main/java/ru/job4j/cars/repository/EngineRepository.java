package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EngineRepository {

    private final CrudRepository repository;

    public Engine add(Engine engine) {
        repository.run(session -> session.persist(engine));
        return engine;
    }

    public void update(Engine engine) {
        repository.run(session -> session.merge(engine));
    }

    public void delete(int id) {
        repository.run(
                "delete from Engine where id = :fId",
                Map.of("fId", id)
        );
    }

    public Optional<Engine> findById(int id) {
        return repository.optional(
                "from Engine where id = :fId", Engine.class,
                Map.of("fId", id)
        );
    }
}
