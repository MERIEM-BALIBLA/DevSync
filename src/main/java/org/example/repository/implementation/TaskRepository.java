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

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }



/*
    public Task save(Task task) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (task.getTags() != null) {
                for (int i = 0; i < task.getTags().size(); i++) {
                    Tag tag = task.getTags().get(i);
                    if (tag.getId() != 0) {
                        task.getTags().set(i, em.merge(tag));
                    } else {
                        em.persist(tag);
                    }
                }
            }
            em.persist(task);
            em.getTransaction().commit();
        }catch (Exception e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }finally {
            em.close();
        }
        return task;
    }
*/

    // Insert new task into the database
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

    // Insert a new tag or merge an existing one
    public Tag insertTag(Tag tag) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            if (tag.getId() == 0) {
                // Persist new tags (ID is 0 or not set)
                em.persist(tag);
            } else {
                // Merge existing tags (ID is non-zero)
                tag = em.merge(tag);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
        return tag;
    }

    // Merge existing tag
    public Tag mergeTag(Tag tag) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Tag managedTag = em.merge(tag); // Merging the detached entity
            em.getTransaction().commit();
            return managedTag;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Error merging tag", e);
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
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Task task = em.find(Task.class, task_id);
            if (task != null) {
                em.remove(task); // Les associations dans task_tags seront supprimées
            } else {
                System.out.println("Tâche non trouvée avec l'ID : " + task_id);
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


/*
    public void deleteTask(int task_id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Task task = em.find(Task.class, task_id);
        if (task != null) {
            em.remove(task);
        }
        em.getTransaction().commit();
        em.close();
    }
*/

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

    /* public Task findById(int id) {
         try (EntityManager em = getEntityManager()) {
             return em.find(Task.class, id);
         }
     }*/
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
