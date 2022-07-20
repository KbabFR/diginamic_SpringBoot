package fr.iocean.species;

import fr.iocean.species.enums.Sex;
import fr.iocean.species.model.Animal;
import fr.iocean.species.repository.AnimalRepository;
import fr.iocean.species.repository.PersonRepository;
import fr.iocean.species.repository.SpeciesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.Arrays;

@SpringBootApplication
public class SpeciesApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(SpeciesApplication.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpeciesApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // TP 04 - Requêtes dérivées du nom
//        testSpeciesRepo();
//        testPersonRepo();
//        testAnimalRepo();

        // TP 05 - @Query
//        testSpeciesRepo2();
//        testPersonRepo2();
//        testAnimalRepo2();

        // TP 06 - Intf custom
        testPersonRepoImpl();
    }

    private void testSpeciesRepo() {
        logger.info(">>> Espèce 'Chien' : {}", this.speciesRepository.findFirstByCommonName("Chien"));
        logger.info(">>> Espèces dont le nom latin contient 'feli' : {}",
                this.speciesRepository.findByLatinNameContainsIgnoreCase("feli"));
    }

    private void testSpeciesRepo2() {
        logger.info(">>> Toutes les Species ordonnées par nom commun ascendant, {}",
                this.speciesRepository.findSpeciesOrder());
        logger.info(">>> Toutes les Species qui commencent par 'C', {}",
                this.speciesRepository.findSpeciesWhereNameLike("C%"));
    }

    private void testPersonRepo() {
        logger.info(">>> Personnes qui ont pour nom Lamarque ou prénom Bill : {}",
                this.personRepository.findByLastnameOrFirstname("Lamarque", "Bill"));
        logger.info(">>> Personnes plus agée ou agée de 24 ans : {}",
                this.personRepository.findByAgeGreaterThanEqual(24));
    }

    private void testPersonRepo2() {
        logger.info(">>> Personnes qui ont un age compris entre 18 et 26 : {}",
                this.personRepository.findPersonWhereAgeBetween(18, 26));


        Animal animal1 = this.animalRepository.findById(1).orElse(null);
        logger.info(">>> Personnes qui sont les 'propriétaires' de l'animal id=1 (Solution 1) : {}",
                this.personRepository.findOwnersOfAnimal(animal1));

        logger.info(">>> Personnes qui sont les 'propriétaires' de l'animal id=1 (Solution 2) : {}",
                this.personRepository.findOwnersOfAnimal2(animal1));
    }

    private void testAnimalRepo() {
        logger.info(
            ">>> Animaux dont l'espèce est Chien : {}",
            this.animalRepository.findBySpecies(
                this.speciesRepository.findFirstByCommonName("Chien")
            )
        );

        logger.info(">>> Animaux qui sont soit Bruns Blancs ou Noirs : {}",
                this.animalRepository.findByColorIn(Arrays.asList("Blanc", "Brun", "Noir")));
    }

    private void testAnimalRepo2() {
        logger.info(">>> Nombre d'animaux dont le sexe est 'F' : {}",
                this.animalRepository.countBySex(Sex.F));

        Animal a6 = this.animalRepository.findById(6).orElse(null);
        logger.info(">>> Animal id=6 a-t-il un propriétaire ? {}",
                this.animalRepository.animalHasOwner(a6));

        logger.info(">>> Animal id=6 a-t-il un propriétaire ? (solution avec mapped by sans query) {}",
                a6.getPersons().isEmpty());


        // Ajout d'un nouvel animal sans propriétaire :
        Animal newAnim = new Animal();
        newAnim.setSpecies(this.speciesRepository.findFirstByCommonName("Chien"));
        newAnim.setColor("Brun");
        newAnim.setName("Babouche");
        newAnim.setSex(Sex.M);
        this.animalRepository.save(newAnim);
        logger.info(">>> Nouvel animal a-t-il un propriétaire ? {}",
                this.animalRepository.animalHasOwner(newAnim));

        // class cast exception
        logger.info(">>> Animal id=6 a-t-il un propriétaire ? {}",
                this.animalRepository.animalHasOwnerSql(6));
    }

    private void testPersonRepoImpl() {
        logger.info(">>> Supprimer les Personnes sans animaux");
        logger.info(">>> Nombre de personnes supprimées : {}",
            this.personRepository.deletePersonsWithoutAnimal());

        logger.info(">>> Insérer 20 personnes aléatoires");
        this.personRepository.insertRandomPersons(200);
    }
}
