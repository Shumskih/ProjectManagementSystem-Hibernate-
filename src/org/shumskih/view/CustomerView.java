package org.shumskih.view;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.controller.CustomerController;
import org.shumskih.controller.ProjectController;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.decorations.Decorations;
import org.shumskih.model.Customer;
import org.shumskih.model.Project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class CustomerView {
    private CustomerController customerController = new CustomerController();
    private ProjectController projectController = new ProjectController();

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Integer customerId;
    private String customerName;

    private String userInput;

    public void createCustomer() {
        Set<Project> projects = new HashSet<>();

        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("Enter customer's name or c to cancel:");
                userInput = br.readLine().trim();

                if (userInput.equals("c")) {
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    customerName = userInput;
                    break;
                }
            }

            while (!exit) {
                while (!userInput.equals("y") && !userInput.equals("n")) {
                    System.out.println("Add project to customer? Enter y = yes or n = no:");
                    userInput = br.readLine().trim().toLowerCase();
                }

                if (userInput.equals("n")) {
                    Customer customer = new Customer(customerName);
                    customerController.save(customer);
                    Decorations.returnToMainMenu();
                    exit = true;
                } else {
                    System.out.println("There is list of projects:");
                    System.out.println("--------------------------");
                    projectController.getAll();
                    System.out.println();

                    System.out.println("Enter ID of project you're going to add:");
                    userInput = br.readLine().trim().toLowerCase();

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
                        session.close();
                    } catch (Exception e) {
                        transaction.rollback();
                        session.close();
                        HibernateUtil.closeSessionFactory(sessionFactory);
                        e.printStackTrace();
                    }

                    System.out.println("Add another project? y = yes, n = no:");
                    userInput = br.readLine().trim().toLowerCase();

                    if (userInput.equals("n")) {
                        Customer customer = new Customer(customerName, projects);
                        customerController.save(customer);
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

    public void showCustomerById() {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("Enter ID of customer or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    customerController.getById(Integer.parseInt(userInput));
                } else {
                    Decorations.returnToMainMenu();
                    exit = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAllCustomers() {
        boolean exit = false;

        customerController.getAll();
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

    public void updateCustomer() {
        boolean exit = false;

        String userInputCustomerName;

        Customer customer = null;
        Integer id = null;
        String name = null;

        Set<Project> projects = new HashSet<>();
        Set<Project> modifiedProjects = new HashSet<>();

        try {
            while (!exit) {
                System.out.println("Enter ID of customer you're going to update or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("c")) {
                    exit = true;
                    Decorations.returnToMainMenu();
                } else {
                    id = Integer.parseInt(userInput);

                    System.out.println("This is a customer you're going to update:");
                    customer = customerController.getById(id);
                    name = customer.getName();
                    break;
                }
            }

            if (!exit) {
                System.out.println("Change name? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();

                if (userInput.equals("y")) {
                    System.out.println("Enter new customer name:");
                    userInputCustomerName = br.readLine().trim();

                    customer = new Customer(id, userInputCustomerName);
                    customerController.update(customer);
                }
            }

            do {
                System.out.println("Change project? y = yes, n = no:");
                userInput = br.readLine().trim().toLowerCase();
                System.out.println();
            } while (!userInput.equals("y") & !userInput.equals("n"));

            if (userInput.equals("n")) {
                exit = true;
                Decorations.returnToMainMenu();
            } else {
                System.out.println("There is list of projects customer has:");
                customer = customerController.getById(id);
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

                        projects = customer.getProjects();
                        for(Project project : projects) {
                            Integer projectId = project.getId();
                            if(projectId != Integer.parseInt(userInput)) {
                                modifiedProjects.add(project);
                            }
                        }

                        customer = new Customer(id, name, modifiedProjects);
                        customerController.update(customer);
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

                        projects = customer.getProjects();
                        modifiedProjects.addAll(projects);

                        Customer updateCustomer = new Customer(id, name, modifiedProjects);
                        customerController.update(updateCustomer);
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

    public void deleteCustomer() {
        boolean exit = false;

        try {
            while (!exit) {
                System.out.println("There is lis of customers:");
                System.out.println("--------------------------");
                customerController.getAll();
                System.out.println();

                System.out.println("Enter ID of customer you're going to delete or c to cancel:");
                userInput = br.readLine().trim().toLowerCase();

                if (!userInput.equals("c")) {
                    customerController.delete(Integer.parseInt(userInput));
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
