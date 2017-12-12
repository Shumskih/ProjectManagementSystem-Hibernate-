package org.shumskih.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.dao.GenericDAO;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.model.Company;

import java.util.List;

public class HibernateCompanyDAOImpl implements GenericDAO<Company, Long> {
    @Override
    public void save(Company company) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(company);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Company getById(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Company company = null;

        try {
            transaction = session.beginTransaction();
            company = (Company) session.get(Company.class, id);
            System.out.println(company);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return company;
    }

    @Override
    public void getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Company> companies;

        try {
            transaction = session.beginTransaction();
            companies = session.createQuery("FROM Company").list();
            for (Company company : companies) {
                System.out.print(company);
            }
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Company company) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer companyId = company.getId();
        String companyName = company.getName();

        try {
            transaction = session.beginTransaction();
            company = (Company) session.get(Company.class, companyId);

           company.setName(companyName);

            session.update(company);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Company company = (Company) session.get(Company.class, id);
            session.delete(company);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
