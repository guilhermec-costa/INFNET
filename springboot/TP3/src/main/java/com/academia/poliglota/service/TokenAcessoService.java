package com.academia.poliglota.service;

import com.academia.poliglota.dto.TokenAcessoResponse;
import com.academia.poliglota.dto.TokenValidacaoResponse;
import com.academia.poliglota.repository.AlunoRepository;
import java.time.Duration;
import java.util.UUID;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TokenAcessoService {

    private static final Duration TOKEN_TTL = Duration.ofMinutes(5);
    private static final String TOKEN_PREFIX = "catraca:token:";

    private final RedisTemplate<String, String> redisTemplate;
    private final AlunoRepository alunoRepository;

    public TokenAcessoService(RedisTemplate<String, String> redisTemplate, AlunoRepository alunoRepository) {
        this.redisTemplate = redisTemplate;
        this.alunoRepository = alunoRepository;
    }

    public TokenAcessoResponse gerarToken(Long alunoId) {
        if (!alunoRepository.existsById(alunoId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }

        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(chave(token), alunoId.toString(), TOKEN_TTL);

        return new TokenAcessoResponse(alunoId, token, TOKEN_TTL.getSeconds());
    }

    public TokenValidacaoResponse validarToken(String token) {
        Boolean existe = redisTemplate.hasKey(chave(token));
        return new TokenValidacaoResponse(token, Boolean.TRUE.equals(existe));
    }

    private String chave(String token) {
        return TOKEN_PREFIX + token;
    }
}
