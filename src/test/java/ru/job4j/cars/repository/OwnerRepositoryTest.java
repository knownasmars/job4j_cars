package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

import org.hibernate.SessionFactory;

import ru.job4j.cars.config.HbmTestConfig;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

class OwnerRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
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
    public void whenAddOwner() {
        User user = new User(1, "test", "test");
        userRepository.create(user);
        Owner owner = new Owner(1, "test", user);
        ownerRepository.add(owner);
        assertThat(owner).isEqualTo(ownerRepository.findById(owner.getId()).get());
    }

    @Test
    public void whenDeleteOwner() {
        User user = new User(1, "test", "test");
        userRepository.create(user);
        Owner owner = new Owner(1, "test", user);
        ownerRepository.add(owner);
        boolean result = ownerRepository.delete(owner.getId());
        assertThat(result).isTrue();
    }

    @Test
    public void whenUpdateOwner() {
        User user = new User(1, "test", "test");
        userRepository.create(user);
        Owner owner = new Owner(1, "test", user);
        ownerRepository.add(owner);
        Owner updatingOwner = new Owner(owner.getId(), "test2", user);
        ownerRepository.update(updatingOwner);
        assertThat(updatingOwner.getName()).isEqualTo(ownerRepository.findById(owner.getId()).get().getName());
    }
}