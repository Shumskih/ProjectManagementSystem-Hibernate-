package org.shumskih.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.dao.GenericDAO;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.model.Developer;
import org.shumskih.model.Project;
import org.shumskih.model.Skill;

import java.util.List;
import java.util.Set;

public class HibernateDeveloperDAOImpl implements GenericDAO<Developer, Long> {
    @Override
    public void save(Developer developer) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(developer);
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
    public Developer getById(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Developer developer = null;

        try {
            transaction = session.beginTransaction();
            developer = (Developer) session.get(Developer.class, id);
            System.out.println(developer);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return developer;
    }

    @Override
    public void getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Developer> developers;

        try {
            transaction = session.beginTransaction();
            developers = session.createQuery("FROM Developer").list();
            for (Developer developer : developers) {
                System.out.print(developer);
            }
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
    public void update(Developer developer) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer developerId = developer.getId();
        String developerName = developer.getName();
        String developerSpecialization = developer.getSpecialization();
        Integer developerExperience = developer.getExperience();
        Integer developerSalary = developer.getSalary();
        Set<Skill> skills = developer.getSkills();
        Set<Project> projects = developer.getProjects();

        try {
            transaction = session.beginTransaction();
            developer = (Developer) session.get(Developer.class, developerId);

            developer.setName(developerName);
            developer.setSpecialization(developerSpecialization);
            developer.setExperience(developerExperience);
            developer.setSalary(developerSalary);
            developer.setSkills(skills);
            developer.setProjects(projects);

            session.update(developer);
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
            Developer developer = (Developer) session.get(Developer.class, id);
            session.delete(developer);
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
}
