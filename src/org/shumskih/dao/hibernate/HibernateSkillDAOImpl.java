package org.shumskih.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.dao.GenericDAO;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.model.Skill;

import java.util.List;

public class HibernateSkillDAOImpl implements GenericDAO<Skill, Long> {
    @Override
    public void save(Skill skill) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
           transaction = session.beginTransaction();

           session.save(skill);
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
    public Skill getById(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Skill skill = null;

        try {
            transaction = session.beginTransaction();
            skill = (Skill) session.get(Skill.class, id);
            System.out.println(skill);

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
        return skill;
    }

    @Override
    public void getAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Skill> skills;

        try {
            transaction = session.beginTransaction();
            skills = session.createQuery("FROM Skill").list();
            for (Skill skill : skills) {
                System.out.print(skill);
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
    public void update(Skill skill) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer skillId = skill.getId();
        String skillName = skill.getName();

        try {
            transaction = session.beginTransaction();
            skill = (Skill) session.get(Skill.class, skillId);

            skill.setName(skillName);

            session.update(skill);
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
            Skill skill = (Skill) session.get(Skill.class, id);
            session.delete(skill);
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
