package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class RecordDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Record> findAllRecords() {
        Query query= entityManager.createQuery("SELECT r FROM  Record r order by r.id ASC ");
        List<Record> records = query.getResultList();
        return records;
    }

    public void saveRecord(Record record){
        entityManager.persist(record);
    }


    public void updateRecordStatus(int id, RecordStatus newStatus){
        Query query= entityManager.createQuery("UPDATE Record SET status = :status  where id = :id");
        query.setParameter("id", id);
        query.setParameter("status", newStatus);
        query.executeUpdate();
    }


    public void deleteRecord(int id){
        Query query = entityManager.createQuery("DELETE from Record WHERE id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
