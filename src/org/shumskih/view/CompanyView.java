package org.shumskih.view;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.controller.CompanyController;
import org.shumskih.controller.ProjectController;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.decorations.Decorations;
import org.shumskih.model.Company;
import org.shumskih.model.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class CompanyView {
    private CompanyController companyController = new CompanyController();
    private ProjectController projectController = new ProjectController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private String companyName;

    private String userInput;

    public void createCompany() {
        Set<Project> projects = new HashSet<>();

        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("Enter name of company or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    companyName = userInput;
                    break;
                }
            }

            while (!exit) {
                while (!userInput.equals("y") && !userInput.equals("n")) {
                    System.out.println("Add project to company? Enter y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if (userInput.equals("n")) {
                    Company company = new Company(companyName);
                    companyController.save(company);
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
                    Project project;

                    try {
                        transaction = session.beginTransaction();
                        project = (Project) session.get(Project.class, Integer.parseInt(userInput));

                        transaction.commit();

                        projects.add(project);
                        System.out.println(project);

                        session.close();
                    } catch (Exception e) {
                        transaction.rollback();
                        session.close();
                        HibernateUtil.closeSessionFactory(sessionFactory);
                        e.printStackTrace();
                    }

                    System.out.println("Add another one project? y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("n")) {
                        Company company = new Company(companyName, projects);
                        companyController.save(company);
                        projects.clear();
                        Decorations.returnToMainMenu();
                        exit = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCompanyById() {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("Enter ID of company or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    companyController.getById(Integer.parseInt(userInput));
                } else {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllCompanies() {
        boolean exit = false;

        companyController.getAll();
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

    public void updateCompany() {
        boolean exit = false;

        String userInputCompanyName;

        Company company = null;
        Integer id = null;
        String name = null;

        Set<Project> projects = new HashSet<>();
        Set<Project> modifiedProjects = new HashSet<>();

        try {
            while (!exit) {
                System.out.println("Enter ID of company you're going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    id = Integer.parseInt(userInput);

                    System.out.println("This is a company you're going to update:");
                    company = companyController.getById(id);
                    name = company.getName();
                    break;
                }
            }

            if (!exit) {
                do {
                    System.out.println("Change name? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                } while (!userInput.equals("y") & !userInput.equals("n"));

                if (userInput.equals("y")) {
                    System.out.println("Enter new company name:");
                    userInputCompanyName = br.readLine().trim();

                    company = new Company(id, userInputCompanyName);
                    companyController.update(company);
                }
            }

            do {
                System.out.println("Change projects? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();
                System.out.println();
            } while (!userInput.equals("y") & !userInput.equals("n"));

            if (userInput.equals("n")) {
                exit = true;
                Decorations.returnToMainMenu();
            } else {
                System.out.println("There is list of projects company has:");
                company = companyController.getById(id);
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

                        projects = company.getProjects();
                        for(Project project : projects) {
                            Integer projectId = project.getId();
                            if(projectId != Integer.parseInt(userInput)) {
                                modifiedProjects.add(project);
                            }
                        }

                        company = new Company(id, name, modifiedProjects);
                        companyController.update(company);
                        projects.clear();
                        modifiedProjects.clear();

                        Decorations.returnToMainMenu();
                        exit = true;
                    } else {
                        System.out.println("There is list of projects:");
                        projectController.getAll();
                        System.out.println();

                        System.out.println("Enter ID of project you're going to add:");
                        userInput = br.readLine().trim().toLowerCase();
                        System.out.println();

                        Project addNewProject = projectController.getById(Integer.parseInt(userInput));
                        modifiedProjects.add(addNewProject);

                        projects = company.getProjects();
                        modifiedProjects.addAll(projects);

                        Company updateCompany = new Company(id, name, modifiedProjects);
                        companyController.update(updateCompany);
                        projects.clear();
                        modifiedProjects.clear();

                        Decorations.returnToMainMenu();
                        exit = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteCompany() {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("There is lis of companies:");
                System.out.println("--------------------------");
                companyController.getAll();
                System.out.println();

                System.out.println("Enter ID of company you are going to delete or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    companyController.delete(Integer.parseInt(userInput));
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
