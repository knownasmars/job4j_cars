package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.jupiter.api.*;

import ru.job4j.cars.config.HbmTestConfig;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class CarRepositoryTest {

    private static SessionFactory sf;

    private final CrudRepository crudRepository = new CrudRepository(sf);

    private final CarRepository carRepository = new CarRepository(crudRepository);

    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

    private final OwnerRepository ownerRepository = new OwnerRepository(crudRepository);

    private final UserRepository userRepository = new UserRepository(crudRepository);

    @BeforeAll
    public static void init() {
        sf = new HbmTestConfig().getSessionFactory();
    }

    @AfterAll
    public static void close() {
        sf.close();
    }

    @BeforeEach
    public void cleanDb() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM cars").executeUpdate();
            session.createQuery("DELETE FROM auto_users").executeUpdate();
            session.createQuery("DELETE FROM engines").executeUpdate();
            session.createQuery("DELETE FROM owners").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Test
    public void whenAddCarThenFindId() {
        Engine engine = new Engine(3, "testEngine");
        engineRepository.add(engine);
        User user = new User(3, "test", "test");
        userRepository.create(user);
        Owner owner = new Owner(3, "test", user);
        ownerRepository.add(owner);
        Car result = carRepository.add(new Car(1, "test", engine, Set.of(owner)));
        assertThat(result).isEqualTo(carRepository.findById(result.getId()).get());
    }

    @Test
    public void whenDeleteCar() {
        Engine engine = new Engine(1, "test");
        engineRepository.add(engine);
        User user = new User(1, "test", "test");
        userRepository.create(user);
        Owner owner = new Owner(1, "test", user);
        ownerRepository.add(owner);
        Car car = carRepository.add(new Car(1, "test", engine, Set.of(owner)));
        boolean result = carRepository.delete(car.getId());
        assertThat(result).isTrue();
    }

    @Test
    public void whenUpdateCar() {
        Engine engine = new Engine(1, "engine");
        engineRepository.add(engine);
        User user = new User(1, "test", "test");
        userRepository.create(user);
        Owner owner = new Owner(1, "test", user);
        ownerRepository.add(owner);
        carRepository.add(new Car(1, "test", engine, Set.of(owner)));
        Car updateCar = new Car(1, "test2", engine, Set.of(owner));
        boolean result = carRepository.update(updateCar);
        assertThat(result).isTrue();
    }
}