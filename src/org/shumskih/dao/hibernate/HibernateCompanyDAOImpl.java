package org.shumskih.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.dao.GenericDAO;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.model.Company;
import org.shumskih.model.Project;

import java.util.List;
import java.util.Set;

public class HibernateCompanyDAOImpl implements GenericDAO<Company, Long> {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Company company) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(company);
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
    public Company getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Company company = null;

        try {
            transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            System.out.println(company);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public void getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Company> companies;

        try {
            transaction = session.beginTransaction();
            companies = session.createQuery("FROM Company").list();
            for (Company company : companies) {
                System.out.print(company);
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
    public void update(Company company) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        Integer companyId = company.getId();
        String companyName = company.getName();

        Set<Project> projects = company.getProjects();

        try {
            transaction = session.beginTransaction();
            company = session.get(Company.class, companyId);

           company.setName(companyName);
           company.setProjects(projects);

            session.update(company);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Company company = session.get(Company.class, id);
            session.delete(company);
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
