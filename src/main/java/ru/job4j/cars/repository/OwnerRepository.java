package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OwnerRepository {

    private final CrudRepository repository;

    public Owner add(Owner owner) {
        repository.run(session -> session.save(owner));
        return owner;
    }

    public boolean update(Owner owner) {
        return repository.booleanRun(session -> {
            session.merge(owner);
            return true;
        });
    }

    public boolean delete(int id) {
        return repository.booleanRun(
                "delete from Owner where id = :fId",
                Map.of("fId", id)
        );
    }

    public Optional<Owner> findById(int id) {
        return repository.optional(
                "from Owner where id = :fId", Owner.class,
                Map.of("fId", id)
        );
    }
}