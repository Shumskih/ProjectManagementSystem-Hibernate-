package org.shumskih.controller;

import org.shumskih.dao.hibernate.HibernateProjectDAOImpl;
import org.shumskih.model.Project;

public class ProjectController {
    private HibernateProjectDAOImpl hibernateProjectDAO = new HibernateProjectDAOImpl();

    public void save(Project project) {
        hibernateProjectDAO.save(project);
    }

    public Project getById(int id) {
        Project project;
        project = hibernateProjectDAO.getById(id);
        return project;
    }

    public void getAll() {
        hibernateProjectDAO.getAll();
    }

    public void update(Project project) {
        hibernateProjectDAO.update(project);
    }

    public void delete(int id) {
        hibernateProjectDAO.delete(id);
    }
}
