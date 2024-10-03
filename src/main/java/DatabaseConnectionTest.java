import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("namePersistence");
            em = emf.createEntityManager();
            em.getTransaction().begin();
            // Essayer d'exécuter une requête simple
            em.createQuery("SELECT 1").getSingleResult();
            System.out.println("Connexion à la base de données réussie !");
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
