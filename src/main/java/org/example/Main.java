package org.example;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        // Créer une SessionFactory à partir de la configuration
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            System.out.println("Connexion à la base de données réussie !");
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Échec de la connexion à la base de données.");
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}