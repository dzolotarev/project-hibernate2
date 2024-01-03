package com.hiberproject2.dao;

import com.hiberproject2.entity.Staff;
import org.hibernate.SessionFactory;

public class StaffDAO extends GenericDAO<Staff> {

    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
