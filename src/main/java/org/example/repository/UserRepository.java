package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.model.User;

import java.util.List;

public class UserRepository {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJPAUnit");

    public void insertUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();

        try {
            em.getTransaction().begin(); // Commence la transaction
            em.persist(user); // Insère l'utilisateur
            em.getTransaction().commit(); // Commit les changements
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Annule la transaction en cas d'erreur
            }
            throw e; // Relancer l'exception pour gestion ultérieure
        } finally {
            em.close(); // Ferme l'EntityManager
        }
    }

    public List<User> getAllUsers() {
        EntityManager em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
           return query.getResultList();
    }

    public void deleteUser(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();

        try {
            em.getTransaction().begin(); // Commence la transaction
            User user = em.find(User.class, userId); // Trouve l'utilisateur par ID
            if (user != null) {
                em.remove(user); // Supprime l'utilisateur
            } else {
                throw new RuntimeException("User not found with ID: " + userId);
            }
            em.getTransaction().commit(); // Commit les changements
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Annule la transaction en cas d'erreur
            }
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur", e);
        } finally {
            em.close(); // Ferme l'EntityManager
        }
    }


    public void close() {
        if (entityManagerFactory != null && !entityManagerFactory.isOpen()) {
            entityManagerFactory.close(); // Ferme l'EntityManagerFactory si nécessaire
        }
    }
}
