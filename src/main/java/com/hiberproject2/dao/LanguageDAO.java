package com.hiberproject2.dao;

import com.hiberproject2.entity.Language;
import org.hibernate.SessionFactory;

public class LanguageDAO extends GenericDAO<Language> {

    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
}
