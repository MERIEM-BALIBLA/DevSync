package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.model.Tag;
import org.example.model.Task;
import org.example.repository.interfaces.TaskInterface;

import java.util.List;

public class TaskRepository implements TaskInterface {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJPAUnit");
    private final TagRepository tagRepository;

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
    public TaskRepository() {
        tagRepository = new TagRepository();
    }

    public Task insertTask(Task task) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();
            return task;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'insertion de la tâche", e);
        } finally {
            em.close();
        }
    }

    public List<Task> getAllTask() {
        EntityManager em = getEntityManager();
        TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t ORDER BY t.id desc ", Task.class);
        return query.getResultList();
    }

    public void deleteTask(int task_id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Task task = em.find(Task.class, task_id);
        if (task != null) {
            em.remove(task);
        }
        em.getTransaction().commit();
        em.close();
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
        try (EntityManager em = getEntityManager()) {
            return em.find(Task.class, id);
        }
    }

    public Task updateTask(Task task) {
        Task editedTask = null;
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Task foundTask = em.find(Task.class, task.getId());
            if (foundTask != null) {
                foundTask.setTitle(task.getTitle());
                foundTask.setDescription(task.getDescription());
                foundTask.setDate(task.getEndDate());
                foundTask.setAssignedUser(task.getAssignedUser());

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
