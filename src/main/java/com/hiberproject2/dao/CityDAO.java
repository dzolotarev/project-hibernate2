package com.hiberproject2.dao;

import com.hiberproject2.entity.City;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CityDAO extends GenericDAO<City> {
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String cityName) {
        Query<City> query = getCurrentSession().createQuery("select c from City c where c.name = :NAME", City.class);
        query.setParameter("NAME", cityName);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
