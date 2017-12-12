package org.shumskih.controller;

import org.shumskih.dao.hibernate.HibernateSkillDAOImpl;
import org.shumskih.model.Skill;

public class SkillController {
    private HibernateSkillDAOImpl hibernateSkillDAO = new HibernateSkillDAOImpl();

    public void save(Skill skill) {
        hibernateSkillDAO.save(skill);
    }

    public Skill getById(int id) {
        Skill skill;
        skill = hibernateSkillDAO.getById(id);
        return skill;
    }

    public void getAll() {
        hibernateSkillDAO.getAll();
    }

    public void update(Skill skill) {
        hibernateSkillDAO.update(skill);
    }

    public void delete(int id) {
        hibernateSkillDAO.delete(id);
    }
}
