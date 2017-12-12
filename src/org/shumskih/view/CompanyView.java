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
import java.util.Set;

public class CompanyView {
    private CompanyController companyController = new CompanyController();
    private ProjectController projectController = new ProjectController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private String companyName;

    private String userInput;

    public void createCompany() {
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

                    Company company = new Company(companyName);
                    companyController.save(company);
                    break;
                }
            }

            while (!exit) {
                System.out.println("Add project to company? y = yes or n = no:");
                userInput = br.readLine().trim().toLowerCase();

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
                    Project project = null;
                    Set<Project> projects = null;

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

                    System.out.println("Add another one project? y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("n")) {
                        Company company = new Company(companyName, projects);
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

        Company company;
        Set<Project> projects = null;

        Integer id = null;
        String name = null;

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

                    company = new Company(userInputCompanyName);
                    companyController.update(company);
                }
            }

            while (!exit) {
                do {
                    System.out.println("Change projects? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                } while (!userInput.equals("y") & !userInput.equals("n"));

                if (userInput.equals("n")) {
                    exit = true;
                    Decorations.returnToMainMenu();
                } else {
                    System.out.println("There is list of projects company has:");
                    company = companyController.getById(id);

                    projects = company.getProjects();
                    for(Project project : projects) {
                        System.out.println(project);
                    }
                }
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

                        Company updateCompany = new Company(id, name, projects);
                        companyController.update(updateCompany);

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
