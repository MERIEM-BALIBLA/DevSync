package org.example.service;

import org.example.model.Token;
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

    public Token update(Token token) {
        return tokenRepository.update(token);
    }

    public List<Token> getAll() {
        return tokenRepository.getAll();
    }
}
