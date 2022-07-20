package fr.iocean.species.repository;

import fr.iocean.species.model.Person;
import org.hibernate.query.NativeQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int deletePersonsWithoutAnimal() {
        Query sqlQuery = em.createNativeQuery("delete p from person p left join person_animals pa on p.id = pa.person_id where pa.animals_id is null");
        return sqlQuery.executeUpdate();
    }

    @Override
    public void insertRandomPersons(int numberToCreate) {
        Random rand = new Random();
        List<String> noms = Arrays.asList("Blanc", "Boudi", "Brahmi", "Brun", "Duflot", "Grobost", "Guigue", "Haned", "Mohamed", "Vignozzi", "Omari", "Ramier", "Randrianarivony", "Warin", "Mage");
        List<String> prenoms = Arrays.asList("David", "Mohand", "Sonia", "Justine", "Valentin", "Garmi", "Véronique", "Abderrahmane", "Amin", "Aurélie", "Ismail", "Alexandre", "Rijandrisolo", "Thomas", "Jordi");

        for (int i = 0; i < numberToCreate ; i++) {
            Person p = new Person();
            p.setAge(rand.nextInt(120));
            p.setFirstname(prenoms.get(rand.nextInt(prenoms.size())));
            p.setLastname(noms.get(rand.nextInt(noms.size())));
            em.persist(p);
        }
    }

}
