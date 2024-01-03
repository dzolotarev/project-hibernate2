package com.hiberproject2.dao;

import com.hiberproject2.entity.Customer;
import org.hibernate.SessionFactory;

public class CustomerDAO extends GenericDAO<Customer> {

    public CustomerDAO(SessionFactory sessionFactory) {
        super(Customer.class, sessionFactory);
    }
}
