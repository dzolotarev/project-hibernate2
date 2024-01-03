package com.hiberproject2.dao;

import com.hiberproject2.entity.FilmText;
import org.hibernate.SessionFactory;

public class FilmTextDAO extends GenericDAO<FilmText> {

    public FilmTextDAO(SessionFactory sessionFactory) {
        super(FilmText.class, sessionFactory);
    }
}
