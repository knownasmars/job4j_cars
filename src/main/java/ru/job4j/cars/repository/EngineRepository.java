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
        repository.run(session -> session.save(engine));
        return engine;
    }

    public boolean update(Engine engine) {
        return repository.booleanRun(session -> {
            session.merge(engine);
            return true;
        });
    }

    public boolean delete(int id) {
        return repository.booleanRun(
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
