package de.promove.autokss.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class MessungDao {
    @Autowired
    private EntityManager entityManager;

    public Date getLastMessungDate() {
        Date date = (Date) entityManager.createQuery("select max(m.pruefDatum) from Messung m").getSingleResult();
        return date;
    }
}
