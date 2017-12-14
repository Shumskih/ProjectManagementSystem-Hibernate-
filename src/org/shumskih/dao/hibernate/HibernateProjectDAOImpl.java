package org.shumskih.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.dao.GenericDAO;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.model.Project;

import java.util.List;

public class HibernateProjectDAOImpl implements GenericDAO<Project, Long> {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.save(project);
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
    public Project getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Project project = null;

        try {
            transaction = session.beginTransaction();
            project = (Project) session.get(Project.class, id);
            System.out.println(project);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public void getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        List<Project> projects;

        try {
            transaction = session.beginTransaction();
            projects = session.createQuery("FROM Project").list();
            for (Project project : projects) {
                System.out.print(project);
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
    public void update(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer projectId = project.getId();
        String projectName = project.getName();
        String projectVersion = project.getVersion();

        try {
            transaction = session.beginTransaction();
            project = (Project) session.get(Project.class, projectId);

            project.setName(projectName);
            project.setVersion(projectVersion);

            session.update(project);
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
            Project project = (Project) session.get(Project.class, id);
            session.delete(project);
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
