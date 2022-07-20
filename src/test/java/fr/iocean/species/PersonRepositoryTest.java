package fr.iocean.species;

import fr.iocean.species.model.Person;
import fr.iocean.species.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
    private PersonRepository repo;

    @BeforeEach
    private void initData() {
        System.out.println("Before Each");
        em.clear();
        Person p2 = new Person();
        p2.setFirstname("James");
        p2.setLastname("Tour");
        p2.setAge(56);
        em.persist(p2);
        Person p3 = new Person();
        p3.setFirstname("Toto");
        p3.setLastname("Toto");
        p3.setAge(12);
        em.persist(p3);
        Person p4 = new Person();
        p4.setFirstname("Toto");
        p4.setLastname("Tour");
        p4.setAge(77);
        em.persist(p4);
        em.flush(); // on flush pour forcer l'entityManager à exécuter ses opérations
    }

    @Test
    public void findByLastnameOrFirstnameTest() {
        List<Person> list = this.repo.findByLastnameOrFirstname("Tour", "Toto");
        Assertions.assertEquals(3, list.size());

        list = this.repo.findByLastnameOrFirstname("Toto", null);
        Assertions.assertEquals(1, list.size());

        list = this.repo.findByLastnameOrFirstname(null, null);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void findByAgeGreaterThanEqual() {
        List<Person> test = this.repo.findByAgeGreaterThanEqual(56);
        Assertions.assertEquals(2, test.size());
    }

    @Test
    public void findPersonWhereAgeBetweenTest() {
        List<Person> test = this.repo.findPersonWhereAgeBetween(12, 76);
        Assertions.assertEquals(2, test.size());
    }

}
