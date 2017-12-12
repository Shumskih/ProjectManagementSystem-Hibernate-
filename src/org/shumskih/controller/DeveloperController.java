package org.shumskih.controller;

import org.shumskih.dao.hibernate.HibernateDeveloperDAOImpl;
import org.shumskih.model.Developer;

public class DeveloperController {
    private HibernateDeveloperDAOImpl hibernateDeveloperDAO = new HibernateDeveloperDAOImpl();

    public void save(Developer developer) {
        hibernateDeveloperDAO.save(developer);
    }

    public Developer getById(int id) {
        Developer developer;
        developer = hibernateDeveloperDAO.getById(id);
        return developer;
    }

    public void getAll() {
        hibernateDeveloperDAO.getAll();
    }

    public void update(Developer developer) {
        hibernateDeveloperDAO.update(developer);
    }

    public void delete(int id) {
        hibernateDeveloperDAO.delete(id);
    }
}
