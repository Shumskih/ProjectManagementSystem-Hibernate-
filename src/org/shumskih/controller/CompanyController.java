package org.shumskih.controller;

import org.shumskih.dao.hibernate.HibernateCompanyDAOImpl;
import org.shumskih.model.Company;

public class CompanyController {
    private HibernateCompanyDAOImpl hibernateCompanyDAO = new HibernateCompanyDAOImpl();

    public void save(Company company) {
        hibernateCompanyDAO.save(company);
    }

    public Company getById(int id) {
        Company company;
        company = hibernateCompanyDAO.getById(id);
        return company;
    }

    public void getAll() {
        hibernateCompanyDAO.getAll();
    }

    public void update(Company company) {
        hibernateCompanyDAO.update(company);
    }

    public void delete(int id) {
        hibernateCompanyDAO.delete(id);
    }

}
