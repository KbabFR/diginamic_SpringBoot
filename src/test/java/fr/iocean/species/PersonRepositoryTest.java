package fr.iocean.species;

import fr.iocean.species.model.Person;
import fr.iocean.species.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class PersonRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    PersonRepository repo;

    @BeforeEach
    private void initData() {
        System.out.println("Before Each");
        em.clear();
        Person p2 = new Person();
        p2.setFirstname("James");
        p2.setLastname("Tour");
        em.persist(p2);
        Person p3 = new Person();
        p3.setFirstname("Toto");
        p3.setLastname("Toto");
        em.persist(p3);
        em.flush(); // on flush pour forcer l'entityManager à exécuter ses opérations
    }

    @Test
    public void findByLastnameOrFirstnameTest() {
        List<Person> list = this.repo.findByLastnameOrFirstname("Tour", "Toto");
        Assertions.assertEquals(2, list.size());

        list = this.repo.findByLastnameOrFirstname("Toto", null);
        Assertions.assertEquals(1, list.size());

        list = this.repo.findByLastnameOrFirstname(null, null);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void test2() {

        System.out.println("test2");
    }
    @Test
    public void test3() {

        System.out.println("test3");
    }
}
