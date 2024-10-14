package org.example.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.model.Request;
import org.example.model.Task;
import org.example.model.Token;
import org.example.model.User;

import java.util.List;

public class TokenRepository {
    private final EntityManager em;

    public TokenRepository() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
        em = emf.createEntityManager();
    }

    public Token save(Token token) {
        em.getTransaction().begin();
        em.persist(token);
        em.getTransaction().commit();
        return token;
    }

    public Token update(Token token) {
        em.getTransaction().begin();
        em.merge(token);
        em.getTransaction().commit();
        return token;
    }

    public List<Token> getAll() {
        TypedQuery<Token> query = em.createQuery("SELECT t FROM Token t ORDER BY t.id desc ", Token.class);
        return query.getResultList();
    }

   /* public List<Token> findByUser(User user){
        try {
            Token token = em.find(Token.class, user.getId());
            return (List<Token>) token;
        } finally {
            em.close();
        }
    }*/


}
