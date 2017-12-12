package org.shumskih.model;

import java.util.HashSet;
import java.util.Set;

public class Developer {
    private Integer id;
    private String name;
    private String specialization;
    private Integer experience;
    private Integer salary;

    private Set<Skill> skills = new HashSet<>();
    private Set<Project> projects = new HashSet<>();

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
