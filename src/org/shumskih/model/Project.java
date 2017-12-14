package org.shumskih.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="version")
    private String version;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private Set<Developer> developers  = new HashSet<>();

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private Set<Customer> customers = new HashSet<>();

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private Set<Company> companies = new HashSet<>();

    public Project() {

    }

    public Project(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public Project(Integer id, String name, String version) {
        this.id = id;
        this.name = name;
        this.version = version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return "\n" +
                "==========" + "\n" +
                "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Version: " + version + "\n";
    }
}
