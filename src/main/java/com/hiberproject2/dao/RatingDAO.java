package com.hiberproject2.dao;

import com.hiberproject2.entity.Rating;
import org.hibernate.SessionFactory;

public class RatingDAO extends GenericDAO<Rating> {

    public RatingDAO(SessionFactory sessionFactory) {
        super(Rating.class, sessionFactory);
    }
}
