package org.example.service;

import org.example.model.Token;
import org.example.model.User;
import org.example.repository.implementation.TokenRepository;

import java.util.List;

public class TokenService {
    TokenRepository tokenRepository;

    public TokenService() {
        this.tokenRepository = new TokenRepository();
    }

    public Token save(Token token) {
        return tokenRepository.save(token);
    }

    public void update(Token token) {
        tokenRepository.update(token);
    }

    public List<Token> getAll() {
        return tokenRepository.getAll();
    }

    public Token userTokens(User user) {
        return tokenRepository.userTokens(user.getId());
    }


}
