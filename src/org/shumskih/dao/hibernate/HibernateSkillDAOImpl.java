package org.shumskih.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.shumskih.dao.GenericDAO;
import org.shumskih.dao.HibernateUtil;
import org.shumskih.model.Skill;

import java.util.List;

public class HibernateSkillDAOImpl implements GenericDAO<Skill, Long> {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Skill skill) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
           transaction = session.beginTransaction();

           session.save(skill);
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
    public Skill getById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Skill skill = null;

        try {
            transaction = session.beginTransaction();
            skill = (Skill) session.get(Skill.class, id);
            System.out.println(skill);

            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            HibernateUtil.closeSessionFactory(sessionFactory);
            sessionFactory = null;
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public void getAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        List<Skill> skills;

        try {
            transaction = session.beginTransaction();
            skills = session.createQuery("FROM Skill").list();
            for (Skill skill : skills) {
                System.out.print(skill);
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
    public void update(Skill skill) {
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
            Skill skill = (Skill) session.get(Skill.class, id);
            session.delete(skill);
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
