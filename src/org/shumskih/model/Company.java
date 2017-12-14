package org.shumskih.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="companies_projects",
            joinColumns = {@JoinColumn(name="company_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name="project_id", nullable = false)})
    private Set<Project> projects;

    public Company() {

    }

    public Company(String name) {
        this.name = name;
    }

    public Company(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company(String name, Set<Project> projects) {
        this.name = name;
        this.projects = projects;
    }
    public Company(Integer id, String name, Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
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

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        System.out.println("==========" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Projects: " + projects + "\n" +
                "==========");

        return null;
    }
}
