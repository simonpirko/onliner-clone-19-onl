package by.tms.onlinerclone.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * @author Andrei Lisouski (Andrlis) - 03/07/2023 - 19:06
 */

@Component
@Transactional
public class HibernateStoreDao {

    @Autowired
    private SessionFactory sessionFactory;

}
