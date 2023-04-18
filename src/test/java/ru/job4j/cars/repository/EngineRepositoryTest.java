package ru.job4j.cars.repository;

import static org.assertj.core.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.junit.jupiter.api.*;

import ru.job4j.cars.config.HbmTestConfig;
import ru.job4j.cars.model.Engine;

class EngineRepositoryTest {
    private static SessionFactory sf;
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final EngineRepository engineRepository = new EngineRepository(crudRepository);

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
    public void whenAddEngine() {
        Engine engine = new Engine(1, "test");
        Engine result = engineRepository.add(engine);
        assertThat(engine).isEqualTo(result);
    }

    @Test
    public void whenUpdateEngine() {
        Engine engine = new Engine(1, "test");
        engineRepository.add(engine);
        Engine updatingEngine = new Engine(engine.getId(), "test1");
        engineRepository.update(updatingEngine);
        assertThat(updatingEngine.getName())
                .isEqualTo(engineRepository.findById(updatingEngine.getId()).get().getName());
    }

    @Test
    public void whenDeleteEngine() {
        Engine engine = new Engine(1, "test");
        engineRepository.add(engine);
        boolean result = engineRepository.delete(engine.getId());
        assertThat(result).isTrue();
    }
}