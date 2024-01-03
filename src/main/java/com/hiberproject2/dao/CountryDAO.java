package com.hiberproject2.dao;

import com.hiberproject2.entity.Country;
import org.hibernate.SessionFactory;

public class CountryDAO extends GenericDAO<Country> {

    public CountryDAO(SessionFactory sessionFactory) {
        super(Country.class, sessionFactory);
    }
}
