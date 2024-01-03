package com.hiberproject2.dao;

import com.hiberproject2.entity.Store;
import org.hibernate.SessionFactory;

public class StoreDAO extends GenericDAO<Store> {

    public StoreDAO(SessionFactory sessionFactory) {
        super(Store.class, sessionFactory);
    }
}
