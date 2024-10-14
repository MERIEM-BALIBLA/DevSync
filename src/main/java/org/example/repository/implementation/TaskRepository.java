package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.model.Tag;
import org.example.model.Task;
import org.example.model.User;
import org.example.repository.interfaces.TaskInterface;

import java.util.List;

public class TaskRepository implements TaskInterface {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJPAUnit");

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public Task insertTask(Task task) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(task);  // Persisting the task object
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        return task;
    }

    public List<Task> getAllTask() {
        EntityManager em = getEntityManager();
        TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t ORDER BY t.id desc ", Task.class);
        return query.getResultList();
    }

    public List<Task> getTasksByUserId(int userId) {
        EntityManager em = getEntityManager();
        List<Task> tasks;

        try {
            em.getTransaction().begin();
            TypedQuery<Task> query = em.createQuery(
                    "SELECT t FROM Task t WHERE t.assignedUser.id = :userId", Task.class);
            query.setParameter("userId", userId);
            tasks = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }

        return tasks;
    }

    public Task findById(int id) {
        EntityManager em = getEntityManager();
        try {
            Task task = em.find(Task.class, id);
            // Initialisation explicite des tags
            if (task != null) {
                task.getTags().size();  // Accéder à la collection pour forcer l'initialisation
            }
            return task;
        } finally {
            em.close();
        }
    }

    public Task updateTask(Task task) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        return task;
    }

    public void deleteTask(long userId) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Task user = em.find(Task.class, userId);

        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Task updateStatus(Task task) {
        Task editedTask = null;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Task foundTask = em.find(Task.class, task.getId());
            if (foundTask != null) {
                foundTask.setCompleted(task.isCompleted());
                editedTask = em.merge(foundTask);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return editedTask;
    }


}
