package org.shumskih.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="developers")
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="specialization")
    private String specialization;

    @Column(name="experience")
    private Integer experience;

    @Column(name="salary")
    private Integer salary;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="developers_skills",
            joinColumns = {@JoinColumn(name="developer_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name="skill_id", nullable = false)})
    private Set<Skill> skills;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="projects_developers",
            joinColumns = {@JoinColumn(name="developer_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name="project_id", nullable = false)})
    private Set<Project> projects;

    public Developer() {

    }

    public Developer(Integer id, String name, String specialization, Integer experience, Integer salary) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
    }

    public Developer(Integer id, String name, String specialization, Integer experience, Integer salary, Set<Skill> skills, Set<Project> projects) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
        this.skills = skills;
        this.projects = projects;
    }

    public Developer(String name, String specialization, Integer experience, Integer salary) {
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
    }

    public Developer(String name, String specialization, Integer experience, Integer salary, Set<Skill> skills, Set<Project> projects) {
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
        this.skills = skills;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", experience=" + experience +
                ", salary=" + salary +
                '}';
    }
}
