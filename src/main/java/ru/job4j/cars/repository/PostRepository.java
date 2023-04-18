package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class PostRepository {
    private final CrudRepository repository;

    public List<Post> findByLastDay() {
        return repository.query("FROM Post WHERE created >= :fLastDay", Post.class,
                Map.of("fLastDay", LocalDate.now().minusDays(1)));
    }

    public List<Post> findByWithPhoto() {
        return repository.query("SELECT p FROM Post p WHERE p.files.size > 0", Post.class);
    }

    public List<Post> findByMarkAuto(String name) {
        return repository.query("SELECT p FROM Post p WHERE p.car.name like :fName", Post.class,
                Map.of("fName", name));
    }
}
