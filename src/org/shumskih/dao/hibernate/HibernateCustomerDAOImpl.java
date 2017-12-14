package org.shumskih.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.dao.GenericDAO;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.model.Customer;
import org.shumskih.model.Project;

import java.util.List;
import java.util.Set;

public class HibernateCustomerDAOImpl implements GenericDAO<Customer, Long> {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(customer);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        }
    }

    @Override
    public Customer getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Customer customer = null;

        try {
            transaction = session.beginTransaction();
            customer = (Customer) session.get(Customer.class, id);
            System.out.println(customer);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        List<Customer> customers;

        try {
            transaction = session.beginTransaction();
            customers = session.createQuery("FROM Customer").list();
            for (Customer customer : customers) {
                System.out.print(customer);
            }
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        Integer customerId = customer.getId();
        String customerName = customer.getName();

        Set<Project> projects = customer.getProjects();

        try {
            transaction = session.beginTransaction();
            customer = (Customer) session.get(Customer.class, customerId);

            customer.setName(customerName);
            customer.setProjects(projects);

            session.update(customer);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Customer customer = (Customer) session.get(Customer.class, id);
            session.delete(customer);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        }
    }
}
