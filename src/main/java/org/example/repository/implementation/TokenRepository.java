package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.Token;

public class TokenRepository {
    private final EntityManager em;

    public TokenRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        em = emf.createEntityManager();
    }

    public void createToken(Token token) {
        em.getTransaction().begin();
        em.persist(token);
        em.getTransaction().commit();
    }

}
