package org.example.repository.implementation;

import jakarta.persistence.*;
import org.example.model.Tag;
import org.example.model.Task;

import java.util.List;

public class TagRepository {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJPAUnit");

    private EntityManager getEntityManager() {

        return entityManagerFactory.createEntityManager();
    }

    public Tag insert(Tag tag) {
        EntityManager em = getEntityManager();
        if (tag.getId() != 0) {
            return em.merge(tag);
        } else {
            em.getTransaction().begin();
            em.persist(tag);
            em.getTransaction().commit();
            return tag;
        }
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
    public Tag merge(Tag tag) {
        Tag editedTag = null;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tag foundTag = em.find(Tag.class, tag.getId());
            if (foundTag != null) {
                foundTag.setTitle(tag.getTitle());
                editedTag = em.merge(foundTag);
            } else {
                editedTag = em.merge(tag);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return editedTag;
    }

    public void delete(int tag_id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tag tag = em.find(Tag.class, tag_id);
            if (tag != null) {
                em.remove(tag);
            } else {
                System.out.println("Tâche non trouvée avec l'ID : " + tag_id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de la tâche", e);
        } finally {
            em.close();
        }
    }



}
