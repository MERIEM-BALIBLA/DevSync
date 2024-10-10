package org.example.repository.implementation;

import jakarta.persistence.*;
import org.example.model.Tag;

import java.util.List;

public class TagRepository {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJPAUnit");

    private EntityManager getEntityManager() {

        return entityManagerFactory.createEntityManager();
    }
    public Tag insert(Tag tag) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tag);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'insertion du tag: " + tag.getTitle(), e);
        }
        return tag;
    }
    public Tag findByTitle(String title) {
        try {
            EntityManager em = getEntityManager();
            return em.createQuery("SELECT t FROM Tag t WHERE t.title = :title", Tag.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public List<Tag> getAllTag() {
        EntityManager em = getEntityManager();
        TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t", Tag.class);
        return query.getResultList();
    }

    public Tag findById(Long id) {
        try {
            EntityManager em = getEntityManager();
            return em.createQuery("SELECT t FROM Tag t WHERE t.id = :id", Tag.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
