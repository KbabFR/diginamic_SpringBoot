package fr.iocean.species.repository;

public interface PersonRepositoryCustom {

    int deletePersonsWithoutAnimal();

    void insertRandomPersons(int numberToCreate);
}
