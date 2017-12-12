package org.shumskih.view;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.controller.DeveloperController;
import org.shumskih.controller.ProjectController;
import org.shumskih.controller.SkillController;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.decorations.Decorations;
import org.shumskih.model.Developer;
import org.shumskih.model.Project;
import org.shumskih.model.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class DeveloperView {
    private DeveloperController developerController = new DeveloperController();
    private SkillController skillController = new SkillController();
    private ProjectController projectController = new ProjectController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer developerId;
    private String developerName;
    private String developerSpecialization;
    private Integer developerExperience;
    private Integer developerSalary;

    private Set<Skill> skills = new HashSet<>();
    private Set<Project> projects = new HashSet<>();

    private String userInput;

    public void createDeveloper() {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("Enter developer's name or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerName = userInput;
                    break;
                }
            }

            while (!exit) {
                System.out.println("Enter developer's specialization or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerSpecialization = userInput;
                    break;
                }
            }

            while (!exit) {
                System.out.println("Enter developer's experience or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerExperience = Integer.parseInt(userInput);
                    break;
                }
            }

            while (!exit) {
                System.out.println("Enter developer's salary or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    developerSalary = Integer.parseInt(userInput);
                    break;
                }
            }

            while (!exit) {
                while (!userInput.equals("y") && !userInput.equals("n")) {
                    System.out.println("Add skill to developer? Enter y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if (userInput.equals("n")) {
                    userInput = "";
                    break;
                } else {
                    System.out.println("There is list of skills:");
                    skillController.getAll();
                    System.out.println();

                    System.out.println("Enter ID of skill you're going to add:");
                    userInput = br.readLine().trim().toLowerCase();
                    System.out.println();


                    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                    Session session = sessionFactory.openSession();
                    Transaction transaction = null;
                    Skill skill = null;

                    try {
                        transaction = session.beginTransaction();
                        skill = (Skill) session.get(Skill.class, Integer.parseInt(userInput));

                        transaction.commit();
                        skills.add(skill);

                        System.out.println(skill);
                    } catch (Exception e) {
                        transaction.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                        HibernateUtil.closeSessionFactory(sessionFactory);
                    }





                    System.out.println("Add another one skill? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("n")) {
                        userInput = "";
                        break;
                    }
                }
            }

            while (!exit) {
                while (!userInput.equals("y") && !userInput.equals("n")) {
                    System.out.println("Add project to developer? Enter y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if (userInput.equals("n")) {
                    Developer developer = new Developer(developerName, developerSpecialization, developerExperience, developerSalary, skills, projects);
                    developerController.save(developer);
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    System.out.println("There is list of projects:");
                    System.out.println("--------------------------");
                    projectController.getAll();
                    System.out.println();


                    System.out.println("Enter ID of project you're going to add:");
                    userInput = br.readLine().trim().toLowerCase();
                    System.out.println();


                    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
                    Session session = sessionFactory.openSession();
                    Transaction transaction = null;
                    Project project = null;

                    try {
                        transaction = session.beginTransaction();
                        project = (Project) session.get(Project.class, Integer.parseInt(userInput));

                        projects.add(project);

                        transaction.commit();
                        System.out.println(project);
                    } catch (Exception e) {
                        transaction.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                        HibernateUtil.closeSessionFactory(sessionFactory);
                    }




                    System.out.println("Add another one project? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("n")) {
                        Developer developer = new Developer(developerName, developerSpecialization, developerExperience, developerSalary, skills, projects);
                        developerController.save(developer);
                        Decorations.returnToMainMenu();
                        exit = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showDeveloperById() {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("Enter developer's ID or c to cancel: ");
                userInput = br.readLine().trim();

                if (!userInput.equals("c")) {
                    developerController.getById(Integer.parseInt(userInput));
                } else {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllDevelopers() {
        boolean exit = false;

        developerController.getAll();
        System.out.println();

        try {
            while (!exit) {
                System.out.println("Enter c to back to main menu:");
                userInput = br.readLine().trim().toLowerCase();

                while (!userInput.equals("c")) {
                    System.out.println("Enter c to back to main menu:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDeveloper() {
        boolean exit = false;

        Integer devId = null;
        String name = null;
        String specialization = null;
        Integer experience = null;
        Integer salary = null;
        Set<Skill> skills = new LinkedHashSet<>();
        Set<Project> projects = new LinkedHashSet<>();

        Developer developer;

        String userInputDevName;
        String userInputDevSpecialization;
        Integer userInputDevExperience;
        Integer userInputDevSalary;

        Integer id = null;

        try {
            while (!exit) {
                System.out.println("Enter developer's ID you are going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    id = Integer.parseInt(userInput);
                    developerId = id;

                    System.out.println("There is a developer you are going to update:");
                    developer = developerController.getById(id);

                    devId = developer.getId();
                    name = developer.getName();
                    specialization = developer.getSpecialization();
                    experience = developer.getExperience();
                    salary = developer.getSalary();
                    skills = developer.getSkills();
                    projects = developer.getProjects();
                }

                while (!exit) {
                    System.out.println("Change: ");
                    System.out.println("    1.name?");
                    System.out.println("    2.specialization?");
                    System.out.println("    3.experience?");
                    System.out.println("    4.salary?");
                    System.out.println("    5.skills?");
                    System.out.println("    6.projects?");
                    System.out.println("    7.Change all fields?");
                    System.out.println("8.Cancel");

                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("8")) {
                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        switch (userInput) {
                            case "1":
                                System.out.println("Enter new developer's name or c to cancel:");
                                userInput = br.readLine().trim();

                                if (userInput.equals("c")) {
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                } else {
                                    userInputDevName = userInput;

                                    developer = new Developer(userInputDevName, specialization, experience, salary, skills, projects);
                                    developerController.update(developer);
                                    break;
                                }
                            case "2":
                                System.out.println("Enter new developer's specialization or c to cancel:");
                                userInput = br.readLine().trim();

                                if (userInput.equals("c")) {
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                } else {
                                    userInputDevSpecialization = userInput;

                                    developer = new Developer(devId, name, userInputDevSpecialization, experience, salary);
                                    developerController.update(developer);
                                    break;
                                }
                            case "3":
                                System.out.println("Enter new developer's experience or c to cancel:");
                                userInput = br.readLine().trim();

                                if (userInput.equals("c")) {
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                } else {
                                    userInputDevExperience = Integer.parseInt(userInput);

                                    developer = new Developer(devId, name, specialization, userInputDevExperience, salary);
                                    developerController.update(developer);
                                    break;
                                }
                            case "4":
                                System.out.println("Enter new developer's salary or c to cancel:");
                                userInput = br.readLine().trim();

                                if (userInput.equals("c")) {
                                    Decorations.returnToMainMenu();
                                    exit = true;
                                } else {
                                    userInputDevSalary = Integer.parseInt(userInput);

                                    developer = new Developer(devId, name, specialization, experience, userInputDevSalary);
                                    developerController.update(developer);
                                    break;
                                }
                            case "5":
                                System.out.println("There is list of skills developer has:");
                                System.out.println("--------------------------------------");
                                developer = developerController.getById(id);

                                skills = developer.getSkills();
                                for(Skill skill : skills) {
                                    System.out.println(skill);
                                }

                                while (!exit) {
                                    System.out.println("Delete skill or insert new? d = delete, i = insert new:");
                                    userInput = br.readLine().trim().toLowerCase();

                                    if (!userInput.equals("d") && !userInput.equals("i")) {
                                        do {
                                            System.out.println("Enter d to delete skill or i to insert new:");
                                            userInput = br.readLine().trim().toLowerCase();
                                        } while (!userInput.equals("d") && !userInput.equals("i"));

                                    } else {
                                        if (userInput.equals("d")) {
                                            System.out.println("Enter ID of skill you're going to delete:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            System.out.println();

                                            skillController.delete(Integer.parseInt(userInput));
                                            Decorations.returnToMainMenu();
                                            exit = true;
                                        } else {
                                            System.out.println("There is list of skills:");
                                            skillController.getAll();
                                            System.out.println();

                                            System.out.println("Enter ID of skill you're going to add:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            System.out.println();

                                            Skill skill = skillController.getById(Integer.parseInt(userInput));
                                            skills.add(skill);

                                            Developer updateDeveloper = new Developer(devId, name, specialization, experience, salary, skills, projects);
                                            developerController.update(updateDeveloper);

                                            Decorations.returnToMainMenu();
                                            exit = true;
                                        }
                                    }
                                }
                            case "6":
                                System.out.println("There is list of projects developer has:");
                                System.out.println("--------------------------------------");
                                developer = developerController.getById(id);

                                projects = developer.getProjects();
                                for(Project project : projects) {
                                    System.out.println(project);
                                }

                                while (!exit) {
                                    System.out.println("Delete project or insert new? d = delete, i = insert new:");
                                    userInput = br.readLine().trim().toLowerCase();

                                    if (!userInput.equals("d") && !userInput.equals("i")) {
                                        do {
                                            System.out.println("Enter d to delete project or i to insert new:");
                                            userInput = br.readLine().trim().toLowerCase();
                                        } while (!userInput.equals("d") && !userInput.equals("i"));

                                    } else {
                                        if (userInput.equals("d")) {
                                            System.out.println("Enter ID of project you're going to delete:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            System.out.println();

                                            projectController.delete(Integer.parseInt(userInput));
                                            Decorations.returnToMainMenu();
                                            exit = true;
                                        } else {
                                            System.out.println("There is list of projects:");
                                            projectController.getAll();
                                            System.out.println();

                                            System.out.println("Enter ID of project you're going to add:");
                                            userInput = br.readLine().trim().toLowerCase();
                                            System.out.println();

                                            Project project = projectController.getById(Integer.parseInt(userInput));
                                            projects.add(project);

                                            Developer updateDeveloper = new Developer(devId, name, specialization, experience, salary, skills, projects);
                                            developerController.update(updateDeveloper);

                                            Decorations.returnToMainMenu();
                                            exit = true;
                                        }
                                    }
                                }
                            case "7":
                                while (!exit) {
                                    System.out.println("Enter developer's name or c to cancel:");
                                    userInput = br.readLine().trim().toLowerCase();

                                    if (userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        developerName = userInput;
                                        break;
                                    }
                                }

                                while (!exit) {
                                    System.out.println("Enter developer's specialization:");
                                    userInput = br.readLine().trim().toLowerCase();

                                    if (userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        developerSpecialization = userInput;
                                        break;
                                    }
                                }

                                while (!exit) {
                                    System.out.println("Enter developer's experience:");
                                    userInput = br.readLine().trim().toLowerCase();

                                    if (userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        developerExperience = Integer.parseInt(userInput);
                                        break;
                                    }
                                }

                                while (!exit) {
                                    System.out.println("Enter developer's salary:");
                                    userInput = br.readLine().trim().toLowerCase();

                                    if (userInput.equals("c")) {
                                        Decorations.returnToMainMenu();
                                        exit = true;
                                    } else {
                                        developerSalary = Integer.parseInt(userInput);
                                        break;
                                    }
                                }

                                if (!exit) {
                                    developer = new Developer(developerName, developerSpecialization, developerExperience, developerSalary, skills, projects);
                                    developerController.update(developer);
                                    break;
                                }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeveloper() {
        boolean exit = false;

        String userInput;

        try {
            while (!exit) {
                System.out.println("Enter developer's ID you are going to delete or c to cancel:");
                userInput = br.readLine().trim();

                if (!userInput.equals("c")) {
                    developerController.delete(Integer.parseInt(userInput));
                    Decorations.returnToMainMenu();
                    break;
                } else {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
