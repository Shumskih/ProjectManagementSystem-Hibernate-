package org.shumskih.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory = null;

    public HibernateUtil() {
        sessionFactory = new Configuration().configure("org/shumskih/resources/hibernate.cfg.xml").buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory(SessionFactory sf) {
        sf.close();
        sessionFactory.close();
    }
}
