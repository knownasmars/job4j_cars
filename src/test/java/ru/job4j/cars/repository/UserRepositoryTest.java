package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.jupiter.api.*;

import ru.job4j.cars.config.HbmTestConfig;
import ru.job4j.cars.model.User;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
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
    public void whenCreateUser() {
        User user = new User(1, "test", "test");
        userRepository.create(user);
        assertThat(user).isEqualTo(userRepository.findById(user.getId()).get());
    }

    @Test
    public void whenUpdateUser() {
        User user = new User(1, "test", "test");
        userRepository.create(user);
        User updatingUser = new User(user.getId(), "test2", "test2");
        userRepository.update(updatingUser);
        assertThat(updatingUser.getLogin()).isEqualTo(userRepository.findById(user.getId()).get().getLogin());
    }

    @Test
    public void whenDeleteUser() {
        User user = new User(1, "test", "test");
        userRepository.create(user);
        boolean result = userRepository.delete(user.getId());
        assertThat(result).isTrue();
    }
}