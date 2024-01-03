package com.hiberproject2.dao;

import com.hiberproject2.entity.Inventory;
import org.hibernate.SessionFactory;

public class InventoryDAO extends GenericDAO<Inventory> {

    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
