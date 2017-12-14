package org.shumskih.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.EAGER)
    private Set<Developer> developers;

    public Skill() {

    }

    public Skill(String name) {
        this.name = name;
    }

    public Skill(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "==========" + "\n" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n";
    }
}
