package org.example.repository.implementation;

import jakarta.persistence.*;
import org.example.model.Request;
import org.example.model.Tag;
import org.example.model.Task;

import java.util.List;
import java.util.Optional;

public class RequestRepository {
    private final EntityManager em;

    public RequestRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        em = emf.createEntityManager();
    }

    public Request save(Request request) {
        em.getTransaction().begin();
        em.persist(request);
        em.getTransaction().commit();
        return request;
    }

    public List<Request> getAll() {
        TypedQuery<Request> query = em.createQuery("SELECT r FROM Request r ORDER BY r.id desc ", Request.class);
        return query.getResultList();
    }

    public Optional<Request> findByTaskId(long id) {
        try {
            TypedQuery<Request> query = em.createQuery("SELECT r FROM Request r WHERE r.task.id = :id", Request.class);
            query.setParameter("id", id);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Request> findById(int id) {
        try {
            TypedQuery<Request> query = em.createQuery("SELECT r FROM Request r WHERE r.id = :id", Request.class);
            query.setParameter("id", id);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Request update(Request request) {
        em.getTransaction().begin();
        em.merge(request);
        em.getTransaction().commit();
        return request;
    }

}
