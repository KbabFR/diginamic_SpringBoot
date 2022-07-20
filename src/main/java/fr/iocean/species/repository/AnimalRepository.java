package fr.iocean.species.repository;

import fr.iocean.species.enums.Sex;
import fr.iocean.species.model.Animal;
import fr.iocean.species.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    /**
     * Renvoie tous les animaux de la Species fournie en paramètre
     * @param species la Species des animaux retournés
     */
    List<Animal> findBySpecies(Species species);

    /**
     * Renvoie tous les animaux dont la couleur fait partie de la liste de couleurs fournie
     * @param colors la liste de couleurs dont la couleur des animaux retournés fait partie
     */
    List<Animal> findByColorIn(List<String> colors);

    /**
     * Renvoie le nombre d’Animaux dont le Sex est égal à la valeur donnée en paramètres
     * @param sex le Sexe que les animaux à dénombrer ont
     */
    @Query("select count(a) from Animal a where a.sex = :sex")
    Integer countBySex(@Param("sex") Sex sex);


    /**
     * Renvoie true si l’animal fourni « appartient » à au moins une personne, false sinon.
     * Solution qui n'utilise pas le mapped by
     */
    @Query("select case when count(p) > 0 then true else false end" +
            " from Person p where :animal member of p.animals ")
    Boolean animalHasOwner(@Param("animal") Animal animal);

    /**
     * Renvoie true si l’animal fourni « appartient » à au moins une personne, false sinon.
     * EN SQL (à corriger : class cast exception quand on utilise cette méthode)
     */
//    @Query(value = "select NOT EXISTS (" +
//            " select * from animal" +
//            " join person_animals on animals_id = animal.id" +
//            " join person on person.id = person_id" +
//            " where animal.id = :animalId" +
//            " )", nativeQuery = true)
    @Query(value = "select count(*) > 0 from person_animals where animals_id = :animalId", nativeQuery = true)
    Boolean animalHasOwnerSql(@Param("animalId") Integer animalId);

}
