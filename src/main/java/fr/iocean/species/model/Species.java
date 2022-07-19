package fr.iocean.species.model;

import javax.persistence.*;

@Entity
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String commonName;

    @Column(length = 200)
    private String latinName;

    @Override
    public String toString() {
        return "Species{" +
                "id=" + id +
                ", commonName='" + commonName + '\'' +
                ", latinName='" + latinName + '\'' +
                '}';
    }

    // Getters / setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }
}
