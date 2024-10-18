package org.example.repository.implementation;

import jakarta.persistence.*;
import org.example.model.User;
import org.example.repository.interfaces.UserInterface;

import java.util.List;

public class UserRepository implements UserInterface {
    // Singleton EntityManagerFactory
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJPAUnit");

    // Obtenez un EntityManager
    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
   /* public void insertUser(User user) {
        EntityManager em = getEntityManager();

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
    }*/
    public User insertUser(User user) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin(); // Commence la transaction
            em.persist(user); // Insère l'utilisateur
            em.getTransaction().commit(); // Commit les changements
            return user; // Retourne l'utilisateur inséré
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Annule la transaction en cas d'erreur
            }
            throw e; // Relancer l'exception pour gestion ultérieure
        } finally {
            em.close(); // Ferme l'EntityManager
        }
    }


    @Override
    public List<User> getAllUsers() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.id ASC", User.class);
            return query.getResultList();
        }
    }

    @Override
    public void deleteUser(int userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, userId);
        if (user != null) {
            em.remove(user);
        }

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void updateUser(User user) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            User existingUser = em.find(User.class, user.getId());
            if (existingUser != null) {
                existingUser.setUsername(user.getUsername());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setManager(user.isManager());
                em.merge(existingUser);
            } else {
                throw new RuntimeException("User not found with ID: " + user.getId());
            }
            em.getTransaction().commit(); // Commit les changements
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Annule la transaction en cas d'erreur
            }
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur", e);
        } finally {
            em.close(); // Ferme l'EntityManager
        }
    }

    @Override
    public User findById(int userId) {
        try (EntityManager em = getEntityManager()) {
            return em.find(User.class, userId);
        }
    }

    public User findByEmail(String email) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Si aucun utilisateur n'est trouvé
        }
    }


    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close(); // Ferme l'EntityManagerFactory si nécessaire
        }
    }
}
