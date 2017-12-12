package org.shumskih.controller;

import org.shumskih.dao.hibernate.HibernateCustomerDAOImpl;
import org.shumskih.model.Customer;

public class CustomerController {
    private HibernateCustomerDAOImpl hibernateCustomerDAO = new HibernateCustomerDAOImpl();

    public void save(Customer customer) {
        hibernateCustomerDAO.save(customer);
    }

    public Customer getById(int id) {
        Customer customer = hibernateCustomerDAO.getById(id);
        return customer;
    }

    public void getAll() {
        hibernateCustomerDAO.getAll();
    }

    public void update(Customer customer) {
        hibernateCustomerDAO.update(customer);
    }

    public void delete(int id) {
        hibernateCustomerDAO.delete(id);
    }
}