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
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Developer developer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(developer);
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
    public Developer getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Developer developer = null;

        try {
            transaction = session.beginTransaction();
            developer = (Developer) session.get(Developer.class, id);
            System.out.println(developer);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public void getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        List<Developer> developers;

        try {
            transaction = session.beginTransaction();
            developers = session.createQuery("FROM Developer").list();
            for (Developer developer : developers) {
                System.out.print(developer);
            }
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            e.printStackTrace();
        }
    }

    @Override
    public void update(Developer developer) {
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
            Developer developer = (Developer) session.get(Developer.class, id);
            session.delete(developer);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            e.printStackTrace();
        }
    }
}
